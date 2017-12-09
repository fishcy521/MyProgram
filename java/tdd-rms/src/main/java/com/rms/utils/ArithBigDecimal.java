package com.rms.utils;


import java.math.BigDecimal;   


	  
	  
	  
	/** *//**  
	 
	* 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精  
	 
	* 确的浮点数运算，包括加减乘除和四舍五入。  
	 
	*/  
	  
	public class ArithBigDecimal{   
	  
	  
	  
	//默认除法运算精度   
	  
	private static final int DEF_DIV_SCALE = 8;   
	
	//默认数字的长度。即（整数位,小数位位数之和）
	private static final int DEF_NUMBER_LENGTH = 15;
	
	
	
	  
	  
	  
	//这个类不能实例化   
	  
	private ArithBigDecimal(){   
	  
	}   
	  
	  
	  
	  
	  
	/** *//**  
	 
	* 提供精确的加法运算。  
	 
	* @param v1 被加数  
	 
	* @param v2 加数  
	 
	* @return 两个参数的和  
	 
	*/  
	  
	public static Double add(Double v1,Double v2){   
		if(v1==null){
			v1=0d;
		}
		if(v2==null){
			v2=0d;
		}
	BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	  
	BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	  
	return b1.add(b2).doubleValue();   
	  
	}   
	  
	  
	  
	/** *//**  
	 
	* 提供精确的减法运算。  
	 
	* @param v1 被减数  
	 
	* @param v2 减数  
	 
	* @return 两个参数的差  
	 
	*/  
	  
	public static Double sub(Double v1,Double v2){   
		if(v1==null){
			v1=0d;
		}
		if(v2==null){
			v2=0d;
		}
	BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	  
	BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	  
	return b1.subtract(b2).doubleValue();   
	  
	}   
	  
	  
	  
	/** *//**  
	 
	* 提供精确的乘法运算。  
	 
	* @param v1 被乘数  
	 
	* @param v2 乘数  
	 
	* @return 两个参数的积  
	 
	*/  
	  
	public static Double mul(Double v1,Double v2){   
		if(v1==null){
			v1=0d;
		}
		if(v2==null){
			v2=0d;
		}
	BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	  
	BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	  
	return b1.multiply(b2).doubleValue();   
	  
	}   
	  
	  
	  
	/** *//**  
	 
	* 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到  
	 
	* 小数点以后10位，以后的数字四舍五入。  
	 
	* @param v1 被除数  
	 
	* @param v2 除数  
	 
	* @return 两个参数的商  
	 
	*/  
	  
	public static Double div(Double v1,Double v2){   
		if(v1==null){
			v1=0d;
		}
		if(v2==null){
			v2=0d;
		}
	return div(v1,v2,DEF_DIV_SCALE);   
	  
	}   
	  
	  
	  
	/** *//**  
	 
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	 
	* 定精度，以后的数字四舍五入。  
	 
	* @param v1 被除数  
	 
	* @param v2 除数  
	 
	* @param scale 表示表示需要精确到小数点以后几位。  
	 
	* @return 两个参数的商  
	 
	*/  
	  
	public static Double div(Double v1,Double v2,int scale){   
		if(v1==null){
			v1=0d;
		}
		if(v2==null){
			v2=0d;
		}
	if(scale<0){   
	  
	throw new IllegalArgumentException(   
	  
	"The scale must be a positive integer or zero");   
	  
	}   
	  
	BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	  
	BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	  
	return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
	  
	}   
	  
	  
	  
	/** *//**  
	 
	* 提供精确的小数位四舍五入处理。  
	 
	* @param v 需要四舍五入的数字  
	 
	* @param scale 小数点后保留几位  
	 
	* @return 四舍五入后的结果  
	 
	*/  
	  
	public static Double round(Double v1,int scale){   
		if(v1==null){
			v1=0d;
		}
	if(scale<0){   
	  
	throw new IllegalArgumentException(   
	  
	"The scale must be a positive integer or zero");   
	  
	}   
	  
	BigDecimal b = new BigDecimal(Double.toString(v1));   
	  
	BigDecimal one = new BigDecimal("1");   
	  
	return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
	  
	}  
	
	/** *//**  
	  用来与ERP计算精度保持一致
	* 提供（相对）精确的除法运算，当发生除不尽的情况时，小数位
	* 的保留位数取决于整数位（模拟ERP）
	* 1.如果整数位为0，则小数位为保留DEF_NUMBER_LENGTH = 15 位
	* 2.如果整数位不为0，则小数位保留（DEF_NUMBER_LENGTH - 整数位位数）位

	* @param v1 被除数  
	 
	* @param v2 除数  
	 
	* @return 两个参数的商  
	 
	*/  
	public static Double formateDivNumber(Double v1,Double v2){
		if(v1==null){
			v1=0d;
		}
		if(v2==null){
			v2=0d;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));   
		BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
		Double divResult = b1.divide(b2,DEF_NUMBER_LENGTH,BigDecimal.ROUND_HALF_UP).doubleValue();
	    String divResultStr = String.valueOf(divResult);
	    if(divResultStr.indexOf(".")==-1){
	    	return divResult;
	    }else{
	    	String roundResStr = divResultStr.substring(0, divResultStr.indexOf("."));//整数位
	    	//如果整数位为0，则仍然保留DEF_NUMBER_LENGTH位数
	    	if("0".equalsIgnoreCase(roundResStr)){
	    		return divResult;
	    	}
	    	//如果整数位不为0，则保留（DEF_NUMBER_LENGTH-整数位位数）的小数位
	    	else{
	    		divResult = b1.divide(b2,(DEF_NUMBER_LENGTH-roundResStr.length()),BigDecimal.ROUND_HALF_UP).doubleValue();
	    		return divResult;
	    	}
	    } 
	}
	  
	}  


