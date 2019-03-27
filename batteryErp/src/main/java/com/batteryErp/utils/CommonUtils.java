package com.batteryErp.utils;

import com.alibaba.fastjson.JSONObject;
import com.star.common.redis.RedisManager;
import com.star.common.utils.CommonGlobal;
import redis.clients.jedis.Jedis;

import java.util.*;

public class CommonUtils
{
	/**
	 * 全局id生成方法，暂用UUID模拟。
	 * @param moduleName 业务名称
	 * @return
	 */
	public static String generateId(String moduleName)
	{

		return CommonGlobal.getUUID();
	}
	
	public static void sort(List<Object> list, final String paramname) {
		if(list != null && list.size() > 1) {
			Collections.sort(list, new Comparator<Object>() {

				@Override
				public int compare(Object o1, Object o2) {
					JSONObject obj1 = (JSONObject) o1;
					JSONObject obj2 = (JSONObject) o2;
					
					long longmm = DateUtil.getLong(obj1.getString(paramname), DateUtil.FORMAT_DETAIL) - DateUtil.getLong(obj2.getString(paramname), DateUtil.FORMAT_DETAIL);
					
					int intmm = (int) longmm;
					return -intmm;
				}
				
			});
		}
	}

	public static Map<String,String> newStringHashMap(){
		return new HashMap<String,String>();
	}
	
	public static Jedis getJedis(int index)
	{
		Jedis jedis = RedisManager.getInstance().getJedis();
		jedis.select(index);
		return jedis;
	}
	
	public static Jedis getRecommendJedis()
	{
		return getJedis(7);
	}

	public static Jedis getActivityRedis()
	{
		return getJedis(8);
	}
	
	public static void releaseJedis(Jedis jedis)
	{
		if (jedis != null)
		{
			jedis.close();
		}
	}
	
	public static boolean getLock(String lockName, int seconds)
	{
		Jedis jedis = null;
		try
		{
			jedis = getRecommendJedis();
			long ret = jedis.setnx(lockName, "begin");
			if (0 == ret)
			{
				Long exp = jedis.ttl(lockName);
				if (exp == -1)
				{
					jedis.expire(lockName, seconds);
				}
				return false;
			}
			jedis.expire(lockName, seconds);
			return true;
			
		}
		finally
		{
			releaseJedis(jedis);
		}
	}
}
