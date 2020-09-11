<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<style>
.btn-circle.btn-lg {
  width: 50px;
  height: 50px;
  padding: 10px 16px;
  font-size: 18px;
  line-height: 1.33;
  border-radius: 25px;
}
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box}
/* Full-width input fields */
input[type=text], input[type=password] {
    width: 100%;
    padding: 15px;
    margin: 5px 0 22px 0;
    display: inline-block;
    border: none;
    background: #f1f1f1;
}

/* Add a background color when the inputs get focus */
input[type=text]:focus, input[type=password]:focus {
    background-color: #ddd;
    outline: none;
}

/* Set a style for all buttons */

button:hover {
    opacity:1;
}


/* Float cancel and signup buttons and add an equal width */
.signupbtn {
  width: 30%;
  text-align: center;
}

/* Add padding to container elements */
.container {
    padding: 16px;
}

/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: #474e5d;
    padding-top: 50px;
}

/* Modal Content/Box */
.modal-content {
    background-color: #fefefe;
    margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
    border: 1px solid #888;
    width: 50%; /* Could be more or less, depending on sceereen size */
}


/* The Close Button (x) */
.close {
    position: absolute;
    right: 35px;
    top: 15px;
    font-size: 40px;
    font-weight: bold;
    color: #f1f1f1;
}

.close:hover,
.close:focus {
    color: #f44336;
    cursor: pointer;
}

/* Clear floats */
.clearfix::after {
    content: "";
    clear: both;
    display: table;
}

/* Change styles for cancel button and signup button on extra small screens */
@media screen and (max-width: 300px) {
    .cancelbtn, .signupbtn {
       width: 30%; 
    }
}


</style>
 <%-- <link href="<%=request.getContextPath() %>/resources/css/reset.css" media="screen" rel="stylesheet" type="text/css">
 <link href="<%=request.getContextPath() %>/resources/css/slickQuiz.css" media="screen" rel="stylesheet" type="text/css">
  <link href="<%=request.getContextPath() %>/resources/css/master.css" media="screen" rel="stylesheet" type="text/css"> --%>
  
  <script type="text/javascript">
  
  function show(){	  

	  var frm = document.quizupdate;
		 
		 var data_form = {userid : frm.userid.value};
		 
		 
		 var PopUp = window.open("quizDeatil.action", "퀴즈", "width=900, height=650, left=500, top=100");
		 
			$.ajax({
				url:"quizupdate.action",
				data: data_form,
				type: "POST",
				success: function(){
					
					if(PopUp) PopUp.location.href=response;
				
				}
			}); 
  		
			 window.location.reload();
  }
  
  </script>
  
<body>

<hr>

<div align="center" style="margin-bottom: 50px;">
	<h3>문화재 문제 풀기</h3>
	<p>문제를 풀기 원하시면 시작 버튼을 눌러주세요.</p> 
	<br>
	<!-- <button type="button" class="btn btn-info btn-circle btn-lg" onclick="document.getElementById('id01').style.display='block'" style="font-size: 10pt; text-align: center;">시작</button> -->
	
	  <h3>${sessionScope.loginuser.userid}님은 ${sessionScope.loginuser.quizgrade}단계입니다.</h3>
	   <c:if test="${sessionScope.loginuser.quiz == 0}"> 
	 		<button type="button" onClick="show();">문제풀기</button>
	  </c:if>
	   <c:if test="${sessionScope.loginuser.quiz == 1}">
	  	 	<span style="size: 10px;">오늘 퀴즈를 푸셨군요! 내일 다시 하실수있어요.</span>
	   </c:if> 
	   
</div>
	
	<form name="quizupdate">
		<input type="hidden" name="userid" value="${sessionScope.loginuser.userid}" />
	</form>
	
	</body>
</html>