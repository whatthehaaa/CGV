<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import ="com.mycgv.dao.*"
    %>
<%
	String nid = request.getParameter("nid");
	CgvNoticeDAO dao = new CgvNoticeDAO();
	boolean result = dao.getDelete(nid);
	
	if(result){
		response.sendRedirect("notice_list.jsp");
	}else{
		response.sendRedirect("../../erroPage.jsp");
	}
%>    
