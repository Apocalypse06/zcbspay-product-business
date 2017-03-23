package com.zcbspay.platform.business.concentrate.realtime.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.RealtimeCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.concentrate.realtime.service.RealtimeCollection;
import com.zcbspay.platform.business.exception.BusinessOrderException;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;
import com.zcbspay.platform.payment.concentrate.RealTimeTrade;
import com.zcbspay.platform.payment.exception.ConcentrateTradeException;

@Service("realtimeCollectionService")
@Transactional
public class RealtimeCollectionImpl implements RealtimeCollection {
	@Autowired
	private ContractDAO contractDAO;

	@Autowired
	private OrderConcentrateService orderConcentrateService;
	
	@Autowired
	private RealTimeTrade realTimeTrade;

	@Override
	public ResultBean pay(RealtimeCollectionBean realtimeCollectionBean) {
		ResultBean resultBean = null;
		ContractBean contractBean  = null;
		if (realtimeCollectionBean == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}

		// 获取合同号
		String debtorConsign = realtimeCollectionBean.getDebtorConsign();
		try {
			contractBean = contractDAO.queryContractByNum(debtorConsign);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean("BP？？？？", "无法获取合同信息！");
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
				// 创建订单，并获取tn
				resultBean = BeanCopyUtil.copyBean(ResultBean.class,
						orderConcentrateService.createCollectionChargesOrder(rtccBean));
				String tn = (String) resultBean.getResultObj();
				
				// 支付
				resultBean = BeanCopyUtil.copyBean(ResultBean.class,realTimeTrade.collectionCharges(tn));
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
		} else {
			return new ResultBean("BP？？？？", "合同信息有误！");
		}

	}

}
