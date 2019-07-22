package com.kensure.mdt.controller;

import co.kensure.frame.ResultInfo;
import co.kensure.frame.ResultRowInfo;
import co.kensure.frame.ResultRowsInfo;
import co.kensure.http.RequestUtils;
import co.kensure.mem.PageInfo;
import com.alibaba.fastjson.JSONObject;
import com.kensure.mdt.entity.*;
import com.kensure.mdt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统设置
 */
@Controller
@RequestMapping(value = "set")
public class SysSetController  extends BaseController {

	@Autowired
	private SysFeeService sysFeeService;

	@Autowired
	private SysGradeService sysGradeService;

	@Autowired
	private SysMsgTemplateService sysMsgTemplateService;

	@Autowired
	private SysCodeService sysCodeService;

	/**
	 * 查询收费情况设置
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectSysFee", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo selectSysFee(HttpServletRequest req, HttpServletResponse rep) {
        List<SysFee> list = sysFeeService.selectList();

		return new ResultRowsInfo(list, list.size());
	}

	/**
	  查看收费情况设置
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSysFee", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo getSysFee(HttpServletRequest req, HttpServletResponse rep) {

		Long id =Long.parseLong( req.getParameter("id"));
        SysFee sysFee = sysFeeService.selectOne(id);

        return new ResultRowInfo(sysFee);
	}

	/**
	 * 保存收费情况设置
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveSysFee", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo saveSysFee(HttpServletRequest req, HttpServletResponse rep) {

        JSONObject json = RequestUtils.paramToJson(req);
        SysFee fee = JSONObject.parseObject(json.toJSONString(), SysFee.class);
        sysFeeService.save(fee);
        return new ResultInfo();
	}

	/**
	 * 删除收费情况设置
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delSysFee", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public ResultInfo delSysFee(HttpServletRequest req, HttpServletResponse rep) {

		Long id =Long.parseLong( req.getParameter("id"));
		sysFeeService.delete(id);

		return new ResultInfo();
	}


    /**
     * 查询评分项设置
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectSysGrade", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo selectSysGrade(HttpServletRequest req, HttpServletResponse rep) {

        String type = req.getParameter("type");

        List<SysGrade> list = sysGradeService.selectList(type);

        return new ResultRowsInfo(list, list.size());
    }


    /**
     查看收费情况设置
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSysGrade", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo getSysGrade(HttpServletRequest req, HttpServletResponse rep) {

        Long id =Long.parseLong( req.getParameter("id"));
        SysGrade sysGrade = sysGradeService.selectOne(id);

        return new ResultRowInfo(sysGrade);
    }

    /**
     * 保存收费情况设置
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveSysGrade", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo saveSysGrade(HttpServletRequest req, HttpServletResponse rep) {

        JSONObject json = RequestUtils.paramToJson(req);
        SysGrade grade = JSONObject.parseObject(json.toJSONString(), SysGrade.class);
        sysGradeService.save(grade);
        return new ResultInfo();
    }

    /**
     * 删除收费情况设置
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delSysGrade", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo delSysGrade(HttpServletRequest req, HttpServletResponse rep) {

        Long id =Long.parseLong( req.getParameter("id"));
        sysGradeService.delete(id);

        return new ResultInfo();
    }


    /**
     * 查询短信模板
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectSysMsgTemp", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo selectSysMsgTemp(HttpServletRequest req, HttpServletResponse rep) {
        List<SysMsgTemplate> list = sysMsgTemplateService.selectList();

        return new ResultRowsInfo(list, list.size());
    }


    /**
     查看短信模板
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSysMsgTemp", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo getSysMsgTemp(HttpServletRequest req, HttpServletResponse rep) {

        Long id =Long.parseLong( req.getParameter("id"));
        SysMsgTemplate sysMsgTemplate = sysMsgTemplateService.selectOne(id);

        return new ResultRowInfo(sysMsgTemplate);
    }

    /**
     查看短信模板
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSysMsgTempByType", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo getSysMsgTempByType(HttpServletRequest req, HttpServletResponse rep) {

        String type = req.getParameter("type");
        SysMsgTemplate sysMsgTemplate = sysMsgTemplateService.getSysMsgTempByType(type);

        return new ResultRowInfo(sysMsgTemplate);
    }

    /**
     * 保存短信模板
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveSysMsgTemp", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo saveSysMsgTemp(HttpServletRequest req, HttpServletResponse rep) {

        JSONObject json = RequestUtils.paramToJson(req);
        SysMsgTemplate sysMsgTemplate = JSONObject.parseObject(json.toJSONString(), SysMsgTemplate.class);
        sysMsgTemplateService.save(sysMsgTemplate);
        return new ResultInfo();
    }

    /**
     * 删除短信模板
     * @param req
     * @param rep
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delSysMsgTemp", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo delSysMsgTemp(HttpServletRequest req, HttpServletResponse rep) {

        Long id =Long.parseLong( req.getParameter("id"));
        sysMsgTemplateService.delete(id);

        return new ResultInfo();
    }

    @ResponseBody
    @RequestMapping(value = "getCodeByType", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public ResultInfo getCodeByType(HttpServletRequest req, HttpServletResponse rep) {

        String type = req.getParameter("type");
        List<SysCode> list = sysCodeService.getCodeByType(type);

        return new ResultRowsInfo(list);
    }

}
