
public abstract class Person {
	private String name;
	private String mail;
	
	public Person() {
		
	}
	public Person(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	public String getName() {
	        return name;
	    }
	    
	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getMail() {
	        return mail;
	    }
	    
	    public void setMail(String mail) {
	        this.mail = mail;
	    }
	
	public abstract void load(String id);
}
