package com.zcbspay.platform.business.concentrate.batch.service;

import com.zcbspay.platform.business.concentrate.bean.BatchPaymentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;

public interface BatchPayment {
	/**
	 * 订单支付
	 * @param batchPaymentBean
	 * @return
	 */
	public ResultBean pay(BatchPaymentBean batchPaymentBean);
}
