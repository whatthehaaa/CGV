package com.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycgv.dao.CgvMemberDAO;
import com.mycgv.vo.CgvMemberVO;
import com.mycgv.vo.SessionVO;
import com.spring.service.MemberServiceImpl;

@Controller
public class LoginController {
	

	@Autowired
	MemberServiceImpl memberService;
	
	/**
	 * �α׾ƿ�
	 */
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		SessionVO svo = (SessionVO)session.getAttribute("svo");
		String result = "";
		
		if(svo != null) {
			session.invalidate();
			result = "index";
		}else {
			result = "errorPage";
		}
		
		return result;
	}
	
	
	/**
	 * �α��� ó��
	 */
	@RequestMapping(value="/login_proc.do", method=RequestMethod.POST)
	public String login_proc(CgvMemberVO vo, HttpSession session) {
		return memberService.getResultLogin(vo, session);
	}
	
	

	/**
	 * �α��� ����� ȭ��
	 * @return
	 */
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "/login/login";
	}
	
	
}














