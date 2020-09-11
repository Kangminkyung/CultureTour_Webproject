<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
 <link href="<%=request.getContextPath() %>/resources/css/reset.css" media="screen" rel="stylesheet" type="text/css">
 <link href="<%=request.getContextPath() %>/resources/css/slickQuiz.css" media="screen" rel="stylesheet" type="text/css">
 <link href="<%=request.getContextPath() %>/resources/css/master.css" media="screen" rel="stylesheet" type="text/css">
  
  <script type="text/javascript">

  function exit(){
	  
	//  alert("gg");
	//  window.location.reload();
	   
	  self.close();
	  
	  
  }
  </script>
  
  	  <div id="slickQuiz">
		        <h1 class="quizName"><!-- where the quiz name goes --></h1>
		
		        <div class="quizArea">
		            <div class="quizHeader">
		                <!-- where the quiz main copy goes -->
		
		                <a class="button startQuiz" href="">시작!</a>
		            </div>
		
		            <!-- where the quiz gets built -->
		        </div>
		
		        <div class="quizResults">
		            <h3 class="quizScore">You Scored: <span><!-- where the quiz score goes --></span></h3>
		            <h3 class="quizLevel"><strong>Ranking:</strong> <span id="level"><!-- where the quiz ranking level goes --></span></h3>
					
		            <div class="quizResultsCopy">
		            <button type=button id="exitbutton" onClick="exit();">나가기</button>
		            
		                <!-- where the quiz result copy goes -->
		            </div>
		        </div>
		     
		        <script src="<%=request.getContextPath() %>/resources/js/jquery-1.7.min.js"></script>
		        <c:if test="${sessionScope.loginuser.quizgrade=='초급' }">
		        	<script src="<%=request.getContextPath() %>/resources/quiz/slickQuiz-config1.js"></script>
		        </c:if>
		        <c:if test="${sessionScope.loginuser.quizgrade=='중급' }">
		        	<script src="<%=request.getContextPath() %>/resources/quiz/slickQuiz-config2.js"></script>
		        </c:if>
		         <c:if test="${sessionScope.loginuser.quizgrade=='고급' }">
		        	<script src="<%=request.getContextPath() %>/resources/quiz/slickQuiz-config3.js"></script>
		        </c:if>
		        
		        <script src="<%=request.getContextPath() %>/resources/quiz/slickQuiz.js"></script>
		        <script src="<%=request.getContextPath() %>/resources/quiz/master.js"></script>
		    </div>
	