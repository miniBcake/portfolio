<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">
<meta charset="utf-8">
<title>Fruitables - Vegetable Website Template</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">


<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">

<!-- CKEditor 5 스크립트 로드 -->
<script
	src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/ckeditor.js"></script>
<script
	src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>

</head>

<body>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<!-- Spinner Start -->
	<div id="spinner"
		class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<!-- Spinner End -->


 	<!-- 모든 페이지 공통으로 쓰는 상단 화면 -->
	<div class="container-fluid fixed-top">
		<div class="container topbar bg-primary d-none d-lg-block">
			<div class="d-flex justify-content-between">
				<div class="top-info ps-2">
					<small class="me-3"><i
						class="fas fa-map-marker-alt me-2 text-secondary"></i> <a href="#"
						class="text-white">123 Street, New York</a></small> <small class="me-3"><i
						class="fas fa-envelope me-2 text-secondary"></i><a href="#"
						class="text-white">Email@Example.com</a></small>
				</div>
			</div>
		</div>
		
		<div class="container px-0">
			<nav class="navbar navbar-light bg-white navbar-expand-xl">
				<a href="main.do" class="navbar-brand ms-4 ms-lg-0">
					<h1 class="fw-bold text-primary m-0">갈<span class="text-secondary">빵</span>질<span class="text-secondary">빵</span>
					</h1>
				</a>
				<button class="navbar-toggler py-2 px-3" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
					<span class="fa fa-bars text-primary"></span>
				</button>
				<div class="collapse navbar-collapse bg-white" id="navbarCollapse">
					<div class="navbar-nav mx-auto">
						<a href="main.do" class="nav-item nav-link">메인메뉴</a>
						<div class="nav-item dropdown">
							<a href="#" class="nav-link dropdown-toggle active"
								data-bs-toggle="dropdown">커뮤니티</a>
							<div class="dropdown-menu m-0 bg-secondary rounded-0">
								<a href="listBoards.do?categoryName=일반" class="dropdown-item">이웃 새글</a> <a
									href="newBoardPage.do" class="dropdown-item">게시글 작성</a> <a
									href="404.html" class="dropdown-item">404 Page</a>
							</div>
						</div>
					</div>
					<div class="d-flex m-3 me-0">
						<button
							class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4"
							data-bs-toggle="modal" data-bs-target="#searchModal">
							<i class="fas fa-search text-primary"></i>
						</button>
						<a href="logout.do"
							class="btn border border-secondary rounded-pill px-2 text-primary me-4">Logout</a>
						<a href="myPage.do" class="my-auto"> <i
							class="fas fa-user fa-2x"></i>
						</a>
					</div>
				
				</div>
			</nav>
		</div>
		
	</div>
	<!-- 모든 페이지 공통으로 쓰는 상단 화면 -->


	<!-- Modal Search Start -->
	<div class="modal fade" id="searchModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-fullscreen">
			<div class="modal-content rounded-0">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Search by
						keyword</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body d-flex align-items-center">
					<div class="input-group w-75 mx-auto d-flex">
						<input type="search" class="form-control p-3"
							placeholder="keywords" aria-describedby="search-icon-1">
						<span id="search-icon-1" class="input-group-text p-3"><i
							class="fa fa-search"></i></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal Search End -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">커뮤니티 게시글 올리기</h1>
		<ol class="breadcrumb justify-content-center mb-0">
			<li class="breadcrumb-item"><a href="listBoards.do?categoryName=일반">게시글 목록</a></li>
		</ol>
	</div>
	<!-- Single Page Header End -->


