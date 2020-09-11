<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<script type="text/javascript">

	function add(idx,userid){
		
		var frm = document.addFrm;
		frm.idx.value=idx;
		frm.userid.value=userid;
		
		frm.method="POST";
		frm.action="addWishCulture.action";
		frm.submit(); 
		
	
	}


</script>

    
    <!-- ${culturevo} -->
<section class="portfolio-content">
    <div class="container">
    
      <div class="row">
        <div class="col-md-10 col-md-offset-1">
          <h1 align="center">${culturevo.ccmaname}</h1>      
  		
  		<c:if test="${culturevo.imageurl!=null }">
  		  <div id="myCarousel" class="carousel slide" data-ride="carousel" style="height:500px;">		
			    <!-- Wrapper for slides -->
			    <div class="carousel-inner">
			      <div class="item active">
			        <img src="${culturevo.imageurl}"  style="width:100%;height:500px;">
			      </div>
			
			      <div class="item">
			        <img src="${culturevo.subimage1}"style="width:100%;height:500px;">
			      </div>
			    
			      <div class="item">
			        <img src="${culturevo.subimage2}" style="width:100%;height:500px;">
			      </div>
			      <div class="item">
			        <img src="${culturevo.subimage3}" style="width:100%;height:500px;">
			      </div>
			    </div>			
			    <!-- Left and right controls -->
			    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
			      <span class="glyphicon glyphicon-chevron-left"></span>
			      <span class="sr-only">Previous</span>
			    </a>
			    <a class="right carousel-control" href="#myCarousel" data-slide="next">
			      <span class="glyphicon glyphicon-chevron-right"></span>
			      <span class="sr-only">Next</span>
			    </a>
			</div>
		</c:if>
		<c:if test="${culturevo.imageurl==null}">
     		 <p><img src="<%=request.getContextPath() %>/resources/img/ready.png"/></p>         
        </c:if>
           
          <p style="font-weight:bold;font-size:20px;margin-top:30px;">문화재 소개!</p>
          <p> ${culturevo.content}</p>
          <div class="col-md-3 col-xs-12">
            <h5>시대</h5>
            <p>${culturevo.cccename}</p>
          </div>
          <div class="col-md-3 col-xs-12">
            <h5>문화재분류</h5>
            <p>${culturevo.gcodename}</p>
          </div>
           <div class="col-md-6 col-xs-12">
            <h5>위치</h5>
            <p>${culturevo.ccbalcad}</p>
          </div>
          
          <div align="center" id="map" style="width:100%;height:400px;"></div>
          <br>
          <button type="button" onClick="add('${culturevo.idx}','${sessionScope.loginuser.userid}')">Wish문화재 추가</button>
          
          <ul class="details">
            <li>
              <h4>About Project</h4>
            </li>
            <li><strong> Client</strong>
              <p>GHD group</p>
            </li>
            <li> <strong>Date</strong>
              <p> March 12, 2014</p>
            </li>
            <li> <strong>url</strong>
              <p><a href="#">Gem-Store.com</a></p>
            </li>
            <li> <strong>Task</strong>
              <p>Dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
            </li>
          </ul>
        </div>
      </div>
    </div>
    
  </section>


<form name="addFrm">
	<input type="hidden" name="idx" />
	<input type="hidden" name="userid" />
</form>


