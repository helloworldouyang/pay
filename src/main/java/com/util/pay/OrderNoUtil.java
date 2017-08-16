package com.util.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 得到随机的订单号码
 * @author Administrator
 *
 */
public class OrderNoUtil {
	
	
	/**
	 * 创建订单号
	 * @return
	 */
	public String getOrderNo() {
		String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
		int i = (int) (Math.random()*900000+100000);
		return str+i;
	}

}
