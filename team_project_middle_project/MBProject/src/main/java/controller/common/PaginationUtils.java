package controller.common;

import model.dto.BoardDTO;
import model.dto.ProductDTO;
import model.dto.ReplyDTO;
import model.dto.StoreDTO;

public class PaginationUtils {

    /**JavaDoc 주석
     * 페이지네이션에 필요한 데이터를 설정하는 메서드
     * @param currentPage 현재 페이지 번호
     * @param pageSize 페이지당 항목 수
     * @param totalRecords 전체 항목 수
     * @param dto 설정할 DTO 객체 (BoardDTO 또는 ReplyDTO)
     */
    public static void setPagination(int currentPage, int pageSize, int totalRecords, Object dto) {
        // 파라미터 로그 출력
        System.out.println("[INFO] setPagination 메서드 호출 - currentPage: " + currentPage + ", pageSize: " + pageSize + ", totalRecords: " + totalRecords);
        
        // 첫 번째 항목의 인덱스
        int startNum = (currentPage - 1) * pageSize + 1;
        // 마지막 항목의 인덱스
        int endNum = currentPage * pageSize;
        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // DTO에 페이지네이션 정보 설정
        if (dto instanceof BoardDTO) {
            BoardDTO boardDTO = (BoardDTO) dto;
            boardDTO.setStartNum(startNum);
            boardDTO.setEndNum(endNum);
        } else if (dto instanceof ReplyDTO) {
            ReplyDTO replyDTO = (ReplyDTO) dto;
            replyDTO.setStartNum(startNum);
            replyDTO.setEndNum(endNum);
        } else if (dto instanceof ProductDTO) {
            ProductDTO productDTO = (ProductDTO) dto;
            productDTO.setStartNum(startNum);
            productDTO.setEndNum(endNum);
        } else if(dto instanceof StoreDTO) {
			StoreDTO storeDTO = (StoreDTO) dto;
			storeDTO.setStoreStartPage(startNum);
			storeDTO.setStoreEndPage(endNum);
        } else {
            throw new IllegalArgumentException("지원되지 않는 DTO 타입입니다.");
        }

        System.out.println("[INFO] 페이지네이션 설정 완료: startNum=" + startNum + ", endNum=" + endNum + ", totalPages=" + totalPages);
    }
    /**JavaDoc 주석
     * 총 페이지 수 계산하는 메서드
     * @param totalRecords 전체 항목 수
     * @param pageSize 페이지당 항목 수
     * @return 총 페이지 수
     */
    public static int calTotalPages(int totalRecords, int pageSize) {
        // 파라미터 로그 출력
        System.out.println("[INFO] calTotalPages 메서드 호출 - totalRecords: " + totalRecords + ", pageSize: " + pageSize);
        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        System.out.println("[INFO] 총 페이지 수 계산 완료: totalPages=" + totalPages);
        return totalPages;
    }
}