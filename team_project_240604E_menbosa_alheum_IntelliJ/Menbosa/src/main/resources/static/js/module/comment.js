// 이 파일은 모듈을 만들어 두는 파일로 사용한다.
// JS의 함수, 클래스를 모듈화 시켜 저장하는 공간이다. (모듈화 == 부품화)
// 우리는 함수를 부품처럼 만들어 두고 사용은 다른 파일에서 할 것이다.
// 이 모듈들을 밖에서 사용할 수 있도록 내보내는 키워드가 export이다.

/**
 * 댓글 작성 모듈
 *
 * @param commentInfo {Object} 댓글 등록에 필요한 정보 boardId, content가 필요하다.
 * @param callback {Function} 정상적으로 처리가 완료되면 실행할 함수
 */

//댓글 작성하는 함수
export function register(commentInfo, callback){
    fetch(`/v1/communicate/${commentInfo.boardCommuNum}/comment`,
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({commentCommuContents: commentInfo.content}),
        }).then(resp => {
        console.log(commentInfo.content);
        if (resp.status === 200) {
            console.log(resp);
            callback();
        }
    });
}

//특정 게시물의 댓글 목록을 가져오는 함수
export function getList(boardCommuNum, callback){
    fetch(`/v1/communicate/${boardCommuNum}/comments`, {
        method : 'get'
    }).then(resp => resp.json())
        .then(dataList => { callback(dataList) });
}


//페이지네이션을 지원하는 댓글목록을 가져오는 함수
export function getList2(boardCommuNum, page, callback){
    fetch(`/v2/communicate/${boardCommuNum}/comments?page=${page}`, {
        method : 'get'
    }).then(resp => resp.json())
        .then(dataList => {
            console.log(dataList)
            callback(dataList) });
}


//댓글 삭제하는 함수
export function remove(commentCommuNum, callback){
    fetch(`/v1/comments/${commentCommuNum}`, {
        method: 'DELETE'
    }).then(resp => {
        if(resp.status === 200){
            callback();
        }
    });
}

//주어진 날짜와 현재 날짜사이의 시간을 계산하여 상대적인 시간 문자열로 반환하는 함수
export function timeForToday(value){
    // new Date() 현재 날짜와 시간
    const today = new Date();
    const timeValue = new Date(value);

    // console.log(today);
    // console.log(timeValue);

    // Math.floor()는 소수점을 내림 처리 해준다.
    // getTime()은 1970년 01/01 을 기준으로 지금까지 몇 ms가 지났는지 알려준다.
    const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);

    // console.log(betweenTime);

    if(betweenTime < 1) { return "방금 전"; }
    if(betweenTime < 60) {
        return `${betweenTime}분 전`;
    }

    const betweenTimeHour = Math.floor(betweenTime / 60);
    if(betweenTimeHour < 24){
        return `${betweenTimeHour}시간 전`;
    }

    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if(betweenTimeDay < 365){
        return `${betweenTimeDay}일 전`;
    }

    return `${Math.floor(betweenTimeDay / 365)}년 전`;
}









