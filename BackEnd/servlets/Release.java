

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Release
 */
@WebServlet("/Release")
public class Release extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Release() {
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
		int idXip = Integer.parseInt(request.getParameter("idXip"));
		String mailP = request.getParameter("mailP");
		int idMed = Integer.parseInt(request.getParameter("idMed"));
		LocalDate date = LocalDate.parse(request.getParameter("date"));
		String respuesta = "Error al crear xip";
		
		System.out.println("Ha llegao");
		System.out.println(mail+" "+idXip+" "+mailP+" "+idMed+" "+date);
		
		Doctor doc = new Doctor();
		doc.isLogged(mail, session);
		doc.loadReleaseList();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia", "root", "");
			String sql_query = "INSERT INTO xip (id, doctor_mail, id_medicine, id_patient, date) VALUES ( ? , ? , ? , ? , ? );";
			PreparedStatement st = conn.prepareStatement(sql_query);
			System.out.println("Ha llegao");
			st.setInt(1, idXip);
			st.setString(2, mail);
			st.setInt(3, idMed);
			st.setString(4, mailP);
			st.setDate(5, java.sql.Date.valueOf(date));
			st.executeUpdate();
			
			Xips xip = new Xips();
			xip.load(idXip);
			
			respuesta = "Xip: "+xip.getId()+" Creado por: "+doc.getName()+" Con la medicina: "+xip.getMedicine().getName()+" Para el paciente: "+xip.getPatient().getName()+" Con la fecha de caducidad: "+xip.getDate()+" Ha sido creado con exito";
		}catch (Exception ex) {
			
		}
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(respuesta);
		
	}

}
