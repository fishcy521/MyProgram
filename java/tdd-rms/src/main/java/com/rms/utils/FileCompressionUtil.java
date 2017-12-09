package com.rms.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
//import java.util.zip.CRC32;
//import java.util.zip.CheckedOutputStream;
//import java.util.zip.ZipInputStream;
//import java.util.zip.CRC32;
//import java.util.zip.CheckedInputStream;
//import java.util.zip.CheckedOutputStream;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;
//import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The Class FileCompressionUtil.
 * 
 */

public class FileCompressionUtil {
	private static Logger log = LoggerFactory.getLogger(FileCompressionUtil.class);

	private static final String PATH_SEP = "/";

	public static final int BUFFER = 2048;

	private FileCompressionUtil() {
	}

	/**
	 * Zip files in path.
	 * 
	 * @param zipFileName
	 *            the zip file name
	 * @param filePath
	 *            the file path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	public static void zipFilesInPath(final String zipFileName, final String filePath) throws IOException {
		final FileOutputStream dest = new FileOutputStream(zipFileName);
		final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		out.setEncoding("GBK");
		log.info("zipFileName=" + zipFileName);
		log.info("filePath=" + filePath);
		try {
			byte[] data = new byte[BUFFER];
			final File folder = new File(filePath);
			log.info("folder是否存在：" + folder.exists() + ";path" + folder.getPath());
			final List<String> files = Arrays.asList(folder.list());
			log.info("文件集大小：" + files.size());
			for (String file : files) {
				log.info("filePath=" + (filePath + PATH_SEP + file));
				final FileInputStream fi = new FileInputStream(filePath + PATH_SEP + file);
				final BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
				out.putNextEntry(new ZipEntry(file));
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
				fi.close();

			}
		} finally {
			out.close();

			dest.close();
		}
	}

	// public static void zipFilesInPath(ZipOutputStream zipOutStream, final String filePath) throws IOException {
	// // final FileOutputStream dest = new FileOutputStream(zipFileName);
	// // final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
	// log.info("filePath=" + filePath);
	// try {
	// byte[] data = new byte[BUFFER];
	// final File folder = new File(filePath);
	// log.info("folder是否存在：" + folder.exists() + ";path" + folder.getPath());
	// final List<String> files = Arrays.asList(folder.list());
	// log.info("文件集大小：" + files.size());
	// for (String file : files) {
	// log.info("filePath=" + (filePath + PATH_SEP + file));
	//
	// File tmpFile = new File(filePath + PATH_SEP + file);
	// if (tmpFile.isDirectory()) {// 递归调用
	// zipFilesInPath(zipOutStream, filePath + PATH_SEP + file);
	// } else {
	// final FileInputStream fi = new FileInputStream(tmpFile);
	// final BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
	// zipOutStream.putNextEntry(new ZipEntry(file));
	// int count;
	// while ((count = origin.read(data, 0, BUFFER)) != -1) {
	// zipOutStream.write(data, 0, count);
	// }
	// origin.close();
	// fi.close();
	// }
	// }
	// } finally {
	//
	// }
	// }

	public static void zipInPath(final String zipFileName, final String filePath) throws IOException {
		final FileOutputStream dest = new FileOutputStream(zipFileName);
		final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		try {
			out.setEncoding("GBK");
			log.info("压缩-->开始");
			zip(out, new File(filePath), "");
			log.info("压缩-->结束");
			out.close();
			dest.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			dest.close();
		}
	}

	private static void zip(ZipOutputStream zOut, File file, String base) {
		try {
			// 如果文件句柄是目录
			if (file.isDirectory()) {
				// 获取目录下的文件
				File[] listFiles = file.listFiles();
				// 建立ZIP条目
				zOut.putNextEntry(new ZipEntry(base + "/"));
				log.info("目录名:" + file.getName() + "|加入ZIP条目:" + base + "/");
				base = (base.length() == 0 ? "" : base + "/");
				// 遍历目录下文件
				for (int i = 0; i < listFiles.length; i++) {
					// 递归进入本方法
					zip(zOut, listFiles[i], base + listFiles[i].getName());
				}
			}
			// 如果文件句柄是文件
			else {
				if (base == "") {
					base = file.getName();
				}
				// 填入文件句柄
				zOut.putNextEntry(new ZipEntry(base));
				log.info("文件名:" + file.getName() + "|加入ZIP条目:" + base);

				// 开始压缩
				// 从文件入流读,写入ZIP 出流
				writeFile(zOut, file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void writeFile(ZipOutputStream zOut, File file) throws IOException {
		log.info("开始压缩" + file.getName());
		FileInputStream in = new FileInputStream(file);
		int len;
		while ((len = in.read()) != -1)
			zOut.write(len);
		log.info("压缩结束" + file.getName());
		in.close();
	}
	/**
	 * 
	 * Zip with checksum. CRC32
	 * 
	 * @param zipFileName
	 *            the zip file name
	 * @param folderPath
	 *            the folder path
	 * @return the checksum
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	// public static long zipFilesInPathWithChecksum(final String zipFileName, final String folderPath) throws IOException {
	// final FileOutputStream dest = new FileOutputStream(zipFileName);
	// final CheckedOutputStream checkStream = new CheckedOutputStream(dest, new CRC32());
	// final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(checkStream));
	//
	// try {
	// byte[] data = new byte[BUFFER];
	//
	// final File folder = new File(folderPath);
	//
	// final List<String> files = Arrays.asList(folder.list());
	//
	// for (String file : files) {
	//
	// final FileInputStream fi = new FileInputStream(folderPath + PATH_SEP + file);
	//
	// final BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
	//
	// out.putNextEntry(new ZipEntry(file));
	//
	// int count;
	//
	// while ((count = origin.read(data, 0, BUFFER)) != -1) {
	//
	// out.write(data, 0, count);
	//
	// }
	// origin.close();
	// }
	// } finally {
	// out.close();
	// checkStream.close();
	// dest.flush();
	// dest.close();
	// }
	// return checkStream.getChecksum().getValue();
	// }
	/**
	 * 
	 * Unzip files to path.
	 * 
	 * @param zipFileName
	 *            the zip file name
	 * @param fileExtractPath
	 *            the file extract path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	// public static void unzipFilesToPath(final String zipFileName, final String fileExtractPath) throws IOException {
	// final FileInputStream fis = new FileInputStream(zipFileName);
	// final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
	// try {
	// ZipEntry entry;
	// while ((entry = zis.getNextEntry()) != null) {
	// int count;
	// byte[] data = new byte[BUFFER];
	// final FileOutputStream fos = new FileOutputStream(fileExtractPath + PATH_SEP + entry.getName());
	// final BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
	// while ((count = zis.read(data, 0, BUFFER)) != -1) {
	// dest.write(data, 0, count);
	// }
	// dest.flush();
	// dest.close();
	// }
	// } finally {
	// fis.close();
	// zis.close();
	// }
	// }
	/**
	 * 
	 * Unzip files to path with checksum. CRC32
	 * 
	 * @param zipFileName
	 *            the zip file name
	 * @param fileExtractPath
	 *            the file extract path
	 * @param checksum
	 *            the checksum
	 * @return true, if checksum matches;
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	// public static boolean unzipFilesToPathWithChecksum(final String zipFileName, final String fileExtractPath, final long checksum) throws IOException {
	//
	// boolean checksumMatches = false;
	//
	// final FileInputStream fis = new FileInputStream(zipFileName);
	//
	// final CheckedInputStream checkStream = new CheckedInputStream(fis, new CRC32());
	//
	// final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(checkStream));
	//
	// try {
	//
	// ZipEntry entry = null;
	//
	// while ((entry = zis.getNextEntry()) != null) {
	//
	// int count;
	//
	// byte[] data = new byte[BUFFER];
	//
	// final FileOutputStream fos = new FileOutputStream(fileExtractPath + PATH_SEP + entry.getName());
	//
	// final BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
	//
	// while ((count = zis.read(data, 0, BUFFER)) != -1) {
	//
	// dest.write(data, 0, count);
	//
	// }
	// dest.flush();
	// dest.close();
	// }
	// } finally {
	// zis.close();
	// fis.close();
	// checkStream.close();
	// }
	//
	// if (checkStream.getChecksum().getValue() == checksum) {
	// checksumMatches = true;
	// }
	// return checksumMatches;
	// }
}
