<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.mycgv.vo.*"
    %>
<%
	SessionVO svo = (SessionVO)session.getAttribute("svo");

	//svo 객체 != null ==>> 로그인 성공!!
	//svo 객체 == null ==>> 로그인 하지 않은 상태
	if(svo != null){
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="http://localhost:9000/MyCGV/css/mycgv.css">
</head>
<body>
	<!-- header -->
	<jsp:include page="../header.jsp" />
	
	<!-- content -->
	<div class="content">
		<section class="section1">
			<div class="mypage">
				<h1>마이페이지</h1>
				<div>
					<span>정보수정</span>
					<span>예매확인</span>
				</div>
			</div>
		</section>		
	</div>
	
	<!-- footer -->
	<jsp:include page="../footer.jsp" />
</body>
</html>
<% }else{ %>
<script>
	alert("로그인을 진행하셔야 접근이 가능합니다.");
</script>
<% } %>













