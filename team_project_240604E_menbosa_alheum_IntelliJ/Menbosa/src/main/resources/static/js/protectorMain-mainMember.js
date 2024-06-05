const bannerPrevButton = document.querySelector(".protectorMain-bannerMove > button:nth-of-type(1)");
const bannerNextButton = document.querySelector(".protectorMain-bannerMove > button:nth-of-type(2)");
const banner = document.querySelector(".protectorMain-bannerImg");


let currentIndex = 0;
const bannerWidth = 970;


bannerPrevButton.addEventListener("click", () => {
  console.log("클릭");
  currentIndex--;
  currentIndex = (currentIndex < 0) ? 0 : currentIndex;
  console.log(currentIndex);
  updateBannerPosition();
});

bannerNextButton.addEventListener("click", () => {
  console.log("클릭2");
  currentIndex = (currentIndex < 2) ? currentIndex + 1 : 0;
  updateBannerPosition();
});

function updateBannerPosition() {
  banner.style.transform = `translateX(-${currentIndex * bannerWidth}px)`;
  banner.style.transition = "0.5s ease";
}