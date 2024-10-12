package model.common;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import model.dto.ProductDTO;

public class Crawling {
	//네이버 쇼핑 - 붕어빵 악세서리 검색
	private static final String URL = "https://search.shopping.naver.com/search/all?query=%EB%B6%95%EC%96%B4%EB%B9%B5%20%EC%95%85%EC%84%B8%EC%84%9C%EB%A6%AC"; //크롤링할 주소
	private static final String CSS_ELEMENTS_PRICE = "div > div.product_info_area__xxCTi > div.product_price_area__eTg7I > strong > span.price > span > em"; //가격 Css Selector
	private static final String CSS_ELEMENTS_IMAGE = "div > div.product_img_area__cUrko > div > a > img"; //이미지, 상품명 Css Selector
	private static final int SCROLL_AMOUNT = 100; // 한번에 스크롤할 픽셀 양
	
	public static ArrayList<ProductDTO> findProductInfo() {
		ArrayList<ProductDTO> datas = new ArrayList<>(); //반환할 데이터
		List<WebElement> priceElements = null; //가격 크롤링 담을 리스트
		List<WebElement> imageElements = null; //이미지 크롤링 담을 리스트 (이미지, 상품명)
		int index = 0; //가격 index
		
		//WebDriverManager를 통한 크롬 드라이버 관리
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		//스크롤을 위한 캐스팅
		JavascriptExecutor js = (JavascriptExecutor) driver;

		//웹페이지 로드
		driver.get(URL);

		try {
			//스크롤하며 요소를 단계적으로 요소를 찾기 위한 반목문
			while (true) {
				priceElements = driver.findElements(By.cssSelector(CSS_ELEMENTS_PRICE)); //가격
				imageElements = driver.findElements(By.cssSelector(CSS_ELEMENTS_IMAGE)); //이미지 (+상품명)

				js.executeScript("window.scrollBy(0, " + SCROLL_AMOUNT + ");"); //스크롤

				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); //암시적 대기

				if (imageElements.size() >= 35) {
					//요소를 35개 이상 찾으면 종료
					System.out.println("log: Crawling Elements find 35 up");
					break;
				}
			}//while문 종료
			//크롤링한 정보 담기
			for (WebElement image : imageElements) {
				//이미지 태그 요소를 기준으로 상품 객체에 정보를 넣고 datas 리스트에 추가
				ProductDTO data = new ProductDTO();
				data.setProductName(image.getAttribute("alt")); //이미지 설명 (상품명과 동일함)
				data.setProductProfileWay(image.getAttribute("src")); //이미지 주소
				data.setProductPrice(Integer.parseInt(priceElements.get(index++).getText().replaceAll("[^\\d]", ""))); //가격
				datas.add(data);//추가
			}//for문 종료
		} catch (NoSuchElementException e) {
			System.err.println("log: Crawling Exception : "+e.getMessage());
			datas.clear();
		} catch (NumberFormatException e) {
			System.err.println("log: Crawling Exception : "+e.getMessage());
			datas.clear();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("log: Crawling Exception : "+e.getMessage());
			datas.clear();
		} catch (Exception e) {
			System.err.println("log: Crawling Exception : "+e.getMessage());
			datas.clear();
		} finally {
			//페이지 닫기
			driver.quit();
		}
		return datas;
	}

}
