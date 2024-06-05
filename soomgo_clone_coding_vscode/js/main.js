let search = document.getElementById("searchmain-search");
let search2 = document.getElementById("header-search");

let dropmenu = document.querySelector(".search-dropmenu");
let dropmenuList = document.querySelector(".search-dropmenu ul");
let dropmenuDiv = document.querySelector(".no-searchlist");

search.lastElementChild.onfocus = searchChange;
search.lastElementChild.onblur = searchOrigin;

search2.lastElementChild.onfocus = searchChange;
search2.lastElementChild.onblur = searchOrigin;

function searchChange() {
  this.parentElement.style.backgroundColor = "white";
  this.parentElement.style.boxShadow =
    "0 0 5px 1px rgb(0,0,0,0.1), 0 0 0 1px #0bdabe inset";

  search.onclick = () => {
    dropmenu.style.display = "inline";

    //초기화
    dropmenu.firstElementChild.firstElementChild.style.color = "#00c7ae";
    dropmenu.firstElementChild.lastElementChild.style.color = "#737373";
    dropmenuList.style.display = "block";
    dropmenuDiv.style.display = "none";
    dropmenu.lastElementChild.firstElementChild.style.visibility = "hidden";

    //인기 키워드 클릭
    dropmenu.firstElementChild.firstElementChild.onclick = () => {
      dropmenu.firstElementChild.firstElementChild.style.color = "#00c7ae";
      dropmenu.firstElementChild.lastElementChild.style.color = "#737373";
      dropmenuList.style.display = "block";
      dropmenuDiv.style.display = "none";
      dropmenu.lastElementChild.firstElementChild.style.visibility = "hidden";
    };
    //최근 검색한 서비스
    dropmenu.firstElementChild.lastElementChild.onclick = () => {
      dropmenu.firstElementChild.lastElementChild.style.color = "#00c7ae";
      dropmenu.firstElementChild.firstElementChild.style.color = "#737373";
      dropmenuList.style.display = "none";
      dropmenuDiv.style.display = "block";
      dropmenu.lastElementChild.firstElementChild.style.visibility = "visible";
    };

    //닫기
    dropmenu.lastElementChild.lastElementChild.onclick = () => {
      dropmenu.style.display = "none";
    };
  };
}

function searchOrigin() {
  //blur
  // console.log(dropmenu.click);
  // console.log(dropmenu.getAttribute('style'));
  // if(dropmenu.getAttribute('style')=='display: none;'){
  // console.log("실행되나");
  this.parentElement.style = "none";
  // dropmenu.style.display = "none"; //드롭메뉴작동확인
  // }else{
  //   console.log("실행되나333333");
  // }
}

/////////////////////////////////////////////////////////////////////////////////////

//메인 배너
let slideBox = document.querySelector(".banner-over span");
let slideImg = document.querySelectorAll(".imglong");
let prevBtn = document.querySelector("#main-banner .arrow-left");
let nextBtn = document.querySelector("#main-banner .arrow-right");

let slideWidth = 970;
let currentIdx = 0;
let slideCnt = slideImg.length;

checkEnd();
slideBox.style.left = 0;

//다음 버튼 클릭 시 발생하는 이벤트
nextBtn.addEventListener("click", () => {
  currentIdx++;
  slideBox.style.left = -(currentIdx * slideWidth) + "px";
  slideBox.style.transition = "0.5s ease";
  checkEnd();
});

//이전 버튼 클릭 시 발생하는 이벤트
prevBtn.addEventListener("click", () => {
  currentIdx--;
  checkEnd();
  slideBox.style.left = -(currentIdx * slideWidth) + "px";
  slideBox.style.transition = "0.5s ease";
});

//슬라이드끝에 도달하면 맨앞이나 맨뒤로 이동
function checkEnd() {
  if (currentIdx < 0) {
    // console.log(currentIdx+"test1");
    currentIdx = slideCnt - 1;
    // console.log(currentIdx+"test2");
    slideBox.style.left = currentIdx * slideWidth + "px";
  }
  if (currentIdx >= slideCnt) {
    currentIdx = 0;
    slideBox.style.left = currentIdx * slideWidth + "px";
  }
}

////////배너-인기서비스
console.log(document.querySelector(".arrow-right").parentNode);

let slideBoxSub = document.querySelector(".hot-slide");
let slideImgSub = document.querySelectorAll(".hot-service-list div a");
let prevBtnSub = document.querySelector("#hot-service .arrow-left");
let nextBtnSub = document.querySelector("#hot-service .arrow-right");

