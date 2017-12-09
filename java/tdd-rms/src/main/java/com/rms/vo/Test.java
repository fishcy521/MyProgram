package com.rms.vo;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.rms.utils.DESPlus;
import com.rms.utils.DESUtils;


public class Test {

	public static void main (String [] args) throws Exception {

		DESUtils crypt = new DESUtils (DESPlus.AES_MICHI_MIN);
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put ("position", "1");
		
		String java_str= JSONObject.fromObject (paramMap).toString ();
		java_str="{\"birthday\":\"1990-02-01\",\"post\":\"3\",\"portraitBig\":\"http://42.62.114.249:9988/files/test/20150821/786923892773303350_50.png\",\"profession\":\"男高音\",\"portraitMiddle\":\"http://42.62.114.249:9988/files/test/20150821/7869238927733033100_100.png\",\"autograph\":\"\",\"userId\":\"124263\",\"gender\":\"1\",\"portraitSmall\":\"http://42.62.114.249:9988/files/test/20150821/786923892773303350_50.png\",\"placeOrigin2\":\"2\",\"realName\":\"帕瓦罗蒂\",\"placeOrigin1\":\"1\",\"schoolId\":\"211\"}";
		//String java_str="{\"position\":\"1\"}";
		System.out.println ("java_str="+java_str);
		
		/*System.out.println ("1="+crypt.encrypt(java_str));
		System.out.println ("2="+crypt.encrypt(crypt.encrypt(java_str)));
		System.out.println ("3="+crypt.encrypt (crypt.encrypt (crypt.encrypt (java_str))));*/
		String java_en = crypt.encrypt (crypt.encrypt (crypt.encrypt (java_str)));
		System.out.println ("java_en="+java_en);
		
		String php_str = "{region2:'247'}";
		String php_encrypt = crypt.encrypt (crypt.encrypt (crypt.encrypt (php_str)));
		System.out.println ("php_encrypt="+php_encrypt);
		
		System.out.println ("de_java="+crypt.decrypt (crypt.decrypt (crypt.decrypt (java_en))));
		System.out.println ("de_php="+crypt.decrypt (crypt.decrypt (crypt.decrypt (php_encrypt))));
		
		/*JSONObject jsonObj_java = JSONObject.fromObject(crypt.decrypt (crypt.decrypt (crypt.decrypt (java_en))));
		System.out.println ("java_="+jsonObj_java.get ("position"));
		
		JSONObject jsonObj_php = JSONObject.fromObject(crypt.decrypt (crypt.decrypt (crypt.decrypt (php_encrypt))));
		System.out.println ("php_="+jsonObj_php.get ("position"));*/
		
		
		System.out.println (">>>>>>>>>>>>测试解密");
		String passStr = "XpTil7CZYRk085uD79xpQoZ6sLJ+4ZMzFIIyV1MGXhajmaSQn7mxQuAIC18oGynoHTbCW43r3eCfE/y17te4c6HOD8WlbnhU+GUk41gEViZuyN8xczUDqCpZ0eKuNsURzlFJD4skpuZetoifnROykn6zUClJsTxX0EqH4ppFPqiUVVqYIHVD15GoYoK3mRBBAevYl21C6HaPxiVlQRqSTYUAoo3X9r2jYOgMEZWYQONM4dquVcL13ErpcYV96oG8Eb2hrNT3VnI5Vhzg9kGVpt5ACOKtqpQ1HYTsMX04mCFcnIg1KCkl56315QjaOS0k2Vkks7LqjRYeM7AFWdvNIKixt6S+h5nvSD1DGLY9KCGmMRHLg9KlV37RnEa7asBNOc7peT2vczZFpbiLTbB+4zh8+bvaRaGcfiVUyc41eHZ12WH/1KX7JtrpilzMITJYYkkigh1eq+pN/O+Qcuamb/gFKYAnVabT9Y26ZgNRTHDvFaJ760enmPFBbXp9E3BmfYyoopySqEUS4ZPP0T07wHbG5r7Dn+waUZthOCIvMZ4KwvfBmz08zRkFOceVAbN+sg5gblJQmhorenXyZVV2ZVMvvY/kbLsAZnRjAyyFxbeLCFCu3n+sCn+qWi8fVfMxaNN2ZEkIORH4G/Bc5o8esjmCmk4JSPp5CTVIUVl03j0PwHZ/o/0rJx2wI53GUQMwULlK4NLnDwUZlSpiqUyMuoTqgsLikR6mY7x824oJjmrSydHwHuEFErBMBzUYeb1LOOeb0Gzsxy0o5HaNgs6zeC8o0C/2c0jCdT5Cp8oSQnfzyaWW3IYG17AcjvB06pErhvB3VGLs6mdp5TlB9UVG7TDlqmNLyQctk1jlJlQnpeoy8kJvSKz3k1UiDnwapzBa6xWr3xOK6iMYLlwIOPK2ufH40RbIptDMmarwzvsFrewUT1cQg01+occJeh6kUdlC5ZH20oGMq/Sbk+HOvmBL9PF9mm6RECLj4BHYIr08YCvyMBbz76UqNcbIb9S1ig2AWa5eFCW9e6+oEBpYB0vdNAnT8WZXgUivSgwakexorN+7eZMeRCRwB089ArDnw6czmNOk3FPtErWLQYM2p883pWasO1EU4QH92f3aWrDPg5XjIgTwNk6zTw==";
		System.out.println ("加密后的字串="+passStr);
		System.out.println ("解密后的字串="+crypt.decrypt (crypt.decrypt (crypt.decrypt (passStr))));
		/*String str1 = "{'value':'Ks9tUheIjYbFEeCFLWL9WNFPS5JSk9MESqpEAknwqZnNHtFZAfxxhRpF9y8m6q8KWYxkahsXuOgwXmtJOp/g=='}";
		String ss = (String) (JSONObject.fromObject (str1)).get("value");
		System.out.println ("ss="+ss);
		System.out.println (crypt.decrypt (crypt.decrypt (crypt.decrypt (ss))));
		String str2 = crypt.encrypt (crypt.encrypt (crypt.encrypt ("加密发生错误")));
		System.out.println ("str2="+str2);*/
		/*String str2 = crypt.encrypt (crypt.encrypt (crypt.encrypt (jsonParam)));
		System.out.println (str1.equals (str2));
		System.out.println (crypt.decrypt (crypt.decrypt (crypt.decrypt (str1))));
		System.out.println (crypt.decrypt (crypt.decrypt (crypt.decrypt (str2))));*/
	}
}
