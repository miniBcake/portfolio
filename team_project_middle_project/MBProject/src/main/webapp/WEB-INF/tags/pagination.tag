<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="totalPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="filterCategory" required="false" type="java.lang.String" %>
<%
    // 한 번에 보일 페이지 버튼의 최대 개수
    int pageRange = 5;

    // 시작 페이지와 끝 페이지 계산
    int startPage = Math.max(1, currentPage - pageRange / 2);
    int endPage = Math.min(totalPages, startPage + pageRange - 1);

    // 만약 끝 페이지가 마지막 페이지보다 작으면, 시작 페이지를 조정
    if (endPage - startPage < pageRange - 1) {
        startPage = Math.max(1, endPage - pageRange + 1);
    }
%>
		<!-- 페이지 네비게이션 -->
                 <div class="pagination">
                     <!-- 이전 페이지 버튼 -->
                     <c:if test="${currentPage > 1}">
                         <a href="?page=${currentPage - 1}&filterCategory=${filterCategory}">&laquo; 이전</a>
                     </c:if>

                     <!-- 페이지 번호 -->
                     <c:forEach var="i" begin="1" end="${totalPages}">
                         <c:choose>
                             <c:when test="${i == currentPage}">
                                 <strong>${i}</strong>
                             </c:when>
                             <c:otherwise>
                                 <a href="?page=${i}&filterCategory=${filterCategory}">${i}</a>
                             </c:otherwise>
                         </c:choose>
                     </c:forEach>

                     <!-- 다음 페이지 버튼 -->
                     <c:if test="${currentPage < totalPages}">
                         <a href="?page=${currentPage + 1}&filterCategory=${filterCategory}">다음 &raquo;</a>
                     </c:if>
                 </div>