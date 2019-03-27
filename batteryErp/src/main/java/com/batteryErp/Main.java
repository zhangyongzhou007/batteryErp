package com.batteryErp;

import org.apache.log4j.Logger;

/**
 * 初始化
 * @ClassName: Main 
 * @Description: 
 * @author jiangchunlin
 * @date 2018-8-22 下午5:12:38
 */
public class Main {
	Logger log = Logger.getLogger(Main.class);
    
    public void init()
	{
    	
    	log.info("===================Begin to do init().=======================");

//		String webappPath = this.getClass().getClassLoader().getResource("../../").getPath();
//		SystemType systemtype = CommonGlobal.getSystemType();
//
//		if(systemtype.equals(SystemType.linux)){
//			webappPath =  File.separator + webappPath;
//		}
//		System.setProperty("home", webappPath);
//		log.info("home path:" + webappPath);
//
//    	String path =this.getClass().getClassLoader().getResource("/").getPath();
//		System.setProperty("home", path);
//		String mqPath  = "mqConfig.xml" ;
//
//		RouterManager.getInstance().init(mqPath);
		// XmppService.getInstance().start();

	}


}
