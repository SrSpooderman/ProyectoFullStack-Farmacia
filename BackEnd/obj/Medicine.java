import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Medicine {
	private int id;
	private String name;
	private Float Tmax;
	private Float Tmin;
	
	public Medicine() {
		
	}
	
	public Medicine(int id, String name, Float Tmax, Float Tmin) {
		this.id = id;
		this.name = name;
		this.Tmax = Tmax;
		this.Tmin = Tmin;
	}
	
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTmax() {
        return Tmax;
    }

    public void setTmax(Float Tmax) {
        this.Tmax = Tmax;
    }

    public Float getTmin() {
        return Tmin;
    }

    public void setTmin(Float Tmin) {
        this.Tmin = Tmin;
    }
	   
    public void load(int id) {
    	String id_s = String.valueOf(id);
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT * FROM medicine WHERE id = ? ";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	st.setString(1, id_s);
        	ResultSet rs = st.executeQuery();
        	
        	if (rs.next()) {
        		this.id = id;
        		this.name = rs.getString("name");
        		this.Tmax = rs.getFloat("tmax");
        		this.Tmin = rs.getFloat("tmin");
        	}
    	}catch(Exception ex) {
    		
    	}
    }
}
