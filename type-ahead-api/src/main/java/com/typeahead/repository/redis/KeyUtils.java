package com.typeahead.repository.redis;


public abstract class KeyUtils {
	static final String UID = "uid:";

	public static String rateLimit(String uid, long expire) {
		return UID + uid + ":rate:" + expire;
	}

	public static String hits(String uid) {
		return UID + uid + ":hits";
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