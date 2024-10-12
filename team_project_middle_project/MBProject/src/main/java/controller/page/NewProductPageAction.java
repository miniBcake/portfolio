package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ConfigUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * NewProductPageAction 클래스는 새 상품 등록 페이지로 이동하기 위한 액션 클래스입니다.
 * 
 * 사용자가 상품을 등록할 수 있도록 요청을 받아 상품 등록 페이지로 
 * 필요한 데이터를 전달한 후 해당 페이지로 포워딩합니다.
 */
public class NewProductPageAction implements Action  {
	 /**
     * 클라이언트의 요청을 처리하고, 상품 등록 페이지로 포워딩합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - 상품 등록 시 필요한 정보가 포함됩니다.
     * @param response 클라이언트의 HTTP 응답 객체.
     * @return ActionForward 상품 등록 페이지로 포워딩할 경로 정보를 포함한 객체.
     */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		 // 로그: 액션 실행 시작
        System.out.println("[INFO] NewProductPageAction 실행 시작");
        // 요청 파라미터로부터 boardCateName 값을 가져옵니다.

        // ActionForward 객체 생성
        ActionForward forward = new ActionForward();
        
        //config.propeties 값 JSP 사용을 위해 전달
        request.setAttribute("uploadUrl", ConfigUtils.getProperty("ckEditor.uploadUrl"));
        request.setAttribute("deleteUrl", ConfigUtils.getProperty("ckEditor.deleteUrl"));
        request.setAttribute("deleteFetchUrl", ConfigUtils.getProperty("ckEditor.delteFileFetchUrl"));
        request.setAttribute("createPostProductFetchUrl", ConfigUtils.getProperty("ckEditor.createPostProductFetchUrl"));

        // 이동할 페이지 설정
        forward.setPath("productRegister.jsp");  // 작성 페이지로 이동 설정
        System.out.println("[INFO] 설정된 이동 경로: " + forward.getPath());
        forward.setRedirect(false);
        // 로그: 포워딩 설정
        System.out.println("[INFO] 포워딩으로 실행");
        System.out.println("[INFO] NewProductPageAction 실행 종료");
        return forward;
	}

}
