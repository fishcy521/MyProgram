package com.rms.commons.global;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rms.modules.utils.ConvertUtil;

public class RmsQuerySqlGlobal {

	protected Log						log			= LogFactory.getLog(this.getClass());

	private static RmsQuerySqlGlobal	instance	= new RmsQuerySqlGlobal();

	private List<QuerySqlBean>			sqlBeanList	= new ArrayList<QuerySqlBean>();		;

	private Map<String, QuerySqlBean>	sqlBeanMap	= new HashMap<String, QuerySqlBean>();

	// Properties properties = new Properties();
	// properties.load(fis);
	public RmsQuerySqlGlobal () {

		reloadQuerySqlFile();
	}

	public static RmsQuerySqlGlobal getInstance () {

		return instance;
	}

	public List<QuerySqlBean> getSqlBeanList () {

		return sqlBeanList;
	}

	public void reloadQuerySqlFile () {

		String initFile = RmsQuerySqlGlobal.class.getResource("").getPath();
		initFile = initFile.substring(0, initFile.lastIndexOf("classes")) + "classes/rms_sql.xml";
		log.info("PmsQuerySqlGlobal Reload XML File=" + initFile);
		try {
			// initFile = URLDecoder.decode(initFile, "UTF-8");
			// FileInputStream fis = new FileInputStream(new File(initFile));
			InputStream is = RmsQuerySqlGlobal.class.getClassLoader().getResourceAsStream("rms_sql.xml");
			sqlBeanList = new QuerySqlService().getQuerySqlList(is);
			if (!ConvertUtil.isNull(sqlBeanList)) {
				for (QuerySqlBean obj : sqlBeanList) {
					sqlBeanMap.put(obj.getId(), obj);
				}
			}
			// fis.close();
			is.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getQuerySqlById (String id) {

		if (RmsGlobal.RELOAD_QUERY_XML_SQLSTRING) {
			reloadQuerySqlFile();
		}
		QuerySqlBean obj = sqlBeanMap.get(id);
		if (obj != null) {
			return obj.getValue();
		}
		return "";
	}

	public Map<String, QuerySqlBean> getSqlBeanMap () {

		return sqlBeanMap;
	}

}
