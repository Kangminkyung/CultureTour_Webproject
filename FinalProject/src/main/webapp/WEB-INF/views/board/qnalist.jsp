<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	
	/* ==== #143. 파일첨부가 되었으므로 테이블의 가로폭 늘이기 ==== */
	
	a{text-decoration: none;}
	
	table {
    border-collapse: collapse;
    border-spacing: 0;
    width: 70%;
	}
	
	th, td {
	    text-align: left;
	    padding: 16px;
	    height: 50px;
	}
	
	tr:nth-child(even) {
	    background-color: #f2f2f2
	}
	
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
		
		frm.action = "qnaview.action";
		frm.method = "get";
		frm.submit();
	}// end of function goView()---------------------
	
	
	function goSearch() {
		
		var frm = document.searchFrm;
	
		frm.action = "<%= request.getContextPath() %>/qnalist.action"; 
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

<div align="center" style="margin-top: 40px;">  
	<h3 style="margin-bottom: 30px;">질문게시판</h3>
	<div align="right" style="margin-bottom: 30px; margin-right: 300px;"> 
	<button type="button" style="background-color: #468EDE; color: #ffffff;" onclick="javascript:location.href='<%=request.getContextPath()%>/addqna.action'">글쓰기</button>	
	</div>
	<table id="table" class="table table-hover" style="width: 70%;"> 	 
		<thead>
			<tr>
				<th style="width: 70px;  text-align: center">글번호</th>
				<th style="width: 360px; text-align: center">제목</th>
				<th style="width: 70px;  text-align: center">성명</th>
				<th style="width: 180px; text-align: center">날짜</th>
				<th style="width: 70px;  text-align: center">조회수</th>
				
				<!-- ==== #144. 파일과 크기를 보여주도록 수정 ==== --> 
				<th style="width: 70px; text-align: center">파일</th>
				<th style="width: 100px;  text-align: center">크기(bytes)</th>
				
			</tr>
		</thead>
		<tbody>
		
		<c:if test="${empty boardList}">
			<tr>
				<td colspan="7" style="text-align: center">질문내용이 없습니다!</td>
			</tr>
		</c:if>
		
		
			<c:forEach var="boardvo" items="${boardList}">
				<tr>
					<td style="text-align: center;">${boardvo.seq}</td>
					<td>
					    <%-- ==== #128. 글제목에 댓글의 갯수를 붙이도록 한다.
					                                     답변글인 경우 제목앞에 공백(여백)과 함께 Re 라는 글자를 붙이도록 한다.--%>
					    <!-- 답변글이 아닌 원글인 경우 -->
					    <c:if test="${boardvo.fk_seq == 0}">
						    <c:if test="${boardvo.commentCount > 0}">
						        <span class="subject" onClick="goView('${boardvo.seq}');">${boardvo.subject} <span style="color: red; font-weight: bold; font-style: italic; font-size: smaller; vertical-align: super;"> [${boardvo.commentCount}]</span></span> 
						    </c:if>
						    <c:if test="${boardvo.commentCount == 0}">
						        <span class="subject" onClick="goView('${boardvo.seq}');">${boardvo.subject}</span>
						    </c:if>
					    </c:if>
					    
					    <!-- 답변글인 경우 -->
					    <c:if test="${boardvo.fk_seq > 0}">
						    <c:if test="${boardvo.commentCount > 0}">
			  			        <span class="subject" onClick="goView('${boardvo.seq}');"><span style="color: red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;&nbsp;</span>${boardvo.subject}<span style="color: red; font-weight: bold; font-style: italic; font-size: smaller; vertical-align: super;">[${boardvo.commentCount}]</span></span> 
						    </c:if>
						    <c:if test="${boardvo.commentCount == 0}">
						        <span class="subject" onClick="goView('${boardvo.seq}');"><span style="color: red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;&nbsp;</span>${boardvo.subject}</span>
						    </c:if>
					    </c:if>
					    
					</td>
					<td style="text-align: center;">${boardvo.name}</td>
					<td style="text-align: center;">${boardvo.regDate}</td>
					<td style="text-align: center;">${boardvo.readCount}</td>
					
					<!-- ==== #145. 첨부파일 여부 표시하기 ==== -->
					<td style="text-align: center;">
						<c:if test="${not empty boardvo.fileName}">  
							<img src="<%= request.getContextPath() %>/resources/img/disk.png" />      
						</c:if>
					</td>
					<td style="text-align: center;">
						<c:if test="${not empty boardvo.fileSize}">  
							${boardvo.fileSize}  <!-- 파일크기 -->
						</c:if>
					</td>  
					
				</tr>
			</c:forEach>			
		</tbody>
	</table>
			
	<form name="goViewFrm">
		<input type="hidden" name="seq" />
	</form>
	
	<%-- ==== #117. 페이지바 보여주기 ==== --%>
	<nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
		<div align="center" style="width: 100%; margin-top: 30px;"> 
			${pageBar}
		</div>
	</nav>
	
	<%-- ==== #105. 글검색 폼 추가하기 : 글제목, 글내용, 글쓴이로 검색하도록 한다. ==== --%> 
	<div style="margin-top: 30px; margin-bottom: 30px;"> 
		<form name="searchFrm">
			<select name="colname" id="colname" style="height: 26px;"> 
				<option value="subject">글제목</option>
				<option value="content">글내용</option>
				<option value="name">글쓴이</option>
			</select>
			<input type="text" name="search" id="search" size="40" />&nbsp;&nbsp;
			<button type="button" onClick="goSearch();">검색</button>
		</form>
	</div>	
	
</div>







    