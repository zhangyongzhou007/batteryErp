package com.batteryErp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils
{
	private static PropertiesUtils instance = new PropertiesUtils();
	
	private Properties dbConfig = new Properties();
	
	private String path = "";
	public static PropertiesUtils getInstance()
	{
		return instance;
	}
	
	public String getFullName(String fileName){
		return path + fileName;
	}
	
	private PropertiesUtils()
	{
		load();
	}
	
	public void load(){
		try
		{
			path =this.getClass().getClassLoader().getResource("/").getPath();
//			String path = "";
			String gatewayPropPath = path + "batteryErp.properties";
			
			dbConfig.load(new FileReader(new File(gatewayPropPath)));
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}
		
	}
	
	public String getProperty(String key)
	{
		return dbConfig.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue)
	{
		return dbConfig.getProperty(key, defaultValue);
	}
}
