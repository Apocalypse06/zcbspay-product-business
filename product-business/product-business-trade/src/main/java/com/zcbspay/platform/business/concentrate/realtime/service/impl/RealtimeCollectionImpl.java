package com.zcbspay.platform.business.concentrate.realtime.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.enums.ContractTypeEnum;
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

		// 合同信息校验
		try {
			String rsp[] = contractDAO
					.checkContract(realtimeCollectionBean.getDebtorConsign(), realtimeCollectionBean.getMerId(),
							realtimeCollectionBean.getDebtorName(), realtimeCollectionBean.getDebtorAccount(),
							realtimeCollectionBean.getCreditorName(), realtimeCollectionBean.getCreditorAccount(),
							ContractTypeEnum.REALTIMECOLLECTION.getCode(), realtimeCollectionBean.getTxnAmt(),
							realtimeCollectionBean.getDebtorBank(), realtimeCollectionBean.getCreditorBank())
					.split(",");
			if (!rsp[0].trim().equals("CT00")) {
				logger.info(rsp[1].trim());
				return new ResultBean(rsp[0].trim(), rsp[1].trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("合同校验异常！");
			return new ResultBean("BC001", "合同信息校验失败！");
		}

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
			return new ResultBean(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("实时代收异常！");
			return new ResultBean("BP003", "实时代收异常！");
		}
	}
}
