package com.spring.service;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv.dao.CgvNoticeDAO;
import com.mycgv.vo.CgvNoticeVO;

@Service("noticeService")
public class NoticeServiceImpl implements BoardService{
	
	@Autowired
	private CgvNoticeDAO noticeDAO;
	
	
	/**���û��� **/
	public int 	getSelectDelete(String[] dellist) {
		return noticeDAO.getSelectDelete(dellist);
	}	
		
	
	/** ����ȭ�� **/
	@Override
	public ModelAndView getUpdate(String id) {
		ModelAndView mv = new ModelAndView();
		CgvNoticeVO vo = noticeDAO.getContent(id);
		
		mv.addObject("vo", vo);
		mv.setViewName("/admin/notice/notice_update");
		
		return mv;		
	}
	
	

	
	
	
	/** ��ü����Ʈ **/
	@Override
	public ModelAndView getList(String rpage, String param) {
		ModelAndView mv = new ModelAndView();

		int start = 0;
		int end = 0;
		int pageSize = 5; //�� �������� ��µǴ� row
		int pageCount = 1; //��ü ������ ��  : ��ü ����Ʈ row /�� �������� ��µǴ� row
		int dbCount = noticeDAO.getListCount(); //DB���� �� ��ü�ο�� ���
		int reqPage = 1; //��û������
		
		//2-2. ��ü������ �� ���ϱ� - ȭ�����
		if(dbCount % pageSize == 0){
			pageCount = dbCount/pageSize;		
		}else{
			pageCount = dbCount/pageSize +1;
		}
		
		//2-3. start, end �� ���ϱ�
		if(rpage != null){
			reqPage = Integer.parseInt(rpage);
			start = (reqPage-1) * pageSize +1 ;
			end = reqPage*pageSize;	
		}else{
			start = reqPage;
			end = pageSize;
		}

		ArrayList<CgvNoticeVO> list = noticeDAO.getList(start, end);
		
		mv.addObject("list",list);
		mv.addObject("dbCount",dbCount);
		mv.addObject("reqPage",reqPage);
		mv.addObject("pageSize",pageSize);
		
		if(param.equals("user")) {
			mv.setViewName("/notice/notice_list");
		}else {
			mv.setViewName("/admin/notice/notice_list");
		}
		
		return mv;
	}
	
	@Override
	public String getResultWrite(Object vo) {
		String result_page="";

		//nfile, nsfile --> nvo
		CgvNoticeVO nvo = (CgvNoticeVO)vo;
		UUID uuid = UUID.randomUUID();
		if(nvo.getFile1().getSize() != 0) {
			nvo.setNfile(nvo.getFile1().getOriginalFilename());
			nvo.setNsfile(uuid+"_"+nvo.getFile1().getOriginalFilename());
		}
		//db ����
		boolean result = noticeDAO.getInsert(nvo);	
		
		if(result){
			//���� ����
			try {
				File file = new File(nvo.getSavepath()+nvo.getNsfile());
				nvo.getFile1().transferTo(file);
			}catch(Exception e) {e.printStackTrace();}
			
			result_page = "redirect:/admin/notice_list.do";
		}else{
			result_page = "errorPage";
		}
		
		return result_page;
	}
	
	@Override
	public ModelAndView getContent(String id, String param) {
		ModelAndView mv = new ModelAndView();
		
		//CgvNoticeDAO dao = new CgvNoticeDAO();
		CgvNoticeVO vo = noticeDAO.getContent(id);
		noticeDAO.getUpdateHits(id);		
		
		mv.addObject("vo", vo);
		
		if(param.equals("user")) {
			mv.setViewName("/notice/notice_content");
		}else {
			mv.setViewName("/admin/notice/notice_content");
		}
		
		return mv;		
		
	}
	
	@Override
	public ModelAndView getResultUpdate(Object vo) {
		ModelAndView mv = new ModelAndView();
		CgvNoticeVO nvo = (CgvNoticeVO)vo;
		boolean result = false;
				
		if(nvo.getFile1().getSize() !=0) {
			//nfile,nsfile
			UUID uuid = UUID.randomUUID();
			nvo.setNfile(nvo.getFile1().getOriginalFilename());
			nvo.setNsfile(uuid+"_"+nvo.getFile1().getOriginalFilename());
		}	
		//db
		result = noticeDAO.getUpdate(nvo);
		
		if(result){
			//server
			try {
				File file = new File(nvo.getSavepath()+nvo.getNsfile());
				nvo.getFile1().transferTo(file);
			}catch(Exception e) {e.printStackTrace();}
			
			mv.setViewName("redirect:/admin/notice_list.do");
		}else{
			mv.setViewName("errorPage");
		}
		
		return mv;
	}
	
	
	
	@Override
	public ModelAndView getResultDelete(String id) {
		ModelAndView mv = new ModelAndView();
		
		//CgvNoticeDAO dao = new CgvNoticeDAO();
		boolean result = noticeDAO.getDelete(id);
		
		if(result){
			mv.setViewName("redirect:/admin/notice_list.do");
		}else{
			mv.setViewName("errorPage");
		}
		
		return mv;
		
	}
	
}












