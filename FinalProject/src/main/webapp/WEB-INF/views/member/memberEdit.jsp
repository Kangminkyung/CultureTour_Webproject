<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<style>
	table#tblMemberRegister {
	     width: 50%;
	     border: hidden; /* 선을 숨기는 것 */
	     margin: 10px;
	}
	
	table#tblMemberRegister #th {
		width: 20%; 
   		height: 70px;
   		text-align: center;
   		background-color: #FAE0D4;
   		font-size: 14pt;
    }
	
	table#tblMemberRegister td {
   		height: 70px; 
    }
	 
	#error_passwd { color: red; padding-left: 10px; margin-bottom: 5px;}
	 
	 .error { color: red; p	adding-left: 10px; margin-bottom: 5px;}
 
	.star {color: red; 
	       font-weight: bold; 
	       font-size: 13pt; 
	 } 
	
	
	 .ml-btn-16 {
	/*General*/
	display: inline-block;
	text-decoration: none;
	
	/*Text*/
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size:13px;
	font-weight:bold;
	line-height:240%;
	color:rgb(146,75,207);
	text-align:center;	
	text-shadow:0px 1px 0px rgba(255,255,255,0.25);
	
	/*Button*/
	background-color: #D5ABFF;
	background-image:-moz-linear-gradient(53% 100% 90deg,rgb(204,153,255) 0%,rgb(223,191,255) 100%); 
	background-image:-webkit-gradient(linear,53% 100%,53% 3%,color-stop(0, rgb(204,153,255)),color-stop(1, rgb(223,191,255)));
	background-image:-webkit-linear-gradient(90deg,rgb(204,153,255) 0%,rgb(223,191,255) 100%);
	background-image:-o-linear-gradient(90deg,rgb(204,153,255) 0%,rgb(223,191,255) 100%);
	background-image:-ms-linear-gradient(90deg,rgb(204,153,255) 0%,rgb(223,191,255) 100%);
	background-image:linear-gradient(90deg,rgb(204,153,255) 0%,rgb(223,191,255) 100%);
	width:120px;
	height:30px;
	border-color:rgb(177,117,232);
	border-width:1px;
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;
	border-style:solid;
	-moz-box-shadow:0px 0px 0px 3px rgba(0,0,0,0.1) ,inset 0px 1px 0px rgba(255,255,255,0.25);
	-webkit-box-shadow:0px 0px 0px 3px rgba(0,0,0,0.1) ,inset 0px 1px 0px rgba(255,255,255,0.25);
	box-shadow:0px 0px 0px 3px rgba(0,0,0,0.1) ,inset 0px 1px 0px rgba(255,255,255,0.25);
	-ms-filter:"progid:DXImageTransform.Microsoft.gradient(startColorstr=#ffdfbfff,endColorstr=#ffcc99ff,GradientType=0)
		progid:DXImageTransform.Microsoft.Glow(Color=#ff000000,Strength=3)";
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#ffdfbfff,endColorstr=#ffcc99ff,GradientType=0)
		progid:DXImageTransform.Microsoft.Glow(Color=#ff000000,Strength=3);
	}

	.rounded {
		-moz-border-radius:100px;
		-webkit-border-radius:100px;
		border-radius:100px;
	}
</style>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">

// 다음 api 주소를 검색해서 가져오는 함수
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('sample6_address').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('sample6_address2').focus();
        }
    }).open();
}// end of function sample6_execDaumPostcode()
	
	$(document).ready(function(){
		
		$(".error").hide();
		$("#error_passwd").hide();
	
		$(".requiredInfo").each(function(){
			
			$(this).blur(function(){
				var data = $(this).val().trim();
				if(data.length == 0) {
					// 입력하지 않거나 공백만 입력했을 경우
					// swal("입력하지 않거나 공백만 입력했을 경우");
					
					$(this).parent().find(".error").show();
					$(":input").attr("disabled",true).addClass("bgcol");
					$("#btnUpdate").attr("disabled",true); 
					$(this).attr("disabled",false).removeClass("bgcol");
				}
				else{
					// 공백이 아닌 글자를 입력한 경우
					// swal("공백이 아닌 글자를 입력한 경우");
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false).removeClass("bgcol");
					$("#btnUpdate").attr("disabled",false); 
				}
			});
			
		});// end of $(".requiredInfo").each()----------------
		
		
		$("#userid").bind("keyup", function(){
			swal("아이디중복확인 버튼을 클릭하여 ID중복 검사를 하세요!!");
			$(this).val("");
		});// end of $("#userid").bind()------------
		
		
		$("#pwd").blur(function() {
			
			var passwd = $(this).val();
			
			/* var regexp_pw = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;  */
			
			var regexp_passwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
			/* 암호는 숫자,영문자,특수문자가 포함된 형태의 8~15글자 이하만 허락해주는 정규표현식 객체 생성 */
			
			var bool = regexp_passwd.test(passwd);
			/* 암호 정규표현식 검사를 하는 것 
			      정규표현식에 만족하면 리턴값은 true,
			      정규표현식에 틀리면 리턴값은 false */
			      
			if(!bool) {
				$("#error_passwd").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$("#btnUpdate").attr("disabled",true); 
				$(this).attr("disabled",false).removeClass("bgcol");
			}   
			else {
				$("#error_passwd").hide();
				$(":input").attr("disabled",false).removeClass("bgcol");
				$("#btnUpdate").attr("disabled",false); 
				$("#pwdcheck").focus();
			}
			
		});// end of $("#pwd").blur()-------------------
		
		
		$("#pwdcheck").blur(function(){
			var passwd = $("#pwd").val();
			var passwdCheck = $(this).val();
			
			if(passwd != passwdCheck) {
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$("#btnUpdate").attr("disabled",true);
				
			//	$("#pwd").attr("disabled",false).removeClass("bgcol");
				$(this).attr("disabled",false).removeClass("bgcol");
			}
			else {
				$(this).parent().find(".error").hide();
				$(":input").attr("disabled",false).removeClass("bgcol");
				$("#btnUpdate").attr("disabled",false);
			}
			
		});// end of $("#pwdcheck").blur()--------------		
	
		$("#hp2").blur(function(){
			
			var hp2 = $(this).val().trim();
			
			if(hp2 != "") {
				// 휴대폰 국번이 데이터가 들어온 경우
				
				var regexp_hp2 = /\d{3,4}/g; 
				// 숫자 3자리 또는 4자리만 들어오도록 허락해주는 정규표현식	
				
				var bool = regexp_hp2.test(hp2);
				
				if(!bool) {
					$(this).parent().find(".error").show();
					$(":input").attr("disabled",true).addClass("bgcol");
					$("#btnUpdate").attr("disabled",true);
					
					$(this).attr("disabled",false).removeClass("bgcol");
				}
				else {
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false).removeClass("bgcol");
					$("#btnUpdate").attr("disabled",false);
				}
				
			}
			else {
				// 휴대폰 국번에 공백만 들어오거나 데이터가 안들어온 경우
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$("#btnUpdate").attr("disabled",true);
				
				$(this).attr("disabled",false).removeClass("bgcol");
			}
			
		});// end of $("#hp2").blur()--------------
		
		
		$("#hp3").blur(function(){
			var hp3 = $(this).val().trim();
			
			if(hp3 != "") {
				// 휴대폰 4자리에 데이터가 들어온 경우
				
				var regexp_hp3 = /\d{4}/g; 
				// 숫자 4자리만 들어오도록 허락해주는 정규표현식	
				
				var bool = regexp_hp3.test(hp3);
				
				if(!bool) {
					$(this).parent().find(".error").show();
					$(":input").attr("disabled",true).addClass("bgcol");
					$("#btnUpdate").attr("disabled",true);
					
					$(this).attr("disabled",false).removeClass("bgcol");
				}
				else {
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false).removeClass("bgcol");
					$("#btnUpdate").attr("disabled",false);
				}
				
			}
			else {
				// 휴대폰 4자리에 공백만 들어오거나 데이터가 안들어온 경우
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$("#btnUpdate").attr("disabled",true);
				
				$(this).attr("disabled",false).removeClass("bgcol");
			}
			
		});// end of $("#hp3").blur()--------------
		
		$("#email").blur(function() {
			var email = $(this).val();
			
			var regexp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
	         // e메일을 검사해주는 정규표현식 객체 생성
	         
			var bool = regexp_email.test(email);
	         
	        if(!bool) {
	        	$(this).parent().find(".error").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$("#btnUpdate").attr("disabled",true);
				
				$(this).attr("disabled",false).removeClass("bgcol");
	        } 
	        else {
	        	$(this).parent().find(".error").hide();
				$(":input").attr("disabled",false).removeClass("bgcol");
				$("#btnUpdate").attr("disabled",false);
	        }
			
		});	// end of $("#email").blur()--------------------
		
	$("#hp1").val("${mvo.hp1}");
		
	});// end of $(document).ready()----------------------------------
	
	
	function goEdit(event) {
		
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
			swal("필수입력란은 모두 입력하셔야 합니다.");
			event.preventDefault(); // click event 를 작동치 못하도록 한다.
			return;
		}
		else {
			var frm = document.registerFrm;
			frm.method = "post";
			frm.action = "memberEditEnd.action";
			frm.submit();
		}
		
	}// end of goEdit(event)------------------
	
	function search(){

        $("#post").val($("#sample6_postcode").val());		
        $("#addr1").val($("#sample6_address").val());
        $("#addr2").val($("#sample6_address2").val());    
 		
	}// end of search()----- 
	
