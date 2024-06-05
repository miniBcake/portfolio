fetch('./../../templates/basic/header-ver2Member.html')
  .then(response => {
    if (!response.ok) {
      throw new Error('실패');
    }
    return response.text();
  })
  .then(html => {
    // console.log(html);
    document.querySelector('header').innerHTML = html;
  })
  .catch(error => {
    console.error('문제발생', error);
  });