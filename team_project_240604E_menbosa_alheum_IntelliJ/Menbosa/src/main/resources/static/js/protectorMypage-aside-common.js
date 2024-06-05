//탈퇴하기
let $asidesecession = $("#mypage-asidesecession");
let asidesecession_value = false;
$asidesecession.on('click',()=>{
  asidesecession_value = confirm("탈퇴하시겠습니까?")
  if(asidesecession_value){
    location.href = `/alheum/mypage/leave`;
  }
});

//로그아웃
let $asidelogout = $("#mypage-asidelogout");
let asidelogout_value = false;
$asidelogout.on('click',()=>{
  asidelogout_value = confirm("로그아웃하시겠습니까?")
  if(asidelogout_value){
    location.href = `/alheum/mypage/logout`;
  }
});