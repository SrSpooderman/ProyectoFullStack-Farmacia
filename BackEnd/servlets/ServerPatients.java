

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServPatients
 */
@WebServlet("/ServePatients")
public class ServePatients extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServePatients() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		StringBuilder json = new StringBuilder();
		
		Doctor doc = new Doctor();
		doc.isLogged(mail, session);
		doc.loadReleaseList();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "SELECT p.mail, p.name FROM patient p";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	ResultSet rs = st.executeQuery();
        	
        	//Json
            json.append("[");
            boolean primera_linea = true;
        	
        	while (rs.next()) {
        		String p_mail = rs.getString("p.mail");
    			String p_name = rs.getString("p.name");
    			if (!primera_linea) {
    				json.append(",");
    			}
    			primera_linea = false;
    			json.append("{\"mail\":\"");
    	        json.append(p_mail);
    	        json.append("\",\"name\":\"");
    	        json.append(p_name);
    	        json.append("\"}");
        	}
        	json.append("]");
        	if (primera_linea) {
                json.setLength(0);
            }
        	
		}catch (Exception ex) {
			
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(json.toString());
		
	}

}
