package web.pojo;

public class role {
	private String role;
	private int role_id;

	public role(String role, int role_id) {
		this.role = role;
		this.role_id = role_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
}
