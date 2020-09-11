<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.spring.project.member.model.MemberVO" %>      
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="google-signin-client_id" content="449624286584-1hjfpvu0g9ugqrmmft15io47nerj8d2j.apps.googleusercontent.com">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
}

* {
  box-sizing: border-box;
}

/* style the container */
.container {
  position: relative;
  border-radius: 5px;
  padding: 20px 0 30px 0;
} 

/* style inputs and link buttons */
input,
.btn {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 4px;
  margin: 5px 0;
  opacity: 0.85;
  display: inline-block;
  font-size: 17px;
  line-height: 20px;
  text-decoration: none; /* remove underline from anchors */
}

input:hover,
.btn:hover {
  opacity: 1;
}

/* add appropriate colors to fb, twitter and google buttons */
.fb { /*네이버*/
  background-color: #00e600;
  color: white;
}

.twitter {
  background-color: #55ACEE;
  color: white;
}

.google {
  background-color: #dd4b39;
  color: white;
}

/* style the submit button */
input[type=submit] {
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

/* Two-column layout */
.col {
  float: left;
  width: 50%;
  margin: auto;
  padding: 0 50px;
  margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* vertical line */
.vl {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  border: 2px solid #ddd;
  height: 175px;
}

/* text inside the vertical line */
.vl-innertext {
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: #f1f1f1;
  border: 1px solid #ccc;
  border-radius: 50%;
  padding: 8px 10px;
}

/* hide some text on medium and large screens */
.hide-md-lg {
  display: none;
}

/* bottom container */
.bottom-container {
  text-align: center;
  background-color: #666;
  border-radius: 0px 0px 4px 4px;
}

/* Responsive layout - when the screen is less than 650px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 650px) {
  .col {
    width: 100%;
    margin-top: 0;
  }
  /* hide the vertical line */
  .vl {
    display: none;
  }
  /* show the hidden text on small screens */
  .hide-md-lg {
    display: block;
    text-align: center;
  }
}
</style>

<%
	MemberVO membervo = (MemberVO)session.getAttribute("loginuser");

    if(membervo == null) {
    	
    /*
            로그인 하려고 할때 WAS(톰캣서버)는 
            사용자 컴퓨터 웹브라우저로 부터 전송받은 쿠키를 검사해서
            그 쿠키의 사용유효시간이 0초 짜리가 아니라면
            그 쿠키를 가져와서 웹브라우저에 적용시키도록 해준다.
            우리는 쿠키의 키 값이 "saveid" 가 있으면
            로그인 ID 텍스트박스에 아이디 값을 자동적으로 올려주면 된다.
    */
    
    Cookie[] cookieArr = request.getCookies();
    /*
            쿠키는 쿠키의 이름별로 여러개 저장되어 있으므로
            쿠키를 가져올때는 배열타입으로 가져와서
            가져온 쿠키배열에서 개발자가 원하는 쿠키의 이름과 일치하는것을
            뽑기 위해서는 쿠키 이름을 하나하나씩 비교하는 것 밖에 없다.
    */
    
    String cookie_key = "";
    String cookie_value = "";
    boolean flag = false;
    
    if(cookieArr != null) {
    	for(Cookie c : cookieArr) {
    		cookie_key = c.getName(); // 쿠키의 이름(키값)을 꺼내오는 메소드.
    		
    		if(cookie_key.equals("saveid")) {
    			cookie_value = c.getValue(); // 쿠키의 value값 을 꺼내오는 메소드.
    			flag = true;
    			break; // for 탈출
    		}
    	}// end of for-----------------
    }// end of if(cookieArr != null)--------------
    
%>


<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

</head>
<body>

<div class="container">
  <form action="loginEnd.action" method="post">
    <div class="row">
      <h3 style="text-align:center">로그인 하기</h3>
      <div class="vl">
        <span class="vl-innertext">or</span>
      </div>

      <div class="col">   
          <!-- 네이버아이디로로그인 버튼 노출 영역 -->
		<div id="naver_id_login" style="text-align:right"><a href="naverlogin.action" onClick="window.open(this.href,'_blank','width=400,height=600'); return false">
		<img width="223" src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png"/></a>
		</div>
		
		<!-- 카카오 아이디 로그인 -->
       <div id="kakao-login-btn" style="text-align:right; margin-top:5px;"></div>
	   <a href="kakaologin.action"></a>	  
	   <script type='text/javascript'>
		  //<![CDATA[
		    // 사용할 앱의 JavaScript 키를 설정해 주세요.
		    Kakao.init('d3dd06afaf69fc66d525066d469b02ae');
		    // 카카오 로그인 버튼을 생성합니다.
		    Kakao.Auth.createLoginButton({
		      container: '#kakao-login-btn',
		      success: function(authObj) {
		        //alert(JSON.stringify(authObj));
		        Kakao.API.request({
		        	
		        	url: "/v1/user/me",
		        	success:function(res){
		       
		        		var id = res.id;
		        		var email = res.kaccount_email;
		        		var nickname = res.properties.nickname;
						
		     
		        		
		        		var data_form ={"userid":id,pwd:nickname,email:email};
		        		$.ajax({
		        			
		        			url:"kakaoDuplicate.action",
		        			type:"POST",
		        			data:data_form,
		        			dataType:"JSON",
		        			success:function(json){
		        		
		        				if(json.cnt == 1){
		        					
		        					  
		        					  var frm = document.kakaoLogin;
		      		        		  frm.kakaouserid.value=id;		      		        	
		      		        		  frm.kakaopwd.value=nickname;
		      		        		  frm.method="POST";
		      		        		  frm.action="loginEnd.action";
		      		        		  frm.submit();		 
		        					
		        					
		        				}else{
		        					if(confirm(nickname+"님 환영합니다. \r\n 저희 홈페이지에 가입하시겠습니까?")==true){       		        		
		      		        		  
		      		        		  var frm = document.kakaoFrm;
		      		        		  frm.id.value=id;
		      		        		  frm.email.value=email;
		      		        		  frm.nickname.value=nickname;
		      		        		  frm.method="POST";
		      		        		  frm.action="kakaoRegister.action";
		      		        		  frm.submit();		   
		      		        		  
		      		        		}else{
		      		        			return;
		      		        		}			        		
		        					
		        				}
		        				
		        			},
		        			error: function(request, status, error){
		    					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		    				}   			
		        			
		        		});    			        
		        		
		        		
		        		
		        		
		        	},fail:function(error){
		        		alert(JSON.stringify(error));	
		        	}		        	
		        });
		      },
		      fail: function(err) {
		         alert(JSON.stringify(err));
		      }
		    });
		  //]]>
		</script>
	
	   <!-- 구글 아이디 로그인 -->       
        <div align="right">
        <div class="g-signin2" data-onsuccess="onSignIn" data-width="220" data-height="45" data-theme="dark" ></div>
        </div>
        <script>
        
        var userid ="";
        var pwd="";
        var name="";
        var email="";     
        
   
        function onSignIn(googleUser){
        	var profile = googleUser.getBasicProfile();
        	console.log("ID : "+profile.getId());
        	console.log("Full Name : "+profile.getName());        	
        	console.log("Email : "+profile.getEmail());
        	
        	 userid = profile.getEmail();        	
        	 pwd = profile.getId();      // 구글 아이디마다 고유한 값 비밀번호로 쓴다.
        	 name = profile.getName()
        	 email = profile.getEmail();    	
        	
        	 if(name!=""){
        		 goLogin();
        	 }
        	
        } 
        
        function goLogin(){        	
			var data_form = {userid : userid,pwd:pwd,name:name,email:email };              	
        	$.ajax({        		
        		url : "kakaoDuplicate.action",
        		type:"POST",
        		data:data_form,
        		dataType:"JSON",
        		success : function(json){        
        			if(json.cnt==1){
        				
        				  var frm = document.googleLogin;
  		        		  frm.userid.value=userid;		      		        	
  		        		  frm.pwd.value=pwd;
  		        		  frm.method="POST";
  		        		  frm.action="loginEnd.action";
  		        		  frm.submit();		
        				
        			}else{
        				if(confirm(name+"님 환영합니다. \r\n 저희 홈페이지에 가입하시겠습니까?")==true){       		        		
    		        		  
    		        		  var frm = document.googleFrm;
    		        		  frm.googleuserid.value=userid;
    		        		  frm.googlepwd.value=pwd;
    		        		  frm.googlename.value=name;
    		        		  frm.method="POST";
    		        		  frm.action="googleRegister.action";
    		        		  frm.submit();		   
    		        		  
    		        		}else{
    		        			return;
    		        		}			        	
        				
        				
        				
        			}   	
        			
        			
        		},
        		error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}   	
        		
        	});    	
        }
        
        </script>
       <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
      	</div>

      <div class="col">
        <div class="hide-md-lg">
          <p>Or sign in manually:</p>
        </div>

        <input type="text" name="userid" placeholder="아이디" required
         <%
							    if(flag==true){  %>
						      value = "<%= cookie_value %>"	
							 <% } %>
							 />
							 							 <% } %>
        <input type="password" name="pwd" placeholder="비밀번호" required>
        <input type="submit" value="로그인">
        <input type="checkbox" class="checkbox"  name="saveid" id="saveid"> 
		<label for="saveid">아이디 저장 </label>
      </div>
      
    </div>
    
    <div class="col" align="right">
      <a style="cursor: pointer;" data-toggle="modal" data-target="#userIdfind" data-dismiss="modal">아이디찾기</a>
    </div>
    <div class="col">
      <a style="cursor: pointer;" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal">비밀번호찾기</a>
    </div>    
  </form>
