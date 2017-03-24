package com.zcbspay.platform.business.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.exception.BusinessOrderException;
import com.zcbspay.platform.business.order.bean.BatchCollectionBean;
import com.zcbspay.platform.business.order.bean.BatchPaymentBean;
import com.zcbspay.platform.business.order.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.order.bean.RealtimePaymentBean;
import com.zcbspay.platform.business.order.bean.ResultBean;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;

@Service("orderConcentrateService")
@Transactional
public class OrderConcentrateServiceImpl implements OrderConcentrateService {

	private static final Logger logger = LoggerFactory.getLogger(OrderConcentrateServiceImpl.class);

	@Autowired
	@Qualifier("concentrateOrderService")
	private com.zcbspay.platform.payment.order.service.ConcentrateOrderService concentrateOrderService;

	@Override
	public ResultBean createCollectionChargesOrder(RealtimeCollectionBean realtimeCollectionBean) {

		try {
			com.zcbspay.platform.payment.order.bean.RealTimeCollectionChargesBean rtccBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.RealTimeCollectionChargesBean.class,
					realtimeCollectionBean);
			rtccBean.setMerchNo(realtimeCollectionBean.getMerId());
			String tn = this.concentrateOrderService.createCollectionChargesOrder(rtccBean);
			return new ResultBean(tn);
		} catch (com.zcbspay.platform.payment.exception.PaymentOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建实时代收订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
	}

	@Override
	public ResultBean createPaymentByAgencyOrder(RealtimePaymentBean realtimePaymentBean) {
		try {
			if (realtimePaymentBean == null || realtimePaymentBean.getTxnSubType() == null
					|| realtimePaymentBean.getTxnType() == null || realtimePaymentBean.getBizType() == null) {
				throw new BusinessOrderException("BO0000");
			}
			com.zcbspay.platform.payment.order.bean.RealTimepaymentByAgencyBean rtpbaBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.RealTimepaymentByAgencyBean.class, realtimePaymentBean);
			rtpbaBean.setMerchNo(realtimePaymentBean.getMerId());
			String tn = this.concentrateOrderService.createPaymentByAgencyOrder(rtpbaBean);
			return new ResultBean(tn);
		} catch (com.zcbspay.platform.payment.exception.PaymentOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建实时代付订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
	}

	@Override
	public ResultBean createCollectionChargesBatchOrder(BatchCollectionBean batchCollectionBean) {
		List<com.zcbspay.platform.payment.order.bean.ConcentrateOrderDetaBean> codBeans = new ArrayList<>();
		try {
			if (batchCollectionBean == null || batchCollectionBean.getTxnSubType() == null
					|| batchCollectionBean.getTxnType() == null || batchCollectionBean.getBizType() == null) {
				throw new BusinessOrderException("BO0000");
			}
			com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean cboBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean.class, batchCollectionBean);
			// 文件域赋值
			for (com.zcbspay.platform.business.order.bean.FileContentBean fcBean : batchCollectionBean
					.getFileContent()) {
				com.zcbspay.platform.payment.order.bean.ConcentrateOrderDetaBean codBean = BeanCopyUtil
						.copyBean(com.zcbspay.platform.payment.order.bean.ConcentrateOrderDetaBean.class, fcBean);
				codBeans.add(codBean);
			}
			cboBean.setDetaList(codBeans);

			String tn = this.concentrateOrderService.createCollectionChargesBatchOrder(cboBean);
			return new ResultBean(tn);
		} catch (com.zcbspay.platform.payment.exception.PaymentOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建批量代收订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
	}

	@Override
	public ResultBean createPaymentByAgencyBatchOrder(BatchPaymentBean batchPaymentBean) {
		List<com.zcbspay.platform.payment.order.bean.ConcentrateOrderDetaBean> codBeans = new ArrayList<>();
		try {
			if (batchPaymentBean == null || batchPaymentBean.getTxnSubType() == null
					|| batchPaymentBean.getTxnType() == null || batchPaymentBean.getBizType() == null) {
				throw new BusinessOrderException("BO0000");
			}
			com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean cboBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.payment.order.bean.ConcentrateBatchOrderBean.class, batchPaymentBean);
			// 文件域赋值
			for (com.zcbspay.platform.business.order.bean.FileContentBean fcBean : batchPaymentBean.getFileContent()) {
				com.zcbspay.platform.payment.order.bean.ConcentrateOrderDetaBean codBean = BeanCopyUtil
						.copyBean(com.zcbspay.platform.payment.order.bean.ConcentrateOrderDetaBean.class, fcBean);
				codBeans.add(codBean);
			}
			cboBean.setDetaList(codBeans);

			String tn = this.concentrateOrderService.createPaymentByAgencyBatchOrder(cboBean);
			return new ResultBean(tn);
		} catch (com.zcbspay.platform.payment.exception.PaymentOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建批量代付订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
	}
}
