let $buttons = $(".protectorTest-suvey-testContents-answer");
let $case = $(".protectorTest-suvey-testContents-answerbuttons");
let $result = $(".suvey-button");
let $resultSurvey = $(".resultSurvey");

let $ScoreInput = $("#scoreInput");
let totalScore = 0;
let qusLength = $case.length;

//클릭하면 클래스 추가
$buttons.each((index, button) => {
    $(button).on('click', function () {
        $(this).parent($case).children($buttons).removeClass('active');
        $(this).toggleClass("active");
    });
});

//점수연산
$result.on('click', () => {
    if($buttons.filter('.active').length === qusLength){

        totalScore = 1; //검사 안한 사람들과 구분하기 위한 점수
        $buttons.each((index, buttonValue) => {
            if($(buttonValue).attr('class') === "protectorTest-suvey-testContents-answer active") {
                totalScore += $(buttonValue).data("value");
            }
        });
        // console.log(totalScore);
        $ScoreInput.val(totalScore);

        //여기서 전송하면 화이트페이지 오류
        $("form").submit();

        // 모달창
        $(".protector-result-background").attr('style', 'display:block');
        $(".protector-result-modalBox").attr('style', 'display:block');

        if(totalScore>=1 && totalScore<6){
            $resultSurvey.html(`
          <div class="protector-result-button state-good">양호</div>
          <div class="protector-result-explanation">해당 검사 결과는 정상 범위 내에 있으며,<br>인지 기능 상태가 좋은 것으로 판단됩니다.</div>`);
        }else if(totalScore<11){
            $resultSurvey.html(`
          <div class="protector-result-button state-mid">주의</div>
          <div class="protector-result-explanation">해당 검사 결과는 정상 범위에 근접해 있으나,<br>인지 기능 상태가 악화될 가능성이 있으므로 주의가 필요합니다.</div>`);
        }else if(totalScore<=31){
            $resultSurvey.html(`
          <div class="protector-result-button state-bad">위험</div>
          <div class="protector-result-explanation">해당 검사 결과는 정상 범위를 벗어나 있으며,<br>인지 기능 상태가 악화 된 것으로 보입니다.</div>`);
        }else{
            alert("오류발생")
            location.href = `/alheum/mypage/survey`;
        }
    }else {
        alert("모든 문항을 체크해주세요");
    }
});
