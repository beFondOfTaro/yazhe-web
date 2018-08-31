package xyz.yazhe.yazheweb.service.user.auth.service;

import java.math.BigInteger;

/**
 * @author Yazhe
 * Created at 16:51 2018/8/31
 */
public interface WebStatisticService {

	/**
	 * 点击量加一
	 * @return 总点击数
	 */
	BigInteger clicksInc();

}
