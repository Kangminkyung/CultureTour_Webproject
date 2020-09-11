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
			
			var orgFilenameval = document.getElementById("orgFilename").value.trim();
			
			if(orgFilenameval == "") {
				alert("사진을 선택하세요!!");
				return;
			}
			
			// form 전송하기
			var frm = document.writeFrm;
			frm.action = "<%=request.getContextPath()%>/addPhotoEnd.action";
			frm.method = "post";
			frm.submit();
			
		});
		
	}); // end of ready()-------------------------------------------
		
</script>

<div align="center"  style="margin-bottom: 0.2%; border: solid 0px red; margin-right: 300px;">

	<form name="writeFrm" enctype="multipart/form-data">                   
		<table id="table">
			<tr>
				<th>성명</th>
				<td>
				    <input type="text" name="userid" value="${sessionScope.loginuser.userid}" readonly />
				</td>
			</tr>
			<tr>
				<th>사진1</th>
				<td><input type="file" id="orgFilename" name="attach"/></td>
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