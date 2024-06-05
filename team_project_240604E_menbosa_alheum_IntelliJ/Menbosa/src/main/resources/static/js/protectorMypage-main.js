//작성한 글 1 배너
let $bannerBox_1 = $("#protectorMypage-mycontents-container .protectorMypage-container-box");
let $bannerImgs_1 = $("#protectorMypage-mycontents-slidbox .mypage-box-half");
let $right_1 = $("#protectorMypage-mycontents .mypage-arrow-right");
let $left_1 = $('#protectorMypage-mycontents .mypage-arrow-left');

let bannerWidth = 332;

let currentIdx_1 = 0;
let slideCnt_1 = $bannerImgs_1.length-2;

$right_1.on('click', moveNext_1);
function moveNext_1(){
  currentIdx_1++;
  $bannerBox_1.css("transition", "0.5s ease");
  $bannerBox_1.css("left", -(currentIdx_1 * bannerWidth));
  checkEnd_1();
}

$left_1.on("click", function(){
  currentIdx_1--;
  $bannerBox_1.css("transition", "0.5s ease");
  $bannerBox_1.css("left", -(currentIdx_1 * bannerWidth));
  checkEnd_1();
});

checkEnd_1();

function checkEnd_1(){
  if(currentIdx_1 <= 0){
    $left_1.css("display", "none");
  }else{
    $left_1.css("display", "block");
  }

  if(currentIdx_1 >= slideCnt_1) {
    $right_1.css("display", "none");
  }else{
    $right_1.css("display", "block");
  }
}

// ////////////////////////////////////////////////////////////////////////////////////////

//어르신관리 2 배너
let $bannerBox_2 = $("#protectorMypage-seniorinfo .protectorMypage-container-box");
let $bannerImgs_2 = $("#protectorMypage-seniorinfo .mypage-box-half");
let $right_2 = $("#protectorMypage-seniorinfo-tab .mypage-arrow-right");
let $left_2 = $('#protectorMypage-seniorinfo-tab .mypage-arrow-left');

// let bannerWidth = 332;

let currentIdx_2 = 0;
let slideCnt_2 = $bannerImgs_2.length-2;

$right_2.on('click', moveNext_2);
function moveNext_2(){
  currentIdx_2++;
  $bannerBox_2.css("transition", "0.5s ease");
  $bannerBox_2.css("left", -(currentIdx_2 * bannerWidth));
  checkEnd_2();
}

$left_2.on("click", function(){
  currentIdx_2--;
  $bannerBox_2.css("transition", "0.5s ease");
  $bannerBox_2.css("left", -(currentIdx_2 * bannerWidth));
  checkEnd_2();
});

checkEnd_2();

function checkEnd_2(){
  if(currentIdx_2 <= 0){
    $left_2.css("display", "none");
  }else{
    $left_2.css("display", "block");
  }

  if(currentIdx_2 >= slideCnt_2) {
    $right_2.css("display", "none");
  }else{
    $right_2.css("display", "block");
  }
}

// ////////////////////////////////////////////////////////////////////////////////////////

//작성한글(1 : 메뉴창)
//수정시 페이지 이동 삭제시 confirm 후 데이터삭제
let $clickmenu_1 = $("#protectorMypage-mycontents .protectorMypage-mycontents-clickmenu");
let menus = document.querySelectorAll(":scope #protectorMypage-mycontents-slidbox .mypage-icon-menu");
let menu_1_height = 65;

menus.forEach(function(menu){
  menu.addEventListener("click", (e)=>{
    $clickmenu_1.css("visibility", "visible");
    $clickmenu_1.css("left",e.target.offsetLeft -(currentIdx_1 * bannerWidth) +"px");
    $clickmenu_1.css("top",e.target.offsetTop+menu_1_height+"px");
  });
});


  $clickmenu_1.on('mouseleave',()=>{
    console.log("발생");
    $clickmenu_1.css("visibility", "hidden");
  });


let deleteBoard = document.getElementsByClassName("deleteBoard");
let deleteBoard_value = false;
Array.from(deleteBoard).forEach(function (board) {
  board.addEventListener('click', () => {
    deleteBoard_value = confirm("정말 삭제하시겠습니까?");
    let division = board.dataset.division;
    let boardNum = board.dataset.board;
    if (deleteBoard_value) {
      switch (division) {
        case '100' :
          location.href = `/alheum/mypage/commuRemove?boardCommuNum=${boardNum}`;
          break;
        case '200' :
          location.href = `/alheum/mypage/recomRemove?boardRecomNum=${boardNum}`;
          break;
      }
    }
  });
});


// ////////////////////////////////////////////////////////////////////////////////////////

//어르신관리(2 : 메뉴창)
//수정시 페이지 이동 삭제시 confirm 후 데이터삭제
let $clickmenu_2 = $("#protectorMypage-seniorinfo-tab .protectorMypage-seniorinfo-clickmenu");
let menus_2 = document.querySelectorAll(":scope #protectorMypage-seniorinfo .mypage-icon-menu");
let menu_2_width = 50;
let menu_2_height = 65;

menus_2.forEach(function(menu){
  menu.addEventListener("click", (e)=>{
    $clickmenu_2.css("visibility", "visible");
    $clickmenu_2.css("left",e.target.offsetLeft -(currentIdx_2 * bannerWidth - bannerWidth + menu_2_width) +"px");
    $clickmenu_2.css("top",e.target.offsetTop+menu_2_height+"px");
  });
});

$clickmenu_2.on('mouseleave',()=>{
  console.log("발생2");
  $clickmenu_2.css("visibility", "hidden");
});

//연결해제 confirm
let disConnect = document.getElementsByClassName("seniorinfo-warning");
let disConnect_value = false;
Array.from(disConnect).forEach(function(menu){
  menu.addEventListener('click',()=>{
    disConnect_value = confirm("정말 연결을 해제하시겠습니까?");
    let user = menu.dataset.user;
    if(disConnect_value){
      location.href = `/alheum/mypage/deleteSenMem?senMemNum=${user}`;
    }
  });
});


///////////////////////////////////////////////////////////////
