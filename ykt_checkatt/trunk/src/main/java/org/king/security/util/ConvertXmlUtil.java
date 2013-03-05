/* ============================================================
 * ��Ȩ��    king ��Ȩ���� (c) 2006
 * �ļ���    org.king.security.util.ConvertXmlUtil.java
 * �������ڣ� 2006-4-26 15:40:56
 * ���ܣ�    {����Ҫʵ�ֵĹ���}
 * ������:   {��������}
 * �޸ļ�¼��
 * ����                    ����         ����
 * =============================================================
 * 2006-4-26 15:40:56      ljf        �����ļ���ʵ�ֻ�������
 * ============================================================
 */

/**
 * 
 */
package org.king.security.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.king.check.domain.Department;
import org.king.security.domain.Menu;

/**
 * <p> ConvertXmlUtil.java </p>
 * <p> {����˵��} </p>
 *
 * <p><a href="ConvertXmlUtil.java.html"><i>�鿴Դ����</i></a></p>  
 *
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-26
 * 
 *
 */
public class ConvertXmlUtil {
	//~ Static fields/initializers =============================================

	  private static Log log = LogFactory.getLog(ConvertXmlUtil.class);

	  //~ Methods ================================================================

	  public static String convertMenuListToXml(Map menuMap) {
	  	StringBuffer xml = new StringBuffer("<?xml version='1.0' encoding='gb2312'?>");
		xml = xml.append("<Root>");
		
	  	String xmlStr = "<?xml version='1.0' encoding='gb2312'?>";
		xmlStr = xmlStr + "<Root>";
	  	
		String rootId = "-1";
		List menuList = (List)menuMap.get(rootId);
		
		//�����һ���˵���Ϊ�գ�����
		if(menuList!=null && menuList.size()>0)
			addTreeNode(menuList,menuMap,xml);
		
		
		xml= xml.append("</Root>");
		
		return xml.toString();
	  }
	
	/*
	 * �ݹ������ӽڵ�˵�xmlƬ��
	 */
	public static void addTreeNode(List menuList,Map menuMap,StringBuffer xmlStr) {
		List temp;
		for(Iterator i = menuList.iterator();i.hasNext();){
			
			   Menu menu = (Menu)i.next();
			   xmlStr = xmlStr.append("<TreeNode ");
			   xmlStr = xmlStr.append("Title='"+menu.getName()+"'");
			
			
			   //������Ӳ˵�
			   temp = (List)menuMap.get(menu.getId());
			   if(temp!=null && temp.size()>0){
				   
				   xmlStr = xmlStr.append(" CheckData=\""+menu.getId()+"\"");
				   xmlStr = xmlStr.append(" Checked=\"false\"");
				   xmlStr = xmlStr.append(" >");
				   addTreeNode(temp,menuMap,xmlStr);
				   xmlStr = xmlStr.append(" </TreeNode>");
			   }else{
				   xmlStr = xmlStr.append(" CheckData=\""+menu.getId()+"\"");
				   xmlStr = xmlStr.append(" Checked=\"false\"");
				   //�ҵ����ʵ�ͼƬ��ʱ�򣬼�ͼƬ
				   //xmlStr = xmlStr.append(" NodeImgSrc='"+menu.getMenuName()+"'");
				   xmlStr = xmlStr.append(" />");
			   }
				
		   }
	}
	
	
	public static String convertToTreeXml(Map deptMap){
		
		StringBuffer xml = new StringBuffer("<?xml version='1.0' encoding='gb2312'?>");
		xml = xml.append("<Root>");
		
	  	String xmlStr = "<?xml version='1.0' encoding='gb2312'?>";
		xmlStr = xmlStr + "<Root>";
	  	
		String rootId = "-1";
		List deptList = (List)deptMap.get(rootId);
		
		//�����һ���˵���Ϊ�գ�����
		if(deptList!=null && deptList.size()>0)
			addDeptTreeNode(deptList,deptMap,xml);
		
		
		xml= xml.append("</Root>");
		
		return xml.toString();
	}
	
	/*
	 * �ݹ������ӽڵ�˵�xmlƬ��
	 */
	public static void addDeptTreeNode(List deptList,Map deptMap,StringBuffer xmlStr) {
		List temp;
		for(Iterator i = deptList.iterator();i.hasNext();){
			
			Department dept = (Department)i.next();
			   xmlStr = xmlStr.append("<TreeNode ");
			   xmlStr = xmlStr.append("Title='"+dept.getName()+"'");
			
			
			   //������Ӳ˵�
			   temp = (List)deptMap.get(dept.getId());
			   if(temp!=null && temp.size()>0){
				   
				   xmlStr = xmlStr.append(" CheckData=\""+dept.getId()+"\"");
				   xmlStr = xmlStr.append(" Checked=\"false\"");
				   xmlStr = xmlStr.append(" >");
				   addDeptTreeNode(temp,deptMap,xmlStr);
				   xmlStr = xmlStr.append(" </TreeNode>");
			   }else{
				   xmlStr = xmlStr.append(" CheckData=\""+dept.getId()+"\"");
				   xmlStr = xmlStr.append(" Checked=\"false\"");
				   //�ҵ����ʵ�ͼƬ��ʱ�򣬼�ͼƬ
				   //xmlStr = xmlStr.append(" NodeImgSrc='"+dept.getMenuName()+"'");
				   xmlStr = xmlStr.append(" />");
			   }
				
		   }
	}
}