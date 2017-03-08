package online.mrsys.movierecommender.vo;

import java.io.Serializable;

public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 4716427017733319718L;
	
	private Integer id;
	private String account;
	private String password;
	private String email;
	private Boolean mailVerified;
	private RoleBean role;
	
	public UserBean() {
	}

	public UserBean(Integer id, String account, String password, String email, Boolean mailVerified, RoleBean role) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.email = email;
		this.mailVerified = mailVerified;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getMailVerified() {
		return mailVerified;
	}

	public void setMailVerified(Boolean mailVerified) {
		this.mailVerified = mailVerified;
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return id + "," + account + "," + password + "," + email + "," + mailVerified + "," + role;
	}

}
