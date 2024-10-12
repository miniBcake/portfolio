<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fail Info Page</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
</head>
<body>

<%
    // 세션에서 'msg'와 'path' 값을 가져옴
    String msg = (String) session.getAttribute("msg");
    String path = (String) session.getAttribute("path");

    // 세션 값을 로그로 출력하고 세션에서 제거
    System.out.println("로그 - 메세지: " + msg);
    System.out.println("로그 - 경로: " + path);
    session.removeAttribute("msg");
    session.removeAttribute("path");
%>

<script>
    // SweetAlert를 사용하여 메시지를 출력하고 확인 버튼 클릭 시 경로로 리디렉션
    swal({
        title: "알림",
        text: "<%= msg %>",
        type: "info",
        showConfirmButton: true,
        confirmButtonText: "확인"
    }, function() {
        window.location.href = "<%= path %>";
    });
</script>

</body>
</html>
