package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.HeroRepository;
import repositories.IncidentRepository;

import java.io.IOException;
import java.util.ArrayList;

import beans.Hero;
import beans.Incident;
import beans.IncidentType;

/**
 * Servlet implementation class AssignHeroToIncident
 */
public class AssignHeroToIncident extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IncidentRepository incidentRepository;
	private final HeroRepository heroRepository;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignHeroToIncident() {
        super();
        incidentRepository = new IncidentRepository();
        heroRepository = new HeroRepository();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/assignHeroForm.jsp").forward(request, response);
	}

	/**
	 * Vérifier si le héro est bien adapté au type de l'incident
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("incidentId");
		int incidentId = param == null ? 0 : Integer.parseInt(param);
		param = request.getParameter("heroId");
		int heroId = param == null ? 0 : Integer.parseInt(param);
		Incident incident = incidentRepository.getIncidentById(incidentId);
		if (heroId == 0) {
			setOldFormParameters(request, response, incident, incidentId, "Veuillez sélectionner un héros");	
		}
		else if (incident == null) {
			setOldFormParameters(request, response, incident, incidentId, "Cet incident n'existe pas");			
		}
		else if (incidentRepository.updateIncidentHero(incidentId, heroId) == false)	{
			setOldFormParameters(request, response, incident, incidentId, "Erreur de base de données, veuillez essayez ultérieurement");
		}
		else
			response.sendRedirect("/Projet4/");
	}
	private void setOldFormParameters(HttpServletRequest request, HttpServletResponse response,
			Incident incident, int incidentId, String errorMessage) throws ServletException, IOException
	{
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("incidentId", incidentId);
		ArrayList<Hero> heroes = incident == null ? new ArrayList<>() : heroRepository.getAllHeroesForIncident(incident);
		request.setAttribute("heroes", heroes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/assignHeroToIncident.jsp").forward(request, response);
	}
}
