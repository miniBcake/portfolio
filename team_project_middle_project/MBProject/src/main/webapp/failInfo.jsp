<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>fail info page</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />

</head>
<body>

<script>
// sweetalert 알림 메세지와 리디렉션 처리
swal({
    title: "False",
    text: '${msg}',
    type: "info",
    showConfirmButton: true,
    confirmButtonText: "확인"
}, function() {
    // 확인 버튼을 누르면 지정된 경로로 리디렉션
    window.location.href = '${path}';
});
</script>


</body>
</html>