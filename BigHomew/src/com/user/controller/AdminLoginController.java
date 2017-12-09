package com.user.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.entity.Administrtor;
import com.entity.Order;
import com.entity.Product;
import com.entity.Producttype;
import com.entity.Shopcar;
import com.user.service.AddProductService;
import com.user.service.AdminLoginService;
import com.user.service.FindProductService;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {
	@Resource
	private AdminLoginService adminloginservice;
	@Resource
	private AddProductService addpproductservice;
	@Resource
	private FindProductService findproductservice;
	//在后台展示订单  和  增加商品的商品类型
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Administrtor admin,Model model,HttpSession session){
		System.out.println(admin.getName());
		System.out.println(admin.getPassword());
		session.setAttribute("adminName", admin.getName());
		session.setAttribute("adminPassword", admin.getPassword());
		List<Administrtor> list=adminloginservice.login(admin.getName());
		
		if(list.get(0).getPassword().equals(admin.getPassword())){
			List<Producttype> ll=adminloginservice.type();
			List<Order> order=adminloginservice.order();
			model.addAttribute("order",order);
			model.addAttribute("type",ll);
			model.addAttribute("name1",list.get(0).getName1());
			
			List<Product> list1=findproductservice.findyproducttype("巨大");
			List<Product> list2=findproductservice.findyproducttype("巨小");
			List<Product> list3=findproductservice.findyproducttype("中");
			System.out.println(list1.get(0).getName());
			model.addAttribute("list1",list1);
			model.addAttribute("list2",list2);
			model.addAttribute("list3",list3);
			
		//	session.setAttribute("order1", order);
		//	session.setAttribute("type1", ll);
		//	session.setAttribute("name2", list.get(0).getName1());
		//	session.setAttribute("list11", list1);
		//	session.setAttribute("list22", list2);
		//	session.setAttribute("list33", list3);
			return "administrator";
		}
		
		return "";
	}
	//增加商品
	@RequestMapping(value="/addproduct",method=RequestMethod.POST)
	public String addproduct( @RequestParam(value="name") String name,@RequestParam(value="price") String price ,@RequestParam(value="type") String type,
		@RequestParam(value="file") MultipartFile file,HttpServletRequest request,Model model,HttpSession session) throws IOException {
		
		
		   String originalFilename = file.getOriginalFilename();   
		   System.out.println(originalFilename);
	        //上传图片            
	        //存储图片的物理路径  
		   String realPath=request.getSession().getServletContext().getRealPath("/");
		   System.out.println(realPath);
	        //新的图片名称  
	        String newFileName = originalFilename.substring(originalFilename.lastIndexOf("_"));  
	        System.out.println(newFileName);
	        //新图片  
	        File newFile = new File(realPath+"/img"+newFileName);  
	        //将内存中的数据写入磁盘  
	        file.transferTo(newFile);    

		Product p=new Product();
		p.setName(name);
		p.setPrice(price);
		p.setType(type);
		p.setImg(newFileName);
		addpproductservice.addproduct(p);
		List<Producttype> ll=adminloginservice.type();
		List<Order> order=adminloginservice.order();
		model.addAttribute("order",order);
		model.addAttribute("type",ll);
		model.addAttribute("name1",session.getAttribute("adminName"));
		
		List<Product> list1=findproductservice.findyproducttype("巨大");
		List<Product> list2=findproductservice.findyproducttype("巨小");
		List<Product> list3=findproductservice.findyproducttype("中");
		System.out.println(list1.get(0).getName());
		model.addAttribute("list1",list1);
		model.addAttribute("list2",list2);
		model.addAttribute("list3",list3);
	//	model.addAttribute("order",session.getAttribute("order1"));
	//	model.addAttribute("type",session.getAttribute("type1"));
	//	model.addAttribute("name",session.getAttribute("name2"));
	//	model.addAttribute("list1",session.getAttribute("list11"));
	//	model.addAttribute("list2",session.getAttribute("list22"));
	//	model.addAttribute("list3",session.getAttribute("list33"));		
		return "administrator";
	}
	//根据产品类类型查找商品进行删除
	@RequestMapping(value="/deleteproduct",method=RequestMethod.GET)
	public String findbyproducytype(@RequestParam(value="id") int id,Model model,HttpSession session){
		System.out.println(id);
		if(id!=0){
		findproductservice.deleteproduct(id);}
	 	List<Producttype> ll=adminloginservice.type();
		List<Order> order=adminloginservice.order();
		model.addAttribute("order",order);
		model.addAttribute("type",ll);
		model.addAttribute("name",session.getAttribute("adminName"));
		
		List<Product> list1=findproductservice.findyproducttype("巨大");
		List<Product> list2=findproductservice.findyproducttype("巨小");
		List<Product> list3=findproductservice.findyproducttype("中");
		System.out.println(list1.get(0).getName());
		model.addAttribute("list1",list1);
		model.addAttribute("list2",list2);
		model.addAttribute("list3",list3);
		
	//	model.addAttribute("order",session.getAttribute("order1"));
	//	model.addAttribute("type",session.getAttribute("type1"));
	//	model.addAttribute("name",session.getAttribute("name2"));
	//	model.addAttribute("list1",session.getAttribute("list11"));
	//	model.addAttribute("list2",session.getAttribute("list22"));
	//	model.addAttribute("list3",session.getAttribute("list33"));
		return "administrator";
	}
	//查看订单详情
	@RequestMapping(value="/ordermessage",method=RequestMethod.GET)
	public String ordermessage(@RequestParam(value="name") String name,Model model){
		List<Shopcar> list=findproductservice.findByUserName(name);
		model.addAttribute("list",list);
		return "ordermessage";
	}
	//删除订单
	@RequestMapping(value="/deleteorder",method=RequestMethod.GET)
	public String deleteorder(@RequestParam(value="id") int orderid,Model model,HttpSession session){
		System.out.println(orderid);
		adminloginservice.delete(orderid);
		List<Producttype> ll=adminloginservice.type();
		List<Order> order=adminloginservice.order();
		model.addAttribute("order",order);
		model.addAttribute("type",ll);
		model.addAttribute("name",session.getAttribute("adminName"));
		
		List<Product> list1=findproductservice.findyproducttype("巨大");
		List<Product> list2=findproductservice.findyproducttype("巨小");
		List<Product> list3=findproductservice.findyproducttype("中");
		System.out.println(list1.get(0).getName());
		model.addAttribute("list1",list1);
		model.addAttribute("list2",list2);
		model.addAttribute("list3",list3);
		//model.addAttribute("order",session.getAttribute("order1"));
		//model.addAttribute("type",session.getAttribute("type1"));
		//model.addAttribute("name",session.getAttribute("name2"));
		//model.addAttribute("list1",session.getAttribute("list11"));
		//model.addAttribute("list2",session.getAttribute("list22"));
		//model.addAttribute("list3",session.getAttribute("list33"));
		return "administrator";
	}
	//根据用户名查询订单 或 根据商品名查找商品
	@RequestMapping(value="/findorder",method=RequestMethod.POST)
	public String findorder(@RequestParam(value="findorder") String name,Model model){
		System.out.println(name);
		List<Order> list= adminloginservice.findorder(name);
		if(list.size()!=0){
			model.addAttribute("list",list);
			return "findOrder";
		}else{
			List<Product> list1= findproductservice.findbyname(name);
			List<Producttype> ll=adminloginservice.type();
			model.addAttribute("type",ll);
			model.addAttribute("p",list1.get(0));
		return "findProduct";
		}
	}
	//修改商品
	@RequestMapping(value="/changeproduct",method=RequestMethod.POST)
	public String changeproduct(Product p,Model model,HttpSession session){
		System.out.println(p.getName());
		adminloginservice.changeproduct(p);
		List<Producttype> ll=adminloginservice.type();
		List<Order> order=adminloginservice.order();
		model.addAttribute("order",order);
		model.addAttribute("type",ll);
		model.addAttribute("name1",session.getAttribute("adminName"));
		
		List<Product> list1=findproductservice.findyproducttype("巨大");
		List<Product> list2=findproductservice.findyproducttype("巨小");
		List<Product> list3=findproductservice.findyproducttype("中");
		System.out.println(list1.get(0).getName());
		model.addAttribute("list1",list1);
		model.addAttribute("list2",list2);
		model.addAttribute("list3",list3);
		return "administrator";
	}
}
