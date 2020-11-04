package web.test;

import web.common.MD5Util;
import web.service.userServiceImpl;

import java.io.IOException;

public class LoginTest {
	public static void main(String[] args) throws IOException {
	userServiceImpl user = new userServiceImpl();
	String username = "alice";
	String password = "123456";
		System.out.println(user.regis(username,MD5Util.md5(password)));
		/*
		if(user.login("alice","123456")) {
			System.out.println(user.role("alice"));
			System.out.print(user.getAuthList("alice"));
		}
		 */
	}
}
