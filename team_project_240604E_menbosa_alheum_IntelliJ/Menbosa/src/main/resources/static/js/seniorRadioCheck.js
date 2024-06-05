document.addEventListener("DOMContentLoaded", function () {
    // 모든 폼 요소를 선택합니다.
    const forms = document.querySelectorAll('form');

    forms.forEach((form) => {
        // 폼 내 모든 라디오 버튼을 선택합니다.
        const radioButtons = form.querySelectorAll('input[type="radio"]');
        // 폼 내 '다음문항' 버튼을 선택합니다.
        const nextButton = form.querySelector('button.next');

        function toggleButtons() {
            let isSelected = false;
            // 모든 라디오 버튼 중 하나라도 선택되어 있는지 확인합니다.
            radioButtons.forEach((radio) => {
                if (radio.checked) {
                    isSelected = true;
                }
            });
            // 선택된 라디오 버튼이 있는 경우 '다음문항' 버튼을 활성화합니다.
            nextButton.disabled = !isSelected;
        }

        // 라디오 버튼의 상태가 변경될 때마다 toggleButtons 함수를 호출합니다.
        radioButtons.forEach((radio) => {
            radio.addEventListener('change', toggleButtons);
        });

        // 초기 버튼 상태를 설정합니다.
        toggleButtons();
    });
});