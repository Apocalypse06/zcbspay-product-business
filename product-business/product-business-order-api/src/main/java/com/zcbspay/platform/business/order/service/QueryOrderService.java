package com.zcbspay.platform.business.order.service;

import com.zcbspay.platform.business.exception.QueryOrderException;
import com.zcbspay.platform.business.order.bean.ResultBean;

/***
 * 查询订单相关
 * 
 * @author lianhai
 *
 */
public interface QueryOrderService {
	/**
	 * 交易订单查询方法
	 * 
	 * @param merchNo
	 *            商户号
	 * @param orderId
	 *            订单号
	 * @return 订单结果bean
	 */
	public ResultBean queryOrder(String merchNo, String orderId) throws QueryOrderException;

	/**
	 * 交易订单查询方法
	 * 
	 * @param tn
	 *            受理订单号
	 * @return
	 */
	public ResultBean queryOrderByTN(String tn) throws QueryOrderException;

	/**
	 * 
	 * @param merchNo
	 * @param orderId
	 * @return
	 * @throws QueryOrderException
	 */
	public ResultBean queryInsteadPayOrder(String merchNo, String orderId) throws QueryOrderException;

	/**
	 * 查询实时集中代收订单
	 * 
	 * @param tn
	 * @return
	 * @throws QueryOrderException
	 */
	public ResultBean queryConcentrateCollectionOrder(String tn) throws QueryOrderException;

	/**
	 * 查询实时集中代付订单
	 * 
	 * @param tn
	 * @return
	 * @throws QueryOrderException
	 */
	public ResultBean queryConcentratePaymentOrder(String tn) throws QueryOrderException;

	/**
	 * 查询集中代收批次信息
	 * 
	 * @param merchNo
	 *            商户号
	 * @param batchNo
	 *            批次号
	 * @param txnDate
	 *            交易日期
	 * @return
	 */
	public ResultBean queryConcentrateCollectionBatch(String merchNo, String batchNo, String txnDate);

	/**
	 * 查询集中代付批次信息
	 * 
	 * @param merchNo
	 *            商户号
	 * @param batchNo
	 *            批次号
	 * @param txnDate
	 *            交易日期
	 * @return
	 */
	public ResultBean queryConcentratePaymentBatch(String merchNo, String batchNo, String txnDate);
}
