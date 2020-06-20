package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.Message;
import entity.MessageVo;
import entity.User;
import service.MessageService;
import service.UserService;
import service.impl.MessageServiceImpl;
import service.impl.UserServiceImpl;

@Controller
public class UserController {
	UserService service = new UserServiceImpl();
	MessageService mservice = new MessageServiceImpl();
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam(value="id") int id,
							@RequestParam(value="pwd")String password,
							HttpServletRequest request,
							HttpSession session) {
		
		User user = service.getUserById(id);
		if (user.getPassword().equals(password)) {
			session.setAttribute("user", user);
			if (user.getLevel()>3) {
				return "redirect:/initTeacher.action";
			}else {
				return "redirect:/initStudent.action";
			}
		}else {
			return "error";
		}
	}
	
	
	@RequestMapping("/checkId")
	public void check(HttpServletResponse response,@RequestParam("id")int id) {
		User user =service.getUserById(id);
		System.out.println(user);
		String answer = "";
		if (user==null) {
			answer = "没有这个学号";
		}else {
			answer = "请继续输入密码";
		}
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(answer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@RequestMapping("/doUpdateId")
	
	public String doUpdateId(@RequestParam(value="id") String id,
			HttpServletRequest request,
			HttpSession session) {
		int i = new Integer(id);
		User user = service.getUserById(i);
		request.setAttribute("user", user);
		return "doUpdate";
	}
	@RequestMapping("/doUpdate")
	public String doUpdate(
			@RequestParam(value="id") String id,
			@RequestParam(value="name") String name,
			@RequestParam(value="age")String age,
			@RequestParam(value="address")String address,
			@RequestParam(value="tele")String tele,
			HttpServletRequest request
			) {
		 boolean flag = service.modify(id,name, age, address, tele);
		return "redirect:/initTeacher.action";
	}
	
	@RequestMapping("/delUser")
	public String delUser(@RequestParam(value="id",required = false) int id,
			HttpServletRequest request,
			HttpSession session){
		service.delUserById(id);
		return "redirect:/initTeacher.action";
	}
	
	@RequestMapping("/addUser")
	public String addUser(
			@RequestParam(value="name")String name,
			@RequestParam(value="pwd")String password,
			@RequestParam(value="address")String address,
			@RequestParam(value="age")int age,
			@RequestParam(value="tele")int tele,
			HttpServletRequest request,
			HttpSession session) {
		service.addUser(name, age, address, tele,password);
		return "redirect:/initTeacher.action";
	}
	@RequestMapping("/initTeacher")
	public String initTeacher(HttpServletRequest request,HttpSession session) {
		List<User> uList = service.getUser();
		List<MessageVo> unDeal=mservice.getUnDealedMsg();
		List<Message> agree=mservice.getAgreeMsg();
		List<Message> disagree=mservice.getRefMsg();
		
		request.setAttribute("ulist", uList);
		request.setAttribute("unDeal", unDeal);
		request.setAttribute("agree", agree);
		request.setAttribute("disagree", disagree);
		return "userInfo";
	}
	
	@RequestMapping("/initStudent")
	
	public String initStudent(HttpServletRequest request,HttpSession session) {
		boolean flag = false;
		User user = (User) session.getAttribute("user");
		List<Message> slist = mservice.getMessageById(user.getId());
		List<Message> mlist = mservice.getAllMessage();
		for (Message message : mlist) {
			if (user.getId()==message.getSidFrom()) {
				request.setAttribute("slist", slist);
				flag=true;
			}
		}
		if (flag) {
			return "student";
		}else {
			return "leaveMsg";
		}
	}
}
