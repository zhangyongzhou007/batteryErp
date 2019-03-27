package com.batteryErp.utils;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MongoUtils {
	private static MongoUtils instance = new MongoUtils();

	private static MongoClient mongoClient = null;

	private static MongoDatabase webChatDB = null;

	private static AtomicBoolean init = new AtomicBoolean(false);

	private MongoUtils() {

	}

	public static synchronized MongoUtils getInstance() {
		if (init.compareAndSet(false, true)) {
			initMongo();
		}


		return instance;
	}

	private static void initMongo() {

		String dbhosts = PropertiesUtils.getInstance().getProperty("dbhosts"); // "192.168.0.16:27037,192.168.0.16:27038";
		// String dbports = dbConfig.getProperty("dbports");
		String dbname = PropertiesUtils.getInstance().getProperty("dbname");// "webchat";
		List<ServerAddress> hostlist = new ArrayList<ServerAddress>();
		if (dbhosts == null) {
		} else {
			String[] adbhosts = dbhosts.split(",");
			// String[] adbports = dbports.split(",");
			for (int i = 0; i < adbhosts.length; i++) {
				String temphost = adbhosts[i];
				String host = temphost.split(":")[0];
				int tempports = Integer.parseInt(temphost.split(":")[1]);
				hostlist.add(new ServerAddress(host, tempports));
			}

			mongoClient = new MongoClient(hostlist);

			webChatDB = mongoClient.getDatabase(dbname);

		}
	}

	public MongoDatabase getWebChatDB() {
		return webChatDB;
	}

}
