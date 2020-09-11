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

		$(".error").hide();
	/* 	
		$(".requiredInfo").each(function(){
			
			$(this).blur(function(){
				var data = $(this).val().trim();
				if(data.length == 0) {
					// 입력하지 않거나 공백만 입력했을 경우
					// alert("입력하지 않거나 공백만 입력했을 경우");
					
					$(this).parent().find(".error").show();
					$(":input").attr("disabled",true).addClass("bgcol");
					$("#btnRegister").attr("disabled",true); 
					$(this).attr("disabled",false).removeClass("bgcol");
				}
				else{
					// 공백이 아닌 글자를 입력한 경우
					// alert("공백이 아닌 글자를 입력한 경우");
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false).removeClass("bgcol");
					$("#btnRegister").attr("disabled",false); 
				}
			});
			
		});// end of $(".requiredInfo").each()----------------
		 */
	}); // end of $(document).ready()-----------------------------------
	

	function goBandAdd(event) {
		
		var flagBool = false;
		
		$(".requiredInfo").each(function(){
			var data = $(this).val().trim();
			if(data == "") {
				flagBool = true;
				return false;
				/*
				   for문에서의 continue; 와 동일한 기능을 하는것이 
				   each(); 내에서는 return true; 이고,
				   for문에서의 break; 와 동일한 기능을 하는것이 
				   each(); 내에서는 return false; 이다.
				*/
			}
		});
		
		if(flagBool) {
			
			$(".error").show();
			
			alert("필수입력란은 모두 입력하셔야 합니다.");
			event.preventDefault(); // click event 를 작동치 못하도록 한다.
			return;
		}
		
		else {
			var frm = document.addFrm;
			frm.method = "post";
			frm.action = "bandAddEnd.action";
			frm.submit();
		}
		
	}// end of goBandAdd(event)------------------

			
	function goReset() {

		$(".error").hide();
		
		var addFrm = document.addFrm;
		addFrm.reset();
		$("#result").empty();	
	}
	
</script>

<div class="container">
	<h3 style="padding-top: 20px; text-align: center;">밴드 만들기</h3>
	
	 <%-- >>>> 파일첨부하기
	 	       enctype="multipart/form-data" 을 해주어야만 파일첨부가 된다. --%>
	<form name="addFrm" action="<%= request.getContextPath() %>/bandAddEnd.action" method="post" enctype="multipart/form-data" >
		<table id="table" class="table table-bordered" style="width: 100%; margin-top: 50px;">
			<tr>
				<th>밴드명&nbsp;<span class="error">필수입력</span></th>
				<td>
					<div style="width: 30%;">
					  <input type="text" id=bname name=bname class="requiredInfo" />
					  <input type="hidden" id="badmin" name=badmin value="${sessionScope.loginuser.userid }"/>
					</div> 
				</td>
			</tr>
			<tr>
				<th>밴드테마&nbsp;<span class="error">필수입력</span></th>
				<td>
				    <div style="width: 50%;">
				    	<select class="requiredInfo" id="bthema" name="bthema">
					    	<c:forEach var="map" items="${bandThemaList}">
						  	  <option value="${map.TCODE}">${map.TNAME}</option>
						  	</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<%-- 첨부파일 타입 추가하기  --%>
			<tr>
				<th>메인이미지&nbsp;<span class="error">필수입력</span></th>
				<td>
				    <div style="width: 50%;">
						<input type="file" id="bimg1" name="attach" class="" />
					</div>
				</td>
			</tr>
			<tr>
         		<th>추가이미지</th>
         		<td>
         		    <div style="width: 50%;">
						<input type="file" id="bimg2" name="attach" />
						<input type="file" id="bimg3" name="attach" />
						<input type="file" id="bimg4" name="attach" />
						<input type="file" id="bimg5" name="attach" />
						<input type="file" id="bimg6" name="attach" />
					</div>
         		</td>
         	</tr>
			<tr>
            	<th>밴드설명&nbsp;<span class="error">필수입력</span></th>
            	<td><textarea id="binfo" name="binfo" class="requiredInfo" rows="7" ></textarea></td>
         	</tr>
		</table>
		<br/>

        <!-- <input name="submit" type="submit" id="submit" class="submit" value="Post Comment"> -->
		<button type="button" id="btnRegister" class="btn btn-secondry btn-info"  style="margin-right: 10px; margin-bottom: 10px; " onClick="goBandAdd(event);">밴드등록</button>
		<button type="button" class="btn btn-secondry btn-info" style="margin-right: 10px; margin-bottom: 10px;" onClick="goReset();">취소</button>
		<button type="button" class="btn btn-secondry btn-info" style="margin-right: 10px; margin-bottom: 10px;" onClick="javascript:location.href='<%= request.getContextPath() %>/bandList.action'">밴드목록</button> 
	
	</form>
</div>











   