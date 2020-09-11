<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">	

	table#personInfoTbl {
	     width: 50%;
	     border: hidden; /* 선을 숨기는 것 */
	     margin: 10px;
	}
	
	table#personInfoTbl #th {
		width: 20%; 
   		height: 70px;
   		text-align: center;
   		background-color: #FAE0D4;
   		font-size: 14pt;
    }
	
	table#personInfoTbl td {
   		height: 70px; 
    }
</style>    
 

<div align="center" class="container" style="margin-bottom: 30px; margin-top: 30px;">
	<table id="personInfoTbl" class="table">
		<tr>
		  <td class="title">▷ 회원번호</td>
		  <td>${membervo.idx}</td>
		</tr>
		
		<tr>
		  <td class="title">▷ 성명</td>
		  <td>${membervo.name}</td>
		</tr>
		
		<tr>
		  <td class="title">▷ 아이디</td>
		  <td>${membervo.userid}</td>
		</tr>
		
		<tr>
		  <td class="title">▷ 비밀번호</td>
		  <td>****${fn:substring(membervo.pwd, 4, fn:length(membervo.pwd) )}</td>
		       <%-- fn:substring(string, begin, end) 은 
                    string 문자열중 string의 begin 인덱스에서 시작해서 end 인덱스에 끝나는 부분의 문자열을 리턴. 
               --%>
		</tr>
		
		<tr>
		  <td class="title">▷ 이메일</td>
		  <td>${membervo.email}</td>
		</tr>
		
		<tr>
		  <td class="title">▷ 연락처</td>
		  <td>${membervo.allHp}</td>
		</tr>
		
		<tr>
		  <td class="title">▷ 우편번호</td>
		  <td>${membervo.allPost}</td>
		</tr>
		
		<tr>
		  <td class="title">▷ 주소</td>
		  <td>${membervo.allAddr}</td>
		</tr>
		
		<tr>
			<td align="center" colspan="2"><button type="button" onclick="location.href='<%= request.getContextPath() %>/memberEdit.action?idx=${membervo.idx}'" style="background-color: #E24B50; color: #FFFFFF;">수정하기</button></td>
		</tr>
		
	</table>
</div>