</div>

<!-- 카카오 회원가입 -->
<form name="kakaoFrm">
	<input type="hidden" name="id" id="id"/>
	<input type="hidden" name="email" id="email"/>
	<input type="hidden" name="nickname" id="nickname"/>
	
</form>

<!-- 카카오 로그인 -->
<form name="kakaoLogin">
	<input type="hidden" name="userid" id="kakaouserid"/>
	<input type="hidden" name="pwd" id="kakaopwd"/>
</form>

<!-- 구글 회원가입 -->
<form name="googleFrm">
	<input type="hidden" name="userid" id="googleuserid"/>
	<input type="hidden" name="pwd" id="googlepwd"/>
	<input type="hidden" name="name" id="googlename"/>
</form>
<!-- 구글 아이디로 로그인 -->
<form name="googleLogin">
	<input type="hidden" name="userid" id="userid"/>
	<input type="hidden" name="pwd" id="pwd"/>
</form>



<%-- ****** 아이디 찾기 Modal ****** --%>
  <div class="modal fade" id="userIdfind" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">아이디 찾기</h4>
        </div>
        <div class="modal-body" style="height: 300px; width: 100%;">
          <div id="idFind">
          	<iframe style="border: none; width: 100%; height: 280px;" src="<%= request.getContextPath() %>/idFind.action">
          	</iframe>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default myclose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>   


  <%-- ****** 비밀번호 찾기 Modal ****** --%>
  <div class="modal fade" id="passwdFind" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">비밀번호 찾기</h4>
        </div>
        <div class="modal-body">
          <div id="pwFind">
          	<iframe style="border: none; width: 100%; height: 350px;" src="<%= request.getContextPath() %>/pwdFind.action">  
          	</iframe>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default myclose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>

</body>
</html>
