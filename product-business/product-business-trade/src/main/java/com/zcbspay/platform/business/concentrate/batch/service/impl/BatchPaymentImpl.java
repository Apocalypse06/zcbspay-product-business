package com.zcbspay.platform.business.concentrate.batch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.batch.service.BatchPayment;
import com.zcbspay.platform.business.concentrate.bean.BatchPaymentBean;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.FileContentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.exception.BusinessOrderException;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;
import com.zcbspay.platform.payment.concentrate.BatchTrade;
import com.zcbspay.platform.payment.exception.ConcentrateTradeException;

@Service("batchPaymentService")
public class BatchPaymentImpl implements BatchPayment {
	@Autowired
	private ContractDAO contractDAO;

	@Autowired
	private OrderConcentrateService orderConcentrateService;

	@Autowired
	private BatchTrade batchTrade;

	@Override
	public ResultBean pay(BatchPaymentBean batchPaymentBean) {
		List<FileContentBean> fcbs = new ArrayList<>();
		ResultBean resultBean = null;
		
		if (batchPaymentBean == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}

		// 遍历文件域
		fcbs = batchPaymentBean.getFileContent();
		for (FileContentBean fcb : fcbs) {
			ContractBean contractBean = contractDAO.queryContractByNum(fcb.getDebtorConsign());
			// 检查代收付账户信息是否和合同中匹配
			if (fcb.getDebtorName().equals(contractBean.getDebtorName())
					&& fcb.getDebtorAccount().equals(contractBean.getDebtorAccountNo())
					&& fcb.getDebtorBank().equals(contractBean.getDebtorAccountNo())) {
			} else {
				return new ResultBean("BP？？？？", "合同信息有误！");
			}
		}
		com.zcbspay.platform.business.order.bean.BatchPaymentBean bpBean = BeanCopyUtil
				.copyBean(com.zcbspay.platform.business.order.bean.BatchPaymentBean.class, batchPaymentBean);

		try {
			// 创建订单，并获取tn
			resultBean = BeanCopyUtil.copyBean(ResultBean.class, orderConcentrateService.createPaymentByAgencyBatchOrder(bpBean));
			String tn = (String) resultBean.getResultObj();
			
			// 支付
			resultBean = BeanCopyUtil.copyBean(ResultBean.class, batchTrade.paymentByAgency(tn));
			return resultBean;
		} catch (BusinessOrderException e) {
			e.printStackTrace();
			return new ResultBean("BP？？？？", "创建订单失败！");
		} catch (ConcentrateTradeException e) {
			e.printStackTrace();
			return new ResultBean("BP？？？？", "支付失败！");
		}catch (Exception e) {
			e.printStackTrace();
			return new ResultBean("BP？？？？", "支付异常！");
		}
		
	}

}
