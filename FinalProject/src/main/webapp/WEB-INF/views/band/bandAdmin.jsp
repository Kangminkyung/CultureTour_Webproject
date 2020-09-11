<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/jquery-ui-1.11.4.custom/jquery-ui.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/jquery-ui-1.11.4.custom/jquery-ui.js"></script> 

<style type="text/css">
  th {width: 25%;}
  .error {color: red; font-weight: bold; font-size: 9pt;}
</style>

<script type="text/javascript">
$(document).ready(function(){	  		  
	  

	  
	  $("#searchName").val("${searchName}");
	  
	  $("#searchName").bind("keydown", function(event){
		  var keyCode = event.keyCode;
		  if(keyCode == 13) {
			  goSearch();
		  }
	  }); 
	  
	  
	
});// end of $(document).ready()----------------------------------
	
	
	function goSearch() {
			  
	     if( $("#searchName").val().trim() == "") {
	 		// 검색어가 공백으로만 되었다면 
	 		   alert("검색어를 입력하세요!!");
	 		   $("#searchName").val("");
	 		   $("#searchName").focus();
	 		   return;
	 		 /*
	 		     javascript:history.go(-1); ==> 뒤로가기
	 		     javascript:history.go(1);  ==> 앞으로가기
	 		     javascript:history.go(0);  ==> 새로고침
	 		     
	 		     javascript:history.back();    ==> 뒤로가기
	 		     javascript:history.forward(); ==> 앞으로가기
	 		  */
	 	  }
	 	  else {
	 		  	var frm = document.BandmemberFrm;
	 	
	 		  	frm.method="get";
	 		  	frm.action="bandAdmin.action";
	 		  	frm.submit();
	 	  }
	   }// end of function goSearch()-----------------------------------
	   
	   
	   
	   
	   function joinallow(userid){
		   
		   var data_form = {"userid" : userid , bcode : ${bcode} };
		   
	       	$.ajax({
	       	
	       		url:"joinallow.action",
	       		 data : data_form,
	       		type:"GET",
	       		success:function(json){                			
	           	
	       			alert("밴드 가입 승인되었습니다!");
	       			
	       			location.reload();
	       			
	       		},
	       		error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}            		                		
	       		
	       	});
			   
		  
	   }// end of statsupdate(userid) --------------------------------
	   
	   
	   
	   
	   
	   
	   
</script>

<div class="container">
	<h3 style="padding-top: 20px; text-align: center;">밴드 관리</h3>	
	 	<form name="BandmemberFrm">
										
				<div class="input-group" style="display: inline-block;">
					<select id="searchType" name="searchType">
						<option value="name">회원명</option>
						<option value="userid">아이디</option>
						<option value="email">이메일</option>
					</select>
					<input type="text" placeholder="이름 검색" name="searchWord" id="searchWord" >
					<input type="hidden" name="bcode" value="${bcode}" >
									
					<button class="btn btn-default" type="submit">
						<i class="glyphicon glyphicon-search" onClick="goSearch();"></i>
					</button>					
						
				
				</div>

		</form>
		
			<!--  	<result property="RNO"  		column="rno" 			javaType="String" />
				<result property="USERID" 	 	column="userid" 		javaType="String" />
		 		<result property="NAME"  		column="name" 			javaType="String" />
		 		<result property="EMAIL"  		column="email" 			javaType="String" />
				<result property="REGISTERDAY"  column="registerday" 	javaType="String" />
		 		<result property="STATUS"  		column="status" 		javaType="String" /> -->
	 	<h2 align="center">밴드원 정보</h2>
		<table class="table table-condensed">
			<thead>
				<tr>				
					<th style="width:20%;">I D</th>
					<th style="width:20%;">이름</th>
					<th>이메일</th>
					<th style="width:27%;">가입일</th>
					<th>상 태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="member" items="${bandmemberList}">
					<tr>				
						<td>${member.USERID}</td>
						<td>${member.NAME}</td>
						<td>${member.EMAIL}</td>
						<td>${member.REGISTERDAY}</td>
						
						<c:if test="${member.STATUS ==0}">
							<td><a href="javascript:joinallow('${member.USERID}');">가입신청</a></td>
						</c:if>
						<c:if test="${member.STATUS == 1}">
							<td>가입완료</td>
						</c:if>
						<c:if test="${member.STATUS == -1} ">
							<td>탈퇴완료</td>
						</c:if>
						
					</tr>
				</c:forEach>
				
				<c:if test="${empty bandmemberList }">
				    <tr>
				       <td colspan="6">
				       		가입된 밴드원이 없습니다.
				       </td>
				    </tr>
				</c:if>											
				<tr align="center">
					<td colspan="5">
						<nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
							<div align="center" style="width: 100%;"> 
								${pageBar}
							</div>
						</nav>					
					</td>				
				</tr>
											
			</tbody>
		</table>
	
	
		<button type="button" class="btn btn-secondry btn-info" style="margin-right: 10px; margin-bottom: 10px;" onClick="javascript:history.back();">뒤로</button>
		<button type="button" class="btn btn-secondry btn-info" style="margin-right: 10px; margin-bottom: 10px;" onClick="javascript:location.href='<%= request.getContextPath() %>/bandList.action'">밴드목록</button> 
	

</div>











   