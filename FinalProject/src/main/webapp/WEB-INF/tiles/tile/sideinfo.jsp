<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.Calendar"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">

*  {
    box-sizing: border-box;
}

body {
    background-color: #f1f1f1;
    font-family: Arial;
}
 

/* Create four equal columns that floats next to each other */
.column {
    float: left;
    /* width: 30%; */
}
 
/* Content */
 .content {
    background-color: #f2f2f2;
    padding: 10px;
  
}
 
</style>
<%-- ===== #38.  tiles 중 sideinfo 페이지 만들기 ===== --%>


<script type="text/javascript">


	var weatherTimejugi = 0;
	
	$(document).ready(function(){
		
		// 위도 경도 구하기
		getLocation();
		
		loopshowNowTime();
		
		var now = new Date();
		var minute = now.getMinutes(); // 현재 분. 18분
		
		if(minute<30){
			weatherTimejugi = (30-minute)*60*1000;
		}else if(minute==30){
			weatherTimejugi = 1000;
		}else{// 현재분. 50분 
			weatherTimejugi = ((60-minute)+30)*60*1000;
			
		}
		
		//showWeather(); // 기상청 날씨정보 공공API XML데이터 호출하기
		//loopshowWeather();
		
	});
	
	
	function getLocation() {
		  if (navigator.geolocation) { // GPS를 지원하면
		    navigator.geolocation.getCurrentPosition(function(position) {
		     // alert(position.coords.latitude + ' ' + position.coords.longitude);
		      
		      
		      var weather = "https://api.openweathermap.org/data/2.5/weather?lat="+position.coords.latitude+"&lon="+ position.coords.longitude+"&appid=0f8bdab1e440bbaf9ba8adc56e23f037";
		      
		      // console.log(weather);
		      
		      $.ajax({
		    	 
		    	  url : weather,
		    	  type:"GET",
		    	  dataType:"JSON",
		    	  success:function(resp){
		    		  	console.log(resp);
		                console.log("현재온도 : "+ (resp.main.temp- 273.15) );
		                console.log("현재습도 : "+ resp.main.humidity);
		                console.log("날씨 : "+ resp.weather[0].main );
		                console.log("상세날씨설명 : "+ resp.weather[0].description );
		                console.log("날씨 이미지 : "+ resp.weather[0].icon );
		                console.log("바람   : "+ resp.wind.speed );
		                console.log("나라   : "+ resp.sys.country );
		                console.log("도시이름  : "+ resp.name );
		                console.log("구름  : "+ (resp.clouds.all) +"%" ); 	    
		                
		                var ondo = Math.floor((resp.main.temp- 273.15)*100)/100;
		                var humidity = resp.main.humidity;		              
		    		  	var baram = resp.wind.speed;
		    		  	var cloud = (resp.clouds.all) +"%";
		             
		    		  	var html  ="<span style='color:green; font-weight:bold;'>";
		    		  		html +="온도 : "+ondo;
		    		  		html +="<br>습도 : "+humidity;
		    		  		html +="<br>바람 : "+baram;
		    		  		html +="<br>구름 : "+cloud+"</span>";
		    		  	
		                $("#displayWeather").html(html);
		    		  
		    	  },
		    	  error : function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}	
		    	  
		      });
		      
		      
		      
		    }, function(error) {
		      console.error(error);
		    }, {
		      enableHighAccuracy: false,
		      maximumAge: 0,
		      timeout: Infinity
		    });
		  } else {
		    alert('GPS를 지원하지 않습니다');
		  }
		}

	
	

	function showNowTime() {
		
		var now = new Date();
	
		//var strNow = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
	
		var strNow="";	
		
		var hour = "";
		if(now.getHours() > 12) {
			hour = " 오후 " + (now.getHours() - 12);
		} else if(now.getHours() < 12) {
			hour = " 오전 " + now.getHours();
		} else {
			hour = " 정오 " + now.getHours();
		}
		
		var minute = "";
		if(now.getMinutes() < 10) {
			minute = "0"+now.getMinutes();
		} else {
			minute = ""+now.getMinutes();
		}
		
		var second = "";
		if(now.getSeconds() < 10) {
			second = "0"+now.getSeconds();
		} else {
			second = ""+now.getSeconds();
		}
		
		strNow += hour + ":" + minute + ":" + second;
		
		$("#clock").html("<span style='color:green; font-weight:bold;'>"+strNow+"</span>");
	
	}// end of function showNowTime() -----------------------------

	
	function loopshowNowTime() {
		showNowTime();
		
		var timejugi = 1000;   // 시간을 1초 마다 자동 갱신하려고.
		
		setTimeout(function() {
						loopshowNowTime();	
					}, timejugi);
		
		
	}// end of loopshowNowTime() --------------------------
	
	function showWeather(){
		$.ajax({
			
			url : "<%=request.getContextPath()%>/weatherXML.action",
			type : "GET",
			dataType :"XML",
			success : function(xml){
			
				var rootElement = $(xml).find(":root");

				
				var weather = $(rootElement).find("weather");
				var updateTime = $(weather).attr("year")+"년 "+$(weather).attr("month")+"월 "+$(weather).attr("day")+"일 "+$(weather).attr("hour")+"시";
				console.log(updateTime);
				var localArr = $(rootElement).find("local");
				console.log(localArr.length);
				
				var html ="업데이트 : <span>"+updateTime+"</span>&nbsp;"; 
					html +="<span style='color:blue;cursor:pointer' onClick='javascript:showWeather();'>업데이트</span>";
					html +="<table class='table table-hover' align='center'>";
					html +="<tr>";
					html +="<th>지역</th>";
					html +="<th>날씨</th>";
					html +="<th>기온</th>";
					html +="</tr>";
				
				for(var i =0;i<localArr.length;i++){
					var local = $(localArr).eq(i); // .eq(Index)는 선택된 요소들을 인덱스 번호로 찾을 수 있는 선택자이다. 마치 배열의 인덱스로 값을 찾는 것과 같은 효과를 낸다.
					
					html += "<tr>";
					html += "<td>"+$(local).text()+"</td>";
					html += "<td>"+$(local).attr("desc")+"</td>";
					html += "<td>"+$(local).attr("ta")+"</td>";
					html += "</tr>";
				}
				 
				html+="</table>";
					
				$("#displayWeather").html(html); 
				
			},
			error : function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}			
		}); 
	}// end of function showWeather()----------
	
	function loopshowWeather(){
		showWeather();

		setTimeout(function() {
				showWeather();
			}, weatherTimejugi);
	
		setTimeout(function() {
			loopshowNowTime();	
		}, weatherTimejugi+(60*60*1000) );


	}
	
	function goBand(bcode){
		var frm = document.bcodeFrm;
		frm.bcode.value = bcode;
		
		frm.action = "bandInfo.action";
		frm.method = "get";
		frm.submit();
		
	}
