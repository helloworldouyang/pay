package com.controller.notify.wechat;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.pay.wechat.WeChatPay;
/**
 * 微信回调函数，需要外网访问
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/wechatnotify")
public class WechatNotify {
	
    @RequestMapping("/notify")
	public void wechatNotify(HttpServletRequest request,HttpServletResponse response){
    	System.out.println("**************into*************wechatnotify");
		WeChatPay weChatPay=new WeChatPay();
		try {
			Map<String, Object> map=weChatPay.weChatNotify(request, response);
			
			boolean bRet= map.get("msg")!=null?Boolean.parseBoolean(map.get("msg").toString()):null;
			if (bRet) {
				System.out.println("***************支付成功，进入回调函数，写自己的业务逻辑。");
				String orderNo=(String) map.get("out_trade_no");//得到请求下单时的订单编号，具体参数可以根据回调函数去设置返回
				System.out.println("orderNo:"+orderNo);
				
			}
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
