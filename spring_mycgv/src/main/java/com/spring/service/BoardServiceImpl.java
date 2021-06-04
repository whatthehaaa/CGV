package com.spring.service;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv.dao.CgvBoardDAO;
import com.mycgv.vo.CgvBoardVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private CgvBoardDAO boardDAO;	
	
	public int getSelectDelete(String[] dellist) {
		return 0;
	}
	
	
	/** ����ȭ�� **/
	@Override
	public ModelAndView getUpdate(String id) {
		ModelAndView mv = new ModelAndView();
		CgvBoardVO vo = boardDAO.getContent(id);
						
		mv.addObject("vo",vo);
		mv.setViewName("/board/board_update");
		
		return mv;
	}
	
	
	
	/** ��ü ����Ʈ **/
	@Override
	public ModelAndView getList(String rpage, String param) {
		ModelAndView mv = new ModelAndView();
		
		int start = 0;
		int end = 0;
		int pageSize = 5; //�� �������� ��µǴ� row
		int pageCount = 1; //��ü ������ ��  : ��ü ����Ʈ row /�� �������� ��µǴ� row
		int dbCount = boardDAO.getListCount(); //DB���� �� ��ü�ο�� ���
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


		ArrayList<CgvBoardVO> list = boardDAO.getList(start, end);
		
		//board_list.jsp ���Ϸ� ������ ����
		mv.addObject("list", list);
		mv.addObject("dbCount", dbCount);
		mv.addObject("pageSize", pageSize);
		mv.addObject("reqPage", reqPage);
		mv.setViewName("/board/board_list");
		
		return mv;
	}
	
	/** �۾��� ó�� **/
	@Override
	public String getResultWrite(Object vo) {
		String result = "";
		
		//bfile, bsfile �̸��� �����ͼ�--> vo ���� 
		CgvBoardVO bvo = (CgvBoardVO)vo; 
		if(bvo.getFile1().getSize() != 0) {
			UUID uuid = UUID.randomUUID();	//�ߺ������� ���� ��� --> bsfile
			bvo.setBfile(bvo.getFile1().getOriginalFilename());
			bvo.setBsfile(uuid +"_"+bvo.getFile1().getOriginalFilename());			
		}		
		
		//DB����
		boolean dao_result = boardDAO.getInsert(bvo);		

		if(dao_result){
			//���� ����--> upload ������ ����(������ġ)
			//String path1 = request.getSession().getServletContext().getRealPath("/");
			//String path2 = "\\resources\\upload\\";
			System.out.println("savepath-->" + bvo.getSavepath());
			File file = new File(bvo.getSavepath()+bvo.getBsfile());
			try {
				bvo.getFile1().transferTo(file);
			}catch(Exception e) { e.printStackTrace();}
			
			result = "redirect:/board_list.do";
		}else{
			result = "errorPage";
		}
		
		return result;
		
	}
	
	/** ������ **/
	@Override
	public ModelAndView getContent(String bid, String param) {
		ModelAndView mv = new ModelAndView();
		
		//CgvBoardDAO dao = new CgvBoardDAO();
		CgvBoardVO vo = boardDAO.getContent(bid);
		boardDAO.getUpdateHits(bid);
		vo.getBcontent().replace("\r\n", "<br>");
		
		mv.addObject("vo",vo);
		mv.setViewName("/board/board_content");
		
		return mv;
	}
	
	/** ������Ʈ ó�� **/
	@Override
	public ModelAndView getResultUpdate(Object vo) {
		ModelAndView mv = new ModelAndView();
		
		//����üũ ��:���ο� ���ϼ���/��:�������� ����
		CgvBoardVO bvo = (CgvBoardVO)vo;
		boolean result = false;
		if(bvo.getFile1().getSize() != 0) {
			//���ο� ���� ����
			//bfile, bsfile --> bvo�߰�
			UUID uuid = UUID.randomUUID();
			bvo.setBfile(bvo.getFile1().getOriginalFilename());
			bvo.setBsfile(uuid+"_"+bvo.getFile1().getOriginalFilename());
		}	
		
		//DB����
		result = boardDAO.getUpdate(bvo);
		
		//upload ������ ������ ����
		//���� ����--> upload ������ ����(������ġ)
		if(result) {
			File file = new File(bvo.getSavepath()+bvo.getBsfile());
			try {
				bvo.getFile1().transferTo(file);
			}catch(Exception e) { e.printStackTrace();}
			
			mv.setViewName("redirect:/board_list.do");
		}else {
			mv.setViewName("errorPage");
		}
		
		return mv;
	}
	
	/** ���� ó�� **/
	@Override
	public ModelAndView getResultDelete(String bid) {
		ModelAndView mv = new ModelAndView();
		
		//String bid = request.getParameter("bid");
		//CgvBoardDAO dao = new CgvBoardDAO();
		boolean result = boardDAO.getDelete(bid);
		
		if(result){
			mv.setViewName("redirect:/board_list.do");
		}else{
			mv.setViewName("errorPage");
		}
		
		return mv;
		
	}
	
}











