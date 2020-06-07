package kr.co.jongnomilk.model;
import java.util.ArrayList;

public class Data {
	String title;
	String content;
	FCMMsgData fcmMsgData;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public FCMMsgData getFcmMsgData() {
		return fcmMsgData;
	}
	public void setFcmMsgData(FCMMsgData fcmMsgData) {
		this.fcmMsgData = fcmMsgData;
	}
	@Override
	public String toString() {
		return "Data [title=" + title + ", content=" + content + ", dbData="
				+ fcmMsgData + "]";
	}
	
}
