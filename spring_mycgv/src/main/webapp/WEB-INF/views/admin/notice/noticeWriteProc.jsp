<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.mycgv.vo.*, com.mycgv.dao.*" 
    %>
    
<%	request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="vo"  class="com.mycgv.vo.CgvNoticeVO" /> 
<jsp:setProperty name="vo" property="*" />  

<%
	CgvNoticeDAO dao = new CgvNoticeDAO();
	boolean result = dao.getInsert(vo);
	if(result){
		response.sendRedirect("notice_list.jsp");
	}else{
		response.sendRedirect("../../errorPage.jsp");
	}
%> 


    
