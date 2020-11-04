package web.pojo;

public class authority {
	private int auth_id;
	private String authority;

	public authority(int auth_id, String authority) {
		this.auth_id = auth_id;
		this.authority = authority;
	}

	public int getAuth_id() {
		return auth_id;
	}

	public void setAuth_id(int auth_id) {
		this.auth_id = auth_id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
