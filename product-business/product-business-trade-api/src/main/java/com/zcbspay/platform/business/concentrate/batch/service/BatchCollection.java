package com.zcbspay.platform.business.concentrate.batch.service;

import com.zcbspay.platform.business.concentrate.bean.BatchCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;

public interface BatchCollection {

	/**
	 * 订单支付
	 * 
	 * @param batchCollectionBean
	 * @return
	 */
	public ResultBean pay(BatchCollectionBean batchCollectionBean);
}
