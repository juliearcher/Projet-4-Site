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

import beans.GPSCoordinates;
import beans.Hero;
import beans.Incident;
import beans.IncidentType;

/**
 * Servlet implementation class CreateIncident
 */
public class CreateIncident extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IncidentTypeRepository incidentTypeRepository;
	private final IncidentRepository incidentRepository;
	private final HeroRepository heroRepository;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateIncident() {
        super();
        incidentTypeRepository = new IncidentTypeRepository();
        incidentRepository = new IncidentRepository();
        heroRepository = new HeroRepository();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<IncidentType> incidentTypes = incidentTypeRepository.getAllIncidentTypes();
		request.setAttribute("incidentTypes", incidentTypes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/createIncident.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<IncidentType> incidentTypes = incidentTypeRepository.getAllIncidentTypes();
		GPSCoordinates coord = null;
		
		String type = request.getParameter("type");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipcode = request.getParameter("zipcode");

		if (!incidentTypes.stream().filter(o -> o.getId() == Integer.parseInt(type)).findFirst().isPresent()) {
			setOldFormParameters(request, response, address, city, zipcode, type, "Type d'incident invalide", incidentTypes);
			return;
		}
		else if ((coord = GPSCoordinates.getGpsCoordinatesByAddress(address, city, zipcode)) == null)
		{
			setOldFormParameters(request, response, address, city, zipcode, type, "Erreur d'adresse, veuillez essayez ultérieurement", incidentTypes);
			return;
		}
		Incident incident = new Incident(Integer.parseInt(type), city, coord.getLatitude(), coord.getLongitude());
		if (!incidentRepository.createIncident(incident))
			setOldFormParameters(request, response, address, city, zipcode, type, "Erreur de base de donnée, veuillez essayez ultérieurement", incidentTypes);			
		else
		{
			ArrayList<Hero> heroes = heroRepository.getAllHeroesForIncident(incident);
			request.setAttribute("incidentId", incident.getId());
			request.setAttribute("code", incident.getCode());
			request.setAttribute("heroes", heroes);
			this.getServletContext().getRequestDispatcher("/WEB-INF/assignHeroToIncident.jsp").forward(request, response);
		}
	}

	private void setOldFormParameters(HttpServletRequest request, HttpServletResponse response,
			String address, String city, String zipcode,
			String type, String message, ArrayList<IncidentType> incidentTypes) throws ServletException, IOException
	{
		request.setAttribute("address", address);
		request.setAttribute("city", city);
		request.setAttribute("zipcode", zipcode);
		request.setAttribute("type", type);
		request.setAttribute("errorMessage", message);
		request.setAttribute("incidentTypes", incidentTypes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/createIncident.jsp").forward(request, response);
	}
}
