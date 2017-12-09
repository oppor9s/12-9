package com.user.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.entity.Administrtor;
import com.entity.Order;
import com.entity.Product;
import com.entity.Producttype;
import com.user.dao.AdminLoginDao;

@Service
public class AdminLoginService {
	@Resource
	private AdminLoginDao adminlogindao;
	public List<Administrtor> login(String name){
		List list=adminlogindao.login(name);
		return list;
	}
	public List<Producttype> type(){
		return adminlogindao.type();
	}
	public List<Order> order(){
		return adminlogindao.order();
	}
	public void delete(int id){
		adminlogindao.delete(id);
	}
	public List<Order> findorder(String name){
		return adminlogindao.findorder(name);
	}
	public void changeproduct(Product p){
		adminlogindao.changeproduct(p);
	}
}
