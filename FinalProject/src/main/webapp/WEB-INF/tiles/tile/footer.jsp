<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
    </div>    
    
     <footer class="footer-bottom">
    <div class="container">
      <div class="row text-center"> 
        
        <!--footer logos-->
        <div class="col-md-12 footer-logos"> <a href="#" class="company-logo"> <img src="<%= request.getContextPath() %>/resources/img/kh.png" class="logo"></a> <span class="company-name" style="font-size: 15pt;">ICE CREAM</span> </div>
        <!--/footer logos--> 
   
        <!--footer copyright-->
        <div class="col-md-12 footer-copyright">
          <p>© Copyright 2018. All rights reserved. made by <a href="https://iei.or.kr/">KH</a></p>
        </div>
        <!--/footer copyright--> 
        
      </div>
    </div>
  </footer>
<!-- </div> -->
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.1.11.1.js"></script> --%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>  -->
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
<script src="<%=request.getContextPath() %>/resources/js/classie.js"></script> 



<!-- services 라이브러리 불러오기 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5a85b637d665b4a8e68e04f6dddfb96f&libraries=services"></script>

<!-- =================== 다음 지도 api   ===============================-->
<!-- cultureDetail.jsp 에 넣으면 작동안됨... 불러오는값 중복시 값바꿀것 -->
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = {
    center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};  

//지도를 생성합니다    
var map = new daum.maps.Map(mapContainer, mapOption); 

//주소-좌표 변환 객체를 생성합니다
var geocoder = new daum.maps.services.Geocoder();

//주소로 좌표를 검색합니다
geocoder.addressSearch('${culturevo.ccbalcad}', function(result, status) {

// 정상적으로 검색이 완료됐으면 
 if (status === daum.maps.services.Status.OK) {

    var coords = new daum.maps.LatLng(result[0].y, result[0].x);

    // 결과값으로 받은 위치를 마커로 표시합니다
    var marker = new daum.maps.Marker({
        map: map,
        position: coords
    });

    // 인포윈도우로 장소에 대한 설명을 표시합니다
    var infowindow = new daum.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:6px 0;">${culturevo.ccmaname}</div>'
    });
    infowindow.open(map, marker);

    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    map.setCenter(coords);
} 
});    
</script>

<!-- top menu
    ================================================== --> 

<script>
function openNav() {
    document.getElementById("mySidenav").style.width = "320px";
    document.getElementById("main-block").style.marginRight = "320px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main-block").style.marginRight= "0";
}

</script> 
<script>


/*====================================
wow
======================================*/
			 new WOW().init();
</script> 
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
    