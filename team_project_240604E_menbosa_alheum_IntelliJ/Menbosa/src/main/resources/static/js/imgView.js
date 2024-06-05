// 모듈 가져오기
import * as reply from "../module/reply.js";

let page = 1;
let hasNext = true;

{   // 버튼 처리
    let $modifyBtn = document.querySelector('.btn-modify');
    let $removeBtn = document.querySelector('.btn-remove');
    let $backBtn = document.querySelector('.btn-back');

    //삭제버튼 처림
    $removeBtn?.addEventListener("click", function () {
        let boardId = this.dataset.id; //클릭된 요소의 data-id 속성값을 가져와 boardId 변수에 저장
        //this : 이벤트 핸들러 안에서 이벤트가 발생한 요소(클릭한 요소)
        //dataset : dataset 객체는 요소의 모든 data-* 속성을 포함
        //  ex) dataset.id ="123"
        //id : data-id 속성의 값을 가져온다
        location.href = `/board/remove?boardId=${boardId}`;
    });

    //수정 버튼 처리
    $modifyBtn?.addEventListener("click", function () {
        let boardId = this.dataset.id; //클릭된 요소의 data-id 속성값을 가져와 변수에 저장
        console.log(boardId)
        location.href = `/board/modify?boardId=${boardId}`;
    });

    //뒤로가기 버튼
    $backBtn?.addEventListener("click", function () {
        window.history.back() //브라우저의 이전 페이지로 이동
    });
}
//===============================================================================


let boardId = document.querySelector('#boardId').value;

displayImgAjax(); //이미지 표시 함수 호출

function displayImgAjax() {
    fetch(`/v1/posts/${boardRecomNum}/files`, {method: 'GET'})
        //서버에 GET요청을 보내 파일 목록을 가져옴
        .then(res => res.json())//응답을 JSON으로 변환
        .then(list => { //변환된 데이터를 list 변수에 저장
            let tags = ''; //HTML 태그를 저장할 변수 초기화

            for (let i = 0; i < list.length; i++) {
                let fileName = list[i].imgFileExt + '/' + list[i].imgFileServer + '_' + list[i].imgFileUser;
                //파일 경로 조합

                tags += `<a href="/download?fileName=${fileName}">
                         <img src="/v1/imgFiles?fileName=${fileName}" data-id="${list[i].fileId}" data-name="${fileName}"/>
                        </a>`;
            }

            let $postImgs = document.querySelector('.post-images'); //이미지가 삽입될 요소

            $postImgs.innerHTML = tags; //생성된 html 태그를 삽입
        });
}

// ------------------------댓글 메뉴 처리-------------------------------------------
{
    let $replyListWrap = document.querySelector('.reply-list-wrap');
    //댓글 목록 래퍼 요소를 선택

    $replyListWrap.addEventListener('click', function (e){
        // console.log(e.target);
        let $target = e.target;
        //클릭된 요소 저장


        if($target.classList.contains('reply-btns')){ //클릭된 요소가 reply-btns 클래스를 가지고 있는지 확인
            // closest('선택자') : 나의 상위 요소중에 찾기
            // querySelector('선택자') : 나의 하위 요소중에 찾기
            let $menu = $target.closest('.reply-btn-box').querySelector('.reply-btns__box');
            //상위 요소에서  reply-btns__box를 찾음

            $menu.classList.toggle('none');
            //none 클래스를 토글
            //none 클래스가 해당 요소에 있으면 제거하고, 없으면 추가한다(요소의 표시 여부 전환)

        }else if($target.classList.contains('reply-modify-btn')){
            //클릭한 요소가 reply-modify-btn 클래스를 가지고 있는지 확인
            $target.closest('.reply-btns__box').classList.add('none')
            //상위요소에서 reply-btns__box 추가하여 숨김

            let $contentBox = $target.closest('.reply').querySelector('.reply-box__content');
            //상위 요소에서 reply-box__content 를 찾음
            let oldContent = $contentBox.innerText;
            //기존 댓글 내용을 저장

            $contentBox.innerHTML = `
                <div class="modify-box">
                    <textarea class="modify-content">${oldContent}</textarea>
                    <button type="button" class="modify-content-btn">수정 완료</button>
                </div>
            `;
            //수정 입력창과 버튼을 표시
        }else if($target.classList.contains('reply-remove-btn')){
            //클릭된 요소가 reply-remove-btn클래스를 가지고 있는지 확인
            $target.closest('.reply-btns__box').classList.add('none');
            //상위요소에 reply-btns__box클래스를 추가하여 숨김
            let replyId = $target.closest('.reply').dataset.id;
            //클릭된 댓글의 ID를 저장
            reply.remove(replyId, () => {
                //댓글 삭제 함수 호출
                page = 1; //페이지를 초기화
                reply.getList2(boardId, page, function (data) {
                    //댓글 목록을 다시 가져옴
                    hasNext = data.hasNext;
                    //다음 페이지 여부를 갱신
                    displayReply(data.contentList);
                    //댓글 목록을 화면에 표시
                });
            });


        }else if($target.classList.contains('modify-content-btn')) {

            let replyId = $target.closest('.reply').dataset.id;
            //클릭된 요소의 상위 reply요소에서 data-id 속성값을 가져옴
            let content = $target.closest('.modify-box').querySelector('.modify-content').value;
            //수정된 댓글 내용을 가져옴

            let updateInfo = {replyId : replyId, content : content};
            //수정된 댓글 정보를 객체로 생성

            reply.modify(updateInfo, () => { //댓글 수정함수 호출
                page = 1;   //페이지 초기화
                reply.getList2(boardId, page, function (data) { //댓글목록을 가져옴
                    hasNext = data.hasNext; //다음 페이지가 있는지 여부를 업데이트
                    displayReply(data.contentList); //댓글 목록 화면에 표시
                });
            });

        }else{  //위 조건에 모두 해당하지 않으면
            document.querySelectorAll('.reply-btns__box') //모든 reply-btns__box 요소를 선택
                .forEach(ele => ele.classList.add('none')); //모두 숨김
        }

    });
}


