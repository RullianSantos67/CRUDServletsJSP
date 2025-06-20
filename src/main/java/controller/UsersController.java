package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(urlPatterns = {"/users", "/user/form", "/user/delete", "/user/insert", "/user/update"})
public class UsersController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
			case "/crud-manager-public3/user/form": {
				listUsers(req);
				req.setAttribute("action", "insert");
				ControllerUtil.forward(req, resp, "/form-user.jsp");
				break;
			}
			case "/crud-manager-public3/user/update": {
				listUsers(req);
				User user = loadUser(req);
				req.setAttribute("user", user);
				req.setAttribute("action", "update");
				ControllerUtil.forward(req, resp, "/form-user.jsp");
				break;
			}
			default:
				listUsers(req);
				ControllerUtil.transferSessionMessagesToRequest(req);
				ControllerUtil.forward(req, resp, "/users.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		if (action == null || action.equals("")) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}
		
		switch (action) {
			case "/crud-manager-public3/user/delete":
				deleteUser(req, resp);
				break;
			case "/crud-manager-public3/user/insert":
				insertUser(req, resp);
				break;
			case "/crud-manager-public3/user/update":
				updateUser(req, resp);
				break;
			default:
				System.out.println("URL inválida " + action);
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/users");
	}

	private User loadUser(HttpServletRequest req) {
		String userIdParameter = req.getParameter("userId");
		int userId = Integer.parseInt(userIdParameter);
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		try {
			User user = dao.findById(userId);
			if (user == null)
				throw new ModelException("Usuário não encontrado para alteração");
			return user;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		return null;
	}
	
	private void listUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		try {
			List<User> users = dao.listAll();
			req.setAttribute("users", users);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}
	
	private void insertUser(HttpServletRequest req, HttpServletResponse resp) {
		String userName = req.getParameter("name");
		String userGender = req.getParameter("gender");
		String userEmail = req.getParameter("mail");
		String userPassword = req.getParameter("password");

		User user = new User(0);
		user.setNome(userName);
		user.setSexo(userGender);
		user.setEmail(userEmail);
		user.setPassword(userPassword);

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		try {
			if (dao.save(user)) {
				ControllerUtil.sucessMessage(req, "Usuário '" + user.getNome() + "' salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Usuário '" + user.getNome() + "' não pode ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void updateUser(HttpServletRequest req, HttpServletResponse resp) {
		String userName = req.getParameter("name");
		String userGender = req.getParameter("gender");
		String userEmail = req.getParameter("mail");
		String userPassword = req.getParameter("password");

		User user = loadUser(req);
		user.setNome(userName);
		user.setSexo(userGender);
		user.setEmail(userEmail);
		user.setPassword(userPassword);

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		try {
			if (dao.update(user)) {
				ControllerUtil.sucessMessage(req, "Usuário '" + user.getNome() + "' atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Usuário '" + user.getNome() + "' não pode ser atualizado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
		String userIdParameter = req.getParameter("id");
		int userId = Integer.parseInt(userIdParameter);

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		try {
			User user = dao.findById(userId);
			if (user == null)
				throw new ModelException("Usuário não encontrado para deleção.");

			if (dao.delete(user)) {
				ControllerUtil.sucessMessage(req, "Usuário '" + user.getNome() + "' deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Usuário '" + user.getNome() + "' não pode ser deletado. Há dados relacionados.");
			}
		} catch (ModelException e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, "Não é possível deletar. Há dados relacionados.");
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
