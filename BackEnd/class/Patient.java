import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Patient extends Person{
	
	public Patient() {
		
	}
	public Patient(String name, String mail) {
		super(name, mail);
	}
	
	public void load(String id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT * FROM patient WHERE mail = ? ";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	st.setString(1, id);
        	ResultSet rs = st.executeQuery();
        	
        	if (rs.next()) {
        		this.setName(rs.getString("name"));
        		this.setMail(rs.getString("mail"));
        	}
        	
		}catch (Exception ex) {
			
		}
	}
}
