
//댓글작성
export function registerComment(commentInfo, callback){
    fetch(`/protector/communicate/${commentInfo.boardCommuNum}/comment`,
        {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({content: commentInfo.content}),
        }).then(resp => {
        if(resp.status === 200){
            callback();
        }
    });
}

//특정 게시글 댓글 목록
export function getCommentList(commentCommuNum, callback){
    fetch(`/protector/communicate/${boardCommuNum}/comments`,{
        method: 'GET'
    }).then(resp => resp.json())
        .then(dataList => {callback(dataList)});
}

//페이지네이션 댓글 목록
export function getCommentList2(commentCommuNum, page, callback){
    fetch(`/protector/communicate/${boardCommuNum}/commentsPage?page=${page}`,{
        method: 'GET'
    }).then(resp => resp.json())
        .then(dataList => {callback(dataList)});
}

//댓글삭제
export function removeComment(commentCommuNum, callback){
    fetch(`/protector/communicate/comments/${commentCommuNum}`,{
        method: 'DELETE'
    }).then(resp => {
        if(resp.status === 200){
            callback();
        }
    });
}