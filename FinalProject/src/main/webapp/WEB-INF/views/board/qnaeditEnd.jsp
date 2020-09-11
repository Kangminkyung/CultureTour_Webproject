<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

	if(${requestScope.n == 1}) {
		alert("글수정 성공 !!");
		location.href = "<%= request.getContextPath() %>/qnaview.action?seq=${seq}";
	}
	else {
		alert("글수정 실패 !!");
		location.href = "<%= request.getContextPath() %>/qnalist.action";
	}
		
</script>