import * as commuModule from "./module/recomComment.js";
import {getCommentList2, registerComment} from "./module/recomComment.js";
let page = 1;
let hasNext = true;


const titleTabMenu = document.querySelector(".menuButton");
const titleSubTabMenu = document.querySelector(".menuButton-modifyDelete");
const commentMenu = document.querySelectorAll(".comments-menuButton");
const commentSubMenu = document.querySelectorAll(".comments-list-submenu");
const replyButton = document.querySelectorAll(".reply");
const replyInputBox = document.querySelectorAll(".reply-inputBox");


titleTabMenu.addEventListener("click", function() {
  titleSubTabMenu.classList.toggle("active");
})


for(let i = 0; i < commentMenu.length; i ++)  {
  commentMenu[i].addEventListener("click", function() {
    commentSubMenu[i].classList.toggle("active");
  })
}



replyButton.forEach((e,i) => {
  e.addEventListener("click", () => {
    replyInputBox[i].classList.toggle("reply-active");

    replyButton.forEach((e,j) => {
      if(j !== i) {
        replyInputBox[j].classList.remove("reply-active");
      }
    })
  })
})


// const detailDelete = document.querySelector(".menuButton-modifyDelete > li:nth-of-type(2)");
// detailDelete.addEventListener("click", function(){
//   confirm("삭제하시겠습니까?")
// })


let boardRecomNum = document.querySelector('#boardRecomNum').value;
// ========================================================================

{

  let $btnComment = document.querySelector('.btn-comment');

  $btnComment?.addEventListener("click", function () {
    let content = document.querySelector('#recomComment-content').value;

    if(!content){
      alert("댓글내용을 입력해주세요.");
      return;
    }

    console.log(content);

    let commentInfo = {
      boardRecomNum : boardRecomNum,
      content : content
    };

    console.log("content:" + commentInfo.content );
    console.log(commentInfo.boardRecomNum);

    commuModule.registerComment(commentInfo, ()=> {
      document.querySelector('#recomComment-content').value = '';
      page = 1;
      commuModule.getCommentList2(boardRecomNum, page, function (data) {
        hasNext = data.hasNext;
        console.log("hasNext:"+ hasNext);
        console.log(data.contentList);

        displayComment(data.contentList);
      });

    });
  });




  commuModule.getCommentList2(boardRecomNum, page, function (data) {
    // data.contentList = undefined;
    hasNext = data.hasNext;
    console.log("hasNext222:"+ hasNext);
    console.log(data);
    displayComment(data.contentList);
  });



  window.addEventListener('scroll', function (){

    if(!hasNext) return;

    let {scrollTop, scrollHeight, clientHeight} = document.documentElement;

    if (clientHeight + scrollTop >= scrollHeight - 5) { //스크롤이 페이지 끝에 도달했는지 확인
      console.log("바닥!!!!!")

      page++; //페이지 번호 증가

      commuModule.getCommentList2(boardRecomNum, page, function (data){ //다음 페이지의 댓글 목록을 가져옴
        hasNext = data.hasNext;
        console.log("hasNext33:"+ hasNext);

        appendComment(data.contentList); //댓글 목록을 화면에 추가
      });
    }
  });

}

let proMemNum = document.querySelector('#proMemNum').value;
// console.log("proMemNum:", proMemNum);


function displayComment(commentList){
  let $commentWrap = document.querySelector('.comments-list'); //댓글 목록을 감싸는 요소

  let tags = ''; //HTML 태그를 저장할 변수 초기화

  console.log(commentList);
  commentList.forEach(e => { //댓글 목록을 순회하며 각 댓글을 html로 생성
    tags += `
              <li>
              <div class="comments-list-own" data-id = "${e.commentRecomNum} ">
                <p><img src="/img/community-profile.png" alt=""></p>
                <div>
                  <div class="comments-list-writer">
                    <span>${e.proMemName}</span>
                     ${e.proMemNum == proMemNum ? '<div class="comments-menuButton">삭제</div>' : ''}
                  </div>
                  <p class="comments-list-contents">
                    <span>${e.commentRecomContents}</span>
                    <span>${commuModule.timeForToday(e.commentRecomDate)}</span>
                  </p>
                </div>
              </div>
            </li>`;

  });

  $commentWrap.innerHTML = tags; //생성된 HTML을 삽입하여 댓글 목록을 화면에 표시
}


function appendComment(commentList) {
  let $commentWrap = document.querySelector('.comments-list');

  let tags = '';
  let session = window.sessionStorage.proMemNum;

  commentList.forEach(e => {
    // console.log(reply)

    tags += `
              <li>
              <div class="comments-list-own" data-id = "${e.commentRecomNum} ">
                <p><img src="/img/community-profile.png" alt=""></p>
                <div>
                  <div class="comments-list-writer">
                    <span>${e.proMemName}</span>
                    ${e.proMemNum == proMemNum ? '<div class="comments-menuButton">삭제</div>' : ''}
                  </div>
                  <p class="comments-list-contents">
                    <span>${e.commentRecomContents}</span>
                    <span>${commuModule.timeForToday(e.commentRecomDate)}</span>
                  </p>
                </div>
              </div>
            </li>`;

  });

  $commentWrap.insertAdjacentHTML("beforeend", tags);

}

let $commentWrap = document.querySelector('.comments-list');

$commentWrap.addEventListener('click', function (e) {
  let $target = e.target;
  if ($target.classList.contains('comments-menuButton')) {
    /* 삭제 버튼이 클릭되었는지 확인하고, 해당 요소를 처리 */
    // $target.closest('.delete-btn').classList.add('none');
    let commentRecomNum = $target.closest('.comments-list-own').dataset.id; // 댓글 ID 가져오기


    if(confirm("삭제하시겠습니까?")){
      commuModule.remove(commentRecomNum, () => {
        // 댓글 삭제 함수 호출
        page = 1; // 페이지를 초기화
        commuModule.getCommentList2(boardRecomNum, page, function (data) {
          // 댓글 목록을 다시 가져옴
          hasNext = data.hasNext;
          // 다음 페이지 여부를 갱신
          displayComment(data.contentList); // displayComment 함수를 사용하여 댓글 목록을 화면에 표시
        });
      });
    }


  }
});