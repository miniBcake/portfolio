<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">
<meta charset="utf-8">
<title>게시물 수정 페이지</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">

<!-- CKEditor 5 스크립트 로드 -->
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>

</head>

<body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Spinner 시작 -->
<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
    <div class="spinner-grow text-primary" role="status"></div>
</div>
<!-- Spinner 끝 -->


<!-- 모든 페이지 공통으로 쓰는 상단 화면 -->
<div class="container-fluid fixed-top">
    <div class="container topbar bg-primary d-none d-lg-block">
        <div class="d-flex justify-content-between">
            <div class="top-info ps-2">
                <small class="me-3"><i class="fas fa-map-marker-alt me-2 text-secondary"></i> <a href="#" class="text-white">123 Street, New York</a></small>
                <small class="me-3"><i class="fas fa-envelope me-2 text-secondary"></i><a href="#" class="text-white">Email@Example.com</a></small>
            </div>
        </div>
    </div>

    <div class="container px-0">
        <nav class="navbar navbar-light bg-white navbar-expand-xl">
            <a href="main.do" class="navbar-brand ms-4 ms-lg-0">
                <h1 class="fw-bold text-primary m-0">갈<span class="text-secondary">빵</span>질<span class="text-secondary">빵</span></h1>
            </a>
            <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                <span class="fa fa-bars text-primary"></span>
            </button>
            <div class="collapse navbar-collapse bg-white" id="navbarCollapse">
                <div class="navbar-nav mx-auto">
                    <a href="main.do" class="nav-item nav-link">메인메뉴</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown">커뮤니티</a>
                        <div class="dropdown-menu m-0 bg-secondary rounded-0">
                            <a href="listBoards.do" class="dropdown-item">이웃 새글</a>
                            <a href="newBoardPage.do" class="dropdown-item">게시글 작성</a>
                            <a href="404.html" class="dropdown-item">404 Page</a>
                        </div>
                    </div>
                </div>
                <div class="d-flex m-3 me-0">
                    <button class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search text-primary"></i>
                    </button>
                    <!-- 로그인 여부에 따라 버튼 표시 -->
                    <c:choose>
                        <c:when test="${not empty sessionScope.member.memberPK}">
                            <!-- 로그인 상태 -->
                            <a href="logout.do" class="btn border border-secondary rounded-pill px-2 text-primary me-4">Logout</a>
                        </c:when>
                        <c:otherwise>
                            <!-- 로그아웃 상태 -->
                            <a href="login.do" class="btn border border-secondary rounded-pill px-2 text-primary me-4">Login</a>
                        </c:otherwise>
                    </c:choose>
                    <a href="myPage.do" class="my-auto"> <i class="fas fa-user fa-2x"></i></a>
                </div>
            </div>
        </nav>
    </div>
</div>
<!-- 모든 페이지 공통으로 쓰는 상단 화면 -->

<!-- Modal Search Start -->
<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
        <div class="modal-content rounded-0">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex align-items-center">
                <div class="input-group w-75 mx-auto d-flex">
                    <input type="search" class="form-control p-3" placeholder="keywords" aria-describedby="search-icon-1">
                    <span id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></span>
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
        <li class="breadcrumb-item"><a href="listBoards.do">게시글 목록</a></li>
    </ol>
</div>
<!-- Single Page Header End -->

<!-- Checkout Page Start -->
<div class="container-fluid py-5">
<div class="container mt-5">
    <!-- 페이지 제목 -->
    <h1>게시물 수정</h1>

    <!-- 게시글 수정 폼 -->
    <form id="postForm">
        <!-- 게시글 제목 입력 필드 -->
        <div class="form-group">
            <label for="postTitle">제목</label>
            <input type="text" id="postTitle" name="title" class="form-control" placeholder="제목을 입력해주세요!" value="${boardDTO.title}" />
        </div>

        <!-- CKEditor를 사용할 게시글 내용 입력 필드 -->
        <div class="form-group">
            <label for="postContent">내용</label>
            <textarea id="postContent" name="content">${boardDTO.content}</textarea>
        </div>

        <!-- 비공개 게시물 체크박스 -->
        <div class="form-group form-check">
            <input type="checkbox" id="postSecret" name="secret" class="form-check-input" ${boardDTO.visibility == 'private' ? 'checked' : ''} />
            <label for="postSecret" class="form-check-label">비공개 게시물로 올리기</label>
        </div>

        <!-- 게시글 수정 버튼 -->
        <button type="button" id="postButton" class="btn btn-primary">게시글 수정</button>
    </form>

    <!-- 파일 업로드 폼 -->
    <form id="uploadForm" enctype="multipart/form-data" class="mt-4">
        <div class="form-group">
            <label for="fileInput">파일 업로드</label>
            <input type="file" name="file" id="fileInput" class="form-control-file" />
            <button type="button" onclick="uploadFile()" class="btn btn-secondary mt-2">파일 업로드</button>
        </div>
    </form>

    <!-- 파일 삭제 폼 -->
    <form id="fileDeleteForm" class="mt-4">
        <div class="form-group">
            <label for="fileSelect">삭제할 파일 선택:</label>
            <select id="fileSelect" class="form-control">
                <!-- 파일 목록이 여기에 동적으로 삽입됩니다 -->
            </select>
            <button type="button" onclick="deleteFile()" class="btn btn-danger mt-2">파일 삭제</button>
        </div>
    </form>
