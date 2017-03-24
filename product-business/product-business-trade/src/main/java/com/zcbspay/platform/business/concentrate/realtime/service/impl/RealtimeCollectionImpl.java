package com.zcbspay.platform.business.concentrate.realtime.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.realtime.service.RealtimeCollection;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;
import com.zcbspay.platform.payment.concentrate.RealTimeTrade;
import com.zcbspay.platform.payment.exception.ConcentrateTradeException;

@Service("realtimeCollectionService")
@Transactional
public class RealtimeCollectionImpl implements RealtimeCollection {
	private static final Logger logger = LoggerFactory.getLogger(RealtimeCollectionImpl.class);

	@Autowired
	private ContractDAO contractDAO;

	@Autowired
	private OrderConcentrateService orderConcentrateService;

	@Autowired
	private RealTimeTrade realTimeTrade;

	@Override
	public ResultBean pay(RealtimeCollectionBean realtimeCollectionBean) {
		ContractBean contractBean = null;

		// 获取合同号
		String debtorConsign = realtimeCollectionBean.getDebtorConsign();
		if (StringUtils.isBlank(debtorConsign)) {
			return new ResultBean("BC0000", "合同号不能为空！");
		}

		try {
			contractBean = contractDAO.queryContractByNum(debtorConsign);
			if (contractBean == null) {
				return new ResultBean("BC0001", "合同信息不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean("BC0002", "无法获取合同信息！");
		}

		// 检查代收付账户信息是否和合同中匹配
		if (realtimeCollectionBean.getCreditorBank().equals(contractBean.getCreditorBranchCode())
				&& realtimeCollectionBean.getCreditorName().equals(contractBean.getCreditorName())
				&& realtimeCollectionBean.getCreditorAccount().equals(contractBean.getCreditorAccountNo())
				&& realtimeCollectionBean.getDebtorName().equals(contractBean.getDebtorName())
				&& realtimeCollectionBean.getDebtorAccount().equals(contractBean.getDebtorAccountNo())
				&& realtimeCollectionBean.getDebtorBank().equals(contractBean.getDebtorBranchCode())) {

			com.zcbspay.platform.business.order.bean.RealtimeCollectionBean rtccBean = BeanCopyUtil.copyBean(
					com.zcbspay.platform.business.order.bean.RealtimeCollectionBean.class, realtimeCollectionBean);
			try {
				// 创建订单，并获取结果
				com.zcbspay.platform.business.order.bean.ResultBean resultBean = orderConcentrateService
						.createCollectionChargesOrder(rtccBean);

				if (resultBean.isResultBool()) {
					String tn = (String) resultBean.getResultObj();

					// 支付
					realTimeTrade.collectionCharges(tn);

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
				logger.info("实时代收异常！");
				return new ResultBean("BP003", "实时代收异常！");
			}
		} else {
			return new ResultBean("BC0003", "合同信息有误！");
		}

	}

}
