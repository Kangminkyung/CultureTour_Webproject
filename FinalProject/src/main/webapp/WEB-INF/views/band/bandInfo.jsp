<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
body {font-family: Arial, Helvetica, sans-serif;}

.myImg2{
    border-radius: 5px;
    cursor: pointer;
    transition: 0.3s;
}

.myImg2:hover {opacity: 0.7;}

/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
}

/* Modal Content (image) */
.modal-content {
    margin: auto;
    display: block;
    width: 80%;
    max-width: 700px;
}

/* Caption of Modal Image */
#caption {
    margin: auto;
    display: block;
    width: 80%;
    max-width: 700px;
    text-align: center;
    color: #ccc;
    padding: 10px 0;
    height: 150px;
}

/* Add Animation */
.modal-content, #caption {    
    -webkit-animation-name: zoom;
    -webkit-animation-duration: 0.6s;
    animation-name: zoom;
    animation-duration: 0.6s;
}

@-webkit-keyframes zoom {
    from {-webkit-transform:scale(0)} 
    to {-webkit-transform:scale(1)}
}

@keyframes zoom {
    from {transform:scale(0)} 
    to {transform:scale(1)}
}

/* The Close Button */
.close {
    position: absolute;
    top: 15px;
    right: 35px;
    color: #f1f1f1;
    font-size: 40px;
    font-weight: bold;
    transition: 0.3s;
}

.close:hover,
.close:focus {
    color: #bbb;
    text-decoration: none;
    cursor: pointer;
}

/* 100% Image Width on Smaller Screens */
@media only screen and (max-width: 700px){
    .modal-content {
        width: 100%;
    }
}

</style>
   <script type="text/javascript">
   
      function bandRegister(userid){
         
         var bcode = ${bcode};
         
         var frm = document.bandjoin;
      
            frm.userid.value = userid;
            frm.bcode.value = bcode;
            frm.method="GET";
            frm.action="<%=request.getContextPath()%>/bandJoin.action";
            frm.submit();
      
      } // end of bandRegister(userid) ------------------------------

      
      
      
   </script>

<form name = "bandjoin">
   <input type="hidden" name="userid"/>
   <input type="hidden" name="bcode"/>
</form>

  <!-- banner Page
    ==========================================-->
<%--     <c:forEach var="viewBandImageListmap" items="${viewBandImageList}" varStatus="status">
      <img src="<%= request.getContextPath() %>/resources/files/${viewBandImageListmap.THUMBNAILFILENAME}" class="my_thumbnail" style="margin-right: 10px;" />
   </c:forEach> --%>
<form name = "editFrm">
   <input type="hidden" name="bcode"/>
</form>   

        <c:if test="${main.IMAGEFILENAME != null}">
           <div id="page-banner" style="background-image: url(<%= request.getContextPath() %>/resources/files/${main.IMAGEFILENAME});"> </div>
        </c:if>
        <c:if test="${main.IMAGEFILENAME == null}">
           <div id="page-banner" style="background-image: url(<%= request.getContextPath() %>/resources/img/default/img${rnd}.png);"> </div>
        </c:if>
        
     
  <!-- portfolio conten
    ==========================================-->
    
  <!--Post container-->
  <div class="container">
    <div class="row wow fdeInUp"> 
      <!--blog posts container-->
      <div class="col-md-8 col-md-offset-2 col-sm-12 single-post">
        <!-- <article class="post"> -->
        <div class="portfolio-item">
          <h1 align="center">${bandInfo.bname}</h1>
          
          <c:forEach var="viewBandImageListmap" items="${viewBandImageSub}"  varStatus="status">           
           <div class="" align="center" >
              <img id= "myImg${status.index}" class="myImg2" src="<%= request.getContextPath() %>/resources/files/${viewBandImageListmap.IMAGEFILENAME}" style="width:500%;max-width:500px"></br></br>
              &nbsp;&nbsp;
           </div>
        
         </c:forEach>
          &nbsp;
         <p>${bandInfo.binfo}</p>
          
          <ul class="details">
            <li>
              <h4>밴드정보</h4>
            </li>
            <li><strong> 테마</strong>
              <p>${bandInfo.bthema}</p>
            </li>
            <li> <strong>생성일</strong>
              <p>${bandInfo.bregdate}</p>
            </li>
            <li> <strong>참가인원</strong>
              <p>${bandInfo.bmembercnt}</p>
            </li>
            <li><strong>밴드장</strong>
              <p>${bandInfo.badmin}</p>
            </li>
          </ul>
          
             
        <!-- </article> -->
        </div>
      </div>
      <!--blog posts container-->
    </div>
  </div>
  
       
       <c:if test="${sessionScope.loginuser.userid == bandInfo.badmin}">
             <div align="center" style="padding-bottom: 10px;"><button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/bandEdit.action?bcode=${bandInfo.bcode}'">수정</button>
            <button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/bandAdmin.action?bcode=${bandInfo.bcode}'">관리</button>
            <button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/bandDel.action?bcode=${bandInfo.bcode}'">삭제</button></div>
      </c:if>
      
       <c:if test="${sessionScope.loginuser.userid != bandInfo.badmin}">
             <div align="center" style="padding-bottom: 10px;"><button type="button" onClick="bandRegister('${sessionScope.loginuser.userid}')" >참가신청</button></div>
      </c:if>
      
        
    </div>
  </section>
  
 <!-- The Modal -->
<div id="myModal" class="modal">
  <span class="close">&times;</span>
  <img class="modal-content" id="img">
  <div id="caption"></div>
</div>         

<script>

var modal = document.getElementById('myModal');

// Get the image and insert it inside the modal - use its "alt" text as a caption
for(var i = 0; i<${size}; i++){
   
   var img = document.getElementById("myImg"+i);
   var modalImg = document.getElementById("img");
   var captionText = document.getElementById("caption");
   img.onclick = function(){
       modal.style.display = "block";
       modalImg.src = this.src;
       captionText.innerHTML = this.alt;
   }
}


// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() { 
    modal.style.display = "none";
}


</script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> 
<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/bootstrap.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/SmoothScroll.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/lity.js"></script> 
<script src="<%=request.getContextPath() %>/resources/js/owl.carousel.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.isotope.js"></script> 
<!-- Custom Javascripts
    ================================================== --> 
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/main.js"></script> 
<script src="<%=request.getContextPath() %>/resources/js/wow.min.js"></script> 
<script src="<%=request.getContextPath() %>/resources/js/imagesloaded.js"></script> 
<script src="<%=request.getContextPath() %>/resources/js/classie.js"></script> 
<script src="<%=request.getContextPath() %>/resources/js/AnimOnScroll.js"></script> 

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
  