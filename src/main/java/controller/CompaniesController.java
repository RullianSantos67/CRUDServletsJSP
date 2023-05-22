package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.User;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/companies", "/company/form", "/company/insert"})
public class CompaniesController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/company/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-company.jsp");			
			break;
		}
		case "/crud-manager/company/update": {
			
			break;
		}
		default:
			listCompanies(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/companies.jsp");
		}
	}
	
	private void listCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		List<Company> companies = null;
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (companies != null)
			req.setAttribute("companies", companies);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/company/insert": {
			insertCompany(req, resp);			
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/companies");
	}

	private void insertCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyName = req.getParameter("name");
		String role = req.getParameter("role");
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		Company comp = new Company();
		comp.setName(companyName);
		comp.setRole(role);
		comp.setStart(ControllerUtil.formatDate(start));
		comp.setEnd(ControllerUtil.formatDate(end));
		comp.setUser(new User(userId));
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
	
		try {
			if (dao.save(comp)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + comp.getName() 
				+ "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + comp.getName()
				+ "' não pode ser salva.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
}
