package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.HeroRepository;
import repositories.IncidentRepository;
import repositories.IncidentTypeRepository;

import java.io.IOException;
import java.util.ArrayList;

import beans.Hero;
import beans.Incident;

/**
 * Servlet implementation class GetIncident
 */
public class GetIncident extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IncidentRepository incidentRepository;
	private final HeroRepository heroRepository;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetIncident() {
        super();
        incidentRepository = new IncidentRepository();
        heroRepository = new HeroRepository();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/getIncident.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Incident incident;
		String date = request.getParameter("date");
		String city = request.getParameter("city");
		String code = request.getParameter("code");
		if ((incident = incidentRepository.getIncidentByCode(date, city, code)) == null)
		{
			request.setAttribute("errorMessage", "Cet incident n'a pas été trouvé ou un héros y a déjà été assigné");
			this.getServletContext().getRequestDispatcher("/WEB-INF/getIncident.jsp").forward(request, response);
		}			
		else
		{
			ArrayList<Hero> heroes = heroRepository.getAllHeroesForIncident(incident);
			request.setAttribute("incidentId", incident.getId());
			request.setAttribute("code", incident.getCode());
			request.setAttribute("heroes", heroes);
			this.getServletContext().getRequestDispatcher("/WEB-INF/assignHeroToIncident.jsp").forward(request, response);
		}
	}

}
