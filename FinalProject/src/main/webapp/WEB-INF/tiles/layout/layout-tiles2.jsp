<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- ===== #35. tiles 를 사용하는 레이아웃 페이지 만들기  ===== --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"  %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문화재 탐방사이트</title>

 <%-- 스마트 에디터 구현 시작(no frame 사용시) --%>
   <link href="<%=request.getContextPath()%>/resources/smarteditor/css/smart_editor2.css" rel="stylesheet" type="text/css">

  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/smarteditor/js/lib/jindo2.all.js" charset="utf-8"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/smarteditor/js/lib/jindo_component.js" charset="utf-8"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/smarteditor/js/SE2M_Configuration.js" charset="utf-8"></script>	<!-- 설정 파일 -->
  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/smarteditor/js/SE2BasicCreator.js" charset="utf-8"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/smarteditor/js/smarteditor2.min.js" charset="utf-8"></script> 

  <!-- 사진첨부샘플  --> 
   <script type="text/javascript" src="<%=request.getContextPath()%>/resources/smarteditor/sample/js/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"></script> 
  <%-- 스마트 에디터 구현 끝(no frame 사용시)     --%>


<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.1.11.1.js"></script>
<!-- <link rel="shortcut icon" href="#" type="image/x-icon"> -->
<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath() %>/resources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/font-awesome.css">
<!-- Slider ================================================== -->
<link href="<%=request.getContextPath() %>/resources/css/owl.carousel.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/resources/css/owl.theme.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/resources/css/animate.css" rel="stylesheet" media="screen">
<!-- lightbox Stylesheet ================================================== -->
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath() %>/resources/css/lity.css">
<!-- Stylesheet ================================================== -->
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath() %>/resources/style.css">
<link href='https://fonts.googleapis.com/css?family=Crimson+Text:400,400i,600|Montserrat:100,200,300,300i,400,500,600,700,800,900|Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/modernizr.custom.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/style.css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/calendar.js"></script>

<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.1.11.1.js"></script> --%>
  
<style type="text/css">  
  #mycontainer2	{padding-top: 0px;float:right; width:100%;}
  	#mysideinfo		{ background-color:#FFFFFF; float:left; width:15%; min-height:1000px; margin-top: 50px;} 
	#mycontent2		{ background-color:#FFFFFF; float:right; width:85%;min-height:1000px; margin-top: 50px; margin-left: 0px;} 
	#myfooter		{ background-color:#555555; clear:both;  }
	
	
</style>  

</head>  

<body>

			
<div id="mycontainer2">
	 
	<%-- <div id="mymenu">
		<tiles:insertAttribute name="menu" />
	</div>
		 --%>
	<div id="myheader2" style="border: 0px solid red;">
		<tiles:insertAttribute name="header2" />
	</div> 
	
	<div id="mycontent2" style="border: 0px solid green;">
		<tiles:insertAttribute name="content" />
	</div>

	<div id="mysideinfo" style="border: 0px solid blue;">
		<tiles:insertAttribute name="sideinfo" />
	</div>
	
	<div id="myfooter">
		<tiles:insertAttribute name="footer" />
	</div>
	

</div>
</body>
</html>