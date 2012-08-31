package com.typeahead.repository.redis;

abstract class KeyUtils {
	static final String UID = "uid:";

	static String hits(String uid) {
		return UID + uid + ":following";
	}

	static String credits(String uid) {
		return UID + uid + ":followers";
	}

	static String apiKey(String auth) {
		return "api:" + auth;
	}

	public static String user(String name) {
		return "user:" + name + ":uid";
	}

	static String users() {
		return "users";
	}

	static String acounts() {
		return "acounts";
	}

	static String globalUid() {
		return "global:uid";
	}

	static String globalPid() {
		return "global:pid";
	}

}