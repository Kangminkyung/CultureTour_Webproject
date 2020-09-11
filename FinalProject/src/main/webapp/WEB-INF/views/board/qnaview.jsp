<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script> 

<style type="text/css">
table, th, td {
	border: 0px solid gray;
}

#table, #table2 {
	width: 1000px; 
	border-collapse: collapse;
	padding: 10px;
}

#table th, #table td {
	padding: 5px;
	height: 50px;
}

#table th {
	width: 120px;
}

#table td {
	width: 860px;
}

a {
	text-decoration: none;
}

</style>

<script type="text/javascript">
	$(document).ready(function() {
		
	}); // end of $(document).ready()


	
	function goWrite() {
		
		var frm = document.addWriteFrm;
		
		var nameval = frm.name.value.trim();
		
		if(nameval == "") {
			alert("먼저 로그인 하세요!!");
			return;
		}
		
		var contentval = frm.content.value.trim();
		
		if(contentval == "") {
			alert("댓글 내용을 입력하세요!!");
			return;
		}
		
		var data_form = {userid : frm.userid.value,
		         name : frm.name.value,
		         content: contentval,
		         parentSeq: frm.parentSeq.value};

		
		$.ajax({
			url: "<%= request.getContextPath() %>/addComment.action",
			data : data_form,
			type: "POST",
			dataType: "JSON",
			success: function(json) {
				
				$.each(json, function(entryIndex, entry) {
					var html = "<tr>";
					html += "<td style='text-align: center;'>" + entry.name + "</td>";
					html += "<td>" + entry.content + "</td>";
					html += "<td style='text-align: center;'>" + entry.regDate + "</td>";
					html += "</tr>"
					
					$("#commentDisplay").prepend(html);
				});
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
		window.location.reload();
	}
	
	
	/* function goDetail(idx){
		
    	var frm = document.idxFrm;
	    frm.idx.value = idx;

		$.ajax({
		
			url : "memberDetail.action",
			type : "get",
			data : {"idx" :idx},
			dataType : "JSON",
			success : function(json){
				var html="";			
				html +=	"<table id='personInfoTbl' >"
					+"<tr> <td class='title' style='margin-right:10px;'> 회원번호</td>"
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
					+"<td>"+json.allHp+"</td>"
					+"</tr>"
					+"<tr> <td class='title' >우편번호</td>"
					+"<td>"+json.allPost+"</td>"
					+"</tr>"
					+"<tr> <td class='title'>주소</td>"
					+"<td>"+json.allAddr+"</td>"
					+"</tr></table>";			
			
				$("#memberInfo").html(html);	
				
			},
			error :  function(request, status, error){
				alert("code: " + request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);  
			}		
		}); 
    } */
    
    
    function goPolice(seq, userid) {
		var frm = document.policeFrm;
		frm.seq.value =  seq;
		frm.police_userid.value =  userid;
		
		swal({
			  title: "댓글을 신고하시겠습니까?",
			  text: "신고된 댓글은 관리자의 승인에 따라 강제 삭제됩니다.",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonClass: "btn-danger",
			  confirmButtonText: "Yes, 신고합니다!",
			  cancelButtonText: "No, 취소합니다!",
			  closeOnConfirm: false,
			  closeOnCancel: false
			},
			function(isConfirm) {
			  if (isConfirm) {
				  
				  frm.action = "<%= request.getContextPath()%>/policeComment.action";
				  frm.method = "POST";
				  frm.submit();
				  
			    /* swal("신고", "댓글이 정상적으로 신고 되었습니다!", "success"); */
			    
			  } else {
			    swal("취소", "댓글 신고가 취소되었습니다.", "error");
			  }
			});
		

	}
	
</script>

 <form name="policeFrm">
	<input type="hidden" name="seq" id="seq" />
	<input type="hidden" name="police_userid" id="police_userid" />
	
</form>

<div style="padding-left: 10%; margin-top: 50px; margin-left: 220px; min-height: 1000px;">

	<table class="table table-hover" id="table">
		<tr>
			<th>글번호</th>
			<td>${boardvo.seq}</td>
		</tr>

		<tr>
			<th>성명</th>
			<td>${boardvo.name}</td>
		</tr>

		<tr>
			<th>제목</th>
			<td>${boardvo.subject}</td>
		</tr>

		<tr>
			<th>내용</th>
			<td>${boardvo.content}</td>
		</tr>

		<tr>
			<th>조회수</th>
			<td>${boardvo.readCount}</td>
		</tr>

		<tr>
			<th>등록일자</th>
			<td>${boardvo.regDate}</td>
		</tr>
		
		<!-- ==== #147. 첨부파일 이름 및 파일크기를 보여주고 첨부파일을 다운받게 만들기 ==== -->
		<tr>
			<th>첨부파일</th>
			<td>
			<c:if test="${sessionScope.loginuser != null}">
				<a href="<%= request.getContextPath() %>/download.action?seq=${boardvo.seq}">${boardvo.orgFilename}</a>
			</c:if>
			<c:if test="${sessionScope.loginuser == null}">
				${boardvo.orgFilename}
			</c:if>			
			</td>
		</tr>
		<tr>
			<th>파일크기(bytes)</th>
			<td>${boardvo.fileSize}</td>
		</tr>
	</table>

	<br>
	<div style="margin-left: 220px;"> 
	<button type="button" style="font-size: 10pt;" class="btn btn-secondry btn-info" onclick="javascript:location.href='<%= request.getContextPath() %>/${goBackURL}'">목록보기</button>&nbsp;&nbsp;
	<button type="button" style="font-size: 10pt;" class="btn btn-secondry btn-info" onclick="javascript:location.href='<%= request.getContextPath() %>/qnaedit.action?seq=${boardvo.seq}'">수정</button>&nbsp;&nbsp;
	<button type="button" style="font-size: 10pt;" class="btn btn-secondry btn-info" onclick="javascript:location.href='<%= request.getContextPath() %>/qnadel.action?seq=${boardvo.seq}'">삭제</button>&nbsp;&nbsp;

	<!-- ==== #120. 답변글쓰기 버튼 추가하기(현재 보고있는 글이 작성하려는 답변글의 원글이 된다.) -->
	<button type="button" style="font-size: 10pt;" class="btn btn-secondry btn-info" onclick="javascript:location.href='<%= request.getContextPath() %>/addqna.action?fk_seq=${boardvo.seq}&groupno=${boardvo.groupno}&depthno=${boardvo.depthno}'">답변글쓰기</button>
	</div>
	<br>

	<!-- ==== #84. 댓글쓰기 form 추가 -->
	<div id="comments" class="comments-area text-left">
		<h2 class="comments-title"> 댓글쓰기 </h2>		
		<form name="addWriteFrm">
			<input type="hidden" name="userid" value="${sessionScope.loginuser.userid}"> 
			성명 : <input type="text" name="name" value="${sessionScope.loginuser.name}" readonly> 
			댓글내용 : <input type="text" name="content" size="80">
			<!-- 댓글에 달리는 원게시물 글번호(즉, 댓글의 부모글 글번호) -->
			<input type="hidden" name="parentSeq" value="${boardvo.seq}">
	
			<button type="button" onclick="goWrite();" style="color: #FFFFFF; background-color: #468EDE;">쓰기</button>
		</form>
	</div>

	<!-- ==== #95. 댓글내용 보여주기 -->
	<div id="comments" class="comments-area text-left">
		<h2 class="comments-title"> 댓글 </h2>
		<table class="table table-hover" id="table2" style="margin-top: 2%; margin-bottom: 50px;">
		<tr>
			<th style="width: 15%; text-align: center;">댓글작성자</th>
			<th style="width: 50%; text-align: center;">댓글내용</th>
			<th style="text-align: center;">작성일자</th>
			<th style="text-align: center;">신고하기</th>
		</tr>
		<tbody id="commentDisplay"></tbody>
		<c:if test="${not empty commentList}">
			<c:forEach var="commentvo" items="${commentList}">
				<tr>
					<td style="text-align: center;">${commentvo.name}</td>
					<td>${commentvo.content}</td>
					<td style="text-align: center;">${commentvo.regDate}</td>
					<td style="text-align: center;">
						<span style="cursor: pointer;">
						<img src="<%=request.getContextPath()%>/resources/img/police.png" class="center" style="width: 30px; height: 30px;" onclick="goPolice('${commentvo.seq}', '${sessionScope.loginuser.userid}');">
						</span>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	</div>
	

</div>

<form name="idxFrm">
	<input type="hidden" name="idx" />
</form>
	
<!-- 개인정보 보기 Modal -->
<!-- <div class="modal fade" id="meberDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><i class="fa fa-close"></i></button>
        <h4 class="modal-title" id="myModalLabel">개인정보</h4>
      </div>
      <div class="modal-body"> 
		<div id="memberDetail">
			<div align="center" id="memberInfo">
			
			</div>
		</div>
      </div>
      <div class="modal-footer ">
        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Click Close</button>
      </div> 
    </div>
  </div>
</div> -->