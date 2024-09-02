package controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Action {
	// .do로 끝나는 액션들은 모두 execute 메서드를 사용해서 작성
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response);
}