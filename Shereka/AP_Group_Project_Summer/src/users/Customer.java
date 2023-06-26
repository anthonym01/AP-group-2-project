package users;

public class Customer {
	
	private String cusId;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String telNum;
	
	
	 // Default Constructor
    public Customer() {
        cusId = "";
        password = "";
        firstName = "";
        lastName = "";
        email = "";
        telNum = "";
     }
    
 // Primary Constructor
    public Customer(String cusId, String password, String firstName, String lastName, String email, String telNum) {
        this.cusId = cusId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telNum = telNum;
    }
    
 // Copy Constructor
    public Customer(Customer customer) {
    	cusId = customer.cusId;
    	password = customer.password;
        firstName = customer.firstName;
        lastName = customer.lastName;
        email = customer.email;
        telNum = customer.telNum;
        
    }
    
    
// Getters and Setters
    
	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

@Override
	public String toString() {
		return "Customer [cusId=" + cusId + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", telNum=" + telNum + "]";
	}
    
 
	
}