let slideWidthSub = 496;
let currentIdxSub = 0;
let slideCntSub = (slideImgSub.length - 4) / 2;

checkEndSub();
slideBoxSub.style.left = 0;

//다음 버튼 클릭 시 발생하는 이벤트
nextBtnSub.addEventListener("click", () => {
  currentIdxSub++;
  slideBoxSub.style.left = -(currentIdxSub * slideWidthSub) + "px";
  slideBoxSub.style.transition = "0.5s ease";
  checkEndSub();
});

//이전 버튼 클릭 시 발생하는 이벤트
prevBtnSub.addEventListener("click", () => {
  currentIdxSub--;
  slideBoxSub.style.left = -(currentIdxSub * slideWidthSub) + "px";
  slideBoxSub.style.transition = "0.5s ease";
  checkEndSub();
});

//슬라이드가 처음이나 마지막에 도달했는지 확인하고 이전/다음 버튼을 표시/숨김처리
function checkEndSub() {
  if (currentIdxSub <= 0) {
    prevBtnSub.style.display = "none";
  } else {
    prevBtnSub.style.display = "block";
  }

  if (currentIdxSub >= slideCntSub) {
    nextBtnSub.style.display = "none";
  } else {
    nextBtnSub.style.display = "block";
  }
}

////////배너-클리닝
let slideBoxSub_cl = document.querySelector(".cleaning-day-imgcontainer");
let slideImgSub_cl = document.querySelectorAll(".cleaning-day-imgcontainer a");
let prevBtnSub_cl = document.querySelector("#cleaning-day .arrow-left");
let nextBtnSub_cl = document.querySelector("#cleaning-day .arrow-right");

let slideWidthSub_cl = 496;
let currentIdxSub_cl = 0;
let slideCntSub_cl = (slideImgSub_cl.length - 4) / 2;

checkEndSub_cl();
slideBoxSub_cl.style.left = 0;

//다음 버튼 클릭 시 발생하는 이벤트
nextBtnSub_cl.addEventListener("click", () => {
  currentIdxSub_cl++;
  slideBoxSub_cl.style.left = -(currentIdxSub_cl * slideWidthSub_cl) + "px";
  slideBoxSub_cl.style.transition = "0.5s ease";
  checkEndSub_cl();
});

//이전 버튼 클릭 시 발생하는 이벤트
prevBtnSub_cl.addEventListener("click", () => {
  currentIdxSub_cl--;
  slideBoxSub_cl.style.left = -(currentIdxSub_cl * slideWidthSub_cl) + "px";
  slideBoxSub_cl.style.transition = "0.5s ease";
  checkEndSub_cl();
});

//슬라이드가 처음이나 마지막에 도달했는지 확인하고 이전/다음 버튼을 표시/숨김처리
function checkEndSub_cl() {
  if (currentIdxSub_cl <= 0) {
    prevBtnSub_cl.style.display = "none";
  } else {
    prevBtnSub_cl.style.display = "block";
  }

  if (currentIdxSub_cl >= slideCntSub_cl) {
    nextBtnSub_cl.style.display = "none";
  } else {
    nextBtnSub_cl.style.display = "block";
  }
}

////////배너-숨고 이야기
let slideBoxSub_hi = document.querySelector(".story-imgcontainer");
let slideImgSub_hi = document.querySelectorAll(".story-imgcontainer a");
let prevBtnSub_hi = document.querySelector("#gosu-story .arrow-left");
let nextBtnSub_hi = document.querySelector("#gosu-story .arrow-right");

let slideWidthSub_hi = 330;
let currentIdxSub_hi = 0;
let slideCntSub_hi = slideImgSub_hi.length - 3;

checkEndSub_hi();
slideBoxSub_hi.style.left = 0;

//다음 버튼 클릭 시 발생하는 이벤트
nextBtnSub_hi.addEventListener("click", () => {
  console.log(currentIdx);
  currentIdxSub_hi++;
  slideBoxSub_hi.style.left = -(currentIdxSub_hi * slideWidthSub_hi) + "px";
  slideBoxSub_hi.style.transition = "0.5s ease";
  checkEndSub_hi();
});

//이전 버튼 클릭 시 발생하는 이벤트
prevBtnSub_hi.addEventListener("click", () => {
  currentIdxSub_hi--;
  slideBoxSub_hi.style.left = -(currentIdxSub_hi * slideWidthSub_hi) + "px";
  slideBoxSub_hi.style.transition = "0.5s ease";
  checkEndSub_hi();
});

