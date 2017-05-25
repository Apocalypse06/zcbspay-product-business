package com.zcbspay.platform.business.concentrate.realtime.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.bean.RealtimePaymentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.enums.ContractTypeEnum;
import com.zcbspay.platform.business.concentrate.realtime.service.RealtimePayment;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;
import com.zcbspay.platform.payment.concentrate.RealTimeTrade;
import com.zcbspay.platform.payment.exception.ConcentrateTradeException;

@Service("realtimePaymentService")
@Transactional
public class RealtimePaymentImpl implements RealtimePayment {
	private static final Logger logger = LoggerFactory.getLogger(RealtimePaymentImpl.class);
	@Autowired
	private ContractDAO contractDAO;

	@Autowired
	private OrderConcentrateService orderConcentrateService;

	@Autowired
	private RealTimeTrade realTimeTrade;

	@Override
	public ResultBean pay(RealtimePaymentBean realtimePaymentBean) {
		// 合同信息校验
		try {
			String rsp[] = contractDAO
					.checkContract(realtimePaymentBean.getDebtorConsign(), realtimePaymentBean.getMerId(),
							realtimePaymentBean.getDebtorName(), realtimePaymentBean.getDebtorAccount(),
							realtimePaymentBean.getCreditorName(), realtimePaymentBean.getCreditorAccount(),
							ContractTypeEnum.REALTIMEPAYMENT.getCode(), realtimePaymentBean.getTxnAmt(),
							realtimePaymentBean.getDebtorBank(), realtimePaymentBean.getCreditorBank())
					.split(",");
			if (!rsp[0].trim().equals("CT00")) {
				logger.info(rsp[1].trim());
				return new ResultBean(rsp[0].trim(), rsp[1].trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("合同校验异常！");
			return new ResultBean("BC001", "合同信息校验失败！");
		}

		try {
			com.zcbspay.platform.business.order.bean.RealtimePaymentBean rtpBean = BeanCopyUtil
					.copyBean(com.zcbspay.platform.business.order.bean.RealtimePaymentBean.class, realtimePaymentBean);

			// 创建订单，并获取结果
			com.zcbspay.platform.business.order.bean.ResultBean resultBean = orderConcentrateService
					.createPaymentByAgencyOrder(rtpBean);

			if (resultBean.isResultBool()) {
				String tn = (String) resultBean.getResultObj();

				// 支付
				realTimeTrade.paymentByAgency(tn);

				return new ResultBean(tn);
			} else {
				return new ResultBean(resultBean.getErrCode(), resultBean.getErrMsg());
			}
		} catch (ConcentrateTradeException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("实时代付异常！");
			return new ResultBean("BP004", "实时代付异常！");
		}
	}
}
