<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">

function goView(bcode) {
	
	var frm = document.goViewFrm;
	frm.bcode.value = bcode;
	
	frm.action = "bandInfo.action";
	frm.method = "get";
	frm.submit();
}// end of function goView()---------------------


function goSearch3() {
	var frm = document.searchFrm;

	frm.action = "<%= request.getContextPath() %>/bandList.action"; 
	frm.method = "GET";
	frm.submit(); 
}

</script>

	<c:if test="${sessionScope.loginuser != null && sessionScope.loginuser.userid ne 'admin'}">
		<div align="center" style="margin-top: 30px; margin-bottom: 30px;">
			 <button type="button" onClick="javascript:location.href='<%=request.getContextPath() %>/bandAdd.action'" style="background-color: #468EDE; color: #FFFFFF;">밴드생성</button>
	  	</div>
  	</c:if>
  	<c:if test="${sessionScope.loginuser != null && (sessionScope.loginuser).userid eq 'admin'}">
  		<div align="center" style="margin-top: 30px; margin-bottom: 30px;">
			 <button type="button" onClick="javascript:location.href='<%=request.getContextPath() %>/bandallow.action'" style="background-color: #468EDE; color: #FFFFFF;">밴드승인신청목록</button>
	  	</div>
  	</c:if>
  <!-- page article section
    ================================================== -->
  <section class="page-article-block text-center"> 
    
    <c:if test="${empty bandList}">
    	<div style="">밴드가 없습니다!!</div>
    </c:if>
    
    <!--article 1-->
    <c:forEach var="bandvo" items="${bandList}">
	    <article class="wow fadeInUp"> 
	    
	    <c:if test="${bandvo.THUMBNAILFILENAME != null}">
	    	<header class="entry-header" style="background-image:url('<%= request.getContextPath() %>/resources/files/${bandvo.THUMBNAILFILENAME}');">
	        	<div class="container">
	        		<div class="clearfix"> 
	         	 		<div class="row">
	            		<small style="font-weight: bold; color: white;">${bandvo.BCODE}</small><br/>
	            		<small style="font-weight: bold; color: white;">${bandvo.TNAME}</small>
	                	<span onclick="goView('${bandvo.BCODE}');" style="cursor: pointer;"><h2>${bandvo.BNAME}</h2></span>
	           			</div>
	            	 </div>
	             </div>
	        </header>
   	 	</c:if>
    
	    <c:if test="${bandvo.THUMBNAILFILENAME == null}">
	    	<header class="entry-header" style="background-image:url('<%= request.getContextPath() %>/resources/img/default/img${rnd}.png');">
	        <div class="container">
	        	<div class="clearfix"> 
		          <div class="row">
		            <small style="font-weight: bold; color: white;">${bandvo.BCODE}</small><br/>
		            <small style="font-weight: bold; color: white;">${bandvo.TNAME}</small>
		            <span onclick="goView('${bandvo.BCODE}');" style="cursor: pointer;"><h2>${bandvo.BNAME}</h2></span>
		          </div>
	            </div>  
	        </div>
	      </header>
	    </c:if>
      
    </article>
    </c:forEach>
    <!--/article 1--> 


<form name="goViewFrm">
		<input type="hidden" name="bcode" />
</form>
	
 <%-- ====  페이지바 보여주기 ==== --%>
<nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
	<div align="center" style="width: 100%;"> 
		${pagebar}
	</div>
</nav>
<!-- 	      
    page nav
    <nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
      <ul>
        <li class="pull-left">
          <div class="nav-previous"><a href="http://localhost/wordpress/page/2/"><i class="fa fa-chevron-left"></i></a></div>
        </li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li class="pull-right">
          <div class="nav-next"><a href="http://localhost/wordpress/page/2/"><i class="fa fa-chevron-right"></i></a></div>
        </li>
      </ul>
    </nav>
    page nav 
 
  -->
  
 <%-- 글검색 폼 추가하기 : 글제목, 글내용, 글쓴이로 검색하도록 한다. ==== --%> 
	<div style="margin-bottom: 10px;">
	<form name="searchFrm">
		<select name="colname" id="colname" style="height: 26px;"> 
			<option value="bname">밴드명</option>
			<option value="binfo">밴드내용</option>
			<option value="badmin">밴드장</option>
		</select>
		<input type="text" name="search" id="search" size="40" />
		<button type="button" onClick="goSearch3();">검색</button>
	</form>
	</div>   
  </section>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> 
<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script type="text/javascript" src="js/bootstrap.js"></script> 
<script type="text/javascript" src="js/SmoothScroll.js"></script> 
<script type="text/javascript" src="js/lity.js"></script> 
<script src="js/owl.carousel.js"></script> 
<script type="text/javascript" src="js/jquery.isotope.js"></script> 
<!-- Custom Javascripts
    ================================================== --> 
<script type="text/javascript" src="js/main.js"></script> 
<script src="js/wow.min.js"></script> 
<script src="js/imagesloaded.js"></script> 
<script src="js/classie.js"></script> 
<script src="js/AnimOnScroll.js"></script> 

<!-- top menu
    ================================================== --> 

<script>
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main-block").style.marginRight = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main-block").style.marginRight= "0";
}

</script> 

<!-- masonry layout
    ================================================== --> 

<script>
    $(function(){

      var $container = $('#pic-block');
    
      $container.imagesLoaded( function(){
        $container.isotope({
          itemSelector : '.photo'
        });
      });
    
    
    });
  </script> 
<script>

/*====================================
Scroll animation
======================================*/
	
		new AnimOnScroll( document.getElementById( 'grid' ), {
				minDuration : 0.4,
				maxDuration : 0.7,
				viewportFactor : 0.2
			} );
			
/*====================================
wow
======================================*/
			 new WOW().init();
</script> 

<!-- Lty Lightbox
    ================================================== --> 
<script>
  // Bind as an event handler
$(document).on('click', '[data-lightbox]', lity);;
</script> 
<script>
new WOW().init();

</script> 
<script>
// Bind as an event handler
$(document).on('click', '[data-lightbox]', lity);
</script>
</body>
</html>
