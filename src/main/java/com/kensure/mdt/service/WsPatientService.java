package com.kensure.mdt.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import co.kensure.frame.JSBaseService;
import co.kensure.http.HttpUtils;
import co.kensure.mem.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.kensure.mdt.entity.AuthUser;
import com.kensure.mdt.entity.SysPatient;
import com.kensure.mdt.entity.query.SysPatientQuery;
import com.kensure.mdt.rep.WsMenZhen;
import com.kensure.mdt.rep.WsZhuYuan;

/**
 * ws调用住院和门诊病人 接口实现类
 */
@Service
public class WsPatientService extends JSBaseService {

	@Resource
	private SysPatientService sysPatientService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 同步门诊和住院病人
	 */
	public void syncData(SysPatientQuery query, AuthUser user) {
		try {
			if ("1".equals(query.getPatientType())) {
				syncZhuYuan(query, user);
			} else {
				syncMenZhen(query, user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 同步住院病人 病人住院号
	 */
	public void syncZhuYuan(SysPatientQuery query, AuthUser user) {
		try {
			if ("1".equals(query.getPatientType()) && StringUtils.isNotBlank(query.getInHospitalNo())) {
				SysPatient pa = getZhuYuanByHm(query.getInHospitalNo());
				if (pa != null) {
					List<SysPatient> list = sysPatientService.selectZhuYuan(query.getInHospitalNo());
					if (CollectionUtils.isEmpty(list)) {
						sysPatientService.save(pa, user);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取住院病人对象
	 */
	private SysPatient getZhuYuanByHm(String hm) {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("service", "searchArchives");
		jsonParam.put("organization", "79649060-6");
		jsonParam.put("hm", hm);
		String urls = "http://172.16.80.85:9020/ez/InformationSearch";
		String aa = HttpUtils.getJsonData(jsonParam, urls);
		SysPatient pa = null;
		if (StringUtils.isNotBlank(aa)) {
			WsZhuYuan zhuyuan = JSONObject.parseObject(aa, WsZhuYuan.class);
			if (StringUtils.isNotBlank(zhuyuan.getBRXM())) {
				pa = zhuyuan.toPatient();
			}
		}
		return pa;
	}

	/**
	 * 同步门诊病人
	 */
	public void syncMenZhen(SysPatientQuery query, AuthUser user) {
		try {
			if ("2".equals(query.getPatientType())) {
				List<SysPatient> palist = getMenZhen();
				if (CollectionUtils.isNotEmpty(palist)) {
					for (SysPatient pa : palist) {
						List<SysPatient> list = sysPatientService.selectMenZhen(pa.getTreatmentNo());
						if (CollectionUtils.isEmpty(list)) {
							sysPatientService.save(pa, user);
						} else {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取住院病人对象
	 */
	private List<SysPatient> getMenZhen() {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("service", "searchByYf");
		jsonParam.put("organization", "79649060-6");
		String urls = "http://172.16.80.85:9020/ez/InformationSearch";
		String aa = HttpUtils.getJsonData(jsonParam, urls);
		List<SysPatient> palist = new ArrayList<>();
		if (StringUtils.isNotBlank(aa)) {
			JSONObject json = JSONObject.parseObject(aa);
			List<WsMenZhen> list = json.getJSONArray("data").toJavaList(WsMenZhen.class);
			if (CollectionUtils.isNotEmpty(list)) {
				list.forEach(x -> {
					palist.add(x.toPatient());
				});
			}
		}
		return palist;
	}

}
