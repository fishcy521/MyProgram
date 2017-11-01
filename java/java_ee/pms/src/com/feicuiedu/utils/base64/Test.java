package com.feicuiedu.utils.base64;


public class Test {

	public static void main(String[] args) {

		String content = "AES����һ�β���skdfjl;askfdj;werjf;werf;lergs;lrgj;lsrgj;rthr;lhr;hytjhyhklyhrt;hjrh;ljyyie5iy5o4i;hy;rlhtdglkrtgjl;trgjtgjlrkgjl;ergj;wergj;lgjl;rtgjrtl;hjr;lh;hejk����klfjsl;gjt;lgtjrh;ltryl;efjkwe;lfj;erlgjre;lgjerw;gljertg;ljetr;lgr;ghtlk;gjtrk;ljt;ljet;ljwe;lrgjl;rgj;klwergjwergkl;rtj;elrgjerl;gjwerl;gjerwl;gwjlskafjkljfasflasfֱ��ѡ�����ڽ���Ԥ����֧�������ǵĹ�����ԱԤ�����Ժ󣬻��Զ��ŵķ�ʽ��֪���Ʊ��ȡƱ��ʽ���ؼ��ֵĲ��뷽ʽ��������Ԥ��������������ο�������ǰ�ھ�����www.jowong.com��ƽ̨��Ԥ����Ʊ�������ֳ���Ʊ������ǰһ����16:00--20��00����Ʊ������Ʊ���ڵ���7��00������Ʊ������Ʊ������AES����һ�β���skdfjl;askfdj;werjf;werf;lergs;lrgj;lsrgj;rthr;lhr;hytjhyhklyhrt;hjrh;ljyyie5iy5o4i;hy;rlhtdglkrtgjl;trgjtgjlrkgjl;ergj;wergj;lgjl;rtgjrtl;hjr;lh;hejk����klfjsl;gjt;lgtjrh;ltryl;efjkwe;lfj;erlgjre;lgjerw;gljertg;ljetr;lgr;ghtlk;gjtrk;ljt;ljet;ljwe;lrgjl;rgj;klwergjwergkl;rtj;elrgjerl;gjwerl;gjerwl;gwjlskafjkljfasflasfֱ��ѡ�����ڽ���Ԥ����֧�������ǵĹ�����ԱԤ�����Ժ󣬻��Զ��ŵķ�ʽ��֪���Ʊ��ȡƱ��ʽ���ؼ��ֵĲ��뷽ʽ��������Ԥ��������������ο�������ǰ�ھ�����www.jowong.com��ƽ̨��Ԥ����Ʊ�������ֳ���Ʊ������ǰһ����16:00--20��00����Ʊ������Ʊ���ڵ���7��00������Ʊ������Ʊ������AES����һ�β���skdfjl;askfdj;werjf;werf;lergs;lrgj;lsrgj;rthr;lhr;hytjhyhklyhrt;hjrh;ljyyie5iy5o4i;hy;rlhtdglkrtgjl;trgjtgjlrkgjl;ergj;wergj;lgjl;rtgjrtl;hjr;lh;hejk����klfjsl;gjt;lgtjrh;ltryl;efjkwe;lfj;erlgjre;lgjerw;gljertg;ljetr;lgr;ghtlk;gjtrk;ljt;ljet;ljwe;lrgjl;rgj;klwergjwergkl;rtj;elrgjerl;gjwerl;gjerwl;gwjlskafjkljfasflasfֱ��ѡ�����ڽ���Ԥ����֧�������ǵĹ�����ԱԤ�����Ժ󣬻��Զ��ŵķ�ʽ��֪���Ʊ��ȡƱ��ʽ���ؼ��ֵĲ��뷽ʽ��������Ԥ��������������ο�������ǰ�ھ�����www.jowong.com��ƽ̨��Ԥ����Ʊ�������ֳ���Ʊ������ǰһ����16:00--20��00����Ʊ������Ʊ���ڵ���7��00������Ʊ������Ʊ������AES����һ�β���skdfjl;askfdj;werjf;werf;lergs;lrgj;lsrgj;rthr;lhr;hytjhyhklyhrt;hjrh;ljyyie5iy5o4i;hy;rlhtdglkrtgjl;trgjtgjlrkgjl;ergj;wergj;lgjl;rtgjrtl;hjr;lh;hejk����klfjsl;gjt;lgtjrh;ltryl;efjkwe;lfj;erlgjre;lgjerw;gljertg;ljetr;lgr;ghtlk;gjtrk;ljt;ljet;ljwe;lrgjl;rgj;klwergjwergkl;rtj;elrgjerl;gjwerl;gjerwl;gwjlskafjkljfasflasfֱ��ѡ�����ڽ���Ԥ����֧�������ǵĹ�����ԱԤ�����Ժ󣬻��Զ��ŵķ�ʽ��֪���Ʊ��ȡƱ��ʽ���ؼ��ֵĲ��뷽ʽ��������Ԥ��������������ο�������ǰ�ھ�����www.jowong.com��ƽ̨��Ԥ����Ʊ�������ֳ���Ʊ������ǰһ����16:00--20��00����Ʊ������Ʊ���ڵ���7��00������Ʊ������Ʊ������";
		String skey = "admin";
		try {
			//����
			byte[] encryptResultStr = BackAES.encrypt(content, skey, 0);

			System.out.println("����-���ܺ�"+new String(encryptResultStr));
			String decryptString=BackAES.decrypt(new String(encryptResultStr),
					skey, 0);
			System.out.println("����-���ܺ�"+decryptString);
			
			/**
			 * String parseByte2HexStr(byte buf[]) //**��������ת����16����
			 *byte[] parseHexStr2Byte(String hexStr) //java��16����ת��Ϊ������
			 */
			
			
//			String skey2 = "admin";
//			byte[] encryptResultStr2 = BackAES.newencrypt(content, skey2);
//		
//			//System.out.println("���������ܺ�"+new String(encryptResultStr2));
//			////.java��2��������ת����16����parseHexStr2Byte
//			String toByteString=BackAES.parseByte2HexStr(encryptResultStr2);//java��16����ת��Ϊ������
//			System.out.println("���������ܺ�ת��16���ƣ�"+toByteString);
//			
//			
//			// /**��16����ת����2����
//
//			byte[] ascToByte=BackAES.parseHexStr2Byte(toByteString);
//			System.out.println("�����������ܺ�ת�ɶ����ƣ�"+new String(ascToByte));
//			
//			byte[] decryptString2=BackAES.newdecrypt(ascToByte,skey2);
//			System.out.println("���������ܺ�"+new String(decryptString2));
//			
		
			
			
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		// String result1 = Base64.encode(encryptResultStr, chartCode);
		// System.out.println("Base64ת���" + result1);
		//
		// System.out.println("============================================");
		// String result2 = Base64.decode(result1, chartCode);
		// System.out.println("Base64�����" + result2);

	}
}
