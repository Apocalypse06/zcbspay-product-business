package com.zcbspay.platform.business.concentrate.batch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.concentrate.batch.service.BatchCollection;
import com.zcbspay.platform.business.concentrate.bean.BatchCollectionBean;
import com.zcbspay.platform.business.concentrate.bean.ContractBean;
import com.zcbspay.platform.business.concentrate.bean.FileContentBean;
import com.zcbspay.platform.business.concentrate.bean.ResultBean;
import com.zcbspay.platform.business.concentrate.contract.dao.ContractDAO;
import com.zcbspay.platform.business.exception.BusinessOrderException;
import com.zcbspay.platform.business.order.service.OrderConcentrateService;
import com.zcbspay.platform.payment.concentrate.BatchTrade;
import com.zcbspay.platform.payment.exception.ConcentrateTradeException;

@Service("batchCollectionService")
@Transactional
public class BatchCollectionImpl implements BatchCollection {
	@Autowired
	private ContractDAO contractDAO;

	@Autowired
	@Qualifier("orderConcentrateService")
	private OrderConcentrateService orderConcentrateService;

	@Autowired
	private BatchTrade batchTrade;
	
	@Override
	public ResultBean pay(BatchCollectionBean batchCollectionBean) {
		List<FileContentBean> fcbs = new ArrayList<>();
		ResultBean resultBean = null;
		ContractBean contractBean  = null;
		if (batchCollectionBean == null || batchCollectionBean.getFileContent() == null) {
			return new ResultBean("BP0000", "参数不能为空！");
		}

		// 遍历文件域
		fcbs = batchCollectionBean.getFileContent();
		for (FileContentBean fcb : fcbs) {
			try {
				contractBean = contractDAO.queryContractByNum(fcb.getDebtorConsign());
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultBean("BP？？？？", "无法获取合同信息！");
			}
			
			// 检查代收付账户信息是否和合同中匹配
			if (fcb.getCreditorBank().equals(contractBean.getCreditorBranchCode())
					&& fcb.getCreditorName().equals(contractBean.getCreditorName())
					&& fcb.getCreditorAccount().equals(contractBean.getCreditorAccountNo())
					&& fcb.getDebtorName().equals(contractBean.getDebtorName())
					&& fcb.getDebtorAccount().equals(contractBean.getDebtorAccountNo())
					&& fcb.getDebtorBank().equals(contractBean.getDebtorAccountNo())) {
			} else {
				return new ResultBean("BP？？？？", "合同信息有误！");
			}
		}

		com.zcbspay.platform.business.order.bean.BatchCollectionBean bcBean = BeanCopyUtil
				.copyBean(com.zcbspay.platform.business.order.bean.BatchCollectionBean.class, batchCollectionBean);

		try {
			// 创建订单，并获取tn
			resultBean = BeanCopyUtil.copyBean(ResultBean.class,
					orderConcentrateService.createCollectionChargesBatchOrder(bcBean));
			String tn = (String) resultBean.getResultObj();
			
			// 支付
			resultBean = BeanCopyUtil.copyBean(ResultBean.class, batchTrade.collectionCharges(tn));
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