</script>



<div style="margin-top: 0px; margin-left:50px;">
	<!-- 현재 시각 -->
	<div id="clock" style="margin-bottom: 50px;"></div> 
	
	 <div class="column" style="border: 0px solid red; ">
    	<div class="content" align="center">
    	 <c:forEach var="image" items="${image}" varStatus="status">
    	 	<c:if test="${status.first}">
    	 		<img src="<%= request.getContextPath() %>/resources/files/${image.IMAGEFILENAME}" style="width:70%; ">
    	 	</c:if>
    	 	
     	 	<c:if test="${image == null}">
    	 		<img src="<%= request.getContextPath() %>/resources/img/default/img2.png" align="middle;" style="width:70%;">
    	 	</c:if> 
    	 </c:forEach>	
          	
     	<h3><span onclick="goBand('${bandvo.bcode}');" style="cursor: pointer; ">${bandvo.bname}</span></h3>
     	<p>밴드장 : ${bandvo.badmin} &nbsp;</p>
      	<p>회원수 : ${bandvo.bmembercnt}명 &nbsp;</p> 
      	<p>생성일 : ${bandvo.bregdate} &nbsp;</p>
    	</div>
	</div>
	<br>
	<div>
		<div class="jquery-calendar"></div>
	</div>	
	<br>
	<div id="displayWeather" style="maring-left:50px;  margin-top: 50px; border: 0px solid #f2f2f2;"></div>
	<br/>

<form name="bcodeFrm">
	<input type="hidden" name="bcode"/>
</form>	
	
</div>


	
	
	
	
	