//슬라이드가 처음이나 마지막에 도달했는지 확인하고 이전/다음 버튼을 표시/숨김처리
function checkEndSub_hi() {
  if (currentIdxSub_hi <= 0) {
    prevBtnSub_hi.style.display = "none";
  } else {
    prevBtnSub_hi.style.display = "block";
  }

  if (currentIdxSub_hi >= slideCntSub_hi) {
    nextBtnSub_hi.style.display = "none";
  } else {
    nextBtnSub_hi.style.display = "block";
  }
}

////////배너-숨고 포트폴리오
let slideBoxSub_pofo = document.querySelector(".gosu-imgcontainer");
let slideImgSub_pofo = document.querySelectorAll(".gosu-imgcontainer li");
let prevBtnSub_pofo = document.querySelector("#gosu-portfolio .arrow-left");
let nextBtnSub_pofo = document.querySelector("#gosu-portfolio .arrow-right");

let slideWidthSub_pofo = 497;
let currentIdxSub_pofo = 0;
let slideCntSub_pofo = (slideImgSub_pofo.length - 4) / 2;

checkEndSub_pofo();
slideBoxSub_pofo.style.left = 0;

//다음 버튼 클릭 시 발생하는 이벤트
nextBtnSub_pofo.addEventListener("click", () => {
  console.log(currentIdx);
  currentIdxSub_pofo++;
  slideBoxSub_pofo.style.left =
    -(currentIdxSub_pofo * slideWidthSub_pofo) + "px";
  slideBoxSub_pofo.style.transition = "0.5s ease";
  checkEndSub_pofo();
});

//이전 버튼 클릭 시 발생하는 이벤트
prevBtnSub_pofo.addEventListener("click", () => {
  currentIdxSub_pofo--;
  slideBoxSub_pofo.style.left =
    -(currentIdxSub_pofo * slideWidthSub_pofo) + "px";
  slideBoxSub_pofo.style.transition = "0.5s ease";
  checkEndSub_pofo();
});

//슬라이드가 처음이나 마지막에 도달했는지 확인하고 이전/다음 버튼을 표시/숨김처리
function checkEndSub_pofo() {
  if (currentIdxSub_pofo <= 0) {
    prevBtnSub_pofo.style.display = "none";
  } else {
    prevBtnSub_pofo.style.display = "block";
  }

  if (currentIdxSub_pofo >= slideCntSub_pofo) {
    nextBtnSub_pofo.style.display = "none";
  } else {
    nextBtnSub_pofo.style.display = "block";
  }
}

//전문가로 활동하시나요?
let slideBox_new = document.querySelector(".img-text");
let slideImg_new = document.querySelectorAll(".text-box");
let prevBtn_new = document.querySelector(".new-left");
let nextBtn_new = document.querySelector(".new-right");

let slideWidth_new = 655;
let currentIdx_new = 0;
let slideCnt_new = slideImg_new.length;

let dot1 = document.querySelector(".dot1");
let dot2 = document.querySelector(".dot2");
let dot3 = document.querySelector(".dot3");

checkEnd_new();

console.log(currentIdx_new);

slideBox_new.style.left = 0;

//이전 버튼 클릭 시 발생하는 이벤트
prevBtn_new.addEventListener("click", () => {
    currentIdx_new--;
    console.log(currentIdx_new);
    checkEnd_new();
    slideBox_new.style.left = -(currentIdx_new * slideWidth_new) + "px";
    console.log("이전버튼" + currentIdx_new * slideWidth_new);
    slideBox_new.style.transition = "0.3s ease";

});

//다음 버튼 클릭 시 발생하는 이벤트
nextBtn_new.addEventListener("click", () => {
    currentIdx_new++;
    console.log(currentIdx_new);
    slideBox_new.style.left = -(currentIdx_new * slideWidth_new) + "px";
    console.log("다음버튼" + currentIdx_new * slideWidth_new);
    slideBox_new.style.transition = "0.3s ease";
    checkEnd_new();
});

