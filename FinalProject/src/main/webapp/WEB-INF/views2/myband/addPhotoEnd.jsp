<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

	<c:if test="${n == 1}">
		alert("사진올리기 성공 !!");
	</c:if>

	<c:if test="${n != 1}">
		alert("사진올리기 실패 !!");
	</c:if>
	
	location.href = "<%= request.getContextPath() %>/photoList.action?bcode=${bcode}";
	// 글목록을 보여주는 페이지로 이동
	
</script> 