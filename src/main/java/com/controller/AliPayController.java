package com.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.pay.OrderNoUtil;
import com.util.pay.ali.AliPay;

@Controller
@RequestMapping("/ali")
public class AliPayController {
	
	@RequestMapping("/pay")
	@ResponseBody
	public  ResponseEntity<HttpEntity> AliPayOrderPay(Model model,String total_price,String orderbody){
		try {
			orderbody=URLDecoder.decode(orderbody, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("------------------into  ali/pay,orderbody:"+orderbody+",total_price:"+total_price);
		AliPay aliPay=new AliPay();
    	
    	OrderNoUtil orderNoUtil=new OrderNoUtil();
    	String WIDout_trade_no=orderNoUtil.getOrderNo();//订单编号
    	String notify_url="http://ec14859664.iok.la:25972/payProject/alinotify/aliPayNotify";//回调函数
    	String return_url="http://ec14859664.iok.la:25972/payProject/alinotifypage/notifyPage";//通知页面
		return aliPay.Ali_Pay(model, WIDout_trade_no, orderbody, total_price, orderbody, notify_url, return_url);
	} 

}