<!-- Checkout Page Start -->
<div class="container-fluid py-5">
    <div class="container py-5">
        <!-- 페이지 제목 출력: 데이터가 있을 경우 '게시글 수정', 없으면 '게시글 작성' -->
        <h1>${empty board ? '게시글 작성' : '게시글 수정'}</h1>

        <form id="postForm">
            <!-- 게시글 제목 입력 필드 -->
            <div class="form-group">
                <label for="postTitle">게시글 제목</label> 
                <input type="text" id="postTitle" name="title" placeholder="제목을 입력해주세요!" value="${board.title}" /> 
                <!-- 기존 제목이 있을 경우 입력 필드에 표시 -->
            </div>
			
			<!-- 게시글 카테고리 선택 필드 추가 -->
   			 <div class="form-group">
 			 	<label for="category">카테고리 선택</label>
       			  <select id="category" name="category">
   					 <option value="1">문의</option>
      				 <option value="2">공개</option>
     			  </select>
   			 </div>
    
            <!-- CKEditor를 사용할 게시글 내용 입력 필드 -->
            <div class="form-group">
                <label for="postContent">게시글 내용</label>
                <textarea id="postContent" name="content">${board.content}</textarea> <!-- 기존 내용이 있을 경우 입력 필드에 표시 -->
            </div>

            <!-- 비공개 게시물 체크박스 -->
            <div class="form-group">
                <input type="checkbox" id="postSecret" name="secret" ${board.secret == 'on' ? 'checked' : ''} /> 
                <!-- 기존 비공개 설정이 있으면 체크 -->
                <label for="postSecret">비공개 게시물로 올리기</label>
            </div>

            <!-- 게시글 등록 또는 수정 버튼 -->
            <button type="button" id="postButton">게시글 ${empty board ? '등록' : '수정'}</button>
        </form>
    </div>
</div>
<!-- Checkout Page End -->

