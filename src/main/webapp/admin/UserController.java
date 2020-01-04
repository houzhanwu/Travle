package org.hnist.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.hnist.model.User;
import org.hnist.service.UserService;


//����spring mvc����һ����������
@Controller
public class UserController {
    @Autowired
    private UserService userService;
   
    @RequestMapping("/tologin")
	public String tologin() {
		return userService.tologin();	}
  	@RequestMapping("/login")
	public String login(@ModelAttribute User user, Model model, HttpSession session) {
		return userService.login(user, model, session);
	}
	@RequestMapping("/userlist")
	public String listallByPage(Model model, Integer pageCur, String act) {
		return userService.listallByPage(model,pageCur,act);	}
	
	@RequestMapping("/toadduser")
	public String toadduser(Model model) {
		model.addAttribute("user", new User());
		return userService.toaddUser();	}
	
	@RequestMapping("/useradd")
	public String addUser(User user, Model model, HttpSession session) {
		return userService.addUser(user, model, session);	}
	
	@RequestMapping("/userdel")
	public String deleteUser(Integer tid, Model model) {
		return userService.deleteUser(tid, model);	}
	
	
	@RequestMapping("/toedituser")
	public String toedituser(Integer tid, User user,Model model) {
		return userService.toeditUser(tid,user,model);	}
	
	@RequestMapping("/useredit")
	public String updateUserById(Integer tid, User user,Model model) {
		return userService.editUser(tid, user);}
}