</div>

<!-- JavaScript 로직 -->
<script>
    let editorInstance; // CKEditor 인스턴스를 저장할 변수

    // 페이지 로드 시 CKEditor 인스턴스 초기화
    document.addEventListener('DOMContentLoaded', () => {
        ClassicEditor
            .create(document.querySelector('#postContent'), {
                language: 'ko',
                height: '2000px', // CKEditor 높이 설정
                ckfinder: {
                    uploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files' // CKEditor 이미지 업로드 경로
                }
            })
            .then(editor => {
                editorInstance = editor;
            })
            .catch(error => {
                console.error('CKEditor 초기화 실패:', error);
            });

        // 게시글 수정 버튼 클릭 시 폼 데이터 전송
        document.getElementById('postButton').addEventListener('click', () => {
            // FormData 객체를 생성하여 폼 데이터를 수집
            const formData = new FormData(document.getElementById('postForm'));
            const editorData = getEditorData();
            // CKEditor에서 입력된 내용을 추가
            formData.append('content', editorInstance.getData());

            // 폼 데이터를 서버로 전송
            fetch('/MMBProject/updateBoard.do', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text()) // 서버의 응답을 텍스트로 변환
            .then(data => {
                console.log('게시글 수정 완료:', data); // 응답 데이터 로그 출력
                window.location.href = 'listBoards.do'; // 게시글 수정 후 목록 페이지로 이동
            })
            .catch(error => {
                console.error('게시글 수정 중 오류 발생:', error); // 오류 발생 시 메시지 출력
            });
        });

        // 파일 업로드 함수
        window.uploadFile = () => {
            // FormData 객체를 생성하여 파일 데이터를 수집
            const formData = new FormData(document.getElementById('uploadForm'));
            formData.append('action', 'upload'); // 업로드 작업을 지정

            // 폼 데이터를 서버로 전송
            fetch('/fileUpload.do', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text()) // 서버의 응답을 텍스트로 변환
            .then(data => {
                console.log('파일 업로드 완료:', data); // 응답 데이터 로그 출력
                loadFileList(); // 파일 업로드 후 파일 목록 갱신
            })
            .catch(error => {
                console.error('파일 업로드 중 오류 발생:', error); // 오류 발생 시 메시지 출력
            });
        }

        
        // CKEditor에서 입력된 데이터 가져오기
        function getEditorData() {
            if (editorInstance) {
                return editorInstance.getData(); // CKEditor에서 데이터 가져오기
            }
            return ''; // CKEditor가 초기화되지 않았을 경우 빈 문자열 반환
        }

        // 게시글 등록/수정 버튼 클릭 시 폼 데이터 전송
        document.getElementById('postButton').addEventListener('click', () => {
            const formData = new FormData(document.getElementById('postForm'));
            formData.append('newBoardContent', editorInstance.getData());

            fetch('/insertBoardAction.do', {  // 컨트롤러 URL
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(data => {
                console.log('게시글 저장 완료:', data);
                window.location.href = 'boardList.do'; // 게시글 저장 후 목록 페이지로 이동
            })
            .catch(error => {
                console.error('게시글 저장 중 오류 발생:', error);
            });
        });
    });
</script>

<!-- Back to Top -->
<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i class="fa fa-arrow-up"></i></a>

<!-- JavaScript Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/waypoints/waypoints.min.js"></script>
<script src="lib/lightbox/js/lightbox.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
</body>
</html>
