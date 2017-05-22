package com.zcbspay.platform.business.concentrate.batch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.batch.service.BatchPayment;
import com.zcbspay.platform.business.concentrate.bean.BatchPaymentBean;
import com.zcbspay.platform.business.concentrate.bean.FileContentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.enums.ContractTypeEnum;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;
import com.zcbspay.platform.payment.concentrate.BatchTrade;
import com.zcbspay.platform.payment.exception.ConcentrateTradeException;

@Service("batchPaymentService")
@Transactional
public class BatchPaymentImpl implements BatchPayment {
	private static final Logger logger = LoggerFactory.getLogger(BatchPaymentImpl.class);

	@Autowired
	private ContractDAO contractDAO;

	@Autowired
	private OrderConcentrateService orderConcentrateService;

	@Autowired
	private BatchTrade batchTrade;

	@Override
	public ResultBean pay(BatchPaymentBean batchPaymentBean) {
		List<FileContentBean> fcbs = batchPaymentBean.getFileContent();
		List<com.zcbspay.platform.business.order.bean.FileContentBean> orderFcbs = new ArrayList<>();
		StringBuffer exInfo = new StringBuffer();
		boolean flag = false; // 合同信息是否有异常：false-无，true-有
		
		// 合同信息校验
		try {
			for (FileContentBean fcb : fcbs) {
				String rsp[] = contractDAO.checkContract(fcb.getDebtorConsign(), batchPaymentBean.getMerId(),
						fcb.getDebtorName(), fcb.getDebtorAccount(), fcb.getCreditorName(), fcb.getCreditorAccount(),
						ContractTypeEnum.BATCHPAYMENT.getCode(), fcb.getAmt()).split(",");
				if (!rsp[0].trim().equals("CT00")) {
					flag = true;
					logger.info("商户订单号为 " + fcb.getOrderId() + " 的" + rsp[1].trim());
					exInfo.append("商户订单号为" + fcb.getOrderId() + "的" + rsp[1].trim() + ",");
				}
			}
			if (flag) {
				return new ResultBean("BC002", exInfo.deleteCharAt(exInfo.length() - 1).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("合同校验异常！");
			return new ResultBean("BC001", "合同信息校验失败！");
		}

		com.zcbspay.platform.business.order.bean.BatchPaymentBean bpBean = BeanCopyUtil
				.copyBean(com.zcbspay.platform.business.order.bean.BatchPaymentBean.class, batchPaymentBean);

		// filecontent 赋值
		for (FileContentBean fileContentBean : fcbs) {
			com.zcbspay.platform.business.order.bean.FileContentBean orderFcb = BeanCopyUtil
					.copyBean(com.zcbspay.platform.business.order.bean.FileContentBean.class, fileContentBean);
			orderFcbs.add(orderFcb);
		}
		bpBean.setFileContent(orderFcbs);

		try {
			// 创建订单，并获取结果
			com.zcbspay.platform.business.order.bean.ResultBean resultBean = orderConcentrateService
					.createPaymentByAgencyBatchOrder(bpBean);
			if (resultBean.isResultBool()) {
				String tn = (String) resultBean.getResultObj();

				// 支付
				batchTrade.paymentByAgency(tn);

				return new ResultBean(tn);
			} else {
				return new ResultBean(resultBean.getErrCode(), resultBean.getErrMsg());
			}
		} catch (ConcentrateTradeException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("批量代付异常！");
			return new ResultBean("BP002", "批量代付异常！");
		}

	}

}
