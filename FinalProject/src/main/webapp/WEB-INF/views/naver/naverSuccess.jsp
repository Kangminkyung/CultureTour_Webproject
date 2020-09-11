<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 


<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<style type="text/css">
html, div, body, h3 {
  margin: 0;
  padding: 0;
}

h3 {
  display: inline-block;
  padding: 0.6em;
}
</style>
<script type="text/javascript">
  $(document).ready(function() {
	var snsid = ${result}.response.id;
    var name = ${result}.response.name;
    var email = ${result}.response.email;
    $("#name").html("환영합니다. <br>"+name+"님");
    $("#email").html(email);

  
  	
    
    });
  //location.href = "${pageContext.request.contextPath}/";
   
  function out(){
	  self.close();
  }
  
  function naverRegister(){
	  
/* 	  var frm = document.naverFrm;
	  frm.naverEmail.value= ${result}.response.email;
	  frm.naverName.value= ${result}.response.name;
	  frm.naverID.value= ${result}.response.id;
	   */
	 
	  var data_form = {userid : ${result}.response.email,pwd:${result}.response.id,name:${result}.response.name};
	  
	  $.ajax({
		 
		  url:"naverRegister.action",
		  type:"POST",
		  data : data_form,
		  success:function(){
			 
				window.opener.location.href="index.action?p=1";
				self.close();
				
		  }
		  
		  
	  });
	   

   	
  
   		//window.opener.location.href="index.action";
		//self.close();
		
	  
	  
  }
  
  
</script>

</head>
<body>
  <div style="background-color: #15a181; width: 100%; height: 300px; text-align: center; color: white;">
  <h2 style="text-align: center" id="name"></h2>
	 <h2 style="text-align: center" id="name"></h2>
  	<h4 style="text-align: center" id="email"></h4>
  <br>
 
  	
	<c:if test="${n==0}">
	  	<h3>저희 사이트에 가입하시겠습니까?</h3>
	  	<br>
	  	<button type="button" onClick="naverRegister();">가입</button>
	  	<button type="button" onClick="out();">돌아가기</button>
  	</c:if>
  	<c:if test="${n==1}">
  		<script javascript="text/javascript">
  		
  	   		window.opener.location.href="index.action";

  			self.close();
  			
  		</script>
  	</c:if>
  	</div>
  
  	<form name="naverFrm">
  		<input type="hidden" name="naverEmail">
  		<input type="hidden" name="naverName">
  		<input type="hidden" name="naverID">
  		
  	</form>
 
 </body>