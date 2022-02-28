package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.HeroRepository;
import repositories.IncidentTypeRepository;

import java.io.IOException;
import java.util.ArrayList;

import beans.GPSCoordinates;
import beans.Hero;
import beans.IncidentType;

/**
 * Servlet implementation class CreateHero
 */
public class CreateHero extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final HeroRepository heroRepository;
	private final IncidentTypeRepository incidentTypeRepository;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateHero() {
        super();
        heroRepository = new HeroRepository();
        incidentTypeRepository = new IncidentTypeRepository();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<IncidentType> incidentTypes = incidentTypeRepository.getAllIncidentTypes();
		request.setAttribute("incidentTypes", incidentTypes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/createHero.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<IncidentType> incidentTypes = incidentTypeRepository.getAllIncidentTypes();
		GPSCoordinates coord = null;
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipcode = request.getParameter("zipcode");
		String password = request.getParameter("password");
		String[] types = request.getParameterValues("incident_types");
		if (types == null || types.length < 1 || types.length > 3 || !areTypesValid(incidentTypes, types))
		{
			setOldFormParameters(request, response, name, email, phone, address, city, zipcode, types, "Sélectionnez entre 1 et 3 types d'incidents maitrisés.", incidentTypes);
			return;
		}
		else if (!heroRepository.isNewHero(name, address))
		{
			setOldFormParameters(request, response, name, email, phone, address, city, zipcode, types, "Ce héros existe déjà, veuillez essayer ultérieurement", incidentTypes);
			return;
		}
		else if ((coord = GPSCoordinates.getGpsCoordinatesByAddress(address, city, zipcode)) == null)
		{
			setOldFormParameters(request, response, name, email, phone, address, city, zipcode, types, "Erreur d'adresse, veuillez essayez ultérieurement", incidentTypes);
			return;
		}
		Hero hero = new Hero(name, email, phone, coord.getLatitude(), coord.getLongitude());
		if (!heroRepository.createHero(hero, password, types))
			setOldFormParameters(request, response, name, email, phone, address, city, zipcode, types, "Erreur de base de donnée, veuillez essayez ultérieurement", incidentTypes);			
		else
		{
			response.sendRedirect("/Projet4/");
		}
	}
	
	private void setOldFormParameters(HttpServletRequest request, HttpServletResponse response,
			String name, String email, String phone, String address, String city, String zipcode,
			String[] types,	String message, ArrayList<IncidentType> incidentTypes) throws ServletException, IOException
	{
		request.setAttribute("name", name);
		request.setAttribute("email", email);
		request.setAttribute("phone", phone);
		request.setAttribute("address", address);
		request.setAttribute("city", city);
		request.setAttribute("zipcode", zipcode);
		request.setAttribute("incidentTypeCheckboxes", types);
		request.setAttribute("errorMessage", message);
		request.setAttribute("incidentTypes", incidentTypes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/createHero.jsp").forward(request, response);
	}
	
	private Boolean areTypesValid(ArrayList<IncidentType> incidentTypes, String[] types)
	{
		for (String type : types)
		{
			if (!incidentTypes.stream().filter(o -> o.getId() == Integer.parseInt(type)).findFirst().isPresent())
			{
				return false;
			}
		}
		return true;
	}

}
