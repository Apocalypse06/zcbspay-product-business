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
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.FileContentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
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
		List<FileContentBean> fcbs = new ArrayList<>();
		List<com.zcbspay.platform.business.order.bean.FileContentBean> orderFcbs = new ArrayList<>();
		ContractBean contractBean = null;

		// 遍历文件域
		fcbs = batchPaymentBean.getFileContent();
		for (FileContentBean fcb : fcbs) {
			try {
				contractBean = contractDAO.queryContractByNum(fcb.getDebtorConsign());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultBean("BC0002", "无法获取合同信息！");
			}
			// 检查代收付账户信息是否和合同中匹配
			if (fcb.getCreditorBank().equals(contractBean.getCreditorBranchCode())
					&& fcb.getCreditorName().equals(contractBean.getCreditorName())
					&& fcb.getCreditorAccount().equals(contractBean.getCreditorAccountNo())
					&& fcb.getDebtorName().equals(contractBean.getDebtorName())
					&& fcb.getDebtorAccount().equals(contractBean.getDebtorAccountNo())
					&& fcb.getDebtorBank().equals(contractBean.getDebtorBranchCode())) {
			} else {
				return new ResultBean("BC0003", "合同信息有误！");
			}
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
