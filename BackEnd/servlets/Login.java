

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
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
			System.out.println("Nuevo ingreso");
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String session_passcode = request.getParameter("session");
			String respuesta;
			
			Doctor doc = new Doctor();
			doc.login(email, password);
			
			int numero_min = 100000000;
			int numero_max = 999999999;
			Random rand = new Random();
			int session_number = numero_min + rand.nextInt(numero_max - numero_min + 1);
			
			
			if (session_passcode.equals(doc.getSession())) {
				respuesta = session_passcode;
			}else {
				if (doc.getMail() == null || doc.getMail().isEmpty()) {
					System.out.println("Contraseña erronea");
					respuesta = "";
					doc.setSession(respuesta);
				} else {
					respuesta = String.valueOf(session_number);
					doc.setSession(respuesta);
				}
			}
			
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
				String sql_query = "UPDATE doctor SET last_log = CURDATE(), session = ? WHERE mail = ?";
				PreparedStatement st = conn.prepareStatement(sql_query);
				st.setString(1, respuesta);
				st.setString(2, email);
				st.executeUpdate();
			}catch (Exception ex) {
				
			}
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(respuesta);
		}

}
