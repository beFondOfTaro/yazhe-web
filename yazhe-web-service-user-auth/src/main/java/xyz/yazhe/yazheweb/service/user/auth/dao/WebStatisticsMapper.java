package xyz.yazhe.yazheweb.service.user.auth.dao;


import java.math.BigInteger;

/**
 * @author Yazhe
 * Created at 16:47 2018/8/31
 */
public interface WebStatisticsMapper {

	/**
	 * 点击量加一
	 * @return
	 */
	int clicksInc();

	BigInteger getClicks();
}
