package com.zcbspay.platform.business.concentrate.realtime.service;

import com.zcbspay.platform.business.concentrate.bean.RealtimePaymentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;

public interface RealtimePayment {
	
	/**
	 * 实时代付
	 * @param realtimePaymentBean
	 * @return
	 */
	public ResultBean pay(RealtimePaymentBean realtimePaymentBean);
}
