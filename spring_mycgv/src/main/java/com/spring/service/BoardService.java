package com.spring.service;

import org.springframework.web.servlet.ModelAndView;


public interface BoardService {
	ModelAndView getList(String rpage, String param);	//��ü����Ʈ
	String getResultWrite(Object vo);	//�۾���ó��
	ModelAndView getContent(String id, String param);	//������
	ModelAndView getResultUpdate(Object vo);//������Ʈ ó��
	ModelAndView getResultDelete(String id);	//����ó��
	ModelAndView getUpdate(String id);
	int getSelectDelete(String[] dellist);
}

