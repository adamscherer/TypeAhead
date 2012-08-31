package com.typeahead.repository.redis;

import java.util.Calendar;
import java.util.Date;

import com.annconia.util.DateUtils;

public abstract class KeyUtils {
	static final String UID = "uid:";

	public static String rateLimit(String uid, long expire) {
		return UID + uid + ":rate:" + expire;
	}

	public static String hits(String uid) {
		return UID + uid + ":hits";
	}

	public static String hitsHourly(String uid) {
		return UID + uid + ":hits:" + DateUtils.truncate(new Date(), Calendar.HOUR).getTime();
	}

	public static String credits(String uid) {
		return UID + uid + ":credits";
	}

	public static String apiKey(String auth) {
		return "api:" + auth;
	}

	public static String user(String name) {
		return "user:" + name + ":uid";
	}

	public static String users() {
		return "users";
	}

	public static String acounts() {
		return "acounts";
	}

	static String globalUid() {
		return "global:uid";
	}

	static String globalPid() {
		return "global:pid";
	}

}