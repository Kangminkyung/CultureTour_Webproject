<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<style type="text/css">

	
	table#personInfoTbl tr {
		/* line-height: 100%; */
	}
	
	table#personInfoTbl td {
		border: 0px solid gray;
		border-collapse: collapse;
		font-size: 10pt;
	}
	
	table#personInfoTbl td.title {
		text-align: justify;
		font-size: 10pt;
		font-weight: bold;
	}
</style> 




<style type="text/css">
	
   div.div_inlineblock {display: inline-block;
   						border: 0px solid red;
   						margin-top: 20px;}

   .th {text-align: center;}
   .td {text-align: center;}
   
   .namestyle {background-color: cyan;  /* cyan => 하늘색 */
      	       font-weight: bold;
    	       font-size: 12pt;
    	       color: blue;
    	       cursor: pointer; }
  
</style>

<script type="text/javascript">
  
  $(document).ready(function(){
	  
	/* 
	  $("#searchName").keyup(function(){
		// 사용자가 텍스트박스 안에서 키보드를 눌렀다가 up 했을 때 이벤트 발생함.
	  	
	  	var form_data = {searchName : $("#searchName").val() };
		
		
		$.ajax({
			url: "wordSearchJSON.do",
			type: "GET",
			data: form_data,
			dataType: "JSON",
			success: function(json) {
				
				if(json.length > 0) {
					
					var html = "";
					
					$.each(json, function(entryIndex, entry) {
						
						var word = entry.name.trim(); 
						 var index = word.toLowerCase().indexOf($("#searchName").val().toLowerCase());
						var str = "";
						var len = $("#searchName").val().length;
						str = "<span class='first' style='color: blue;'>" + word.substr(0, index) + "</span>" 
							+ "<span class='second' style='color: red; font-weight: bold;'>" + word.substr(index, len) + "</span>"
							+ "<span class='third' style='color: blue;'>" + word.substr(index+len) + "</span>";
						html += "<span style='cursor: pointer;'>" + str + "</span>" + "<br/>"; 
							
						//var word = entry.name.trim();
						
					//	html+=word+"<br>";  
						
						
						
					}); // end if $.each()
									
					$("#displayData").html(html);
					$("#displayData").show();
				} //end of if	
				else {
					// 검색된 데이터가 없는 경우
					$("#displayData").hide();
				}
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
			
			
		}); //end of ajax

	  }); // end of  $("#searchword").keyup(function(){
	   */
	  
		  
	  
		$("#displayData").click(function(event){
			var word = "";
			var $target = $(event.target);
			if($target.is(".first")){
				word = $target.text() + $target.next().text() + $target.next().next().text();
			}
			else if($target.is(".second")) {
				word =  $target.prev().text() + $target.text() + $target.next().text();
			}
			else if($target.is(".third")) {
				word =  $target.prev().prev().text() + $target.prev().text() + $target.text();
			} 
			 word = $target.text(); 
			$("#searchName").val(word);
			// 텍스트박스에 검색된 결과의 문자열을 입력해준다.
			
			$("#display").hide();
			
			goSearch();
			});
	
		
	  
	  
	  $("#searchName").val("${searchName}");
	  
	  $("#searchName").bind("keydown", function(event){
		  var keyCode = event.keyCode;
		  if(keyCode == 13) {
			  goSearch();
		  }
	  }); 
	  
	  
	  $(".name").bind("mouseover", function(event){
		  var $target = $(event.target);
		  $target.addClass("namestyle");
	  });
	  
	  
	  $(".name").bind("mouseout", function(event){
		  var $target = $(event.target);
		  $target.removeClass("namestyle");
	  });
	  
  });// end of $(document).ready()----------------------------------
	
	
	function gomemberSearch() {
			  
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
	 		  	var frm = document.memberFrm;
	 	
	 		  	frm.method="get";
	 		  	frm.action="memberlist.action";
	 		  	frm.submit();
	 	  }
	   }// end of function goSearch()-----------------------------------
	
