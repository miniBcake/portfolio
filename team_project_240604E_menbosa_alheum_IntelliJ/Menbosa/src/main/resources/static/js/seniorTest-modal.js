document.addEventListener('DOMContentLoaded', () => {
  const endTestButton = document.querySelector('.end');
  const modalBackground = document.querySelector('.protector-result-background');
  const modalBox = document.querySelector('.protector-result-modalBox');

  endTestButton.addEventListener('click', () => {
    modalBackground.style.display = 'block';
    // modalBox.style.display = 'block';
  });

});

 document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('showModal') === 'true') {
      document.querySelector('.protector-result-background').style.display = 'block';
    }
  });