<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  
  
  <script type="text/javascript">
  
  
  	if( ${sessionScope.loginuser != null && requestScope.gobackURL == null}  ){
  		alert("로그인 성공!");  	
  		location.href="<%=request.getContextPath()%>/index.action";
  
  	}else if( ${sessionScope.loginuser != null && requestScope.gobackURL != null} ){
  		alert("로그인 성공!");  	
  		location.href="${gobackURL}";
  	}else{
  		alert("로그인 실패!");
  		javascript:history.back(); // 이전 페이지로 이동~
  	}
  
  	
  	
  
  
  </script>