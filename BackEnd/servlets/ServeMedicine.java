

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
 * Servlet implementation class ServeMedicines
 */
@WebServlet("/ServeMedicines")
public class ServeMedicines extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServeMedicines() {
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
			String sql_query = "SELECT * FROM medicine";
			PreparedStatement st = conn.prepareStatement(sql_query);
        	ResultSet rs = st.executeQuery();
        	
        	//Json
            json.append("[");
            boolean primera_linea = true;
        	
        	while (rs.next()) {
        		int id_med = rs.getInt("id");
    			String name_med = rs.getString("name");
    			double tmax_med = rs.getDouble("tmax");
    			double tmin_med = rs.getDouble("tmin");
    			if (!primera_linea) {
    				json.append(",");
    			}
    			primera_linea = false;
    			json.append("{\"id\":\"");
    	        json.append(Integer.toString(id_med));
    	        json.append("\",\"name\":\"");
    	        json.append(name_med);
    	        json.append("\",\"Tmax\":\"");
    	        json.append(Double.toString(tmax_med));
    	        json.append("\",\"Tmin\":\"");
    	        json.append(Double.toString(tmin_med));
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
