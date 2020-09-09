package biz.westpole.site.domain;

public class CommonMessageDTO {

	private String mode;
	private String message;
	private String redirectUri;
	private BoardDTO params;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public BoardDTO getParams() {
		return params;
	}
	public void setParams(BoardDTO params) {
		this.params = params;
	}
	
	
}
