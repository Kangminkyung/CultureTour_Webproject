<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">

	table#tblMemberRegister {
	     width: 80%;
	     border: hidden; /* 선을 숨기는 것 */
	     margin: 10px;
	}
	
	table#tblMemberRegister #th {
		width: 25%; 
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

</style>  

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<script type="text/javascript">
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

	var flag = 0;
	 
 	$(document).ready(function(){		
 		
 		
 		$(".error").hide();
 		$("#error_passwd").hide(); 		
		$(".requiredInfo").each(function(){			
			$(this).blur(function(){
				var data = $(this).val().trim();
				if(data.length == 0) {
					// 입력하지 않거f나 공백만 입력했을 경우
					// swal("입력하지 않거나 공백만 입력했을 경우");
								
					$(this).parent().find(".error").show();
					$(":input").attr("disabled",true);
					$("#btnRegister").attr("disabled",true); 
					$(this).attr("disabled",false);
				}
				else{
					// 공백이 아닌 글자를 입력한 경우
					// swal("공백이 아닌 글자를 입력한 경우");
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false);
					$("#btnRegister").attr("disabled",false); 
				}
			});
			
		});// end of $(".requiredInfo").each()----------------		
		
		
		$("#userid").blur(function(){			
	
					
					var idchk = $("#userid").val().trim();				
					$.ajax({							
						url: "idcheck.action",
						type: "GET",
						data : {userid:$("#userid").val()},
						dataType :"json",
						success : function(json){						
							if(json.cnt>0 || idchk.length == 0){								
								$("#iderror").show();
								$("#userid").val("");
								$("#userid").focus();
								$(":input").attr("disabled",false);
								$("#error_passwd").hide(); 			
								return;								
							}else{
								alert("사용 가능한 아이디입니다.");
								$("#iderror").hide();						
								$("#pwd").focus();
								flag=1;
							
							}						
						}								
					});		   	
					
				});// end of $("#userid").blur(function()
		
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
				$("#btnRegister").attr("disabled",true); 
				$(this).attr("disabled",false).removeClass("bgcol");
			}   
			else {
				$("#error_passwd").hide();
				$(":input").attr("disabled",false).removeClass("bgcol");
				$("#btnRegister").attr("disabled",false); 
				$("#pwdcheck").focus();
			}
			
		});// end of $("#pwd").blur()-------------------
		
		
		$("#pwdcheck").blur(function(){
			var passwd = $("#pwd").val();
			var passwdCheck = $(this).val();
			
			if(passwd != passwdCheck) {
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$("#btnRegister").attr("disabled",true);
				
			//	$("#pwd").attr("disabled",false).removeClass("bgcol");
				$(this).attr("disabled",false).removeClass("bgcol");
			}
			else {
				$(this).parent().find(".error").hide();
				$(":input").attr("disabled",false).removeClass("bgcol");
				$("#btnRegister").attr("disabled",false);
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
					$(":input").attr("disabled",true);
					$("#btnRegister").attr("disabled",true);
					
					$(this).attr("disabled",false);
				}
				else {
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false);
					$("#btnRegister").attr("disabled",false);
				}
				
			}
			else {
				// 휴대폰 국번에 공백만 들어오거나 데이터가 안들어온 경우
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true);
				$("#btnRegister").attr("disabled",true);
				
				$(this).attr("disabled",false);
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
					$(":input").attr("disabled",true);
					$("#btnRegister").attr("disabled",true);
					
					$(this).attr("disabled",false);
				}
				else {
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false);
					$("#btnRegister").attr("disabled",false);
				}
				
			}
			else {
				// 휴대폰 4자리에 공백만 들어오거나 데이터가 안들어온 경우
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true);
				$("#btnRegister").attr("disabled",true);
				
				$(this).attr("disabled",false);
			}
			
		});// end of $("#hp3").blur()--------------
		
		$("#email").blur(function() {
			var email = $(this).val();
			
			var regexp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
	         // e메일을 검사해주는 정규표현식 객체 생성
	         
			var bool = regexp_email.test(email);
	         
	        if(!bool) {
	        	$(this).parent().find(".error").show();
				$(":input").attr("disabled",true);
				$("#btnRegister").attr("disabled",true);
				
				$(this).attr("disabled",false);
	        } 
	        else {
	        	$(this).parent().find(".error").hide();
				$(":input").attr("disabled",false);
				$("#btnRegister").attr("disabled",false);
	        }
			
		});	// end of $("#email").blur()--------------------	
		
			

	
		    
 		
 	});// end of document.ready()------------------
	
	function goRegister(event){
 		
		var flagBool = false;
/* 		
		if(flag==0){
			swal("아이디 중복검사를 실시하세요!");
			$("#userid").focus();
			return;
		} */
		
			
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
				frm.action = "memberRegisterEnd.action";
				frm.submit();
			}
	
 	}// end of function goRegister(event)---------
	
	function goLogin(event){
 		
		var loginUserid = $("#loginUserid").val().trim();
		var loginPwd = $("#loginPwd").val().trim();
		
		if(loginUserid == "") {
			swal("아이디를 입력하세요!!");
			$("#loginUserid").val("");
			$("#loginUserid").focus();
			event.preventDefault();
			return;
		}
		
		if(loginPwd == "") {
			swal("비밀번호를 입력하세요!!");
			$("#loginPwd").val("");
			$("#loginPwd").focus();
			event.preventDefault();
			return;
		}
		
		var frm = document.loginFrm;
		frm.method = "post";
		frm.action = "loginEnd.action";
		frm.submit();
 		
		
	}// end of goLogin()----------
 	
	function idCheck(){
		
		if(flag==0){
			$.ajax({					
				url: "<%=request.getContextPath()%>/idDuplicateCheck.aciton",
				type: "post",
				data : {userid:$("#userid").val()},
				dataType :"json",
				success : function(json){						
					if(json.cnt>0){							
						swal("사용 불가능한 아이디입니다.");	
						$("#userid").attr("disabled",false);
						flag=0;
						$("#userid").val("");
						//$(":input").attr("disabled",false);
						
						return;
					
					}else{
						swal("사용 가능한 아이디입니다.");
						flag =1;
					//	$("#pwd").focus();
					}						
				}								
			});		   			    	 
		}else{
			return;
		}
		
		
	}
 
	// 검색한 주소를 input에 입력하는것 
	function search(){

         $("#post").val($("#sample6_postcode").val());		
         $("#addr1").val($("#sample6_address").val());
         $("#addr2").val($("#sample6_address2").val());    
  		
	}// end of search()-----
	
