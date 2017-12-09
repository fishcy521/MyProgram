package com.rms.tdd.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.rms.service.tdd.InterfaceManager;
import com.rms.tdd.TestParent;

public class TestJson extends TestParent {

	@Autowired
	private InterfaceManager	interfaceManager;

	public static void main(String [] args) {

		InputStream is = TestJson.class.getClassLoader().getResourceAsStream("json.txt");

		StringBuilder sbResult = new StringBuilder();
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			while (true) {
				String str = br.readLine();
				if (StringUtils.isEmpty(str)) {
					break;
				}
				sbResult.append(str);
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sbResult=" + sbResult);

		/*JSONObject jsonObj = JSONObject.fromObject(sbResult.toString());
		String size = jsonObj.getString("size");
		JSONArray jsonArray = JSONArray.fromObject(size);
		for (int i = 0; i < jsonArray.size(); i ++) {
			JSONObject tmp = jsonArray.getJSONObject(i);
			System.out.println(tmp.getString("width")+">>"+tmp.getString("height"));
        }*/
		//System.out.println("size=" + size);
		
		JSONObject jsonObj = JSONObject.fromObject(sbResult.toString());
		String rtnMsg = jsonObj.getString("rtnMsg");
		String rtnType = jsonObj.getString("rtnType");
		System.out.println("rtnMsg="+rtnMsg+"||rtnType="+rtnType);
		String result = jsonObj.getString("result");
		//JSONObject jsonObj
		
	}

	public void testJson1() {

	}
}
