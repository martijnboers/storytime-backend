package model;

public class EmailConfiguration {
	private String email;
	private String password;
	private String host;
	private String port;
	
	public EmailConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public EmailConfiguration(String email, String password, String host, String port) {
		this.email = email;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	

}
