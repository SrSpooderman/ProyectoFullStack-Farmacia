import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.Date;

public class Doctor extends Person{
	private String pass;
	private LocalDate lastlog;
	private String session;
	private ArrayList<Xips> releaseArray;
	
	public Doctor() {
		super();
	}
	public Doctor(String name, String mail, String pass, LocalDate lastlog, String session) {
		super(name, mail);
		this.pass = pass;
		this.lastlog = lastlog;
		this.session = session;
	}
	
  public String getPass() {
        return pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public LocalDate getLastlog() {
        return lastlog;
    }
    
    public void setLastlog(LocalDate lastlog) {
        this.lastlog = lastlog;
    }
    
    public String getSession() {
        return session;
    }
    
    public void setSession(String session) {
        this.session = session;
    }
    
    public ArrayList<Xips> getReleaseArray() {
        return this.releaseArray ;
    }
    
    public void setReleaseArray(ArrayList<Xips> releaseArray) {
        this.releaseArray = releaseArray;
    }

	public void load(String id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT * FROM doctor WHERE mail = ? ";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	st.setString(1, id);
        	ResultSet rs = st.executeQuery();
        	
        	if (rs.next()) {
        		this.setName(rs.getString("name"));
        		this.setMail(rs.getString("mail"));
        		this.setPass(rs.getString("pass"));
        		
        		Date lastlog_Date = (rs.getDate("last_log"));
        		this.setLastlog(lastlog_Date.toLocalDate());
        		
        		int session_code = rs.getInt("session");
        		this.setSession(String.valueOf(session_code));
        	}
        	
		}catch (Exception ex) {
			
		}
	}
	
	public void login(String mail, String pass) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT pass FROM doctor WHERE mail = ? ";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	st.setString(1, mail);
        	ResultSet rs = st.executeQuery();
        	
        	if (rs.next()) {
        		String pass_DDBB = rs.getString("pass");
        		if (pass_DDBB.equals(pass)) {
        			this.load(mail);
        		}
        	}
		}catch (Exception ex) {
			
		}
	}
	
	public Boolean isLogged(String mail, String session) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT last_log, session, pass FROM doctor WHERE mail = ? ";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	st.setString(1, mail);
        	ResultSet rs = st.executeQuery();
        	
        	if (rs.next()) {
        		Date lastlog_DDBB = rs.getDate("last_log");
        		String session_DDBB = rs.getString("session");
        		if(session_DDBB.equals(session)) {
        			LocalDate fechaActual = LocalDate.now();
        			LocalDate fecha_DDBB = lastlog_DDBB.toLocalDate();
        			long diferencia = ChronoUnit.DAYS.between(fecha_DDBB, fechaActual);
        			if (diferencia < 1) {
        				String pass = rs.getString("pass");
        				this.login(mail, pass);
        				return true;
        			}else {
        				return false;
        			}
        		} else {
        			return false;
        		}
        	}else {
        		return false;
        	}
		}catch (Exception ex) {
			return false;
		}
	}
	
	public void loadReleaseList() {
		try {
			ArrayList<Xips> lista_xips = new ArrayList<>();
			Boolean bucle_xip = true;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT * FROM xip WHERE doctor_mail = ? ";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	st.setString(1, this.getMail());
        	ResultSet rs = st.executeQuery();
        	
        	while (bucle_xip) {
        		if (rs.next()) {
        			int id_xip = rs.getInt("id");
            		Xips xip = new Xips();
            		xip.load(id_xip);
            		
            		lista_xips.add(xip);
        			
            	}else {
            		bucle_xip = false;
            	}
        	}
        	this.setReleaseArray(lista_xips);
		}catch (Exception ex) {
			
		}
	}
	
	public String getTable() {
		String respuesta = "<table>"+"\n";
		respuesta += "<tr>"+"\n"+
					 "<th>ID</th>"+"\n"+
					 "<th>Medicina</th>"+"\n"+
					 "<th>Paciente</th>"+"\n"+
					 "<th>Fecha</th>"+"\n"+
					 "<tr>"+"\n";
		
		LocalDate today = LocalDate.now();
		for (Xips xip : this.releaseArray) {
			if (xip.getDate().isEqual(today) || xip.getDate().isAfter(today)) {
				respuesta += "<tr>" + "\n" +
                        "<td>" + xip.getId() + "</td>" + "\n" +
                        "<td>" + xip.getMedicine().getName() + "</td>" + "\n" +
                        "<td>" + xip.getPatient().getName() + "</td>" + "\n" +
                        "<td>" + xip.getDate() + "</td>" + "\n" +
                        "<tr>" + "\n";
			}
			
		}
		
		respuesta += "</table>";
		return respuesta;
	}

}
