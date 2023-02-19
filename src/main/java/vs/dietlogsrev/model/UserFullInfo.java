package vs.dietlogsrev.model;

public class UserFullInfo {
    
    private String username;
    private String email;
    private UserNeededInfo info;
    
    public UserFullInfo() {}
    
	public UserFullInfo(String username, String email, UserNeededInfo info) {
		this.username = username;
		this.email = email;
		this.info = info;
	}

	public UserFullInfo(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserNeededInfo getInfo() {
		return info;
	}

	public void setInfo(UserNeededInfo info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "UserFullInfo [username=" + username + ", email=" + email + ", info=" + info + "]";
	}

}
