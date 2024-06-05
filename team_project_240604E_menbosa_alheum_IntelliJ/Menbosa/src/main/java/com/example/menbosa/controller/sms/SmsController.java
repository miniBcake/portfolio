package com.example.menbosa.controller.sms;

import com.example.menbosa.service.protector.mypage.MypageService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private static final Logger log = LoggerFactory.getLogger(SmsController.class);
    final DefaultMessageService messageService;
    private final MypageService mypageService;

    public SmsController(MypageService mypageService) {
        this.mypageService = mypageService;
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCSPOHDCMRBTVPM6", "E8CC6OBRIR12YC1NVQGMHPQWBUC5PRT5", "https://api.coolsms.co.kr");
    }

    @PostMapping("/send-one")
    public SingleMessageSentResponse sendOneTest(String phoneNum, int division, String name) {
        //TODO 실제로 문자 발송됩니다!!!!! DB에 본인 번호 데이터 넣어서 테스트 본인 번호로 진행하세요!!!!!!!!
        //TODO 실제로 문자 발송됩니다!!!!! DB에 본인 번호 데이터 넣어서 테스트 본인 번호로 진행하세요!!!!!!!!

        //난수 생성
        String smsMessagePost = ""+randomNumberCreate();
        String msg = "";

        //pro 인지 sen 인지 구분하여 회원확인 + 컬럼 인증값 저장 쿼리 사용
        //proMem = 1, senMem:jiyoon = 2
        switch (division){
            case 1:
                String proMemNum = ""+mypageService.selectProExi(name, phoneNum);
                mypageService.updateProCerti(smsMessagePost, proMemNum);
                msg = "알흠 ["+smsMessagePost+"] 인증번호를 입력해주세요 (*코딩연습문자입니다)";
                break;
            case 2:
                String senMemNum = ""+mypageService.selectSenExi(name, phoneNum);
                mypageService.updateSenCerti(smsMessagePost, senMemNum);
                msg = "알흠 ["+smsMessagePost+"] 인증번호를 입력해주세요 (*코딩연습문자입니다)";
                break;
        }

        //메세지 전송 값 입력 후 전송
        Message message = new Message();
        message.setFrom("01026573508");
        message.setTo(phoneNum);
//        message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다.");
        message.setText(msg);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }

    //난수 생성 메소드 (5자리)
    private int randomNumberCreate(){
        int min = 10000;
        int max = 99999;
        return (int)Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
