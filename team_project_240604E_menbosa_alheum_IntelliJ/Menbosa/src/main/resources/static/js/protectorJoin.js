//전체동의
NodeList.prototype.map = Array.prototype.map;

const all = document.querySelector("input#checkAll");
const terms = document.querySelectorAll("input.protectorJoin-check");
console.log(terms);

//전체 동의 체크박스를 클릭할 때 마다 실행되는 이벤트 리스너
all.addEventListener("click", () => {
    terms.forEach((term) => {
        term.checked = all.checked;
    });
});

//약관동의 체크박스를 클릭할 때마다 실행되는 이벤트 리스너
terms.forEach((term) => {
    //약관동의 체크박스를 나타내는 NodeList에 대해 forEach 메소드를 호출
    term.addEventListener("click", (e) => {
        //현재 반복중인 약관동의 체크박스에 대해 클릭 이벤트 리스너를 추가함
        all.checked = terms.map((term) => term.checked).filter((checked) => checked).length === 3;
        console.log(all);
        console.log(term);
        console.log(terms);
        // .length === 6;
        //전체동의 체크박스 상태를 변경
        //terms.map((term) => term.checked) : 약관동의 체크박스들의 checked 속성값을 배열로 매핑
        //filter((checked) => checked) : checked가 true인 요소만 걸러냄
        //length === 3; : 체크박스에 선택된 개수가 3개인지 확인
    });
});

// document.getElementById("regist-ok-btn").addEventListener("click", function(event) {
//     const name = document.getElementById("join_name").value.trim();
//     const phoneFront = document.getElementById("join_phoneFront").value.trim();
//     const phoneBack = document.getElementById("join_phoneBack").value.trim();
//     const password = document.getElementById("join_password").value.trim();
//     const passwordVerify = document.querySelector(".protectorJoin-passwordVerify").value.trim();
//     const email = document.getElementById("join_email").value.trim();
//     const certification = document.querySelector(".protectorJoin-inputNumber").value.trim();
//     const agreeTerms = document.getElementById("checkAll").checked;
//     const individualAgreements = Array.from(document.querySelectorAll("input.protectorJoin-check")).every(checkbox => checkbox.checked);
//
//     if (!name || !phoneFront || !phoneBack || !password || !passwordVerify || !email || !certification || !agreeTerms || !individualAgreements) {
//         event.preventDefault();
//         alert("잘못된 입력입니다.");
//     } else if (password !== passwordVerify) {
//         event.preventDefault();
//         alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
//     }
//     alert("가입하시겠습니까?");
//     $("form").submit();
// });

document.getElementById("regist-ok-btn").addEventListener("click", function(event) {
    const name = document.getElementById("join_name").value.trim();
    const phoneFront = document.getElementById("join_phoneFront").value.trim();
    const phoneBack = document.getElementById("join_phoneBack").value.trim();
    const password = document.getElementById("join_password").value.trim();
    const passwordVerify = document.querySelector(".protectorJoin-passwordVerify").value.trim();
    const email = document.getElementById("join_email").value.trim();
    const certification = document.querySelector(".protectorJoin-inputNumber").value.trim();
    const agreeTerms = document.getElementById("checkAll").checked;
    const individualAgreements = Array.from(document.querySelectorAll("input.protectorJoin-check")).every(checkbox => checkbox.checked);

    if (!name || !phoneFront || !phoneBack || !password || !passwordVerify || !email || !certification || !agreeTerms || !individualAgreements) {
        event.preventDefault();
        alert("잘못된 입력입니다.");
    } else if (password !== passwordVerify) {
        event.preventDefault();
        alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
    } else {
        if (confirm("가입하시겠습니까?")) {
            $("form").submit();
        }
    }
});