<!-- JavaScript for CKEditor and form submission -->
<script>
	let editorInstance; // CKEditor 인스턴스를 저장할 변수
	
	//페이지 로드 시 CKEditor 인스턴스 초기화
	document.addEventListener('DOMContentLoaded', () => {
	 ClassicEditor
	     .create(document.querySelector('#postContent'), { // 'postContent' 요소에 CKEditor 생성
	         language: 'ko', // 에디터 언어를 한국어로 설정
	         ckfinder: {
	             uploadUrl: '/MMBProject/FileUpload?action=upload', // 이미지 업로드를 처리할 서버 URL
	             options: {
	                 resourceType: 'Images', // 리소스 타입을 이미지로 설정
	                 deleteUrl: '/MMBProject/FileUpload?action=delete' // 이미지 삭제 요청 URL
	             }
	         },
	         height: 500 // CKEditor의 높이를 500px로 설정
	     })
	     .then(editor => {
	         editorInstance = editor; // CKEditor 인스턴스를 변수에 저장
	
	         // 데이터 변경 이벤트 리스너 추가
	         editor.model.document.on('change:data', () => {
	             const currentData = editor.getData(); // 현재 에디터의 데이터를 가져옴
	
	             console.log("[DEBUG] 현재 에디터 데이터:", currentData); // 현재 에디터 데이터 로그
	
	             // 이전 데이터와 비교하여 삭제된 이미지 찾기
	             if (previousData) {
	                 const deletedImages = findDeletedImages(previousData, currentData); // 삭제된 이미지 찾기
	                 deletedImages.forEach(imageUrl => {
	                     console.log("[INFO] 삭제 요청할 이미지 URL:", imageUrl); // 삭제할 이미지 URL 로그
	                     deleteFile(imageUrl);  // 이미지 삭제 함수 호출
	                 });
	             }
	
	             previousData = currentData;  // 현재 데이터를 이전 데이터로 업데이트
	             console.log("[DEBUG] 이전 에디터 데이터 업데이트됨"); // 이전 데이터 업데이트 로그
	         });
	     })
	     .catch(error => {
	         console.error("[ERROR] CKEditor 초기화 실패:", error); // CKEditor 초기화 실패 시 오류 로그
	     });
	});
	
	let previousData = ''; // 이전 에디터 데이터를 저장할 변수
	
	//이미지 삭제 요청을 서버에 보내는 함수
	function deleteFile(filePath) {
	 console.log("[INFO] 파일 삭제 요청 중:", filePath); // 파일 삭제 요청 로그
	 fetch('/MMBProject/FileUpload?action=delete&filePath=' + encodeURIComponent(filePath), { // 파일 삭제 요청을 서버로 보냄
	     method: 'POST' // HTTP POST 메서드 사용
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
	
	//이전 데이터와 현재 데이터를 비교하여 삭제된 이미지를 찾는 함수
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

 // 게시글 등록 함수
    function createPost() {
    const formData = new URLSearchParams();
    const editorData = getEditorData();  // CKEditor에서 입력된 데이터 가져오기

    formData.append('newBoardTitle', document.getElementById('postTitle').value);
    formData.append('newBoardContent', editorData);
    formData.append('secretBoardContents', document.getElementById('postSecret').checked ? 'on' : '');
    formData.append('category', document.getElementById('category').value); // 만약 카테고리를 선택하는 요소가 있다면

    // editorData에서 이미지 경로를 추출하여 imagePaths로 설정
    const imagePaths = getImagePathsFromEditorData(editorData).join(',');
    formData.append('imagePaths', imagePaths);

    // 클라이언트 로그 추가
    console.log('[DEBUG] Form Data:', formData.toString());
    
    // fetch 호출을 반환하도록 수정
    return fetch('/MMBProject/insertBoard.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData.toString() // URLSearchParams 객체를 문자열로 변환하여 전송
    });
}

function updatePost() {
    // fetch 호출을 반환하도록 수정
    return fetch('/MMBProject/updateBoard.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // JSON 형식으로 데이터 전송
        },
        body: JSON.stringify({
            title: document.getElementById('postTitle').value,
            content: getEditorData(),
            secret: document.getElementById('postSecret').checked ? 'on' : '',
            boardId: '${board.boardNum}' // 수정 시 필요한 게시글 ID
        })
    });
}


    // 게시글 삭제 함수
    function deletePost() {
        return fetch('/deleteBoard.do', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // JSON 형식으로 데이터 전송
            },
            body: JSON.stringify({
                boardId: '${board.boardNum}' // 삭제 시 필요한 게시글 ID
            })
        });
    }

    // 게시글 제출 함수
	function submitPost() {
    const title = document.getElementById('postTitle').value;
    const content = getEditorData();

    // 제목 또는 내용이 비어 있는지 확인
    if (title.trim() === '' || content.trim() === '') {
        alert('제목과 내용을 모두 입력해 주세요.');
        return;
    }

    // 게시글 등록 또는 수정 확인
    const action = '${empty board ? "create" : "update"}';
    const actionFunction = action === 'create' ? createPost : updatePost;
    const actionText = action === 'create' ? '등록' : '수정';

    if (confirm(`게시글을 ${actionText}하시겠습니까?`)) {
        actionFunction()
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 올바르지 않습니다.');
                }
                // 응답이 JSON 형식인지 확인하고 파싱
                return response.text().then(text => {
                    try {
                        return JSON.parse(text);
                    } catch (error) {
                        throw new Error('JSON 형식이 아닙니다.');
                    }
                });
            })
            .then(data => {
                if (data.success) {
                    alert(`게시글이 ${actionText}되었습니다.`);
                    window.location.href = 'listBoards.do?categoryName=일반'; // 성공 시 목록 페이지로 이동
                } else {
                    alert(`게시글 ${actionText}에 실패했습니다: ${data.message}`);
                }
            })
            .catch(error => {
                alert(`게시글 ${actionText}에 실패했습니다.`);
                console.error(error);
            });
    } else {
        alert(`게시글 ${actionText}이 취소되었습니다.`);
    }
}



    // 게시글 등록 또는 수정 버튼 클릭 시 submitPost 함수 호출
    document.getElementById('postButton').addEventListener('click', submitPost);
</script>

	<!-- Back to Top -->
	<a href="#"
		class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
		class="fa fa-arrow-up"></i></a>


	<!-- JavaScript Libraries -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/lightbox/js/lightbox.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="js/main.js"></script>
</body>

</html>