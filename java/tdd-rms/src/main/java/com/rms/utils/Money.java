package com.rms.utils;

public class Money {
	public static String getNumberToRMB(double m) {
		String num = String.valueOf(m);
		return getNumberToRMB(num);
	}

	public static String getNumberToRMB(String m) {
		String num = "零壹贰叁肆伍陆柒捌玖";
		String dw = "圆拾佰仟万亿";
		String result = "元";

		String mm[] = null;
		mm = m.split("\\.");
		if (mm != null && mm.length == 2) {
			String money = mm[0];
			String tmpNum = mm[1];
			if (tmpNum != null && !"".equals(tmpNum)) {
				if (tmpNum.length() == 1) {
					result += num.charAt(Integer.parseInt("" + mm[1].charAt(0))) + "角";
				} else if (tmpNum.length() == 2) {
					result += num.charAt(Integer.parseInt("" + mm[1].charAt(0))) + "角" + num.charAt(Integer.parseInt("" + mm[1].charAt(1))) + "分";
				}
			}
			if (money != null && !"".equals(money)) {
				for (int i = 0; i < money.length(); i++) {
					String str = "";
					int n = Integer.parseInt(money.substring(money.length() - i - 1, money.length() - i));
					str = str + num.charAt(n);
					if (i == 0) {
						str = str + dw.charAt(i);
					} else if ((i + 4) % 8 == 0) {
						str = str + dw.charAt(4);
					} else if (i % 8 == 0) {
						str = str + dw.charAt(5);
					} else {
						str = str + dw.charAt(i % 4);
					}
					result = str + result;
				}
			}
			result = result.replaceAll("零([^亿万圆角分])", "零");
			result = result.replaceAll("亿零+万", "亿零");
			result = result.replaceAll("零+", "零");
			result = result.replaceAll("零([亿万圆])", "");
			result = result.replaceAll("壹拾", "拾");
		}
		if ("元".equals(result)) {
			return "";
		} else
			return result;
	}
}