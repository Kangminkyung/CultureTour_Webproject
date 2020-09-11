<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

	if(${requestScope.n == 1}) {
		alert("글삭제 성공 !!");
		location.href = "<%= request.getContextPath() %>/showNotice.action";
	}
	else {
		alert("글삭제 실패 !!");
		javascript:history.back();
	}
		
</script>