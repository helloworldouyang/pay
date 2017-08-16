package com.util.framework;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware{
	
	
private static ApplicationContext appCtx; 
	
	/**
	 * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException
	{
		// TODO Auto-generated method stub
		System.out.println("ApplicationContextHelper------------>init");
		 appCtx = arg0; 
	}
	
	/**
	 * 获取ApplicationContext 
	 * @return
	 */
    public static ApplicationContext getApplicationContext(){  
        return appCtx;  
    }  
      
    /**
     * 这是一个便利的方法，帮助我们快速得到一个BEAN  
     * @param beanName
     * @return
     */
    public static Object getBean( String beanName ) {    
        return appCtx.getBean( beanName );    
    } 

}