</script>





<div align="center"> 

<c:if test="${empty mvo}">		
	<span style="color: red; font-weight: bold;">[${str_idx}]</span>번 회원정보는 존재하지 않습니다.
	<br/><br/>
	[<a href="javascript:self.close();">닫기</a>]
	<%-- javascript:self.close() 이 팝업창을 닫게 하는 것이다. --%>
</c:if>

<c:if test="${not empty mvo}">
	<form name="registerFrm">
	    <input type="hidden" name="idx" id="idx" value="${mvo.idx}">	    	    
	    <div class="signup-form" style="width:99%;">	
	    	<h3 align="center">개인정보수정 (<span style="font-size: 10pt; font-style: italic; color: red;"><span class="star">*</span>표시는 필수입력사항</span>)</h3>	    	
	    	<table id="tblMemberRegister" class="table"> 
			
				<tr>		
					<th>성명&nbsp;&nbsp;<span class="star">*</span></th>
					<td>
						<input type="text" name="name" id="name" value="${mvo.name}" class="requiredInfo" required />
						<span class="error">성명은 필수입력 사항입니다.</span>
						<input type="hidden" name="userid" value="${(sessionScope.loginuser).userid}" />
					</td>
				</tr>
				
				<tr>			
					<th>비밀번호&nbsp;&nbsp;<span class="star">*</span></th>
					<td>
						<input type="password" name="pwd" id="pwd" class="requiredInfo" required />
						<span id="error_passwd">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로만 입력가능합니다.</span>
					</td>
				</tr>
				
				<tr>
					<th>비밀번호확인&nbsp;&nbsp;<span class="star">*</span></th>
					<td>					
						<input type="password" name="pwdcheck" id="pwdcheck" class="requiredInfo" required />
						<span class="error">암호가 일치하지 않습니다.</span>
					</td>
				</tr>

				<tr>
					<th>이메일&nbsp;&nbsp;<span class="star">*</span></th>
					<td>
						<input type="text" name="email" id="email" value="${mvo.email}" class="requiredInfo" placeholder="123@abc.com" required />
						<span class="error">이메일 형식에 맞지 않습니다.</span>
					</td>
				</tr>
										
				<tr>
					<th>연락처&nbsp;&nbsp;<span class="star">*</span></th>
					<td>
					    <select name="hp1" id="hp1" style="width:22%; background-color: #FFFFFF; height: 30px;"> 
							<option value="010" selected>010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="018">018</option>
							<option value="019">019</option>
						</select>&nbsp;-&nbsp;
					    <input type="text" name="hp2" id="hp2" size="4" maxlength="4" placeholder="전화번호 앞"style="width:25%;display: inline-block;"/>&nbsp;-&nbsp;
					    <input type="text" name="hp3" id="hp3" size="4" maxlength="4" placeholder="전화번호 뒤"style="width:25%;display: inline-block;"/>
					    <span class="error error_hp"><br>휴대폰 형식이 아닙니다.</span>
					</td>
				</tr>
				
				<tr>		
					<th>우편번호</th>
					<td>
					   <input type="text" name="post" id="post" value="${mvo.post}" size="4" maxlength="3" />&nbsp;
					    <!-- 우편번호 찾기 -->									    
  						 <a style="cursor: pointer;" data-toggle="modal" data-target="#address" data-dismiss="modal">
					  		 <img id="zipcodeSearch" src="<%= request.getContextPath() %>/resources/img/zipcode.gif" style="vertical-align: middle;" />
					  	</a> 
					   <span class="error error_post">우편번호 형식이 아닙니다.</span><br/>
					</td>
				</tr> 
				
				<tr>	
					<th>주소</th>				
					<td>
					  <input type="text" name="addr1" id="addr1" value="${mvo.addr1}" size="29" maxlength="100" /><br><br>
					  <input type="text" name="addr2" id="addr2" value="${mvo.addr2}" size="29" maxlength="100" />
					</td>
				</tr>							

				<tr style="border-left-style: hidden; border-right-style: hidden; border-bottom-style: hidden;">
					<td colspan="2" style="height: 70px; text-align: center; vertical-align: middle;">
						<button type="button" id="btnEdit" style="height: 40px; background-color: #468EDE; color: white; border: none;" onClick="goEdit(event);">정보수정</button>
					</td>		
				</tr>
			</table>				
		</div>
	</form>
</c:if>btnUpdate
</div>

<%-- ****** 우편번호 찾기 Modal ****** --%>
  <div class="modal fade" id="address" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" style="text-align:center;font-weight:bold;color:gray">주소 검색</h4>
        </div>
        <div class="modal-body">
          <div id="address">
          	<div align="center">
	          	<input type="text" id="sample6_postcode" placeholder="우편번호" style="width:38%;margin-right:10px;">
				<input type="button" onclick="sample6_execDaumPostcode()" style="background-color: #E24B50; border: none; color: white;" value="우편번호 찾기"><br>
				<input type="text" id="sample6_address" placeholder="주소" style="margin-top:10px; width:81%;">
				<input type="text" id="sample6_address2" placeholder="상세주소" style="margin-top:10px;width:81%;">
			</div>
          </div>
        </div>
        <div class="modal-footer">
			<button type="button" class="btn btn-default myclose" data-dismiss="modal" onClick="search();">Close</button>
        </div>
      </div>
      
    </div>
  </div>


