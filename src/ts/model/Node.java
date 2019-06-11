package ts.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement	
public class Node {
	private String id;
	private int cnt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", cnt=" + cnt + "]";
	}
	
}
