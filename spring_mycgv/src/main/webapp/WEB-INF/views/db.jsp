<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*" %>
    
<%
	//0. 드라이버 준비 - 현재프로젝트에 저장
	//1. 드라이버 로딩
	Class.forName("oracle.jdbc.driver.OracleDriver");
System.out.println("1단계 성공~");

	//2. Connection 객체 생성
	String url="jdbc:oracle:thin:@localhost:1521";
	String user="scott";
	String pass="tiger";
	Connection conn = DriverManager.getConnection(url,user,pass);
System.out.println("2단계 Connection 객체 생성~");

	//3. Statement(PreparedStatement) 객체 생성
	Statement stmt = conn.createStatement();
System.out.println("3단계 Statement ~");	

	//4. SQL 실행 - ResultSet 객체 생성
	String sql = "select empno, ename, hiredate from emp";
	ResultSet rs = stmt.executeQuery(sql); 
	
	//5. ResultSet 객체 생성되는 경우 - 데이터 가져오기
	while(rs.next()){
		//브라우저에 출력
		out.write("<p>");
		out.write(rs.getString(1));
		out.write(rs.getString(2));
		out.write(rs.getString(3));
		out.write("<p>");		
	}	
	
	//6. 객체 close
	if(rs != null) rs.close();
	if(stmt != null) stmt.close();
	if(conn != null) conn.close();
%>      
    
