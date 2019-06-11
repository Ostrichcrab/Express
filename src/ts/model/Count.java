package ts.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement	
public class Count {
	private int uid;
	private int cnt;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return "Count [uid=" + uid + ", cnt=" + cnt + "]";
	}
	
}