//슬라이드끝에 도달하면 색변환
function checkEnd_new() {
  if (currentIdx_new <= 0) {
    prevBtn_new.style.color = "#dbdbdb";
    prevBtn_new.style.pointerEvents = "none";
  } else {
    prevBtn_new.style.color = "#737373";
    prevBtn_new.style.pointerEvents = "all";
  }

  if (currentIdx_new >= slideCnt_new - 1) {
    nextBtn_new.style.color = "#dbdbdb";
    nextBtn_new.style.pointerEvents = "none";
  } else {
    nextBtn_new.style.color = "#737373";
    nextBtn_new.style.pointerEvents = "all";
  }

  //dot 색 변환
  function checkdot() {
    if (currentIdx_new === 0) {
      dot1.style.color = "#737373";
      dot2.style.color = "#dbdbdb";
      dot3.style.color = "#dbdbdb";
    }
    if (currentIdx_new === 1) {
      dot1.style.color = "#dbdbdb";
      dot2.style.color = "#737373";
      dot3.style.color = "#dbdbdb";
    }
    if (currentIdx_new === 2) {
      dot1.style.color = "#dbdbdb";
      dot2.style.color = "#dbdbdb";
      dot3.style.color = "#737373";
    }
  }
  checkdot();

  //dotclick
  dot1.onclick = () => {
    dot1.style.color = "#737373";
    dot2.style.color = "#dbdbdb";
    dot3.style.color = "#dbdbdb";
    currentIdx_new = 0;
    slideBox_new.style.left = -(currentIdx_new * slideWidth_new) + "px";
    slideBox_new.style.transition = "0.3s ease";
  };

  dot2.onclick = () => {
    dot1.style.color = "#dbdbdb";
    dot2.style.color = "#737373";
    dot3.style.color = "#dbdbdb";
    currentIdx_new = 1;
    slideBox_new.style.left = -(currentIdx_new * slideWidth_new) + "px";
    slideBox_new.style.transition = "0.3s ease";
  };

  dot3.onclick = () => {
    dot1.style.color = "#dbdbdb";
    dot2.style.color = "#dbdbdb";
    dot3.style.color = "#737373";
    currentIdx_new = 2;
    slideBox_new.style.left = -(currentIdx_new * slideWidth_new) + "px";
    slideBox_new.style.transition = "0.3s ease";
  };
}

/////////////////////////////////////////////////////////////////////////////////////

////////////////무한배너 예시자료
// function Slider(target) {
//   // 상태
//   let index = 1;
//   let isMoved = true;
//   const speed = 1000; // ms

//   // 방향
//   const transform = "transform " + speed / 1000 + "s";
//   let translate = (i) => "translateX(-" + 100 * i + "%)";

//   // 슬라이더
//   const slider = document.querySelector(".banner-over");
//   const sliderRects = slider.getClientRects()[0];
//   slider.style["overflow"] = "hidden";

//   // 슬라이더 화면 컨테이너
//   // const container = document.createElement("div");
//   // container.style["display"] = "flex";
//   // container.style["flex-direction"] = type === "V" ? "column" : "row";
//   // container.style["width"] = sliderRects.width + "px";
//   // container.style["height"] = sliderRects.height + "px";
//   // container.style["transform"] = translate(index);

//   // 슬라이더 화면 목록
//   let boxes = [].slice.call(slider.children);
//   boxes = [].concat(boxes[boxes.length - 1], boxes, boxes[0]);

//   // 슬라이더 화면 스타일
//   const size = boxes.length;
//   for (let i = 0; i < size; i++) {
//     const box = boxes[i];
//     box.style["flex"] = "none";
//     box.style["flex-wrap"] = "wrap";
//     box.style["height"] = "100%";
//     box.style["width"] = "100%";
//     container.appendChild(box.cloneNode(true));
//   }

//   // 처음/마지막 화면 눈속임 이벤트
//   container.addEventListener("transitionstart", function () {
//     isMoved = false;
//     setTimeout(() => {
//       isMoved = true;
//     }, speed);
//   });
//   container.addEventListener("transitionend", function () {
//     // 처음으로 순간이동
//     if (index === size - 1) {
//       index = 1;
//       container.style["transition"] = "none";
//       container.style["transform"] = translate(index);
//     }
//     // 끝으로 순간이동
//     if (index === 0) {
//       index = size - 2;
//       container.style["transition"] = "none";
//       container.style["transform"] = translate(index);
//     }
//   });

//   // 슬라이더 붙이기
//   slider.innerHTML = "";
//   slider.appendChild(container);

//   return {
//     move: function (i) {
//       if (isMoved === true) {
//         index = i;
//         container.style["transition"] = transform;
//         container.style["transform"] = translate(index);
//       }
//     },
//     next: function () {
//       if (isMoved === true) {
//         index = (index + 1) % size;
//         container.style["transition"] = transform;
//         container.style["transform"] = translate(index);
//       }
//     },
//     prev: function () {
//       if (isMoved === true) {
//         index = index === 0 ? index + size : index;
//         index = (index - 1) % size;
//         container.style["transition"] = transform;
//         container.style["transform"] = translate(index);
//       }
//     }
//   };
// }

// // 입력
// const s1 = new Slider(".banner-over");

// setInterval(() => s1.next(), 1000);
