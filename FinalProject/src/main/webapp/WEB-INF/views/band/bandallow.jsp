<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">

<style type="text/css">
	body {
        color: #566787;
		background: #f5f5f5;
		font-family: 'Varela Round', sans-serif;
		font-size: 13px;
	}
	.table-wrapper {
        background: #fff;
        padding: 20px 25px;
        margin: 30px 0;
		border-radius: 3px;
        box-shadow: 0 1px 1px rgba(0,0,0,.05);
        
    }
	.table-title {        
		padding-bottom: 15px;
		background: #435d7d;
		color: #fff;
		padding: 16px 30px;
		margin: -20px -25px 10px;
		border-radius: 3px 3px 0 0;
    }
    .table-title h2 {
		margin: 5px 0 0;
		font-size: 24px;
	}
	.table-title .btn-group {
		float: right;
	}
	.table-title .btn {
		color: #fff;
		float: right;
		font-size: 13px;
		border: none;
		min-width: 50px;
		border-radius: 2px;
		border: none;
		outline: none !important;
		margin-left: 10px;
	}
	.table-title .btn i {
		float: left;
		font-size: 21px;
		margin-right: 5px;
	}
	.table-title .btn span {
		float: left;
		margin-top: 2px;
	}
    table.table tr th, table.table tr td {
        border-color: #e9e9e9;
		padding: 12px 15px;
		vertical-align: middle;
		
    }
	table.table tr th:first-child {
		width: 60px;
	}
	table.table tr th:last-child {
		width: 100px;
	}
    table.table-striped tbody tr:nth-of-type(odd) {
    	background-color: #fcfcfc;
	}
	table.table-striped.table-hover tbody tr:hover {
		background: #f5f5f5;
	}
    table.table th i {
        font-size: 13px;
        margin: 0 5px;
        cursor: pointer;
    }	
    table.table td:last-child i {
		opacity: 0.9;
		font-size: 22px;
        margin: 0 5px;
        
    }
	table.table td a {
		font-weight: bold;
		color: #566787;
		display: inline-block;
		text-decoration: none;
		outline: none !important;

	}
	table.table td a:hover {
		color: #2196F3;
	}
	table.table td a.edit {
        color: #FFC107;
    }
    table.table td a.delete {
        color: #F44336;
    }
    table.table td i {
        font-size: 19px;
    }
	table.table .avatar {
		border-radius: 50%;
		vertical-align: middle;
		margin-right: 10px;
	}
    .pagination {
        float: right;
        margin: 0 0 5px;
    }
    .pagination li a {
        border: none;
        font-size: 13px;
        min-width: 30px;
        min-height: 30px;
        color: #999;
        margin: 0 2px;
        line-height: 30px;
        border-radius: 2px !important;
        text-align: center;
        padding: 0 6px;
    }
    .pagination li a:hover {
        color: #666;
    }	
    .pagination li.active a, .pagination li.active a.page-link {
        background: #03A9F4;
    }
    .pagination li.active a:hover {        
        background: #0397d6;
    }
	.pagination li.disabled i {
        color: #ccc;
    }
    .pagination li i {
        font-size: 16px;
        padding-top: 6px
    }
    .hint-text {
        float: left;
        margin-top: 10px;
        font-size: 13px;
    }    
	/* Custom checkbox */
	.custom-checkbox {
		position: relative;
	}
	.custom-checkbox input[type="checkbox"] {    
		opacity: 0;
		position: absolute;
		margin: 5px 0 0 3px;
		z-index: 9;
	}
	.custom-checkbox label:before{
		width: 18px;
		height: 18px;
	}
	.custom-checkbox label:before {
		content: '';
		margin-right: 10px;
		display: inline-block;
		vertical-align: text-top;
		background: white;
		border: 1px solid #bbb;
		border-radius: 2px;
		box-sizing: border-box;
		z-index: 2;
	}
	.custom-checkbox input[type="checkbox"]:checked + label:after {
		content: '';
		position: absolute;
		left: 6px;
		top: 3px;
		width: 6px;
		height: 11px;
		border: solid #000;
		border-width: 0 3px 3px 0;
		transform: inherit;
		z-index: 3;
		transform: rotateZ(45deg);
	}
	.custom-checkbox input[type="checkbox"]:checked + label:before {
		border-color: #03A9F4;
		background: #03A9F4;
	}
	.custom-checkbox input[type="checkbox"]:checked + label:after {
		border-color: #fff;
	}
	.custom-checkbox input[type="checkbox"]:disabled + label:before {
		color: #b8b8b8;
		cursor: auto;
		box-shadow: none;
		background: #ddd;
	}
	/* Modal styles */
	.modal .modal-dialog {
		max-width: 400px;
	}
	.modal .modal-header, .modal .modal-body, .modal .modal-footer {
		padding: 20px 30px;
	}
	.modal .modal-content {
		border-radius: 3px;
	}
	.modal .modal-footer {
		background: #ecf0f1;
		border-radius: 0 0 3px 3px;
	}
    .modal .modal-title {
        display: inline-block;
    }
	.modal .form-control {
		border-radius: 2px;
		box-shadow: none;
		border-color: #dddddd;
	}
	.modal textarea.form-control {
		resize: vertical;
	}
	.modal .btn {
		border-radius: 2px;
		min-width: 100px;
	}	
	.modal form label {
		font-weight: normal;
	}	
</style>

