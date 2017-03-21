package com.zcbspay.platform.business.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.exception.BusinessOrderException;
import com.zcbspay.platform.business.order.bean.BatchCollectionBean;
import com.zcbspay.platform.business.order.bean.BatchPaymentBean;
import com.zcbspay.platform.business.order.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.order.bean.RealtimePaymentBean;
import com.zcbspay.platform.business.order.bean.ResultBean;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;

@Service("orderConcentrateService")
public class OrderConcentrateServiceImpl implements OrderConcentrateService {

//	private static final Logger log = LoggerFactory.getLogger(ConcentrateOrderServiceImpl.class);

	@Autowired
	@Qualifier("concentrateOrderService")
	private com.zcbspay.platform.payment.order.service.ConcentrateOrderService concentrateOrderService;

	@Override
	public ResultBean createCollectionChargesOrder(RealtimeCollectionBean realtimeCollectionBean)
			throws BusinessOrderException {

		try {
			if (realtimeCollectionBean == null || realtimeCollectionBean.getTxnSubType() == null
					|| realtimeCollectionBean.getTxnType() == null || realtimeCollectionBean.getBizType() == null) {
				throw new BusinessOrderException("BO0000");
			}
			com.zcbspay.platform.payment.order.bean.RealTimeCollectionChargesBean rtccBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.RealTimeCollectionChargesBean.class,
					realtimeCollectionBean);
			String tn = this.concentrateOrderService.createCollectionChargesOrder(rtccBean);
			return new ResultBean(tn);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage());
			throw new BusinessOrderException("BO0002");// 创建订单异常
		}
	}

	@Override
	public ResultBean createPaymentByAgencyOrder(RealtimePaymentBean realtimePaymentBean)
			throws BusinessOrderException {
		try {
			if (realtimePaymentBean == null || realtimePaymentBean.getTxnSubType() == null
					|| realtimePaymentBean.getTxnType() == null || realtimePaymentBean.getBizType() == null) {
				throw new BusinessOrderException("BO0000");
			}
			com.zcbspay.platform.payment.order.bean.RealTimepaymentByAgencyBean rtpbaBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.RealTimepaymentByAgencyBean.class, realtimePaymentBean);
			String tn = this.concentrateOrderService.createPaymentByAgencyOrder(rtpbaBean);
			return new ResultBean(tn);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage());
			throw new BusinessOrderException("BO0002");// 创建订单异常
		}
	}

	@Override
	public ResultBean createCollectionChargesBatchOrder(BatchCollectionBean batchCollectionBean)
			throws BusinessOrderException {
		try {
			if (batchCollectionBean == null || batchCollectionBean.getTxnSubType() == null
					|| batchCollectionBean.getTxnType() == null || batchCollectionBean.getBizType() == null) {
				throw new BusinessOrderException("BO0000");
			}
			com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean cboBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean.class, batchCollectionBean);
			String tn = this.concentrateOrderService.createCollectionChargesBatchOrder(cboBean);
			return new ResultBean(tn);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage());
			throw new BusinessOrderException("BO0002");// 创建订单异常
		}
	}

	@Override
	public ResultBean createPaymentByAgencyBatchOrder(BatchPaymentBean batchPaymentBean) throws BusinessOrderException {
		try {
			if (batchPaymentBean == null || batchPaymentBean.getTxnSubType() == null
					|| batchPaymentBean.getTxnType() == null || batchPaymentBean.getBizType() == null) {
				throw new BusinessOrderException("BO0000");
			}
			com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean cboBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean.class, batchPaymentBean);
			String tn = this.concentrateOrderService.createPaymentByAgencyBatchOrder(cboBean);
			return new ResultBean(tn);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage());
			throw new BusinessOrderException("BO0002");// 创建订单异常
		}
	}
}
