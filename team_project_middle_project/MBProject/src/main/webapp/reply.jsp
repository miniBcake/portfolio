<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import="java.util.ArrayList, java.util.HashMap, java.util.Map, java.util.List"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
   <head>
      <title>댓글 작성!</title>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
      <link rel="stylesheet" href="assets/css/main.css" />
      <link rel="stylesheet" href="assets/css/pagination.css">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
      
   </head>
   <body class="subpage">
      <div id="page-wrapper">

         <!-- 임시 게시물 데이터 설정 -->

        <!-- Header 커스텀 태그 -->
        <custom:header/>

        <!-- Content -->
        <section id="content">
            <div class="container">
                <div class="row">
                    <div class="col-3 col-12-medium">

                        <!-- 댓글 작성하는 게시글의 정보를 보여주는 곳 -->
                  <section>
                      <h2>${board.boardTitle}</h2><br>
                      <h3>작성자 : ${board.memberNickname}</h3><br>
                      <p>내용 : ${board.boardContent}</p>
                      <button>
                         <a href="viewBoard.do?boardNum=${board.boardNum}">다시 게시글 보러가기</a>
                      </button>
                  </section>

                     </div>
                     <div class="col-9 col-12-medium imp-medium">
                        <!-- Main Content -->
                           <section id="reply-section">
                               <header>
                                   <h2>댓글 작성</h2>
                                   <h3>게시글에 댓글을 달아보세요!</h3>
                                   <form action="addReply.do" method="post">
                                      <input type="hidden" name="boardNum" value="${board.boardNum}" />
                                       <textarea class="form-control" name="newReplyContents" id="replyContent" placeholder="작성할 댓글을 입력해주세요!"></textarea>
                                       <button class="btn btn-primary w-100 py-3" type="submit">댓글 작성</button>
                                   </form>
                               </header>
                           </section>
                           
                           <section>
                              <!-- 댓글이 작성되면 페이지네이션으로 댓글 추가 -->
                              <c:if test="${empty replyList}">
                              아직 작성된 댓글이 없습니다..
                              </c:if>
                              <c:if test="${not empty replyList}">
                                  <c:forEach var="reply" items="${replyList}">
                                      <div id="comment-${reply.replyNum}">
                                          <div class="comment-content" id="content-${reply.replyNum}">
                                              작성자: ${reply.memberNickname}<br>
                                              <h2>${reply.replyContent}</h2>
                                          </div>
                                       <br>                                          
                              
                              <!-- 댓글 수정 버튼 -->
                              <button class="btn btn-primary btn-sm" onclick="checkAndEdit(${reply.replyNum}, ${board.boardNum}); return false;">
                                  수정하기
                              </button>
                              
                              <!-- 댓글 수정 폼이 동적으로 들어갈 div -->
                              <div id="editForm-${reply.replyNum}"></div>
                                          <!-- 댓글 삭제 버튼 -->
                                          <form action="deleteReply.do" method="post" style="display:inline;">
                                           <input type="hidden" name="replyNum" value="${reply.replyNum}" />
                                           <input type="hidden" name="boardNum" value="${board.boardNum}" />
                                              <button class="btn btn-danger btn-sm" type="submit">삭제하기</button>
                                          </form>
                              
                                         <!-- 댓글 수정 폼 -->
                                          <c:if test="${param.editId == reply.replyNum}">
                                              <form action="updateReply.do" method="post">
                                                  <input type="hidden" name="replyNum" value="${reply.replyNum}" />
                                                  <textarea class="form-control" name="replyContent" style="height: 150px;">${reply.content}</textarea>
                                                  <button class="btn btn-primary mt-2" type="submit">수정 완료</button>
                                                  <a href="viewBoard.do?boardNum=${board.boardNum}">
                                                      <button class="btn btn-secondary mt-2" type="button">취소</button>
                                                  </a>
                                              </form>
                                          </c:if>
                                          
                                          <hr>
                                      </div>
                                  </c:forEach>
                              </c:if>
                              
                                 <!-- 페이지네이션 시작 -->
                        <section id="pagination">
                            <div class="pagination">
                                <!-- 이전 페이지 버튼 -->
                                <c:if test="${currentPage > 1}">
                                    <a href="?page=${currentPage - 1}&boardNum=${board.boardNum}">&laquo; 이전</a>
                                </c:if>
                        
                                <!-- 페이지 번호 -->
                                <c:set var="startPage" value="${currentPage - 5}"/>
                                <c:set var="endPage" value="${currentPage + 4}"/>
                        
                                <c:if test="${startPage < 1}">
                                    <c:set var="startPage" value="1"/>
                                </c:if>
                                <c:if test="${endPage > totalPages}">
                                    <c:set var="endPage" value="${totalPages}"/>
                                </c:if>
                        
                                <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                    <c:choose>
                                        <c:when test="${i == currentPage}">
                                            <strong>${i}</strong>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="?page=${i}&boardNum=${board.boardNum}">${i}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                        
                                <!-- 다음 페이지 버튼 -->
                                <c:if test="${currentPage < totalPages}">
                                    <a href="?page=${currentPage + 1}&boardNum=${board.boardNum}">다음 &raquo;</a>
                                </c:if>
                            </div>
                        </section>
                        <!-- 페이지네이션 종료 -->
                           </section>
                     </div>
                  </div>
               </div>
            </section>
         
         <!-- footer 커스텀 태그 -->
         <custom:footer/>
      </div>

      <!-- Scripts -->
         <script src="assets/js/jquery.min.js"></script>
         <script src="assets/js/browser.min.js"></script>
         <script src="assets/js/breakpoints.min.js"></script>
         <script src="assets/js/util.js"></script>
         <script src="assets/js/main.js"></script>
                             <script>
                              // 수정 폼을 보여주기 전에 원래 댓글 내용을 저장해둠
                              // 댓글 수정 버튼 클릭 시 호출되는 함수
                              function checkAndEdit(replyNum, boardNum) {
                                  console.log("checkAndEdit 함수 호출됨 - replyNum:", replyNum, "boardNum:", boardNum);
                              
                                  // 서버로 댓글 작성자가 수정할 권한이 있는지 확인하는 AJAX 요청
                                  $.ajax({
                                      url: "checkReplyOwner.do",  // 서버에 요청할 URL
                                      type: "POST",  // HTTP 요청 방식 (POST)
                                      data: {
                                          replyNum: replyNum,  // 댓글 번호
                                          boardNum: boardNum  // 게시글 번호도 함께 전송
                                      },
                                      dataType: "json",  // 서버로부터 받을 데이터 형식 (JSON)
                                      success: function(response) {
                                          console.log("서버 응답:", response);
                              
                                          if (!response.isLoggedIn) {
                                              // 로그인 상태가 아니면 경고 메시지를 출력하고 로그인 페이지로 리디렉션
                                              swal({
                                                  title: "로그인이 필요합니다",
                                                  text: response.message,
                                                  type: "warning",
                                                  showConfirmButton: true,
                                                  confirmButtonText: "로그인 페이지로 이동"
                                              }, function() {
                                                  window.location.href = "/MBProject/loginPage.do";  // 로그인 페이지로 이동
                                              });
                                          } else if (response.isOwner) {
                                              // 댓글 작성자라면 수정 폼을 동적으로 생성
                                              $("#editForm-" + replyNum).html(
                                                  '<form id="editForm-' + replyNum + '" action="updateReply.do" method="post">' +
                                                  '<input type="hidden" name="replyNum" value="' + replyNum + '" />' +
                                                  '<input type="hidden" name="boardNum" value="' + boardNum + '" />' +
                                                  '<textarea class="form-control" name="updatedContent" style="height: 150px;">' + response.replyContent + '</textarea>' +
                                                  '<button class="btn btn-primary mt-2" type="submit">수정 완료</button>' +
                                                  '<button class="btn btn-secondary mt-2" type="button" onclick="cancelEdit(' + replyNum + ');">취소</button>' +
                                                  '</form>'
                                              );
                                          } else {
                                              // 수정 권한이 없는 경우 경고 메시지 출력
                                              swal({
                                                  title: "수정 권한이 없습니다",
                                                  text: response.message,
                                                  type: "error",
                                                  showConfirmButton: true
                                              });
                                          }
                                      },
                                      error: function(xhr, status, error) {
                                          console.error("AJAX 요청 실패:", status, error);
                                      }
                                  });
                              }
                              
                              // 수정 취소 시 호출되는 함수
                              function cancelEdit(replyNum) {
                                  console.log("취소할 댓글 번호:", replyNum);
                                  $("#editForm-" + replyNum).empty();  // 수정 폼을 제거
                              }
                              </script>

   </body>
</html>