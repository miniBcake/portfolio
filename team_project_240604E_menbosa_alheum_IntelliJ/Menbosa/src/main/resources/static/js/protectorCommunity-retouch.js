const cancelButton = document.querySelector(".cancel");

function back() {
  history.back()
}

const inputFile = document.querySelector("input[type='file']");

function deleteFile(e) {
  console.log(inputFile);
  inputFile.value = '';
}