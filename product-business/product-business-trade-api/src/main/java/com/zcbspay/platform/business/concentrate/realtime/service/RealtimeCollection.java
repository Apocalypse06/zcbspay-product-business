package com.zcbspay.platform.business.concentrate.realtime.service;

import com.zcbspay.platform.business.concentrate.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;

public interface RealtimeCollection {

	/**
	 * 订单支付
	 * 
	 * @param realtimeCollectionBean
	 * @return
	 */
	public ResultBean pay(RealtimeCollectionBean realtimeCollectionBean);
}
