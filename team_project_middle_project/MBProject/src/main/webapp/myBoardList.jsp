<%@page import="java.util.Random"%>
<%@page import="model.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>갈빵질빵 - 나의 게시글</title>
</head>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<style>
.pagination a, .pagination strong {
    display: inline-block;
    margin: 0 5px;
    padding: 5px 10px;
    text-decoration: none;
    color: #007bff;
}

.pagination a:hover {
    background-color: #e9ecef;
    border-radius: 4px;
}

.pagination strong {
    font-weight: bold;
    color: #495057;
}
</style>
<body>

<!-- Header -->
<custom:header/>
<section id="content">
    <div class="container">
        <div class="row">
            <div class="col-9 col-12-medium">
                <!-- Main Content -->
                <section>
                    <!-- 게시글 목록 출력 -->
                    
                    <h3>나의 게시글 목록</h3>
                    <div>
                        <ul>
                            <c:forEach var="board" items="${myBoardList}">
                                <li>제목 : ${board.boardTitle}</li> <!-- 게시글 제목과 기타 데이터 -->
                                <li>작성일 : ${board.boardWriteDay}</li>
                                <li>좋아요 : ${board.likeCnt}</li>
                                <a href="viewBoard.do?boardNum=${board.boardNum}">
                                <button type="button">자세히 보기</button>
                                </a>
                                <hr>
                            </c:forEach>
                        </ul>
                    </div>

                    <custom:pagination currentPage="${currentPage}" totalPages="${totalPages}" filterCategory="${filterCategory}" />
                </section>
            </div>
            <custom:userMenu/>
        </div>
    </div>
</section>

<!-- Footer -->
<custom:footer/>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/browser.min.js"></script>
<script src="assets/js/breakpoints.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/main.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
    integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
    crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>
