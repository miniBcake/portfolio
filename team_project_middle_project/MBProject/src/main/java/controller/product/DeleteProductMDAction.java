package controller.product;

import java.io.File;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.AuthorizationUtils;
import controller.common.ConfigUtils;
import controller.common.ErrorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dao.ProductDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;
import model.dto.ProductDTO;

/**
 * DeleteProductMDAction 클래스는 관리자가 특정 상품을 삭제하는 기능을 제공합니다.
 * 이 클래스는 상품 및 관련 이미지, 게시글 데이터를 삭제하고, 삭제 후 상품 목록 페이지로 리다이렉트합니다.
 */

public class DeleteProductMDAction implements Action {
    /**
     * 클라이언트의 요청을 처리하고, 해당 상품과 관련된 게시글과 이미지를 삭제한 후 상품 목록 페이지로 리다이렉트합니다.
     * 삭제 작업은 관리자 권한을 가진 사용자만 수행할 수 있습니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체. 
     *                 - "productNum": 삭제할 상품의 번호.
     * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward 삭제 후 리다이렉트할 경로 정보를 포함한 ActionForward 객체.
     */
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] DeleteProductMDAction 시작");

        // 1. 세션에서 로그인 및 관리자 권한 확인
        HttpSession session = request.getSession();

        // RBAC 권한 확인: 관리자 권한이 있는지 체크
        if (!AuthorizationUtils.isAdmin(session)) {
            System.out.println("[ERROR] 관리자 권한이 없음.");
            ErrorUtils.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "401: 관리자 권한이 필요합니다.");
            return null;
        }

        // 2. 클라이언트로부터 넘어온 productNum 가져오기 및 유효성 검사
        Integer productNum = ErrorUtils.validateAndParseIntParam(request, response, "productNum"); // 삭제할 상품 번호
        if (productNum == null) {
            return null; // 파라미터 유효성 검증 실패 시 처리 중단
        }

        System.out.println("[INFO] 요청된 상품 번호: " + productNum);
        
        // 5. BoardNum을 가져오고, 상품 정보확인을 위한 조회
        ProductDAO productDAO = new ProductDAO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCondition("MD_ONE");
        productDTO.setProductNum(productNum); 
        ProductDTO DetailProductDTO = productDAO.selectOne(productDTO); // 해당 상품을 조회

        if (DetailProductDTO == null) {
            System.out.println("[ERROR] 해당 상품을 찾을 수 없습니다.");
            ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 상품을 찾을 수 없습니다.");
            return null;
        }
        
        // boardNum 상품DTO에서 꺼내옴
        int boardNum = DetailProductDTO.getBoardNum();

        // 6. 게시글에 관련된 이미지 파일 확인
        ImageDAO imageDAO = new ImageDAO();
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setBoardNum(boardNum);

        ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);  // 해당 게시글의 이미지 목록 가져오기

        if (images != null && !images.isEmpty()) {
            System.out.println("[INFO] 이미지 삭제 시작: " + images.size() + "개의 이미지 발견");

            for (ImageDTO img : images) {
                // 실제 파일 시스템에서 이미지 파일 삭제
                String imagePath = ConfigUtils.getProperty("delete.path") + img.getImageWay();  // 이미지 경로 설정
                File file = new File(imagePath);

                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("[INFO] 파일 삭제 성공: " + imagePath);
                    } else {
                        System.err.println("[ERROR] 파일 삭제 실패: " + imagePath);
                        ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 파일 삭제 실패");
                        return null;
                    }
                } else {
                    System.err.println("[WARN] 파일이 존재하지 않음: " + imagePath);
                }

                // 데이터베이스에서 이미지 정보 삭제
                boolean imageDeleted = imageDAO.delete(img);  // 이미지 정보를 데이터베이스에서 삭제
                if (!imageDeleted) {
                    System.err.println("[ERROR] 이미지 삭제 실패: 이미지 번호 " + img.getImageNum());
                    ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 이미지 삭제 실패");
                    return null;
                } else {
                    System.out.println("[INFO] 이미지 삭제 성공: 이미지 번호 " + img.getImageNum());
                }
            }
        } else {
            System.out.println("[INFO] 삭제할 이미지가 없습니다.");
        }

        // 7. 게시글 삭제
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNum(boardNum);
        boolean boardDeleteResult = boardDAO.delete(boardDTO);
        if (!boardDeleteResult) {
            System.out.println("[ERROR] 게시글 삭제 실패 : " + boardDTO.toString());
            ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 삭제 실패");
            return null;
        }
        System.out.println("[INFO] 게시글 삭제 성공 : " + boardDTO.toString());

        // 8. 상품 삭제
        ProductDTO productDelete = new ProductDTO();
        productDelete.setProductNum(productNum);
        boolean productDeleteResult = productDAO.delete(productDelete);
        if (!productDeleteResult) {
            System.out.println("[ERROR] 상품 삭제 실패 - 상품 ID: " + productNum);
            ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 상품 삭제 실패");
            return null;
        }
        System.out.println("[INFO] 상품 삭제 성공 - 상품 ID: " + productNum);

        // 9. 삭제 성공 후 리다이렉트
        ActionForward forward = new ActionForward();
        forward.setRedirect(true); // 리다이렉트 방식으로 이동
        forward.setPath("listProduct.do"); // 상품 목록 페이지로 이동
        System.out.println("[INFO] 상품 삭제 후 listProduct.do로 리다이렉트");
        return forward;
    }
}
