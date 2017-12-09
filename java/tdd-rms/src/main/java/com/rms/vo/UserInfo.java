package com.rms.vo;

public class UserInfo {

	// 分部
	private String subCompanyName;
	
    // 部门
	private String partCompanyName;
	
	// 编号
	private String workCode;
	
	// 登录名
	private String loginId;
	
	// 姓名
	private String lastName;
	
	// 密码
	private String passwd;
	
	// 性别
	private String sex;
	
	// 安全界别
	private String secLevel;
	
	// 职务
	private String jobActivitiesName;
	
	// 岗位
	private String jobTitles;
	
	// 职务类型
	private String  jobGroups; 
	
	// 职称
	private String jobCall;
	
	public String getSubCompanyName() {
		return subCompanyName;
	}
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}
	public String getPartCompanyName() {
		return partCompanyName;
	}
	public void setPartCompanyName(String partCompanyName) {
		this.partCompanyName = partCompanyName;
	}
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSecLevel() {
		return secLevel;
	}
	public void setSecLevel(String secLevel) {
		this.secLevel = secLevel;
	}
	public String getJobActivitiesName() {
		return jobActivitiesName;
	}
	public void setJobActivitiesName(String jobActivitiesName) {
		this.jobActivitiesName = jobActivitiesName;
	}
	public String getJobTitles() {
		return jobTitles;
	}
	public void setJobTitles(String jobTitles) {
		this.jobTitles = jobTitles;
	}
	public String getJobGroups() {
		return jobGroups;
	}
	public void setJobGroups(String jobGroups) {
		this.jobGroups = jobGroups;
	}
	public String getJobCall() {
		return jobCall;
	}
	public void setJobCall(String jobCall) {
		this.jobCall = jobCall;
	}
	public String getManagerStr() {
		return managerStr;
	}
	public void setManagerStr(String managerStr) {
		this.managerStr = managerStr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	// 直接上级 这里用胸牌号，也就是登录id loginid
	private String managerStr;
	//	状态
	//	0：试用
	//	1：正式
	//	2：临时
	//	3：试用延期
	//	4：解聘
	//	5：离职
	//	6：退休
	//	7：无效
	private String status;
	// 办公地点
	//private String 
}
