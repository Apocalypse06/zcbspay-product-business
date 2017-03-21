package com.zcbspay.platform.business.order.service;

import com.zcbspay.platform.business.exception.BusinessOrderException;
import com.zcbspay.platform.business.order.bean.BatchCollectionBean;
import com.zcbspay.platform.business.order.bean.BatchPaymentBean;
import com.zcbspay.platform.business.order.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.order.bean.RealtimePaymentBean;
import com.zcbspay.platform.business.order.bean.ResultBean;

/**
 * 集中代收付订单生成接口
 *
 * @author lianhai
 * @version
 * @date 2017年3月15日 15:39:53
 * @since
 */
public interface OrderConcentrateService {
	/**
	 * 实时代收订单易接口
	 * @param realtimeCollectionBean
	 * @return
	 * @throws BusinessOrderException 
	 */
	public ResultBean createCollectionChargesOrder(RealtimeCollectionBean realtimeCollectionBean) throws BusinessOrderException;
	
	/**
	 * 实时代付订单接口
	 * @param realtimePaymentBean
	 * @return
	 * @throws BusinessOrderException 
	 */
	public ResultBean createPaymentByAgencyOrder(RealtimePaymentBean realtimePaymentBean) throws BusinessOrderException;
	
	/**
	 * 批量代收订单接口
	 * @param batchCollectionBean
	 * @return
	 * @throws BusinessOrderException
	 */
	public ResultBean createCollectionChargesBatchOrder(BatchCollectionBean batchCollectionBean) throws BusinessOrderException;
	/**
	 * 批量代付订单接口
	 * @param batchPaymentBean
	 * @return
	 * @throws BusinessOrderException
	 */
	public ResultBean createPaymentByAgencyBatchOrder(BatchPaymentBean batchPaymentBean) throws BusinessOrderException;
}
