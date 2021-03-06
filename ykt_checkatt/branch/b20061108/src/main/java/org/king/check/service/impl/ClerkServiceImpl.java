/* ============================================================
 * 版权：    king 版权所有 (c) 2006
 * 文件：    org.king.check.service.impl.ClerkServiceImpl.java
 * 创建日期： 2006-6-19 13:31:54
 * 功能：    {具体要实现的功能}
 * 所含类:   {包含的类}
 * 修改记录：
 * 日期                    作者         内容
 * =============================================================
 * 2006-6-19 13:31:54      ljf        创建文件，实现基本功能
 * ============================================================
 */

/**
 * 
 */
package org.king.check.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.king.check.dao.ClerkDAO;
import org.king.check.domain.Tclerkinfo;
import org.king.check.service.ClerkService;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p> ClerkServiceImpl.java </p>
 * <p> {功能说明} </p>
 *
 * <p><a href="ClerkServiceImpl.java.html"><i>查看源代码</i></a></p>  
 *
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-6-19
 * 
 *
 */
public class ClerkServiceImpl implements ClerkService {
	private static final Log log = LogFactory.getLog(ClerkServiceImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	private ClerkDAO clerkDAO;
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	public void setClerkDAO(ClerkDAO clerkDAO) {
		this.clerkDAO = clerkDAO;
	}


	/* (non-Javadoc)
	 * @see org.king.check.service.ClerkService#saveClerk(org.king.check.domain.Clerk)
	 */
	public void saveClerk(Tclerkinfo clerk) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.king.check.service.ClerkService#getClerkByStuNumber(java.lang.String)
	 */
	public Tclerkinfo getClerkByStuNumber(String stuNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.king.check.service.ClerkService#getClerkBycardNumber(java.lang.String)
	 */
	public Tclerkinfo getClerkBycardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.king.check.service.ClerkService#getClerkByClerkName(java.lang.String)
	 */
	public Tclerkinfo getClerkByClerkName(String clerkName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.king.check.service.ClerkService#getClerksByDepartId(java.lang.String)
	 */
	public List getClerksByDepartId(String departId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.king.check.service.ClerkService#updateClerk(org.king.check.domain.Clerk)
	 */
	public void updateClerk(Tclerkinfo clerk) {
		// TODO Auto-generated method stub

	}

	/* hanjiwei modify it 20060918
	 * @see org.king.check.service.ClerkService#queryClerk(java.util.Map)
	 * 
	 */
	public List queryClerk(Map filterMap) {
		StringBuffer sql = new StringBuffer(2048);
		sql.append("SELECT B.CUT_ID as CUT_ID,B.STUEMP_NO AS STUEMPID,B.CUT_NAME AS STUEMPNAME,")
		.append(" C.DEPT_NAME,D.DICT_CAPTION AS IFOFFICE FROM YKT_CK.T_CLERKINFO A ")
		.append(" LEFT JOIN YKT_CUR.T_CIF_CUSTOMER B ON A.CUST_ID=B.CUT_ID")
		.append(" LEFT JOIN (SELECT DICT.DICT_VALUE,DICT.DICT_CAPTION FROM ")
		.append(" YKT_CUR.T_PIF_DICTIONARY DICT WHERE DICT.DICT_NO=1200) D")
		.append(" ON A.IFOFFICE=D.DICT_VALUE LEFT  JOIN YKT_CK.DEPARTMENT C")
		.append(" ON A.DEPT_ID=C.DEPT_ID WHERE 1=1 ");
		//参数处理
		if(filterMap!=null&&!filterMap.isEmpty()){
			String personCode = (String)filterMap.get("personCode");
			if (StringUtils.isNotEmpty(personCode))
				sql.append(" AND B.STUEMP_NO LIKE "+"'%"+personCode+"%'");
			String personName = (String)filterMap.get("personName");
			if (StringUtils.isNotEmpty(personName))
				sql.append(" AND B.CUT_NAME LIKE "+"'%"+personName+"%'");			
			String department = (String)filterMap.get("department");
			if (StringUtils.isNotEmpty(department)&&!department.equals("all"))
				sql.append(" AND A.DEPT_ID = '"+department+"'");
		}
		log.debug("Sql--------------->"+sql.toString());
		List clerkList = jdbcTemplate.queryForList(sql.toString());
		return clerkList;
	}

	/**
	 * 
	 */
	public List queryAvailableClerk(Map filterMap) {
		//查询sql
		String Sql = "SELECT * FROM (SELECT B.CARD,B.PERSON_CODE AS PERSON_CODE,B.PERSON_NAME AS PERSON_NAME,B.PERSON_TYPE,C.DEPT_NAME AS DEPT_NAME,''as is_seat,0 as is_clerk,'否' as is_clerk_title "+
		             "FROM (SELECT * FROM YKT_CK.usr_person A WHERE A.PERSON_CODE NOT IN (SELECT CLERKID FROM YKT_CK.CLERK)) B "+
		             "LEFT JOIN YKT_CK.DEPARTMENT C ON  B.DEPT=C.DEPT_ID) Z";
			 
		String sSqlUnion=" UNION "+
		"SELECT * FROM (SELECT B.CARD,B.PERSON_CODE AS PERSON_CODE,B.PERSON_NAME AS PERSON_NAME,B.PERSON_TYPE,E.DEPT_NAME AS DEPT_NAME,D.DICT_CAPTION AS IS_SEAT,1 as is_clerk,'是' as is_clerk_title "+
		"FROM  (SELECT A.CLERKID AS PERSON_CODE,C.PERSON_NAME,C.DEPT,A.ISSEAT,C.PERSON_TYPE,C.CARD FROM YKT_CK.CLERK A LEFT JOIN YKT_CK.USR_PERSON C ON A.CLERKID=C.PERSON_CODE ) B"+
	      " LEFT JOIN (SELECT DICT.DICT_VALUE,DICT.DICT_CAPTION FROM YKT_CK.COM_DICTIONARY DICT WHERE DICT.DICT_NO=1002) D ON B.ISSEAT=D.DICT_VALUE"+
	      " LEFT JOIN YKT_CK.DEPARTMENT E ON  B.DEPT=E.DEPT_ID) Z"
				;
		String sCod = " WHERE 1=1 ";
		
		//参数处理
		if(filterMap!=null&&!filterMap.isEmpty()){
			String personCode = (String)filterMap.get("personCode");
			if (StringUtils.isNotEmpty(personCode))
				sCod +=" AND Z.PERSON_CODE LIKE "+"'%"+personCode+"%'";
			String personName = (String)filterMap.get("personName");
			if (StringUtils.isNotEmpty(personName))
				sCod +=" AND Z.PERSON_NAME LIKE "+"'%"+personName+"%'";
			String card = (String)filterMap.get("card");
			if (StringUtils.isNotEmpty(card))
				sCod +=" AND Z.CARD LIKE "+"'%"+card+"%'";
			
			String personType = (String)filterMap.get("personType");
			if (StringUtils.isNotEmpty(personType)&&!personType.equals("all"))
				sCod +=" AND Z.PERSON_TYPE = '"+personType+"'";
			
			String department = (String)filterMap.get("department");
			if (StringUtils.isNotEmpty(department)&&!department.equals("all"))
				sCod +=" AND Z.DEPT_NAME = '"+department+"'";
			
		}
	
		Sql = Sql+sCod+sSqlUnion+sCod;
		
		
		List clerkList = jdbcTemplate.queryForList(Sql);
		return clerkList;
	}


	/**
	 * 
	 */
	public int setupClerk(List clerkList) {
		
		int count = 0;
		if(clerkList!=null&&!clerkList.isEmpty()){
			for(int i=0;i<clerkList.size();i++){
				Tclerkinfo clerk = (Tclerkinfo)clerkList.get(i);
				boolean flag = clerkDAO.isExist(clerk.getCustId());
				
				if(!flag){
					clerkDAO.save(clerk);
				}else{
					clerkDAO.update(clerk);
				}
				count ++;
			}
		}
		return count;
	}

	
	
	public void deleteClerk(String clerkId){
		
		if(clerkId!=null&&clerkId!=""){
			clerkDAO.delete(clerkDAO.get(Integer.valueOf(clerkId)));
		}		
	}
	
	public Tclerkinfo get(Serializable id)
	{
		return clerkDAO.get(id);
	}
	
	/**
	 * hanjiwei modify it 20060914
	 */
	public Map getClerkDetailByClerkId(String  clerkId)throws Exception
	{
		Map infoMap=new HashMap();
		
		/*
		 * 得到员工姓名 部门
		 */
		List infoList = null;
		StringBuffer strSql = new StringBuffer(1024);
		strSql.append("SELECT B.CUT_ID as CUT_ID,B.STUEMP_NO AS STUEMPID,B.CUT_NAME AS STUEMPNAME,")
        .append(" A.DELAY_OVERTIME, A.OVERTIME_VALUE, A.TWOREST_OVERTIME, A.HOLIDAY_OVERTIME,A.AHEAD_OVERTIME,")
		.append(" C.DEPT_NAME AS DEPTNAME,C.DEPT_ID AS DEPTID,D.DICT_CAPTION AS IFOFFICE FROM YKT_CK.T_CLERKINFO A ")
		.append(" LEFT JOIN YKT_CUR.T_CIF_CUSTOMER B ON A.CUST_ID=B.CUT_ID")
		.append(" LEFT JOIN (SELECT DICT.DICT_VALUE,DICT.DICT_CAPTION FROM ")
		.append(" YKT_CUR.T_PIF_DICTIONARY DICT WHERE DICT.DICT_NO=1200) D")
		.append(" ON A.IFOFFICE=D.DICT_VALUE LEFT  JOIN YKT_CK.DEPARTMENT C")
		.append(" ON A.DEPT_ID=C.DEPT_ID WHERE B.CUT_ID="+clerkId);
        log.debug("sql:"+strSql.toString());
        infoList=jdbcTemplate.queryForList(strSql.toString());
        
        String deptId="";
        if(!infoList.isEmpty())
        {
        	Map tempMap=(Map)infoList.get(0);
        	deptId=tempMap.get("DEPTID").toString();
        	infoMap.put("custId",tempMap.get("CUT_ID"));
        	infoMap.put("stuempId",tempMap.get("STUEMPID"));
        	infoMap.put("stuempName",tempMap.get("STUEMPNAME"));
        	infoMap.put("deptName",tempMap.get("DEPTNAME"));
        	infoMap.put("deptId",tempMap.get("DEPTID"));
        	infoMap.put("ifOffice",tempMap.get("IFOFFICE"));
        	infoMap.put("aheadOverTime",tempMap.get("AHEAD_OVERTIME"));
        	infoMap.put("delayOverTime",tempMap.get("DELAY_OVERTIME"));
        	infoMap.put("overTime",tempMap.get("OVERTIME_VALUE"));       	
        	infoMap.put("twoReset",tempMap.get("TWOREST_OVERTIME"));
        	infoMap.put("holiday",tempMap.get("HOLIDAY_OVERTIME"));
        }
        /*
         * 得到员工工作制度
         */
        StringBuffer workTimeConf = new StringBuffer(1024);
        workTimeConf.append("select distinct WORKINFO_ID, WORKTIME_ID" )
        .append(" from YKT_CK.T_DEPT_WORKCONFINFO where DEPT_ID='"+deptId+"'");
        infoList=jdbcTemplate.queryForList(workTimeConf.toString());
        
        String workInfo = "";
        String workTime = "";
        if(!infoList.isEmpty())
        {
        	Map tempMap=(Map)infoList.get(0);
        	workInfo = tempMap.get("WORKINFO_ID").toString();
        	workTime = tempMap.get("WORKTIME_ID").toString();
        	infoMap.put("workInfo",tempMap.get("WORKINFO_ID"));
        	infoMap.put("workTime",tempMap.get("WORKTIME_ID"));
        }
        /*
         * 得到员工 工作制度
         */
        StringBuffer workConf = new StringBuffer(1024);
        workConf.append("select WORKCONF_NAME, OVERDUE_TIME, LEAVEEARLY_TIME,")
        .append(" OVER_TIME,DELAY_TIME, CHECK_TIME")
        .append(" from YKT_CK.T_WORKCONFINFO where WORKCONF_ID='"+workInfo+"'");
        infoList=jdbcTemplate.queryForList(workConf.toString());
        if(!infoList.isEmpty())
        {
        	Map tempMap=(Map)infoList.get(0);
        	infoMap.put("wordConfName",tempMap.get("WORKCONF_NAME"));
        	infoMap.put("overdueTime",tempMap.get("OVERDUE_TIME"));
        	infoMap.put("leaveEarlyTime",tempMap.get("LEAVEEARLY_TIME"));
        	infoMap.put("over",tempMap.get("OVER_TIME"));
        	infoMap.put("delay",tempMap.get("DELAY_TIME"));
        	infoMap.put("check",tempMap.get("CHECK_TIME"));
        }
        /*
         * 得到员工 工作时间制度
         */
        StringBuffer timeConf = new StringBuffer(1024);
        timeConf.append("select WORKTIME_NAME, ONTIME1, OFFTIME1, ONTIME2, OFFTIME2, ")
        .append(" ONTIME3, OFFTIME3, ONTIME4, OFFTIME4")
        .append(" from YKT_CK.T_WORKTIMEINTO where WORKTIME_ID='"+workTime+"'");
        infoList=jdbcTemplate.queryForList(timeConf.toString());
        if(!infoList.isEmpty())
        {
        	Map tempMap=(Map)infoList.get(0);
        	infoMap.put("workTimeName",tempMap.get("WORKTIME_NAME"));
        	infoMap.put("onTime1",formatTime(tempMap.get("ONTIME1").toString()));
        	infoMap.put("offTime1",formatTime(tempMap.get("OFFTIME1").toString()));
        	infoMap.put("onTime2",formatTime(tempMap.get("ONTIME2").toString()));
        	infoMap.put("offTime2",formatTime(tempMap.get("OFFTIME2").toString()));
        	infoMap.put("onTime3",formatTime(tempMap.get("ONTIME3").toString()));
        	infoMap.put("offTime3",formatTime(tempMap.get("OFFTIME3").toString()));
        	infoMap.put("onTime4",formatTime(tempMap.get("ONTIME4").toString()));
        	infoMap.put("offTime4",formatTime(tempMap.get("OFFTIME4").toString()));
        }
		return infoMap;
	}
	
	private String formatTime(String time){
		String hh = "";
		String mm = "";
		if (("".equals(time))||(null==time)){
			return "";
		}else{
			hh = time.substring(0,2);
			mm = time.substring(2,4);
			return hh+":"+mm;
		}
	}

	/**
	private String getIfOffice(int ifOffice){
		String returnValue = "";
		if (ClerkConfig.ISOFFICE==ifOffice){
			returnValue = ClerkConfig.ISOFFICE_NAME;
		}else if (ClerkConfig.ISNOTOFFICE==ifOffice){
			returnValue =  ClerkConfig.ISNOTOFFICE_NAME;
		}
		return returnValue;
	}
	**/
}
