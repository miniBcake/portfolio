fetch('./../../templates/basic/footer.html')
  .then(response => {
    if (!response.ok) {
      throw new Error('실패');
    }
    return response.text();
  })
  .then(html => {
    // console.log(html);
    document.querySelector('footer').innerHTML = html;
  })
  .catch(error => {
    console.error('문제발생', error);
  });