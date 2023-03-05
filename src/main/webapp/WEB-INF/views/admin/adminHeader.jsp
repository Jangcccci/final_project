<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${ param.title }</title>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<!-- bootstrap js: jquery load 이후에 작성할것.-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<script src="https://kit.fontawesome.com/f16d134c1f.js" crossorigin="anonymous"></script>

<!-- bootstrap css -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

<!-- 사용자작성 css -->
<link rel="stylesheet" href="${ pageContext.request.contextPath }/resources/css/common.css"/>
<c:if test="${not empty msg}">
<script>
	alert("${msg}");
</script>
</c:if>
</head>
<body>
<div class="flex container mx-auto pt-2">
  <div class="adm-side-bar">
    <aside class="w-44 flex flex-col rounded-xl bg-gray-600 text-gray-100">
      <div class="h-12 flex justify-center items-center p-4 text-lg font-bold border-b box-border">
        <span>관리자 메뉴</span>
      </div>
      <div class="font-bold flex justify-center">
        <ul class="flex flex-col">
          <li class="p-2">
             <a href="${pageContext.request.contextPath}/member/members.do">
              회원 관리
            </a>
          </li>
          <li class="p-2">
            <a href="${pageContext.request.contextPath}/movie/movieList.do">
              영화 관리
            </a>
          </li>
          <li class="p-2">
            <a href="#">
              매출 관리
            </a>
          </li>
          <li class="p-2">
            <a href="#">
              예매율 관리
            </a>
          </li>
        </ul>
      </div>
    </aside>
  </div>
<%--   <!-- 서비스 메뉴 -->
</div>
<!-- 서브 메뉴 -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
		<li class="nav-item active">
		  <a class="nav-link" href="#">영화</a>
		</li>
		<li class="nav-item active">
		  <a class="nav-link" href="#">극장</a>
		</li>
		<li class="nav-item active">
		  <a class="nav-link 
		  	<c:if test="${ pageContext.request.servletPath eq '/WEB-INF/views/reservation/reservation.jsp' }">red</c:if>" 
		  	href="${ pageContext.request.contextPath }/reservation/reservation.do">예매</a>
		</li>
		<li class="nav-item">
		   <a class="nav-link disabled" href="${ pageContext.request.contextPath }/admin/admin.do">관리자</a>
		</li>
		</ul>
		<form class="form-inline my-2 my-lg-0">
			<input id="searchMovie" class="form-control mr-sm-2" type="search" placeholder="" aria-label="Search">
			<button class="searchBtn" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
		</form>
	</div>
</nav>
<!-- 서브 메뉴 -->
 --%>