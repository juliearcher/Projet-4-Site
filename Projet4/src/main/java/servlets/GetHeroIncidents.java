package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repositories.IncidentRepository;

import java.io.IOException;
import java.util.ArrayList;

import beans.Hero;
import beans.Incident;

/**
 * Servlet implementation class GetHeroIncidents
 */
public class GetHeroIncidents extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IncidentRepository incidentRepository;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHeroIncidents() {
        super();
        incidentRepository = new IncidentRepository();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Hero user = (Hero) session.getAttribute("user");
		if (user == null)
			request.setAttribute("errorMessage", "Merci de vous connecter pour voir la liste de vos incidents.");
		else {
			ArrayList<Incident> incidents = incidentRepository.getHeroIncidents(user);
			System.out.println(incidents.size());
			request.setAttribute("incidents", incidents);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/heroIncidentsList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
