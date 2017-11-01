import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/12.
 */
public class FileCreate {
	
	final static String DOC_FIEL_EXTEND = ".doc";
	final static String CONFIG_FILE_NAME = "config.properties";
	final static String CONFIG_FILE_KEY_USERNAME = "userName";
	final static String CONFIG_FILE_KEY_HOLIDAYS = "holidays";
	final static String COMMA = ",";
	final static String PARENT_FILE_PATH = "F:\\file\\";
	final static String DATE_FORMATE = "yyyy-MM-dd";
	
	public static void main(String[] args) {
		String strDate = "2017-04-12";
		String endDate = "2017-05-30";
		
		
		FileCreate fileCreate = new FileCreate();
		
		List<String> lstUsers = fileCreate.getPropertiesValus(FileCreate.CONFIG_FILE_KEY_USERNAME);
		List<String> lstHolidays = fileCreate.getPropertiesValus(FileCreate.CONFIG_FILE_KEY_HOLIDAYS);
		
		List<String> lstDate = fileCreate.getDataStr(strDate,endDate);
		
		for (String tmpDate :lstDate) {
			
			if (lstHolidays.contains(tmpDate)) {
				continue;
			}
			File file = new File(FileCreate.PARENT_FILE_PATH+tmpDate);
			if (!file.exists()) {
				file.mkdirs();
			}
			
			for (String user:lstUsers) {
				File docFile = new File(file,user+FileCreate.DOC_FIEL_EXTEND);
				if (!docFile.exists()) {
					try {
						docFile.createNewFile();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	/**
	 * @param strBegin yyyy-MM-dd
	 * @param strEnd   yyyy-MM-dd
	 * @return
	 */
	public List<String> getDataStr(String strBegin, String strEnd) {
		
		List<String> litResult = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat(FileCreate.DATE_FORMATE);
		try {
			Date beginDate = sdf.parse(strBegin);
			Date endDate = sdf.parse(strEnd);
			
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(beginDate);
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(endDate);
			
			while (true) {
				if (calBegin.after(calEnd)) {
					break;
				}
				
				if ((calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
						&& (calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)) {
				
					String tempDate = sdf.format(calBegin.getTime());
					
					litResult.add(tempDate);
					System.out.println(tempDate);
				}
				
				calBegin.add(Calendar.DAY_OF_YEAR, 1);
				
			}
			
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		return litResult;
	}
	
	public List<String> getPropertiesValus(String key) {
		
		List<String> lstResult = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(FileCreate.CONFIG_FILE_NAME)));
			
			String strValue = prop.getProperty(key);
			
			lstResult = Arrays.asList(strValue.split(FileCreate.COMMA));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return lstResult;
	}
	
	
}