// ------------------------------댓글 처리-----------------------------
{
    let $btnReply = document.querySelector('.btn-reply');
    //댓글 작성버튼 선택

    $btnReply?.addEventListener('click', function () {
        let content = document.querySelector('#reply-content').value;
        //댓글 입력창의 내용을 가져옴

        if(!content) { //내용이 비어있는지 확인
            alert("댓글 내용은 필수 사항입니다.") //경고메시지 표시
            return;
        }

        let replyInfo = {
            boardId : boardId,
            content : content
        };

        reply.register(replyInfo, () => { //댓글 등록 함수 호출
            document.querySelector('#reply-content').value = ''; //댓글 입력창 초기화
            page = 1; //페이지 초기화
            reply.getList2(boardId, page, function (data) {
                hasNext = data.hasNext;
                displayReply(data.contentList);
            });
        });
    });


    // reply.getList(boardId, displayReply);
    reply.getList2(boardId, page, function (data){ //페이지 로드시 댓글 목록을 가져옴
        hasNext = data.hasNext;
        displayReply(data.contentList);
    });

    window.addEventListener('scroll', function (){
        // console.dir(document.documentElement)
        if(!hasNext) return;
        // documentElement 객체에서 3개의 프로퍼티를 동시에 가져온다.
        let {scrollTop, scrollHeight, clientHeight} = document.documentElement;

        // console.log("scrollTop(스크롤 상단의 현재 위치) : ", scrollTop);
        // console.log("scrollHeight(전체 문서의 높이) : ", scrollHeight);
        // console.log("clientHeight(클라이언트[웹브라우저]의 화면 높이) : ", clientHeight);

        if (clientHeight + scrollTop >= scrollHeight - 5) { //스크롤이 페이지 끝에 도달했는지 확인
            console.log("바닥!!!!!")

            page++; //페이지 번호 증가

            reply.getList2(boardId, page, function (data){ //다음 페이지의 댓글 목록을 가져옴
                hasNext = data.hasNext;
                appendReply(data.contentList); //댓글 목록을 화면에 추가
            });
        }
    });
}


function displayReply(replyList){
    let $replyWrap = document.querySelector('.reply-list-wrap'); //댓글 목록을 감싸는 요소

    let tags = ''; //HTML 태그를 저장할 변수 초기화

    replyList.forEach(r => { //댓글 목록을 순회하며 각 댓글을 html로 생성
        // console.log(reply)

        tags += `
            <div class="reply" data-id="${r.replyId}">
                <div class="reply-box">
                    <div class="reply-box__writer">${r.loginId}</div>
                    <div class="reply-box__content">${r.content}</div>
                </div>
                
                <div class="reply-time">
                    ${reply.timeForToday(r.modifiedDate)}
                </div>
                
                <div class="reply-btn-box">
                    <span class="reply-btns"></span>
                    <div class="reply-btns__box none">
                        <div class="reply-remove-btn">삭제</div>
                        <div class="reply-modify-btn">수정</div>
                    </div>
                </div>
            </div>
        `;
    });

    $replyWrap.innerHTML = tags; //생성된 HTML을 삽입하여 댓글 목록을 화면에 표시
}


function appendReply(replyList){
    let $replyWrap = document.querySelector('.reply-list-wrap');

    let tags = '';

    replyList.forEach(r => {
        // console.log(reply)

        tags += `
            <div class="reply" data-id="${r.replyId}">
                <div class="reply-box">
                    <div class="reply-box__writer">${r.loginId}</div>
                    <div class="reply-box__content">${r.content}</div>
                </div>
                
                <div class="reply-time">
                    ${reply.timeForToday(r.modifiedDate)}
                </div>
                
                <div class="reply-btn-box">
                    <span class="reply-btns"></span>
                    <div class="reply-btns__box none">
                        <div class="reply-remove-btn">삭제</div>
                        <div class="reply-modify-btn">수정</div>
                    </div>
                </div>
            </div>
        `;
    });

    // innerHTML은 기존의 자식요소드를 전부 덮어 씌우기때문에 새로운 값을 누적하지 않는다.
    // $replyWrap.innerHTML = tags;

    // 새로운 요소를 누적하기 위해서는 insertAdjacentHTML() 메서드를 이용해야한다.
    // insertAdjacentHTML(position, html)
    // position의 종류는 4가지가 있다.
    // 1. beforebegin : element 요소의 바로 앞에 삽입
    // 2. afterbegin : element 요소의 첫번째 자식 요소로 삽입
    // 3. beforeend : element 요소의 마지막 자식요소로 삽입
    // 4. afterend : element 요소의 바로 뒤에 삽입

    //      [beforebegin]
    // <div class="reply-wrap">
    //      [afterbegin]
    //  <div class="child"></div>
    //  <div class="child"></div>
    //      [beforeend]
    // </div>
    //      [afterend]
    $replyWrap.insertAdjacentHTML("beforeend", tags);
}















