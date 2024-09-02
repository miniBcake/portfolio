package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewBoardPageAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        // 로그: 액션 실행 시작
        System.out.println("[INFO] NewBoardPageAction 실행 시작");

        // ActionForward 객체 생성
        ActionForward forward = new ActionForward();

        // 이동할 페이지 설정
        forward.setPath("boardwrite.jsp");  // 작성 페이지로 이동 설정

        // 로그: 페이지 경로 설정
        System.out.println("[INFO] 설정된 이동 경로: " + forward.getPath());

        // 리다이렉트 설정
        forward.setRedirect(true);  // 리다이렉트 방식으로 이동 설정

        // 로그: 리다이렉트 설정
        System.out.println("[INFO] 리다이렉트 방식으로 설정됨");

        // 로그: 액션 실행 종료
        System.out.println("[INFO] NewBoardPageAction 실행 종료");

        // 설정된 ActionForward 객체 반환
        return forward;
    }
}