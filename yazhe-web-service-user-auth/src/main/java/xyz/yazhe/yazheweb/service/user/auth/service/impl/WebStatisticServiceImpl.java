package xyz.yazhe.yazheweb.service.user.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.yazhe.yazheweb.service.user.auth.dao.WebStatisticsMapper;
import xyz.yazhe.yazheweb.service.user.auth.service.WebStatisticService;

import java.math.BigInteger;

/**
 * @author Yazhe
 * Created at 16:52 2018/8/31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WebStatisticServiceImpl implements WebStatisticService {

	@Autowired
	private WebStatisticsMapper webStatisticsMapper;

	@Override
	public BigInteger clicksInc() {
		webStatisticsMapper.clicksInc();
		return webStatisticsMapper.getClicks();
	}

}
