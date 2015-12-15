package com.user.rest;
import static spark.Spark.*;

import com.user.controller.UserController;

import user.com.service.UserService;
public class Main {

	 public static void main(String[] args) {
		 System.out.println("main was invoked");
		 // once the request is made we will create a instance of our controller
		 new UserController(new UserService()); 
	    }

}
