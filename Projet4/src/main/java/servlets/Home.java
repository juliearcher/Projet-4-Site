package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.HeroRepository;
import repositories.IncidentTypeRepository;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

import beans.Hero;

/**
 * Servlet implementation class Home
 */
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final HeroRepository heroRepository;
	private final IncidentTypeRepository incidentTypeRepository;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        heroRepository = new HeroRepository();
        incidentTypeRepository = new IncidentTypeRepository();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		ArrayList<Hero> heroes = heroRepository.getAllHeroes();
		for (Hero hero : heroes) {
			hero.setEmail(incidentTypeRepository.getHeroIncidentTypes(hero));
		}
		request.setAttribute("heroes", heroes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
