<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>

	$(document).ready(function(){
		
		showRank();
		
		$("#displayData").hide();
		
		$("#cultureSearch").val("${cultureSearchWord}");
		
	   $("#cultureSearch").keyup(function(){
			// 사용자가 텍스트박스 안에서 키보드를 눌렀다가 up 했을 때 이벤트 발생함.
		  	var form_data = {cultureSearch : $("#cultureSearch").val() };
			
			$.ajax({
				url: "cultureSearchJSON.action",
				type: "GET",
				data: form_data,
				dataType: "JSON",
				success: function(json) {
					
					if(json.length > 0) {
						
						var html = "";
						
						$.each(json, function(entryIndex, entry) {
							
						//	alert(entry.culturename);						
							var word = entry.culturename.trim(); 
							var index = word.toLowerCase().indexOf($("#cultureSearch").val().toLowerCase());
							var str = "";
							var len = $("#cultureSearch").val().length;
							str = "<span class='first' style='color: blue;'>" + word.substr(0, index) + "</span>" 
								+ "<span class='second' style='color: red; font-weight: bold;'>" + word.substr(index, len) + "</span>"
								+ "<span class='third' style='color: blue;'>" + word.substr(index+len) + "</span>";
							html += "<span style='cursor: pointer;'>" + str + "</span><br/>"; 
								
						//	html+=word+"<br>";  
							
						}); // end if $.each()
										
						$("#displayData").html(html);
						$("#displayData").show();
					} //end of if	
					else {
						// 검색된 데이터가 없는 경우
						$("#displayData").hide();
					}
					
				}
			
			}); //end of ajax

		  }); // end of  $("#searchword").keyup(function(){
			  
	 	$("#displayData").click(function(event){
		 	var word = "";
			
			var $target = $(event.target);
			
			if($target.is(".first")) {
				word = $target.text() + $target.next().text() + $target.next().next().text(); 
			}
			else if($target.is(".second")) {
				word = $target.prev().text() + $target.text() + $target.next().text();
			}
			else if($target.is(".third")) {
				word = $target.prev().prev().text() + $target.prev().text() + $target.text();
			}
		
			$("#cultureSearch").val(word);
			// 텍스트박스에 검색된 결과의 문자열을 입력해준다.
			
			$("#displayData").hide();
			
			goSearch();
			});
	 
	  $("#cultureSearch").bind("keydown", function(event){
		  var keyCode = event.keyCode;
		  if(keyCode == 13) {
			  goSearch();
		  }
	  }); 
	 
	}); // end of ready 
	
	function goSearch() {
		  
			var cultureSearch = $("#cultureSearch").val();
			//alert(cultureSearch);
			var frm = document.frmcultureSearch;
		//$("#cultureSearch").val(word);
		//alert(cultureSearch);
			frm.frmCultureSearch.value = cultureSearch;
			frm.method="GET";
			frm.action="<%=request.getContextPath()%>/cultureSearch.action";
			frm.submit();
			
	}// end of function goSearch()-----------------------------------

	<%-- function cultureSearch(){
		var cultureSearch = $("#cultureSearch").val();
		//alert(cultureSearch);
		var frm = document.frmcultureSearch;;

		frm.frmCultureSearch.value = cultureSearch;
		frm.method="GET";
		frm.action="<%=request.getContextPath()%>/cultureSearch.action";
		frm.submit();
	}  --%>
		
	function logout(){
		 Kakao.Auth.logout();
	 }
	
	function showRank(){
		 
		getTableRank();
 
		 var timejugi = 1000*60;   // 시간을 10초 마다 자동 갱신하려고.
				
		setTimeout(function() {
				showRank();
					}, timejugi);
		 	
	 }
	
	function getTableRank(){
		  
		 $.ajax({
				
				url: "rankShowJSON.action",
				type: "GET",
				dataType : "JSON", 
				success : function(json){
					
					$("#displayTable").empty();

					var html = "<h3>실시간 검색 순위</h3>"
						html += "<table class='table table-hover' align='ceeenter' width='280px' height='180px' >";
						html += "<tr>";
						html += "<th class='myaligncenter' style='width:20%;'>등수</th>";
						html += "<th class='myaligncenter'>문화재명</th>";
						html += "</tr>";
					
					$.each(json, function(entryIndex, entry){
						
						html +="<tr>";
						html +="<td class='myaligncenter'>"+entry.RNO+"</td>";
						html +="<td class='myaligncenter'><a href=cultureDetail.action?idx="+entry.IDX+">"+entry.CCMANAME+"</a></td>";
						html +="</tr>";
				
					});
			
					
					
					html += "</table>";
					
					$("#displayTable").append(html);
					
				}
			
			});
			
	 }// end of getTableRank()
	
