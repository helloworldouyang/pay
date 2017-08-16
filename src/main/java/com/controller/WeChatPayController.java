package com.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.pay.OrderNoUtil;
import com.util.pay.util.EncodeUtil;
import com.util.pay.wechat.WeChatPay;

@Controller
@RequestMapping("/wechat")
public class WeChatPayController {
	
	
	
	@RequestMapping("/pay")
	@ResponseBody
	public Map WechatOrderPay(String body,String total_fee) throws Exception{
		
		int  totalPay=(int) (Float.parseFloat(total_fee)*100);
		String payTotal=String.valueOf(totalPay);
		body=URLDecoder.decode(body, "UTF-8");
		
		System.out.println("into----------wechatPay,body:"+body+",total_fee:"+payTotal);
		
		WeChatPay weChatPay=new WeChatPay();
		OrderNoUtil orderNoUtil=new OrderNoUtil();
		String OrderNo=orderNoUtil.getOrderNo();
		String notify_url="http://ec14859664.iok.la:25972/payProject/wechatnotify/notify";
		
		HashMap map=new HashMap();
		map.put("codeUrl", weChatPay.pay(body, payTotal, OrderNo, notify_url));
		return map;
	}
	
	
	/** 
     * 得到生成二维码图片并直接以流的形式输出到页面 
     * @param code_url 
     * @param response 
     */  
    @RequestMapping("/qr_codeImg")  
    public void getQRCode(String code_url,HttpServletResponse response){  
    	EncodeUtil encodeUtil=new EncodeUtil();
    	encodeUtil.encodeQrcode(code_url, response);  
    }  
}
