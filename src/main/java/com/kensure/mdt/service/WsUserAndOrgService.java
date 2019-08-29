package com.kensure.mdt.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import cn.com.bsoft.BSXmlWsEntryClassService;
import cn.com.bsoft.BSXmlWsEntryClassServicePortType;
import co.kensure.frame.JSBaseService;
import co.kensure.io.FileUtils;
import co.kensure.mem.DateUtils;
import co.kensure.mem.XmltUtils;

import com.kensure.mdt.entity.SysOrg;
import com.kensure.mdt.entity.SysUser;

/**
 * ws调用用户和科室接口实现类
 */
@Service
public class WsUserAndOrgService extends JSBaseService {

	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 同步用户
	 */
	public void initUser() {
		List<String> codelist = getAllUserCode();
		for (String code : codelist) {
			SysUser user = getUserByCode(code);
			if (user == null) {
				continue;
			}
			// 有了就不增加了
			SysUser old = sysUserService.selectByNumber(user.getNumber());
			if (old != null) {
				continue;
			}
			sysUserService.insert(user);
		}
	}

	/**
	 * 获取所有用户
	 */
	private List<String> getAllUserCode() {
		BSXmlWsEntryClassService ss = new BSXmlWsEntryClassService();
		BSXmlWsEntryClassServicePortType port = ss.getBSXmlWsEntryClassServicePort();

		cn.com.bsoft.InvokeRequest _invoke_parameters = new cn.com.bsoft.InvokeRequest();
		_invoke_parameters.setService("EmployeeQueryAll");
		cn.com.bsoft.InvokeResponse _invoke__return = port.invoke(_invoke_parameters);
		String out = _invoke__return.getOutput();
		// 转码
		try {
			out = new String(out.getBytes("gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Document doc = XmltUtils.parseDom4j(out);
		Element root = doc.getRootElement();
		List<Element> childnodes = (List<Element>) root.elements("Personnel");
		List<String> codelist = new ArrayList<>();
		childnodes.forEach(x -> {
			String code = x.element("PersonCode").getText();
			codelist.add(code);
		});
		return codelist;
	}

	/**
	 * 获取code获取用户详情
	 */
	private SysUser getUserByCode(String personcode) {
		BSXmlWsEntryClassService ss = new BSXmlWsEntryClassService();
		BSXmlWsEntryClassServicePortType port = ss.getBSXmlWsEntryClassServicePort();

		cn.com.bsoft.InvokeRequest _invoke_parameters = new cn.com.bsoft.InvokeRequest();
		_invoke_parameters.setService("EmployeeQuery");
		String param = "<BSXml><MsgHeader><Sender>HIS</Sender><MsgType>RES_0103</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><Personnel><PersonCode>" + personcode
				+ "</PersonCode></Personnel></BSXml>";
		_invoke_parameters.setParameter(param);
		cn.com.bsoft.InvokeResponse _invoke__return = port.invoke(_invoke_parameters);
		String out = _invoke__return.getOutput();
		SysUser user = getUserByXml(out);
		return user;
	}

	/**
	 * 获取code获取用户详情
	 */
	private SysUser getUserByXml(String out) {
		Document doc = XmltUtils.parseDom4j(out);
		Element root = doc.getRootElement();
		Element x = root.element("Personnel");
		String PersonCode = x.element("PersonCode").getText();
		String PersonName = x.element("PersonName").getText();

		// 生日19490903
		String Birthday = x.element("Birthday").getText();
		String Education = x.element("Education").attributeValue("DisplayName");
		String Mobile = x.element("Mobile").getText();
		// 科室
		String OfficeCode = x.element("OfficeCode").getText();
		// 机构代码
		String OrganizCode = x.element("OrganizCode").getText();
		if (!"79649060-6".equals(OrganizCode)) {
			return null;
		}

		// 4是科室主任
		String Jobpost = x.element("Jobpost").getText();
		// 职称
		String MajorType = x.element("MajorType").attributeValue("DisplayName");

		SysUser user = new SysUser();
		user.setNumber(PersonCode);
		user.setName(PersonName);
		if (StringUtils.isNotBlank(Birthday)) {
			Date birthday = DateUtils.parse(Birthday, DateUtils.DAY_FORMAT1);
			user.setBirthday(birthday);
			Date now = new Date();
			Long age = (now.getTime() - birthday.getTime()) / 1000 / 60 / 60 / 24 / 365;
			user.setAge(age.intValue());
		}

		String pid = "99";
		if (StringUtils.isNotBlank(OfficeCode)) {
			pid = OfficeCode;
		}
		user.setDepartment(pid);
		user.setTitle(MajorType);
		user.setEducation(Education);
		user.setPhone(Mobile);
		user.setCreatedOrgid("11");
		user.setKszr("4".equals(Jobpost) ? 1 : 0);
		user.setPassword("1");
		return user;
	}

	/**
	 * 同步机构
	 */
	public void initOrg() {
		List<String> codelist = getAllOrgCode();
		for (String code : codelist) {
			SysOrg org = getOrgByCode(code);
			if (org == null) {
				continue;
			}
			// 有了就不增加了
			SysOrg old = sysOrgService.selectOne(org.getId());
			if (old != null) {
				continue;
			}
			sysOrgService.insert(org);
		}
	}

	/**
	 * 获取所有部门
	 */
	private List<String> getAllOrgCode() {
		BSXmlWsEntryClassService ss = new BSXmlWsEntryClassService();
		BSXmlWsEntryClassServicePortType port = ss.getBSXmlWsEntryClassServicePort();

		cn.com.bsoft.InvokeRequest _invoke_parameters = new cn.com.bsoft.InvokeRequest();
		_invoke_parameters.setService("DepatmentQueryAll");
		cn.com.bsoft.InvokeResponse _invoke__return = port.invoke(_invoke_parameters);
		String out = _invoke__return.getOutput();
		Document doc = XmltUtils.parseDom4j(out);
		Element root = doc.getRootElement();
		List<Element> childnodes = (List<Element>) root.elements("Office");
		List<String> codelist = new ArrayList<>();
		childnodes.forEach(x -> {
			String OfficeCode = x.element("OfficeCode").getText();
			codelist.add(OfficeCode);
		});
		return codelist;
	}

	/**
	 * 获取code获取组织详情
	 */
	private SysOrg getOrgByCode(String OfficeCode) {
		BSXmlWsEntryClassService ss = new BSXmlWsEntryClassService();
		BSXmlWsEntryClassServicePortType port = ss.getBSXmlWsEntryClassServicePort();

		cn.com.bsoft.InvokeRequest _invoke_parameters = new cn.com.bsoft.InvokeRequest();
		_invoke_parameters.setService("DepatmentQuery");
		String param = "<BSXml><MsgHeader><Sender>HIS</Sender><MsgType>RES_0203</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><Office><OfficeCode>" + OfficeCode + "</OfficeCode></Office></BSXml>";
		_invoke_parameters.setParameter(param);
		cn.com.bsoft.InvokeResponse _invoke__return = port.invoke(_invoke_parameters);
		String out = _invoke__return.getOutput();
		SysOrg org = getOrgByXml(out);
		return org;
	}

	/**
	 * 获取code获取用户详情
	 */
	private SysOrg getOrgByXml(String out) {
		Document doc = XmltUtils.parseDom4j(out);
		Element root = doc.getRootElement();
		Element x = root.element("Office");
		String OfficeCode = x.element("OfficeCode").getText();
		String OfficeName = x.element("OfficeName").getText();
		String OrganizCode = x.element("OrganizCode").getText();
		String ParentId = x.element("ParentId").getText();
		String OrganizType = x.element("OrganizType").attributeValue("DisplayName");
		SysOrg org = new SysOrg();
		org.setId(OfficeCode);
		org.setName(OfficeName);
		String pid = "11";
		if (!"79649060-6".equals(OrganizCode)) {
			return null;
		}
		if (StringUtils.isNotBlank(ParentId)) {
			pid = ParentId;
		}
		org.setPid(pid);
		org.setCreatedOrgid("11");
		org.setArea(OrganizType);
		return org;
	}

	public static void main(String[] args) {
		String out = FileUtils.read("D:\\1234");
		Document doc = XmltUtils.parseDom4j(out);

		List<Element> childnodes = (List<Element>) doc.getRootElement().elements("Office");
		List<String> codelist = new ArrayList<>();
		childnodes.forEach(x -> {
			String OfficeCode = x.element("OfficeCode").getText();
			codelist.add(OfficeCode);
		});

	}
}
