<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"
   import="java.util.ArrayList, java.util.HashMap, java.util.Map, java.util.List"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<title>게시글 작성</title>
<meta charset="utf-8" />
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/pagination.css">



<style>
/* select 박스 스타일 */
#category {
   width: 6%;
   padding: 9px;
   border: 1px solid #ddd;
   border-radius: 4px;
   font-size: 16px;
   box-sizing: border-box;
   background-color: #f9f9f9;
   transition: border-color 0.3s;
}

#category:focus {
   border-color: #007bff;
   outline: none;
}

/* select 박스 스타일 */
#visibility {
   width: 6%;
   padding: 9px;
   border: 1px solid #ddd;
   border-radius: 4px;
   font-size: 16px;
   box-sizing: border-box;
   background-color: #f9f9f9;
   transition: border-color 0.3s;
}

#visibility:focus {
   border-color: #007bff;
   outline: none;
}
</style>

<style type="text/css">
.custom-checkbox {
   display: inline-block;
   position: relative;
   padding-left: 35px; /* 체크박스와 라벨 텍스트 사이의 여백 */
   cursor: pointer;
   font-size: 16px; /* 텍스트 크기 조정 */
}

.custom-checkbox input {
   position: absolute;
   opacity: 0;
   cursor: pointer;
}

.checkmark {
   position: absolute;
   top: 0;
   left: 0;
   height: 25px; /* 체크박스 높이 */
   width: 25px; /* 체크박스 너비 */
   background-color: #eee;
   border-radius: 5px; /* 둥근 모서리 */
}

.custom-checkbox input:checked ~ .checkmark {
   background-color: #2196F3;
}

.checkmark:after {
   content: "";
   position: absolute;
   display: none;
}

.custom-checkbox input:checked ~ .checkmark:after {
   display: block;
   left: 9px;
   top: 5px;
   width: 5px;
   height: 10px;
   border: solid white;
   border-width: 0 3px 3px 0;
   transform: rotate(45deg);
}
</style>


</head>

<body class="subpage">
   <div id="page-wrapper">

      <!-- Header -->
      <custom:header />

      <!-- Content -->
      <section id="content">
         <div class="container">
            <div class="row">
               <div class="col-3 col-12-medium">

                  <!-- Sidebar -->
                  <section>
                     <header>
                        <h2>게시판 선택하기</h2>
                     </header>
                     <ul class="link-list">
                        <li><a href="listBoards.do?categoryName=normal"">일반 게시판</a></li>
                        <li><a href="listBoards.do?categoryName=request">문의 게시판</a></li>
                     </ul>
                  </section>
               </div>
               <div class="col-9 col-12-medium imp-medium">

                  <!-- Main Content -->
                  <section>
                     <header>
                        <h2>게시물 수정</h2>
                          <!-- 게시글 번호를 위한 hidden input -->
   						  <input type="hidden" id="boardNum" name="boardNum" value="${board.boardNum}" />
    
                        <h3>게시물을 수정 해주세요!</h3>
                     </header>
                  </section>

                  <section>
                     <form id="postForm">
                        <div>
							<!-- 게시물 카테고리가 'request'일 경우에만 비공개 체크박스 표시 -->
							<c:if test="${categoryName == 'request'}">
							   <!-- 비공개 게시물 선택 -->
							   <label class="custom-checkbox">
							      <input type="checkbox" id="postSecret">
							      <span class="checkmark"></span>
							      비공개 게시물로 등록
							   </label>
							</c:if>
                              게시물로 등록
							<select id="boardCateName" name="boardCateName">
							    <option value="normal" <c:if test="${boardCateName == 'normal'}">selected</c:if>>일반</option>
							    <option value="request" <c:if test="${boardCateName == 'request'}">selected</c:if>>문의</option>
							</select>
                        </div>
                        <br>

                        
                           <div>
                              <label for="boardTitle">게시글 제목 : </label> <input type="text"
                                 id="boardTitle" name="boardTitle" placeholder="제목을 입력해주세요!" value="${board.boardTitle}" />
                           </div>
                           <br>
                           <!-- CKEditor를 사용할 게시글 내용 입력 필드 -->
                           <div>
                              <label for="postContent">게시글 내용</label><br>
                              <!-- 기존 내용이 있을 경우 입력 필드에 표시 -->
                              <textarea id="postContent" name="boardContent">
                                 <c:out value="${board.boardContent}" />
                              </textarea>
                              <!-- c out 태그을 사용하면 Hello &lt;b&gt;World&lt;/b&gt;와 같이 이스케이프되어 출력됩니다. -->
                              <!-- 게시글 등록 버튼 -->
                              <button class="btn btn-primary w-100 py-3" id="postButton"
                                 type="submit">게시글 등록</button>
                           </div>
                     </form>
                  </section>
               </div>
            </div>
         </div>
      </section>

      <custom:footer />
   </div>

   <!-- Scripts -->
   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>

   <!-- CKEditor 5 스크립트 로드 -->
   <!-- CKEditor 메인 라이브러리 (ckeditor.js): 이 스크립트는 CKEditor 5의 메인 라이브러리 파일입니다. ckeditor.js는 CKEditor 에디터의 핵심 기능을 제공합니다. -->
   <script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/ckeditor.js"></script>
   <script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>

   <!-- CKEditor Scripts -->
