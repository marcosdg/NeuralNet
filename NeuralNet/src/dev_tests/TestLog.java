package dev_tests;

import utils.Log;


public class TestLog {

	public static void main(String[] args) {
		Log log = new Log(TestLog.class.getName());
		log.log("Hello!");
		log.log("This is an example");
	}

}
