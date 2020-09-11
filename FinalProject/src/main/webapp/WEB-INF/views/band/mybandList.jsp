<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

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
	
	var deleteIdx;
	var checkvalue;
	
	$(document).ready(function(){		
	
		// Activate tooltip
		$('[data-toggle="tooltip"]').tooltip();
		
		
		var test = "${type}";
		var type ="";
		
		if(test=="가입밴드")
			type="join";
		else
			type="create";
			
		$("#choice").val(type);
		
		
		
		
	});
	
	function goOneDelete(){		
	
	    var type = $("#choice").val();
	    
		var frm = document.frmDelete;
		frm.bandIdxModal.value = deleteIdx;
		frm.type.value=type;
		frm.method="POST";
		frm.action="oneDeleteBand.action";
		frm.submit();
	
	}
	
	function getBandidx(idx) {			  
		deleteIdx = idx;		
	}
	
	function onChange(){
		
		var type = $("#choice").val();
		location.href="mybandList.action?type="+type;
		
		
	}	

</script>


</head>
<body>
	<section id="portfolio-type-a">
		<div class="container">
        	<div class="table-wrapper">
            	<div class="table-title">
                	<div class="row">
                    	<div class="col-sm-6">
							<h2 style="color: #FFFFFF;float:left;margin-right:17px;">나의 밴드 목록</h2>
							<select id="choice" onchange="onChange();" style="margin-top:12px;color:black;">
								<option value="join">가입밴드</option>
								<option value="create" selected>생성밴드</option>							
							</select>
						</div>						
						<div class="col-sm-6">
							<a href="<%=request.getContextPath()%>/bandList.action" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>새로운 밴드 가입</span></a>
						</div>
                	</div>
            	</div>
            	<table class="table table-striped table-hover">
                	<thead>
                    	<tr>
                    		<th style="width: 10%; text-align: center;">밴드번호</th>
	                        <th>이미지</th>
 	                        <th>밴드명</th>
							<th>테마</th>
	                        <th>삭제</th>
                    	</tr>
                	</thead>
                	<tbody> 
                		<c:forEach var="map" items="${mybandList}" varStatus="status">
	                    	<tr>
	                    		<td style="text-align: center;">${map.BCODE}</td>
		                        <td onclick="location.href='<%= request.getContextPath() %>/mybandDetail.action?bcode=${map.BCODE}'" style="cursor:pointer;">
		                        	<img src="<%= request.getContextPath() %>/resources/files/${map.THUMBNAILFILENAME}" width="120px" height="100px">
`								</td>
		                        <td onclick="location.href='<%= request.getContextPath() %>/mybandDetail.action?bcode=${map.BCODE}'" style="cursor:pointer;">${map.BNAME}</td>
								<td onclick="location.href='<%= request.getContextPath() %>/mybandDetail.action?bcode=${map.BCODE}'" style="cursor:pointer;">${map.TNAME}</td>
		                        <td onClick="getBandidx('${map.BCODE}');">
		                            <a href="#deleteModal" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="탈퇴">&#xE872;</i></a>
		                        </td>
		                    </tr>
                    	</c:forEach>
                    	
                    	<c:if test="${empty mybandList}">
                    		<tr>
                    			<td colspan="5" align="center">
                    				밴드가 없습니다!
                    			</td>
                    		</tr>
                    	</c:if>
                    	
                    	
                	</tbody>
				</table>
				
				<nav class="navigation posts-navigation  wow fdeInUp" role="navigation" >
					<div align="center" style="width: 100%;"> 
						${pageBar}
					</div>
				</nav>
				
        	</div>
    	</div>
		
		<!-- Delete Modal HTML -->
		<div id="deleteModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<form name="frmDelete">
						<div class="modal-header">						
							<h4 class="modal-title">밴드 탈퇴</h4>
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						</div>
						<div class="modal-body">					
							<h3>정말 탈퇴하시겠습니까?</h3>
							
							<p class="text-warning"><small>탈퇴하면 되돌릴수 없습니다.</small></p>
						</div>
						<div class="modal-footer">
							<input type="button" class="btn btn-default" data-dismiss="modal" value="취소">						
							<input type="button" onclick="goOneDelete();" class="btn btn-danger" value="삭제">
							<input type="hidden" id="bandIdxModal" name="bandIdxModal"/>
							<input type="hidden" id="type" name="type"/>
						</div>
					</form>
				</div>
			</div>
		</div>
  </section>

</body>
</html>
