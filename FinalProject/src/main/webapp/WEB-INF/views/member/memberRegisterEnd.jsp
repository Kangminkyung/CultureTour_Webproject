<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <script type="text/javascript">
  
  
  	if( ${sessionScope.n != null && requestScope.gobackURL == null}  ){
  		alert("회원가입 성공!");  	
  		location.href="<%=request.getContextPath()%>/index.action";
  
  	}else if( ${sessionScope.n != null && requestScope.gobackURL != null} ){
  		alert("회원가입 성공!");  	
  		location.href="${gobackURL}";
  	}else{
  		alert("회원가입 실패!");
  		javascript:history.back(); // 이전 페이지로 이동~
  	}
  
  
  
  </script>