</script>


<!--Top Nav content-->
<div id="mySidenav" class="sidenav"> <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
  <nav>
    <ul>
      <li><span class="company-name">메뉴</span></li>
      <li><a href="index.action">홈</a></li>
      <c:if test="${sessionScope.loginuser != null && sessionScope.loginuser.userid ne 'admin'}">
      <li><a href="memberDetail.action?idx=${sessionScope.loginuser.idx}">나의정보</a>
        <ul class="sub-menu">
          <li><a href="memberEdit.action?idx=${sessionScope.loginuser.idx}" >개인정보수정</a></li>
          <li><a href="CultureWishList.action" >나의 관심문화재</a></li>   
          <li><a href="mybandList.action" >나의 밴드보기</a></li>   
        </ul>
	  </li>
      </c:if>
      <c:if test="${sessionScope.loginuser != null && (sessionScope.loginuser).userid eq 'admin'}">
     		 <li><a href="memberlist.action">회원정보보기</a></li>
      </c:if>
      <li><a>커뮤니티</a>
        <ul class="sub-menu">
         <li><a href="bandList.action" >밴드</a></li> 
         <li><a href="quiz.action" >퀴즈</a></li>
         <li><a href="wordCloud.action" >검색어차트</a>
        </ul>
      </li>
      <li><a>게시판</a>
        <ul class="sub-menu">
         <li><a href="showNotice.action" >공지사항</a></li> 
         <li><a href="qnalist.action" >자유게시판</a></li>
        </ul>
      </li>
      <c:if test="${sessionScope.loginuser!=null}">
      	<c:if test="${sessionScope.loginuser.userid eq 'admin'}">
      	<li><a href="policeList.action">신고댓글보기</a></li>
      	</c:if> 
      	<c:if test="${sessionScope.loginuser.userid ne 'admin'}">
      	<li><a href="memberDelete.action?idx=${sessionScope.loginuser.idx}">회원탈퇴</a></li>
      	</c:if>
      	<li><a href="logout.action">로그아웃</a></li>
      </c:if>
    </ul>
  </nav>  
  <hr>
  
  <br>
  &nbsp;&nbsp;<input type="text" id="cultureSearch" name="cultureSearch" value="${cultureSearchWord}" style="width: 185px;"></input>
	<button type="button" style="width: 3px; text-align: center; background-color: #468EDE; color: #FFFFFF;" onclick="goSearch();"><i class="fa fa-search"></i></button>
	<div id="display" style="width: 300px;  border: solid 0px gray; position: relative; left: 6.5%; top: 5px;" align="left">
		<div id="displayData" style="overflow:auto;height: 200px; border: solid 0px red;" ></div>
	</div>  

<div id="displayTable" style="min-width: 90%; margin-top: 50px; margin-bottom: 50px; padding-left: 20px; padding-right: 10px;"></div>

</div>

<!--Top Nav content-->
<div id="main-block">  
   
  <!-- Header section for center the head items add id "top-header-centre" --> 
  <header id="top-header" style="border-bottom: 0.5px solid #e6e6e6;"> 
    <div class="container">  
      <div class="row">    
        <div class="col-md-3 header-col-1"><a href="#" class="company-logo"></a></div>
        <div class="col-md-6  header-col-2"> <a href="#" class="company-logo"></a>  
          <div class="clearfix"></div>  
          <span class="company-name" style="font-size: 20pt;">문화재 탐방 사이트</span>            
          <img src="<%=request.getContextPath()%>/resources/img/index.png" class="center" onclick="<%= request.getContextPath() %>/index.action">        
          	<c:if test="${sessionScope.loginuser.userid == null}">  
          		<button type="button" onclick="javascript:location.href='<%=request.getContextPath()%>/login.action'" style="background-color: #E24B50; color: #ffffff; font-weight: bold;">로그인</button>
				<button type="button" onclick="javascript:location.href='<%=request.getContextPath()%>/memberform.action'" style="background-color: #468EDE; color: #ffffff; font-weight: bold;">회원가입</button>
          	</c:if>
			<c:if test="${sessionScope.loginuser.userid != null}">
				<p style="font-size: 15pt;"><span style="color: #002080; font-weight: bold;">${sessionScope.loginuser.name}</span> 님 환영합니다.</p>
          	</c:if>         
         
          </div>
        <div class="col-md-3 header-col-3"> <span  class="pull-right openmenu-nav" onclick="openNav()"><i class="fa  fa-bars "></i></span></div>
      </div>
    </div>
    
   <form name="frmcultureSearch">
  		<input type="hidden" name="frmCultureSearch" />
  </form>
  
  </header>
  <!-- /Header section --> 
  
  <div>