<script type="text/javascript">
	
	var allowbandidx;
	var checkvalue;
	

	$(document).ready(function(){		
	
	
		// Activate tooltip
		$('[data-toggle="tooltip"]').tooltip();
		
		
	});
	
	function allowband(){		
		
		//alert("gg");
		
		var frm = document.frmallowband;
		
		frm.bandIdxModal.value = allowbandidx;
		
		frm.method="POST";
		frm.action="oneallowband.action";
		frm.submit();
	
	}
	
	function getBandidx(idx) {			  
		allowbandidx = idx;		
	}
	
	function allCheckBox() {

		var bool = $("#allCheckOrNone").is(':checked');
		/* $(""#allCheckOrNone").is(':checked') 은
	            선택자 $("#allCheckOrNone") 이 체크 되어지면 true 를 나타내고,
	            선택자 $("#allCheckOrNone") 이 체크가 안되어지면 false 를 나타내어주는 것이다.  
	    */
		
		$(".chkboxpnum").prop('checked', bool);
			
	}// end of allCheckBox()------------
	
	function checkboxevent(){
		
		var pnumArr = document.getElementsByName("pnum");
		
		var cnt = 0;
		
		
	for(var i=0; i<pnumArr.length; i++) {
			
			if(pnumArr[i].checked == true) {
				cnt++;
			
		}
	}
	

	if(cnt==0){
			
		  alert("승인할 밴드를 하나이상 선택하세요!!");
		  
	}else if(cnt > 0){
		
		 var yn = confirm("선택하신 밴드를 승인하시겠습니까?");
	
		 if(yn==false) {
			 return;
			 }else{
				 
				 var frm = document.bandallowfrm;
				 frm.method ="post";
				 frm.action ="checkboxbandallow.action";
			     frm.submit();
				 
			 }
		 
		 }
		 
	}// end of allCheckBox()-------------------
	
	
	
	
</script>


</head>
<body>
	<section id="portfolio-type-a">
		<div class="container">
        	<div class="table-wrapper">
            	<div class="table-title">
                	<div class="row">
                    	<div class="col-sm-12">
							<h2 style="color: #FFFFFF;text-align:center;margin-right:5px;">밴드 신청 목록(관리자페이지)</h2>
						</div>						
                	</div>
            	</div>
            <form name="bandallowfrm">
            	<table class="table table-striped table-hover">
                	<thead>
                    	<tr>
                    		<th><input type="checkbox" id="allCheckOrNone" onClick="allCheckBox();" /><span style="font-size: 10pt;"><label for="allCheckOrNone">전체선택</label></span></th>
                    		<th style="width: 10%; text-align: center;">밴드번호</th>
	                        <th>이미지</th>
 	                        <th>밴드명</th>
							<th>테마</th>
	                        <th>승인</th>
                    	</tr>
                	</thead>
                	<tbody> 
                	
                	<c:if test="${empty bandlist}">
                		<tr>
                			<td colspan="6" style="text-align: center;">가입신청인이 없습니다!</td>
                		</tr>
                	</c:if>
                	
              		<c:forEach var="map" items="${bandlist}" varStatus="status">
	                    	<tr>
	                    	  	<td><input type="checkbox" name="pnum" id="pnum${status.index}" value="${map.BCODE}" class="chkboxpnum" />&nbsp;<label for="pnum${status.index}">선택</label></td>
							
	                    		<td style="text-align: center;">${map.BCODE}</td>
		                        <td onclick="location.href='<%= request.getContextPath() %>/mybandDetail.action?bcode=${map.BCODE}'" style="cursor:pointer;">
		                        	<img src="<%= request.getContextPath() %>/resources/files/${map.THUMBNAILFILENAME}">
`								</td>
		                        <td onclick="location.href='<%= request.getContextPath() %>/mybandDetail.action?bcode=${map.BCODE}'" style="cursor:pointer;">${map.BNAME}</td>
								<td onclick="location.href='<%= request.getContextPath() %>/mybandDetail.action?bcode=${map.BCODE}'" style="cursor:pointer;">${map.TNAME}</td>
		                        <td onClick="getBandidx('${map.BCODE}');">
		                        	
			                      <a href="#updateModal" class="delete" data-toggle="modal"><img src="<%= request.getContextPath() %>/resources/img/downimages.jpg" title="승인"></a>
		                        
		                    <!--         <i class="" data-toggle="tooltip" title="승인">&#xE872;</i></a> -->
		                        </td>
		                    </tr>
                    	</c:forEach> 
                    
                	</tbody>
				</table>
			</form>
					<button type="button" onclick="checkboxevent();" style="color: #FFFFFF; background-color: #468EDE;">선택 밴드 승인</button>
				<nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
			 	<div align="center" style="width: 100%;"> 
						${pageBar}
					</div> 
				</nav>
				
        	</div>
    	</div>
		
		<!-- Delete Modal HTML -->
		<div id="updateModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<form name="frmallowband">
						<div class="modal-header">						
							<h4 class="modal-title">밴드 가입 승인</h4>
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						</div>
						<div class="modal-body">					
							<h3>이밴드를 승인하시겠습니까?</h3>
							
							<p class="text-warning"><small>.</small></p>
						</div>
						<div class="modal-footer">
							<input type="button" class="btn btn-default" data-dismiss="modal" value="취소">						
							<input type="button" onclick="allowband();" class="btn btn-danger" value="승인">
							<input type="hidden" id="bandIdxModal" name="bandIdxModal"/>
						</div>
					</form>
				</div>
			</div>
		</div>
  </section>

</body>
</html>
