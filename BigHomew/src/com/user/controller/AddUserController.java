package com.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.entity.LoginUser;
import com.entity.Order;
import com.user.service.AddUserService;
import com.user.service.AdminLoginService;

@Controller
@RequestMapping("/adduser")
public class AddUserController {
@Resource
private AddUserService addUserService;
@Resource
private AdminLoginService adminloginservice;
//注册新用户
@RequestMapping("/add")
	public String adduser(LoginUser loginUser){
		System.out.println(111111);
		addUserService.adduser(loginUser);
		return "index";
	}
//用户登陆
@RequestMapping("/login")
	public String login(LoginUser loginUser,HttpSession session){
		String name = loginUser.getName();
		List<LoginUser> list=addUserService.findName(name);
		if(list.get(0).getPassword().equals(loginUser.getPassword())){
			System.out.print("登陆成功");
			System.out.println(loginUser.getName());
			System.out.println(loginUser.getPassword());
			session.setAttribute("name",loginUser.getName());
			System.out.println(session.getAttribute("name"));
			return "index2";
		}else{
			System.out.print("登陆失败");
			return "regster";
		}	
	}
//修改个人信息
	@RequestMapping("/usermessage")
	public String message(Model model,HttpSession session){
		List<LoginUser> list1=addUserService.findName((String) session.getAttribute("name"));
		System.out.println("000000000000"+(String) session.getAttribute("name"));
		System.out.println("000000000000"+list1.get(0).getName());
		LoginUser user=new LoginUser();
		user.setAddress(list1.get(0).getAddress());
		user.setEmail(list1.get(0).getEmail());
		user.setId(list1.get(0).getId());
		user.setName(list1.get(0).getName());
		user.setPassword(list1.get(0).getPassword());
		user.setPhone(list1.get(0).getPhone());
		model.addAttribute("password0",list1.get(0).getPassword());
		model.addAttribute("user",user);
		List<Order> list2= adminloginservice.findorder((String) session.getAttribute("name"));
		model.addAttribute("list2",list2);
		return "personal";
	}
}
