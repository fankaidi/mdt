package com.kensure.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kensure.mdt.service.WsPatientService;

@Service
public class SKTask {

	@Resource
	private WsPatientService wsPatientService;

	/**
	 * add by fankd 每天7点，同步病人信息
	 */
	@Scheduled(cron = "0 0 7 1/1 * ?")
	public void deleteLogData() {
		try {
			wsPatientService.syncMenZhenTask();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
