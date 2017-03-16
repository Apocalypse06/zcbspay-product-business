package com.zcbspay.platform.business.concentrate.realtime.service;

import com.zcbspay.platform.business.concentrate.bean.ResultBean;

public interface RealtimeTradeQuery {

	/****
	 * 根据tn查询实时交易
	 * @param tn
	 * @return
	 */
	public ResultBean queryOrder(String tn);
}
