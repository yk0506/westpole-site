package biz.westpole.site.domain;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Alias("boardDTO")
public class BoardDTO extends CommonDTO {
	
	
	private int bltnNo;
	private int boardId;
	private String subject;
	private String contents;
	private String userNick;
	private String userId;
	private Date regDate;
	private String password;
	private String mode;
	
	
	public int getBltnNo() {
		return bltnNo;
	}
	public void setBltnNo(int bltnNo) {
		this.bltnNo = bltnNo;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
