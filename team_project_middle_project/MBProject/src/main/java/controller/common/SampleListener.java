package controller.common;

import java.util.ArrayList;
import java.util.Random;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.common.Crawling;
import model.dao.ProductCateDAO;
import model.dao.ProductDAO;
import model.dto.ProductCateDTO;
import model.dto.ProductDTO;

@WebListener
public class SampleListener implements ServletContextListener {
	private ProductDAO productDAO;
	private ProductDTO productDTO;


	//생성자
    public SampleListener() {
        this.productDAO = new ProductDAO();
        this.productDTO = new ProductDTO();  
        productDTO.setEndNum(10);//페이지네이션 용 값
    }

    //서버 시작시
    public void contextInitialized(ServletContextEvent sce)  { 
         if(productDAO.selectAll(productDTO).isEmpty()) {
        	 System.out.println("log: Listener Product isEmpty");
        	 //만약 상품 DB가 비어있을 경우
        	 //크롤링한 데이터 받아오기
        	 ArrayList<ProductDTO> datas = Crawling.findProductInfo();
        	 //현재 있는 상품 카테고리 리스트 받아오기
        	 ProductCateDTO productCateDTO = new ProductCateDTO();
        	 ProductCateDAO productCateDAO = new ProductCateDAO();
        	 ArrayList<ProductCateDTO> productCateList = productCateDAO.selectAll(productCateDTO);
        	 //랜덤값을 위한 랜덤 객체 선언
        	 Random rand = new Random();
        	 for(ProductDTO data : datas) {
        		 //크롤링한 데이터를 순회하며 카테고리 번호를 랜덤값으로 넣어
        		 data.setProductCateNum(productCateList.get(rand.nextInt(productCateList.size())).getProductCateNum());
        		 data.setCondition("CRAWLING_ONLY");
        		 //DB에 상품 추가
        		 productDAO.insert(data);
        	 }
         }
         System.out.println("log: Listener End");

    }

    //서버 종료시
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }
}
