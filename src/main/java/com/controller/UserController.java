package com.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.entity.AliPay;
import com.entity.User;
import com.service.IUserService;
import com.util.framework.ApplicationContextHelper;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Resource
	private IUserService iUserService;
	
	
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public String getUserInfo(int id){
		
		User user=iUserService.getUserById(id);
		
		System.out.println("--------------------------:"+JSON.toJSONString(user));
		return user.getUserName();
	}
	
	
	
	/**
	 * 到阿里支付页面
	 */
	@RequestMapping("/toAliPay")
	public ModelAndView toAliPay(int id,String page){
		User user=iUserService.getUserById(id);
		ModelMap modelMap=new ModelMap();
		modelMap.put("name", user.getUserName());
		
		return new ModelAndView(page,modelMap);
	}
	
	/**
	 * 得到配置信息的测试
	 * @return
	 */
	@RequestMapping("/applicationtest")
	@ResponseBody
	public  String applicationtest(){
		AliPay aliPay=(AliPay)ApplicationContextHelper.getBean("alipay");
		System.out.println("aliPayOrederPaynotify_url:"+aliPay.getAliPayOrederPaynotify_url());
		return aliPay.getAliPayOrederPaynotify_url();
	}
	

}
