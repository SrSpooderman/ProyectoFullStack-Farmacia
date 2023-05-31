import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Xips {
	private int id;
	private Medicine medicine;
	private Patient patient;
	private LocalDate date;
	
	public Xips() {
		
	}
	public Xips(int id, Medicine medicine, Patient patient, LocalDate date) {
		this.id = id;
		this.medicine = medicine;
		this.patient = patient;
		this.date = date;
	}
	
	public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Medicine getMedicine() {
        return medicine;
    }
    
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void load(int id) {
    	String id_s = String.valueOf(id);
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT * FROM xip WHERE id = ? ";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	st.setString(1, id_s);
        	ResultSet rs = st.executeQuery();
        	
        	if (rs.next()) {
        		Date date = rs.getDate("date");
        		this.setId(id);
        		this.setDate(date.toLocalDate());
        		
        		//Medicine y patient
        		String id_patient = rs.getString("id_patient");
        		Patient paciente = new Patient();
        		paciente.load(id_patient);
        		this.setPatient(paciente);
        		
        		int id_medicine = rs.getInt("id_medicine");
        		Medicine medicina = new Medicine();
        		medicina.load(id_medicine);
        		this.setMedicine(medicina);
        	}
        	
    	}catch (Exception ex) {
    		
    	}
    }
}
