<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section id="portfolio-type-a">
    <div class="container"> <!-- Container -->
    	
      <div id="lightbox" class="row">
      	<c:forEach var="culturevo" items="${cultureList}">
		   	<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12 branding">
		         <div class="portfolio-item">
		           <div class="hover-bg"> <a href="cultureDetail.action?idx=${culturevo.idx}">
		             <div class="hover-text"> <small>${culturevo.ccbakdcd}</small>
		               <h2>${culturevo.ccmaname}</h2>
		               <div class="clearfix"></div>
		               <span>${culturevo.cccename}</span> </div>
		               	<c:if test="${culturevo.imageurl==null}">
     		 				<img src="<%=request.getContextPath() %>/resources/img/ready.png" class="img-responsive"/></a></div>     
       					 </c:if>
       					  <c:if test="${culturevo.imageurl!=null}">
     		 				<img src="${culturevo.imageurl}" class="img-responsive" alt="..."></a></div>
     		 			 </c:if> 			 
		             
		         </div>
		     </div>
    	</c:forEach>

      </div>
	  <%-- ====  페이지바 보여주기 ==== --%>
	  <nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
		<div align="center" style="width: 100%;"> 
			${pagebar}
		</div>
	  </nav>
    </div>
  </section>
 