</script>  

	<div class="container" align="center">
		<h3 align="center">회원가입 (<span style="font-size: 10pt; font-style: italic; color: red;"><span class="star">*</span>표시는 필수입력사항</span>)</h3>
    	<hr>
		<form name="registerFrm">
 
			<table id="tblMemberRegister">
				<tr>
					<th>성명&nbsp;&nbsp;<span class="star">*</span></th>
					<td style="width: 80%; text-align: left;">
					    <input type="text" name="name" id="name" class="requiredInfo" required style="width:60%;display: inline-block;margin-right:5px;"/> 
							<span class="error"><br>성명은 필수입력 사항입니다.</span>
					</td>
				</tr>
				<tr>
					<th>아이디&nbsp;&nbsp;<span class="star">*</span></th>
					<td style="width: 80%; text-align: left;">
					    <input type="text" name="userid" id="userid" class="requiredInfo" required style="width:60%;display: inline-block;margin-right:5px;"value="${userid}"/>&nbsp;&nbsp;
					    <!-- 아이디중복체크 -->
					    
					    <span class="error" id="iderror"><br>사용하실수 없는 아이디입니다.</span>
					  
					</td> 
				</tr>
				<tr>				
					<th>비밀번호&nbsp;&nbsp;<span class="star">*</span></th>			
					<td style="width: 80%; text-align: left;"><input type="password" name="pwd" id="pwd" class="requiredInfo" required required style="width:60%;"/>
						<br><span id="error_passwd">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로만 입력가능합니다.</span>
					</td>
				</tr>
				<tr>
					<th>비밀번호확인&nbsp;&nbsp;<span class="star">*</span></th>
					<td style="width: 80%; text-align: left;"><input type="password" id="pwdcheck" class="requiredInfo" required required style="width:60%;" /> 
						<br><span class="error">암호가 일치하지 않습니다.</span>
					</td>
				</tr>
	
				<tr>
					<th>이메일&nbsp;&nbsp;<span class="star">*</span></th>
					<td style="width: 80%; text-align: left;"><input type="text" name="email" id="email" class="requiredInfo" placeholder="123@abc.com"  required style="width:60%;" /> 
				   <span class="error"><br>이메일 형식에 맞지 않습니다.</span>
					</td>
				</tr>
				<tr>
					<th>연락처&nbsp;&nbsp;<span class="star">*</span></th>
					<td style="width: 80%; text-align: left;">
					   <select name="hp1" id="hp1" style="width:22%; background-color: #FFFFFF; height: 30px;">
							<option value="010" selected>010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="018">018</option>
							<option value="019">019</option>
						</select>&nbsp;-&nbsp;
					    <input type="text" name="hp2" id="hp2" size="4" maxlength="4" placeholder="전화번호 앞"style="width:32%;display: inline-block;"/>&nbsp;-&nbsp;
					    <input type="text" name="hp3" id="hp3" size="4" maxlength="4" placeholder="전화번호 뒤"style="width:32%;display: inline-block;"/>
					   <span class="error error_hp"><br>휴대폰 형식이 아닙니다.</span>
					</td>
				</tr>
				<tr>
					<th>우편번호</th>
					<td style="width: 80%; text-align: left;">
					   <input type="text" name="post" id="post" size="4" maxlength="3" style="width:33%; display:inline-block;"/>
					 <!--   <input type="text" name="post2" id="post2" size="4" maxlength="3" placeholder="우편" style="display: inline-block; width:20%;margin-right:5px;"/>&nbsp;&nbsp; -->
					   <!-- 우편번호 찾기 -->
					   	<a style="cursor: pointer;" data-toggle="modal" data-target="#address" data-dismiss="modal">
					  	 <img id="zipcodeSearch" src="<%=request.getContextPath() %>/resources/img/zipcode.gif" style="vertical-align: middle;" />
					  	</a>
					   <br><span class="error error_post"><br>우편번호 형식이 아닙니다.</span>
					</td>
				</tr>
				<tr>
					<th>주소</th>	
					<td style="width: 80%; text-align: left;">
					   <input type="text" id="addr1" name="addr1" size="60" maxlength="100" /><br> <br> 
					   <input type="text" id="addr2" name="addr2" size="60" maxlength="100" />
					</td>
				</tr>		
			</table>
					
					
		<div style="margin-bottom: 30px; margin-top: 30px;">
	      <button type="reset" style="background-color: #E24B50; color: #ffffff;">취소</button>
	      <button type="submit" id="btnRegister" class="signupbtn" onClick="goRegister(event);" style="background-color: #468EDE; color: #ffffff;">가입하기</button>
	    </div> 
		</form>										 	
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
				<input type="button" onclick="sample6_execDaumPostcode()" style="background-color: #E24B50; border: none; color: white;" class="ml-btn-16 rounded" value="우편번호 찾기"><br>
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