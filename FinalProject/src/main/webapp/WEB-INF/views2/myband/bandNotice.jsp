<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	.subjectstyle {font-weight: bold;
	               color: navy;
	               cursor: pointer;}
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		$(".subject").hover(function(event){
							 var $target = $(event.target);
							 $target.addClass("subjectstyle");
		                    }, function(event){
		                     var $target = $(event.target);
							 $target.removeClass("subjectstyle");
		                    }
		);
		
		searchKeep();
		
	}); // end of $(document).ready()-------------------------
	
	
	function goView(seq) {
		
		var frm = document.goViewFrm;
		frm.seq.value = seq;
		
		frm.action = "<%= request.getContextPath() %>/bandNoticeView.action";
		frm.method = "get";
		frm.submit(); 
	}// end of function goView()---------------------
	
	
	function goSearch() {
		
		var frm = document.searchFrm;
	
		frm.action = "<%= request.getContextPath() %>/bandNotice.action?bcode=${bocde}"; 
		frm.method = "GET";
		frm.submit();
	}
	
	
	function searchKeep() {
		<c:if test="${ (colname != 'null' && not empty colname) && (search != 'null' && not empty search) }">       
		   $("#colname").val("${colname}");
		   $("#search").val("${search}");
		</c:if>
	}
	
</script>

<table class="table table-hover" style="width: 70%; margin-left: 50px;">
	<thead>
		<tr>
			<th>작성자</th>
			<th>글제목</th>
			<th>등록시간</th>
		</tr>
	</thead>
	
	<tbody>
		<c:if test="${empty bandNoticeList}">
			<tr>
				<td colspan="4" style="text-align: center; color: red;">등록된 공지가 없습니다.</td> 
			</tr>
		</c:if>
		<c:if test="${not empty bandNoticeList}">
		<c:forEach var="bandnoticevo" items="${bandNoticeList}">
			<tr> 
				<td>${bandnoticevo.name}</td>
				<td><span class="subject" onClick="goView('${bandnoticevo.seq}');">${bandnoticevo.subject}</span></td>
				<td>${bandnoticevo.regDate}</td>				
			</tr> 
		</c:forEach> 
		</c:if>
		
	</tbody>
</table>  

  <c:if test="${sessionScope.loginuser.userid == bandInfo.badmin}">
	
	<div style="margin-left: 550px; margin-top: 30px;">
	<button type="button" style="background-color: #468EDE; color: #FFFFFF;" onclick="javascript:location.href='<%= request.getContextPath() %>/addBandNotice.action?bcode=${bcode}'">글쓰기</button>
	</div>

</c:if>
<form name="goViewFrm">
	<input type="hidden" name="seq" />
</form>
	
<%-- ==== #117. 페이지바 보여주기 ==== --%>
<nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
	<div align="center" style="width: 80%; margin-top: 30px; margin-right: 500px;">  
		${pageBar}
	</div>
</nav>

<%-- ==== #105. 글검색 폼 추가하기 : 글제목, 글내용, 글쓴이로 검색하도록 한다. ==== --%> 
<!-- <div style="margin-top: 30px; margin-bottom: 30px;"> 
	<form name="searchFrm">
		<select name="colname" id="colname" style="height: 35px; font-size: 10pt;"> 
			<option value="subject">글제목</option> 
			<option value="content">글내용</option>
		</select>
		<input type="text" name="search" id="search" size="40" />&nbsp;&nbsp;
		<button type="button" onClick="goSearch();">검색</button>
	</form>
</div>	
 -->