let secessionButton = document.querySelector(".secessionButton");

secessionButton.addEventListener("click", function(){
    if(confirm("탈퇴 하시겠습니까?")){
      alert("탈퇴되었습니다.");
    }
});