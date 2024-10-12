package controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * ConfigUtils 클래스는 설정 파일(config.properties)의 속성을 로드하고, 해당 속성 값을 가져오는 유틸리티 클래스입니다.
 * 이 클래스는 애플리케이션이 시작될 때 설정 파일을 로드하며, 특정 속성 값을 가져오는 기능을 제공합니다.
 */
public class ConfigUtils {
    /**
     * 정적 초기화 블록으로 config.properties 파일을 로드합니다.
     * 클래스가 로드될 때 config.properties 파일이 클래스패스에서 읽혀 properties 객체에 저장됩니다.
     * 파일이 존재하지 않거나 읽기 오류가 발생할 경우 오류 메시지를 출력합니다.
     */
    // Properties 객체 생성 - 설정 파일의 속성을 담을 객체
    private static Properties properties = new Properties();

    // 정적 초기화 블록: 클래스가 로드될 때 config.properties 파일을 읽어들입니다.
    static {
        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties")) {
            // config.properties 파일이 없을 경우 오류 메시지를 출력
            if (input == null) {
                System.out.println("[ERROR] config.properties 파일을 찾을 수 없습니다.");
            } else {
                // 파일을 정상적으로 읽어들였을 경우 properties 객체에 로드
                properties.load(input);
                System.out.println("[INFO] config.properties 파일이 성공적으로 로드되었습니다.");
            }
        } catch (IOException ex) {
            // 파일 읽기 중 예외 발생 시 오류 메시지를 출력
            System.out.println("[ERROR] config.properties 파일을 로드하는 중 오류가 발생했습니다.");
            ex.printStackTrace();
        }
    }

    // 속성 값을 가져오는 메서드
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            // 정상적으로 값이 있을 경우 해당 값을 반환
            System.out.println("[INFO] 속성 값 반환: " + key + " = " + value);
        } else {
            // 값이 없을 경우 경고 메시지를 출력
            System.out.println("[WARN] 속성 값이 존재하지 않습니다: " + key);
        }
        return value;
    }
}

