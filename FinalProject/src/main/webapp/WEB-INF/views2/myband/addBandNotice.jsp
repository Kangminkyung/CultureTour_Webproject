<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">
	table, th, td, input, textarea {border: solid gray 1px;}
	
	#table {border-collapse: collapse;
	 		width: 1000px;
	 		}
	#table th, #table td{padding: 5px;}
	#table th{width: 120px; background-color: #DDDDDD;}
	#table td{width: 860px;}
	.long {width: 470px;}
	.short {width: 120px;} 		

</style>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		// 쓰기버튼
		$("#btnWrite").click(function(){
			
			
			// 글제목 유효성 검사
			var subjectval = document.getElementById("subject").value.trim();
			
			if(subjectval == "") {
				alert("글제목을 입력하세요!!");
				return;
			}
			
			// 글내용 유효성 검사
			var contentval = document.getElementById("content").value.trim();
			
			if(contentval == "") {
				alert("글내용 입력하세요!!");
				return;
			}
			
			// 글암호 유효성 검사
			var pwval = document.getElementById("pw").value.trim();
			
			if(pwval == "") {
				alert("글암호를 입력하세요!!");
				return;
			}
			
			// form 전송하기
			var frm = document.writeFrm;
			frm.action = "<%=request.getContextPath()%>/addBandNoticeEnd.action";
			frm.method = "post";
			frm.submit();
			
		});
		
	}); // end of ready()-------------------------------------------
		
</script>

<div align="center"  style="margin-bottom: 0.2%; border: solid 0px red; margin-right: 300px;">

	<form name="writeFrm">                
		<table id="table">
			<tr>
				<th>성명</th>
				<td>
				    <input type="hidden" name="userid" value="${sessionScope.loginuser.userid}" readonly />
					<input type="text" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject" id="subject" class="long" /></td>
			</tr>
			<tr>
            	<th>내용</th>
            	<td>
            	<textarea name="content" id="content" rows="10" cols="100" style="width:95%; height:412px;"></textarea>             	
            	</td>
         	</tr>

			<tr>
				<th>암호</th>
				<td><input type="password" name="pw" id="pw" class="short" /></td>
			</tr>
		</table>
		<br/>
		
		<div align="center" style="margin-bottom: 30px;">
			<button type="button" id="btnWrite" style="color: #FFFFFF; background-color: #468EDE;">쓰기</button>
			<button type="button" style="color: #FFFFFF; background-color: #E24B50;" onClick="javascript:history.back();">취소</button>
		</div>
		<input type="hidden" name="bcode" value="${bcode}"/>
	</form>

</div>	