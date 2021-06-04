package com.spring.controller;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv.vo.CgvNoticeVO;
import com.spring.service.BoardService;
import com.spring.service.MemberService;

@Controller
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService noticeService;
	
	/**
	 * ����Ʈ - ���û��� ó��
	 */
	@RequestMapping(value="/admin/notice_list_del.do"
			, method=RequestMethod.GET)
	public ModelAndView notice_list_del(String chklist) {
		ModelAndView mv = new ModelAndView();
		
		//String chklist --> Array
		StringTokenizer st = new StringTokenizer(chklist,","); 
		String[] dellist = new String[st.countTokens()];
		for(int i=0;i<dellist.length;i++) {
			dellist[i] = st.nextToken();
		}
		
		int result = noticeService.getSelectDelete(dellist);
		
		mv.setViewName("redirect:/admin/notice_list.do");
		
		return mv;
	}
	
	
	/**
	 * �������� ���� - �������� ���� ó��		
	 * @return
	 */
	@RequestMapping(value="/admin/notice_delete_proc.do"
									, method=RequestMethod.GET)
	public ModelAndView notice_delete_proc(String nid) {	
		return noticeService.getResultDelete(nid);
	}
	
	/**
	 * �������� ���� - �������� ���� ó��		
	 * @return
	 */
	@RequestMapping(value="/admin/notice_update_proc.do"
									, method=RequestMethod.POST)
	public ModelAndView notice_update_proc(CgvNoticeVO vo, HttpServletRequest request) {
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		vo.setSavepath(root_path+attach_path);
		
		return noticeService.getResultUpdate(vo);
	}
	
	
	/**
	 * �������� ���� - �������� ���� ȭ��
	 * @return
	 */
	@RequestMapping(value="/admin/notice_delete.do", method=RequestMethod.GET)
	public ModelAndView notice_delete(String nid) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("nid", nid);
		mv.setViewName("/admin/notice/notice_delete");
		
		return mv;
	}
	
	
	/**
	 * �������� ���� - �������� ���� ȭ��
	 * @return
	 */
	@RequestMapping(value="/admin/notice_update.do", method=RequestMethod.GET)
	public ModelAndView notice_update(String nid) {		
		return noticeService.getUpdate(nid);
	}
	
	
	/**
	 * �������� ���� - �������� �۾��� ó��		
	 * @return
	 */
	@RequestMapping(value="/admin/notice_write_proc.do"
									, method=RequestMethod.POST)
	public String notice_write_proc(CgvNoticeVO vo, HttpServletRequest request) {
		//�������� ��λ���
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		vo.setSavepath(root_path+attach_path);
		
		return noticeService.getResultWrite(vo);
	}
		
	
	/**
	 * �������� ���� - �������� �� ����	
	 * @return
	 */
	@RequestMapping(value="/admin/notice_content.do", method=RequestMethod.GET)
	public ModelAndView notice_content(String nid) {				
		return noticeService.getContent(nid, "admin");
	}
	
	/**
	 * �������� ���� - �������� �۾���		
	 * @return
	 */
	@RequestMapping(value="/admin/notice_write.do", method=RequestMethod.GET)
	public String notice_write() {		
		return "/admin/notice/notice_write";
	}
	
	
	/**
	 * �������� ���� - �������� ��ü ����Ʈ		
	 * @return
	 */
	@RequestMapping(value="/admin/notice_list.do", method=RequestMethod.GET)
	public ModelAndView notice_list(String rpage) {		
		return noticeService.getList(rpage, "admin");
	}
	
	
	/**
	 * ȸ������ - ȸ�� ������		
	 * @return
	 */
	@RequestMapping(value="/admin/member_content.do", method=RequestMethod.GET)
	public ModelAndView member_content(String id) {		
		return memberService.getMemberContent(id);
	}
	
	
	/**
	 * ȸ������ - ȸ�� ��ü ����Ʈ			
	 * @return
	 */
	@RequestMapping(value="/admin/member_list.do", method=RequestMethod.GET)
	public ModelAndView member_list(String rpage) {		
		return memberService.getMemberList(rpage);
	}
	
	/**
	 * ������ ���� ������				
	 * @return
	 */
	@RequestMapping(value="/admin.do", method=RequestMethod.GET)
	public String admin() {
		return "/admin/member_list_ajax_proc.do",
				method=RequestMethod.GET,
				produces="text";
	}
}
