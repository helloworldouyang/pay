package com.controller.notify.ali;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.pay.ali.AliPay;

@Controller
@RequestMapping("/alinotify")
public class AliNotify {
	
	/**
	 * 支付宝回调函数
	 * @param request
	 * @return
	 */
	@RequestMapping("/aliPayNotify")
	@ResponseBody
	public String aliPayNotify(HttpServletRequest request){
		System.out.println("------------------into***aliPayNotify");
		AliPay aliPay=new AliPay();
		Map<String, Object> map=aliPay.notify(request);
		
		String sRet= (String) map.get("msg");
		System.out.println("out_trade_no:"+map.get("out_trade_no"));
		boolean bReti=false;
		if(sRet.equals("success")){
			System.out.println("支付成功，进行回调处理。");
			
		}
		return sRet;
	}

}
