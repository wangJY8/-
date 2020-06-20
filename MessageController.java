package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entity.Message;
import entity.User;
import service.MessageService;
import service.UserService;
import service.impl.MessageServiceImpl;
import service.impl.UserServiceImpl;

@Controller
public class MessageController {
	MessageService service = new MessageServiceImpl();
	
	UserService uservice = new UserServiceImpl();
	@RequestMapping("/leaveMsg")
	public String leaveMsg(@RequestParam("sidFrom")int sidFrom,@RequestParam("content")String content,
			HttpServletRequest request,
			HttpSession session) {
		String info ="";
		if (service.sendMsg(sidFrom ,content)) {
			info ="成功";
			System.out.println(info);
			List<Message> slist = service.getMessageById(sidFrom); 
			User u = uservice.getUserById(sidFrom);
			session.setAttribute("user",u);
			request.setAttribute("slist", slist);
		}else {
			info ="失败";
		}
		request.setAttribute("info", info);
		return "student";
	}
	//班主任操作
	@RequestMapping("msg/{type}/deal")
	public String agree(@RequestParam("mid")int mid,

			@PathVariable("type") String type,
			HttpServletRequest request) {
		String info ="";
		if (service.deal(mid, type)) {
			info="操作成功";
		}else {
			info = "操作失败";
		}
		request.setAttribute("info", info);
		return "redirect:/initTeacher.action";
	}
	
	@RequestMapping("/readMessage")
	public void readMessage() {
		service.readMessage();
	}
	
	@RequestMapping("/feedBack")
	public String feedBack(int mid,RedirectAttributes ra) {
		String info = "";
		if (service.feedBack(mid)) {
			info = "撤回成功";
		}else {
			info = "撤回失败";
		}
		ra.addFlashAttribute(info);
		return "redirect:/initStudent.action";
	}
}
