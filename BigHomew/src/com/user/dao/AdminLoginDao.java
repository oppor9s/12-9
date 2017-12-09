package com.user.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.entity.Administrtor;
import com.entity.Order;
import com.entity.Product;
import com.entity.Producttype;

@Repository
public class AdminLoginDao {
	@Resource
	private SessionFactory sessionFactory;
	//管理员登陆
	public List<Administrtor> login(String name){
		Query query=this.sessionFactory.getCurrentSession().createQuery("from Administrtor where name=?");
		 query.setParameter(0, name);
		 List list=query.list();
		return list;
	}
	//查询产品类型
	public List<Producttype> type(){
		Query query=this.sessionFactory.getCurrentSession().createQuery("from Producttype");
		List<Producttype> list=query.list();
		
		return list;
	}
	//查询订单
	public List<Order> order(){
		Query query=this.sessionFactory.getCurrentSession().createQuery("from Order");
		List<Order> list=query.list();
		return list;
	}
	//增加商品
	//删除订单
	public void delete(int id){
		Query query=this.sessionFactory.getCurrentSession().createQuery("delete from Order where id = ?");
		query.setParameter(0, id);
		query.executeUpdate();
	}
	//根据用户名查找订单
	public List<Order> findorder(String name){
		Query query=this.sessionFactory.getCurrentSession().createQuery("from Order where userName=?");
		query.setParameter(0, name);
		List<Order> list=query.list();
		return list;
	}
	//修改商品
	public void changeproduct(Product p){
		System.out.println(p.getName()+"ooooo");
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		session.update(p);
		tx.commit();
		session.close();
	}
}
