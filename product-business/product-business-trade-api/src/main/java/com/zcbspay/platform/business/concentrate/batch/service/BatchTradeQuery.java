package com.zcbspay.platform.business.concentrate.batch.service;

import com.zcbspay.platform.business.concentrate.bean.ResultBean;

public interface BatchTradeQuery {
	/****
	 * 根据tn查询订单
	 * 
	 * @param tn
	 * @return
	 */
	public ResultBean queryOrder(String tn);
}
