package com.util.pay.wechat;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.util.pay.util.HttpUtil;
import com.util.pay.util.XMLUtil;




public class WeChatPay {
	
	/**
	 * 微信支付单位是分
	 * @param body 订单主体部分
	 * @param total_fee  支付价格
	 * @param OrderNo  订单编号
	 * @param notify_url  回调函数（必须有外网访问的）
	 * @return
	 * @throws UnknownHostException
	 */
	public String  pay(String body,String total_fee,String OrderNo,String notify_url) throws Exception{
		
		  // 账号信息  
        String appid = WeChatInfo.getAppid();  // appid  
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret  
        // 商业号  
        String mch_id = WeChatInfo.getMchid();  
        // key  
        String key = WeChatInfo.getKey();   
  
        String currTime = PayCommonUtil.getCurrTime();  
        String strTime = currTime.substring(8, currTime.length());  
        String strRandom = PayCommonUtil.buildRandom(4) + "";  
        //随机字符串  
        String nonce_str = strTime + strRandom;
        
        // 价格   注意：价格的单位是分  
        //String order_price = String.valueOf(getorder_price(Org_id)*100.0);
        // 商品名称  
        //String body = "企嘉科技商品";     
  
        InetAddress addr = InetAddress.getLocalHost();
    	String localip=addr.getHostAddress();//获得本机IP
		
		
    	  // 获取发起电脑 ip  
        String spbill_create_ip = WeChatInfo.getIP();
        String trade_type = "NATIVE";
        String time_start =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DATE, 1);
        String time_expire =  new SimpleDateFormat("yyyyMMddHHmmss").format(ca.getTime());
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body",body);
        packageParams.put("out_trade_no", OrderNo);
        //packageParams.put("total_fee", "1");
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", localip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        packageParams.put("time_start", time_start);
        packageParams.put("time_expire", time_expire);
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);
        packageParams.put("sign", sign);
        
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        System.out.println("请求xml：：：："+requestXML);
   
        String resXml = HttpUtil.postData(WeChatInfo.PAY_API, requestXML);
        System.out.println("返回xml：：：："+resXml);
          //
        Map map = XMLUtil.doXMLParse(resXml);
        //String return_code = (String) map.get("return_code");  
        //String prepay_id = (String) map.get("prepay_id");  
        String urlCode = (String) map.get("code_url");
        System.out.println("打印调用统一下单接口生成二维码url:::::"+urlCode);
        return urlCode;
	}
	
	
	
	/**
	    * 微信支付回调方法 
	    * @param request
	    * @param response
	    * @return Map<String, Object> msg 是否成功 price 价格 transaction_id 微信支付订单号 out_trade_no 内部主动生成的订单号与我们传给微信的订单号一致
	    * @throws Exception
	    */
	   public Map<String, Object> weChatNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{  
	      
	    //读取参数  
	    InputStream inputStream ;  
	    StringBuffer sb = new StringBuffer();  
	    inputStream = request.getInputStream();  
	    String s ;  
	    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
	    while ((s = in.readLine()) != null){
	        sb.append(s);  
	    }
	    in.close();  
	    inputStream.close();  
	  
	    //解析xml成map  
	    Map<String, String> m = new HashMap<String, String>();  
	    m = XMLUtil.doXMLParse(sb.toString());  
	      
	    //过滤空 设置 TreeMap  
	    SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
	    Iterator it = m.keySet().iterator();  
	    while (it.hasNext()) {  
	        String parameter = (String) it.next();  
	        String parameterValue = m.get(parameter);  
	          
	        String v = "";  
	        if(null != parameterValue) {  
	            v = parameterValue.trim();  
	        }  
	        packageParams.put(parameter, v);  
	    }  
	      
	    // 账号信息  
	       String key = WeChatInfo.getKey(); // key  
	       String out_trade_no = (String)packageParams.get("out_trade_no");  		//商户系统的订单号，与请求一致。
	       //logger.info(packageParams);  
	       //定义返回map集合
	       Map<String, Object> result=new HashMap<String,Object>();
	    //判断签名是否正确  
	    if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {
	        //------------------------------  
	        //处理业务开始  
	        //------------------------------  
	        String resXml = "";  
	        if("SUCCESS".equals((String)packageParams.get("result_code"))){  
	            // 这里是支付成功  
	            //////////执行自己的业务逻辑////////////////  
	            String mch_id = (String)packageParams.get("mch_id");  					//商户号
	            String openid = (String)packageParams.get("openid");  					//用户标识
	            String is_subscribe = (String)packageParams.get("is_subscribe");  		//是否关注公众账号
	            String bank_type = (String)packageParams.get("bank_type");  			//付款银行
	            String total_fee = (String)packageParams.get("total_fee");  		  	//订单金额
	            String transaction_id = (String)packageParams.get("transaction_id");  	//微信支付订单号
	            
	            double price=Integer.parseInt((String)packageParams.get("total_fee"))/100.0;
	            
	            
	            result.put("msg", true);
	            result.put("price", price);
	            result.put("transaction_id", transaction_id);
	            result.put("out_trade_no", out_trade_no);
	            
	            System.out.println("price:"+String.valueOf(price));
	            System.out.println("mch_id:"+mch_id);  
	            System.out.println("openid:"+openid);  
	            System.out.println("is_subscribe:"+is_subscribe);  
	            System.out.println("out_trade_no:"+out_trade_no);  
	            System.out.println("total_fee:"+total_fee);  
	            System.out.println("bank_type:"+bank_type);  
	            System.out.println("transaction_id:"+transaction_id);  
	            
	            
	          //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
             resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                     + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
	            //成功回调后需要处理预生成订单的状态和一些支付信息  
	        } else {  
	        	result.put("msg", false);
	            System.out.println("支付失败,错误信息：" + packageParams.get("err_code")+  
	                                "-----订单号：：："+out_trade_no+"*******支付失败时间：：：："  
	                    +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
	            
	            String err_code = (String)packageParams.get("err_code");  
	      
	             resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
	                     + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
	        
	        }  
	        //------------------------------  
	        //处理业务完毕  
	        //------------------------------  
	        BufferedOutputStream out = new BufferedOutputStream(
	                response.getOutputStream());
	        out.write(resXml.getBytes());
	        
	        out.flush();
	        out.close();
	    } else{
	    	result.put("msg", false);
	        System.out.println("通知签名验证失败---时间::::"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
	    }
		return result;
	   }
	
	
}
