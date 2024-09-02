package controller.common;

public class ActionForward {
	// 페이지의 이동 방법과 경로를 반환하는 객체
	private boolean redirect;	// 방법
	private String path;		// 경로
	public boolean isRedirect() {
		return redirect;
	}
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}