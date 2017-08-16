package com.controller.notify.ali;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.util.pay.ali.AlipayNotify;


@Controller
@RequestMapping("/alinotifypage")
public class AliNotifyPage {
	
	@RequestMapping("/notifyPage")
	public ModelAndView notifyPage(HttpServletRequest request){
		System.out.println("into--------------------notifyPage");
		ModelAndView mv = new ModelAndView("success_page");
		
		 //获取支付宝GET过来反馈信息  
        Map<String,String> params = new HashMap<String,String>();  
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {  
            String name = (String) iter.next();  
            String[] values = (String[]) requestParams.get(name);  
            String valueStr = "";  
            for (int i = 0; i < values.length; i++) {  
                valueStr = (i == values.length - 1) ? valueStr + values[i]  
                        : valueStr + values[i] + ",";  
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化  
            try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}  
            params.put(name, valueStr);  
        }  
          
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//  
        //商户订单号  
  
//        String out_trade_no = request.getParameter("out_trade_no");  
  
        //支付宝交易号  
  
//        String trade_no = request.getParameter("trade_no");  
  
        //交易状态  
        String trade_status = request.getParameter("trade_status");  
          
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//  
          
        //计算得出通知验证结果  
        boolean verify_result = AlipayNotify.verify(params);  
          
        if(verify_result){//验证成功  
            //////////////////////////////////////////////////////////////////////////////////////////  
            //请在这里加上商户的业务逻辑程序代码  
        	System.out.println("++++++++++++++++++++++++页面跳转同步通知页面路径验证成功");
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——  
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){  
                //判断该笔订单是否在商户网站中已经做过处理  
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序  
                    //如果有做过处理，不执行商户的业务程序  
            }
            System.out.println("++++++++++++++++++++++++页面跳转同步通知页面路径结束");
            //该页面可做页面美工编辑  
//          out.println("验证成功<br />");  
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——  
  
            //////////////////////////////////////////////////////////////////////////////////////////  
        }else{  
        	System.out.println("++++++++++++++++++++++++页面跳转同步通知页面路径验证失败");
            //该页面可做页面美工编辑  
//          out.println("验证失败");  
        }  
		return mv;
		
	}

}
