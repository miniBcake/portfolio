const downButton = document.querySelectorAll(".downButton");
const answer = document.querySelectorAll(".answer");
const clickQuestion = document.querySelectorAll(".question");

// foreach문으로 toggle 추가
clickQuestion.forEach((e, i) => {
  e.addEventListener("click", () => {
    answer[i].classList.toggle("view");
    downButton[i].classList.toggle("rotate");

    // 클릭된 질문을 말고 나머지 질문 닫기
    clickQuestion.forEach((e, j) => {
      if (j !== i) {
        answer[j].classList.remove("view");
        downButton[j].classList.remove("rotate");
      }
    });
  });
});