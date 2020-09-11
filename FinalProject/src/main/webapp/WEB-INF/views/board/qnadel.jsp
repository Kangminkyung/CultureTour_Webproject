<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
	
	table, th, td {
		border: 1px solid gray;
	}
	
	#table th, #table td {
		padding: 5px;
	}
	
	#table th {
		background-color: #DDDDDD;
	}
	
	a {
		text-decoration: none;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		
		
	}); // end of $(document).ready()
	
	function goDelete() {
		var frm = document.delFrm;
		
		var pwval = frm.pw.value.trim(); 
		if(pwval == "") {
			alert("암호를 입력하세요.");
			return;
		}
		
		frm.action = "<%=request.getContextPath()%>/qnadelEnd.action";
		frm.method = "post";
		frm.submit();
	}
	
</script>

<div style="padding-left: 10%;">
	<h1>글삭제</h1>
	
	<form name="delFrm">
		<table id="table">
			<tr>
				<th>암호</th>
				<td>
					<input type="password" name="pw">
					<input type="hidden" name="seq" value="${seq}">
				</td>
			</tr>
		</table>
		<br>
		<button type="button" onclick="goDelete();">삭제</button>
		<button type="button" onclick="history.back();">취소</button>
	</form>
</div>