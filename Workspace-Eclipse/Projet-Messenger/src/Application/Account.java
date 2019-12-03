package Application;

public class Account {
	
	private String pseudo;
	
	private String username;
	
	private String password;
	
	private Address address;
	
	public Account(String username_, String password_,String pseudo_, Address address_) {
		this.username = username_;
		this.password = password_;
		this.address = address_;
		this.setPseudo(pseudo_);
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAdress(Address address) {
		this.address = address;
	}
	
	
	//A IMPLEMENTER//
	public boolean verifyUnicityNewNickname(String NewNickname) {
		
		return false;
	}
	
	//A IMPLEMENTER//
	public boolean verifyPassword(String password) {
		
		return false;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	

}
