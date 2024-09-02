<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>alert 스크립트</title>
</head>
<body>
<%
out.print(session.getAttribute("loginResult"));
// checkResult가 null이 아닌 경우
if(session.getAttribute("checkResult")!=null){
   //session에서 비밀번호 확인 결과값 받아오기
boolean checkResult = (boolean)session.getAttribute("checkResult");
if(checkResult){
   String path = (String)request.getAttribute("path");
   out.println("<script>alert('비밀번호 확인 성공!');location.href='"+path+"';</script>");
}
else if (!checkResult){
   out.println("<script>alert('비밀번호 확인 실패. 다시 입력해주세요!'); history.go(-1);</script>");
}
}

// 로그인 결과 받아오기 (null이 아닌 경우)
if(session.getAttribute("loginResult")!=null){   
boolean loginResult = (boolean)session.getAttribute("loginResult");
//로그인 성공 
   if(loginResult){
      out.println("<script>alert('로그인 성공!'); location.href='mainPage.do';</script>");      
   }else if(!loginResult){
      out.println("<script>alert('로그인 실패 : 비밀번호 혹은 아이디를 확인해주세요!');history.go(-1);</script>");
   }
}

//회원가입 결과가 null이 아닌 경우
if(session.getAttribute("joinResult")!=null){   
boolean joinResult = (boolean)session.getAttribute("joinResult");
//회원가입 성공 
   if(joinResult){
      out.println("<script>alert('회원가입 성공!'); location.href='mainPage.do';</script>");   
      }else if(!joinResult){
      out.println("<script>alert('회원가입 실패 : 입력한 내용을 확인해주세요!');history.go(-1);</script>");
      }
}


%>

</body>
</html>