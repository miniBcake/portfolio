//빈칸검사
let emailCheck = document.querySelector("#email");
let passwdCheck = document.querySelector("#password");
let nameCheck = document.querySelector("#name");

function changeRedBlueMsg() {
  //focus
  if (this.parentElement.childNodes.length == 5) {
    this.style.boxShadow = "0 0 0px 4px rgb(0,199,174,0.2)";
  } else if (!this.value) {
    this.style.boxShadow = "0 0 0 4px rgb(255,0,0,0.2)";
  }
}

function changeAll() {
  //blur
  if (!this.value) {
    this.style.border = "1px solid red";
    this.style.boxShadow = "none";

    let pTag = document.createElement("p");
    pTag.style.color = "red";

    if (this.type == "email") {
      pTag.innerText = `이메일 주소를 입력해주세요.`;
    }
    if (this.type == "password") {
      pTag.innerText = `비밀번호를 입력해주세요.`;
    }
    if (this.type == "text") {
      pTag.innerText = `이름을 입력해주세요.`;
    }

    if (this.parentElement.childNodes.length == 5) {
      this.parentElement.insertBefore(pTag, null);
    }
  } else {
    this.style.border = "1px solid rgb(226, 226, 226)";
    this.style.boxShadow = "none";

    if (this.parentElement.childNodes.length == 6) {
      this.parentElement.lastElementChild.remove();
    }
  }
}

emailCheck.addEventListener("focus", changeRedBlueMsg);
emailCheck.addEventListener("blur", changeAll);

passwdCheck.addEventListener("focus", changeRedBlueMsg);
passwdCheck.addEventListener("blur", changeAll);

nameCheck.addEventListener("focus", changeRedBlueMsg);
nameCheck.addEventListener("blur", changeAll);

// 체크박스
NodeList.prototype.map = Array.prototype.map;

const all = document.querySelector("input#terms-all");
const termset = document.querySelectorAll("input.terms");

all.addEventListener("click", () => {
  termset.forEach((terms) => {
    terms.checked = all.checked;
  });
});

termset.forEach((term) => {
  term.addEventListener("click", (e) => {
    all.checked =
      termset.map((term) => term.checked).filter((checked) => checked)
        .length === 6;
  });
});
