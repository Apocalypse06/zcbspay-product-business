package com.zcbspay.platform.business.concentrate.realtime.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.RealtimePaymentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.realtime.service.RealtimePayment;
import com.zcbspay.platform.business.exception.BusinessOrderException;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;
import com.zcbspay.platform.payment.concentrate.RealTimeTrade;
import com.zcbspay.platform.payment.exception.ConcentrateTradeException;

@Service("realtimePaymentService")
@Transactional
public class RealtimePaymentImpl implements RealtimePayment {
	@Autowired
	private ContractDAO contractDAO;

	@Autowired
	private OrderConcentrateService orderConcentrateService;

	@Autowired
	private RealTimeTrade realTimeTrade;

	@Override
	public ResultBean pay(RealtimePaymentBean realtimePaymentBean) {
		ResultBean resultBean = null;
		if (realtimePaymentBean == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}

		// 获取合同号
		String debtorConsign = realtimePaymentBean.getDebtorConsign();
		ContractBean contractBean = contractDAO.queryContractByNum(debtorConsign);
		// 检查代收付账户信息是否和合同中匹配
		if (realtimePaymentBean.getDebtorName().equals(contractBean.getDebtorName())
				&& realtimePaymentBean.getDebtorAccount().equals(contractBean.getDebtorAccountNo())
				&& realtimePaymentBean.getDebtorBank().equals(contractBean.getDebtorAccountNo())) {

			com.zcbspay.platform.business.order.bean.RealtimePaymentBean rtpBean = BeanCopyUtil
					.copyBean(com.zcbspay.platform.business.order.bean.RealtimePaymentBean.class, realtimePaymentBean);

			try {
				// 创建订单，并获取tn
				resultBean = BeanCopyUtil.copyBean(ResultBean.class,
						orderConcentrateService.createPaymentByAgencyOrder(rtpBean));
				String tn = (String) resultBean.getResultObj();

				// 支付
				resultBean = BeanCopyUtil.copyBean(ResultBean.class, realTimeTrade.paymentByAgency(tn));
				return resultBean;
			} catch (BusinessOrderException e) {
				e.printStackTrace();
				return new ResultBean("BP？？？？", "创建订单失败！");
			} catch (ConcentrateTradeException e) {
				e.printStackTrace();
				return new ResultBean("BP？？？？", "支付失败！");
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultBean("BP？？？？", "支付异常！");
			}

		} else {
			return new ResultBean("BP？？？？", "合同信息有误！");
		}
	}

}