<script>
    let editorInstance;
    let previousData = ''; // 이전 에디터 데이터를 저장할 변수
    const fileUploadUrl = '<%= request.getAttribute("uploadUrl") %>';
	const fileDeleteUrl = '<%= request.getAttribute("deleteUrl") %>';

    document.addEventListener('DOMContentLoaded', () => {
        // CKEditor 초기화
        ClassicEditor.create(document.querySelector('#postContent'), {
            language: 'ko',
            ckfinder: {
                uploadUrl: fileUploadUrl,
                options: {
                    resourceType: 'Images',
                    deleteUrl: fileDeleteUrl
                }
            },
            height: 500
        })
        .then(editor => {
            editorInstance = editor;

            // 초기 데이터 설정
            const initialContent = `<c:out value="${board.boardContent}" escapeXml="false" />`;
            // escapeXml == 
            editor.setData(initialContent);

            // 초기 데이터 설정 후 previousData에 저장
            previousData = editor.getData();
            console.log("[DEBUG] CKEditor 초기화 완료, 기존 데이터 로드됨:", previousData);

            // 에디터 데이터 변경 감지
            editor.model.document.on('change:data', () => {
                const currentData = editor.getData();
                console.log("[DEBUG] 현재 에디터 데이터:", currentData);

                if (previousData) {
                    const deletedImages = findDeletedImages(previousData, currentData);
                    deletedImages.forEach(imageUrl => {
                        console.log("[INFO] 삭제 요청할 이미지 URL:", imageUrl);
                        deleteFile(imageUrl);
                    });
                }

                previousData = currentData;
                console.log("[DEBUG] 이전 에디터 데이터 업데이트됨");
            });
        })
        .catch(error => {
            console.error("[ERROR] CKEditor 초기화 실패:", error);
        });
    });

    // 이미지 삭제 요청을 서버에 보내는 함수
    function deleteFile(filePath) {
    	// 서버에서 설정된 deleteUrl을 JSP에서 JavaScript로 전달
        const deleteUrl = '<%= request.getAttribute("deleteFetchUrl") %>'; 
        //ckEditor.delteFileFetchUrl=/MBProject/FileUpload?action=delete&filePath=
        console.log("[INFO] 파일 삭제 요청 중:", filePath); // 파일 삭제 요청 로그
        fetch(deleteUrl + encodeURIComponent(filePath), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded' // 명시적으로 Content-Type 설정
            }
        })
        .then(response => response.json()) // 응답을 JSON으로 파싱
        .then(data => {
            if (data.deleted) {
                console.log('[INFO] 파일이 성공적으로 삭제되었습니다.'); // 파일 삭제 성공 로그
            } else {
                console.error('[ERROR] 파일 삭제 실패:', data.message); // 파일 삭제 실패 로그
            }
        })
        .catch(error => console.error('[ERROR] 파일 삭제 요청 오류:', error)); // 파일 삭제 요청 중 오류 발생 시 로그
    }

    // 이전 데이터와 현재 데이터를 비교하여 삭제된 이미지를 찾는 함수
    function findDeletedImages(previousData, currentData) {
        // 이전 데이터에서 이미지 URL을 추출
        const prevImageUrls = Array.from(previousData.matchAll(/<img[^>]+src="([^">]+)"/g)).map(match => match[1]);
        console.log("[DEBUG] 이전 이미지 URL 목록:", prevImageUrls); // 이전 이미지 URL 목록 로그

        // 현재 데이터에서 이미지 URL을 추출
        const currImageUrls = Array.from(currentData.matchAll(/<img[^>]+src="([^">]+)"/g)).map(match => match[1]);
        console.log("[DEBUG] 현재 이미지 URL 목록:", currImageUrls); // 현재 이미지 URL 목록 로그

        // 이전 데이터에 있었으나 현재 데이터에 없는 이미지 URL을 필터링하여 반환
        const deletedImages = prevImageUrls.filter(url => !currImageUrls.includes(url));
        console.log("[DEBUG] 삭제된 이미지 URL 목록:", deletedImages); // 삭제된 이미지 URL 목록 로그
        return deletedImages;
    }

    // CKEditor에서 입력된 데이터에서 이미지 경로를 추출하는 함수
    function getImagePathsFromEditorData(editorData) {
        // editorData에서 <img> 태그의 src 속성을 찾아서 배열로 반환
        return Array.from(editorData.matchAll(/<img[^>]+src="([^">]+)"/g)).map(match => match[1]);
    }

    // CKEditor에서 입력된 데이터 가져오기
    function getEditorData() {
        if (editorInstance) {
            return editorInstance.getData(); // CKEditor에서 데이터 가져오기
        }
        return ''; // CKEditor가 초기화되지 않았을 경우 빈 문자열 반환
    }

    // 게시물 수정 제출 함수
    function submitPost() {
    	const submitPostFetchUrl = '<%= request.getAttribute("submitPostBoardFetchUrl") %>';
        const formData = new FormData();
        const editorData = getEditorData();

        formData.append('boardNum', document.getElementById('boardNum').value); // 서버가 요구하는 모든 필수 파라미터를 포함
        formData.append('fixBoardTitle', document.getElementById('boardTitle').value);
        formData.append('fixBoardContent', editorData);
        formData.append('category', document.getElementById('boardCateName').value);
        
        // postSecret 체크박스가 있는지 확인하고, 없으면 기본값을 '공개'로 설정
        const postSecretCheckbox = document.getElementById('postSecret');
        if (postSecretCheckbox) {
            formData.append('secretBoardContents', postSecretCheckbox.checked ? 'on' : '');
        } else {
            formData.append('secretBoardContents', ''); // 기본값을 공개로 설정
        }
        
        const imagePaths = getImagePathsFromEditorData(editorData).join(',');
        console.log('[INFO] Image경로 보내기 직전', imagePaths);
        formData.append('imagePaths', imagePaths);

        console.log('[DEBUG] Form Data:', formData);

        return fetch(submitPostFetchUrl, {
            method: 'POST',
            body: formData
	        });
	    }
		 // 게시글 등록 버튼 클릭 시 게시글 등록 함수 호출
		    document.getElementById('postButton').addEventListener('click', (event) => {
		        event.preventDefault(); // 기본 폼 제출 동작 방지
		
		        submitPost() // submitPost가 프로미스를 반환하도록 함
		        .then(response => {
		            if (response.ok) {
		                return response.json(); // JSON 응답을 반환
		            } else {
		                alert('게시글 등록에 실패했습니다.');
		                throw new Error('게시글 등록 실패');
		            }
		        })
		        .then(data => {
		            const boardNum = data.boardNum; // 응답에서 boardNum 추출
		
		            // 사용자가 확인 버튼을 누르면 리다이렉션
		            if (confirm('게시글이 성공적으로 수정되었습니다! 확인 버튼을 누르면 작성한 게시글을 보는 페이지로 이동합니다!')) {
		                // 클라이언트 측에서 URL 리다이렉션
		                window.location.href = `viewBoard.do?boardNum=` + boardNum;
		            }
		        })
		        .catch(error => {
		            console.error('Error:', error);
		        });
		    });
</script>

</body>
</html>