</script>

	<div class="container">

		<h2 align="center">회원 정보</h2>
		<table class="table table-condensed">
			<thead>
				<tr>
					<th style="width: 24%;">ID</th>
					<th style="width: 25%;">이름</th>
					<th style="width: 50%;">Email</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="member" items="${memberList}">
					<tr>
						<td>${member.userid}</td>
						<td onClick="goDetail('${member.idx}');"><a style="cursor: pointer;" data-toggle="modal" data-target="#meberDetail" data-dismiss="modal">${member.name}</a></td>
						<td>${member.email}</td>
					</tr>
				</c:forEach>
				
				<c:if test="${empty memberList}">
				    <tr>
				       <td colspan="4" style="text-align: center">
				       		가입된 회원이 없습니다.
				       </td>
				    </tr>
				</c:if>
											
			<tr>
					<th colspan="4" style="text-align: center;">
							<nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
								<div align="center" style="width: 100%;"> 
									${pageBar}
								</div>
							</nav>
					</th>					
			</tr>
											
			</tbody>
		</table>
		
		<form name="memberFrm">
										
				<div align="center" class="input-group" style="display: inline-block; margin-left: 400px;">
					<select id="searchType" name="searchType">
						<option value="name">회원명</option>
						<option value="userid">아이디</option>
						<option value="email">이메일</option>
					</select>
					<input type="text" placeholder="이름 검색" name="searchName" id="searchName" >				
					<button class="btn btn-default" type="submit">
						<i class="glyphicon glyphicon-search" onClick="gomemberSearch();"></i>
					</button>			
					
				</div>

		</form>
			
		</div>
	
	<%-- 특정 회원의 정보를 조회후 변경/삭제/복구 해주는 폼생성하기 --%>
	<form name="idxFrm">
	<input type="hidden" name="idx" />
	<input type="hidden" name="goBackURL" />
	</form>

  
    <script>
    function goDetail(idx){
    	//alert("idx값"+idx);
    	var frm = document.idxFrm;
	    frm.idx.value = idx;
	 
	  //  frm.method = "get";
	//    frm.action = "memberDetail.do";
	//    frm.submit();

	$.ajax({
		
		url : "memberDetailJSON.action",
		type : "get",
		data : {"idx" :idx},
		dataType : "JSON",
		success : function(json){
			var html="";					
				html +=	"<table id='personInfoTbl' >"
					+"<tr> <td class='title' style='width:20%;'> 회원번호</td>"
					+"<td>"+json.idx+"</td>"
					+"</tr>"
					+"<tr> <td class='title'>성명</td>"
					+"<td>"+json.name+"</td>"
					+"</tr>"
					+"<tr> <td class='title'>아이디</td>"
					+"<td>"+json.userid+"</td>"
					+"</tr>"
					+"<tr> <td class='title'>이메일</td>"
					+"<td>"+json.email+"</td>"
					+"</tr>"
					+"<tr> <td class='title'>연락처</td>"
					+"<td>"+json.phone+"</td>"
					+"</tr>"
					+"<tr> <td class='title' >우편번호</td>"
					+"<td>"+json.post+"</td>"
					+"</tr>"
					+"<tr> <td class='title'>주소</td>"
					+"<td>"+json.addr+"</td>"
					+"<tr> <td class='title'>퀴즈등급</td>"
					+"<td>"+json.quizgrade+"</td>"
					+"<tr> <td class='title'>가입일</td>"
					+"<td>"+json.registerday+"</td>"
					+"</tr></table>";				
					

		
		
			
			$("#memberInfo").html(html);
	
			
		},
		error :  function(request, status, error){
			alert("code: " + request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);  
		}		
	});
	    
	    
	    
	    
    }
    </script>


     <%-- ****** 개인정보 보여주는  Modal ****** --%>
  <div class="modal fade" id="meberDetail" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" style="text-align:center;font-weight:bold;color:gray">개인정보</h4>
        </div>
        <div class="modal-body">
          <div id="memberDetail">
			<div align="center" id="memberInfo">
						
		
			</div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default myclose" data-dismiss="modal" >닫기</button>
        </div>
      </div>
      
    </div>
  </div>


</body>
</html>