package com.zcbspay.platform.business.concentrate.batch.service;

import com.zcbspay.platform.business.concentrate.bean.ResultBean;

public interface BatchTradeQuery {
	/****
	 * 根据tn查询批量交易
	 * 
	 * @param tn
	 * @return
	 */
	public ResultBean queryOrder(String tn);
}
