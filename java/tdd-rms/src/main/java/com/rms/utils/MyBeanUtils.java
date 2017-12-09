package com.rms.utils;

import java.lang.reflect.Field;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ReflectionUtils;

public class MyBeanUtils {

	public static void PoToModel2(Object po, Object vo) {
		if (po != null) {
			try {
				BeanUtils.copyProperties(vo, po);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
		}
	}

	public static void ModelToPo2(Object po, Object vo) {
		if (vo != null) {
			try {
				BeanUtils.copyProperties(po, vo);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
		}
	}

	public static void PoToModel(Object po, Object vo) {
		try {
			Field[] fields = po.getClass().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if (fieldName != null) {
					// 通过反射调用getter函数给取得成员变量的值
					Object result = BeanUtils.getProperty(po, fieldName);
					if (result != null) {
						if (result instanceof String) {
							result = ((String) result).trim();
						}
						try {
							BeanUtils.copyProperty(vo, fieldName, result);
						} catch (Exception e1) {}
					}
				}
			}
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
	}

	public static void ModelToPo(Object po, Object vo) {
		try {
			Field[] fields = po.getClass().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if (fieldName != null) {
					// 通过反射调用getter函数给取得成员变量的值
					Object result = BeanUtils.getProperty(vo, fieldName);
					if (result != null) {
						if (result instanceof String) {
							result = ((String) result).trim();
						}
						try {
							BeanUtils.copyProperty(po, fieldName, result);
						} catch (IllegalArgumentException e1) {}
					}
				}
			}
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
	}

	private static String getGetMethodName(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	private static String getSetMethodName(String field) {
		return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}
}
