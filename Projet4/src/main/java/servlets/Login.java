package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repositories.HeroRepository;

import java.io.IOException;
import java.util.ArrayList;

import beans.Hero;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final HeroRepository heroRepository;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        heroRepository = new HeroRepository();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Hero user = heroRepository.getHero(username, password);
		if (user == null) {
			request.setAttribute("errorMessage", "Informations de connexion incorrectes");
			this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		response.sendRedirect(request.getContextPath() + "/");
	}

}
