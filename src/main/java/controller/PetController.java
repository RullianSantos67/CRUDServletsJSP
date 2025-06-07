package controller;

import model.Pet;
import model.User;
import model.dao.DAOFactory;
import model.dao.PetDAO;
import model.dao.UserDAO;
import model.ModelException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/pets")
public class PetController extends HttpServlet {

    private PetDAO petDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        petDAO = DAOFactory.createDAO(PetDAO.class);
        userDAO = DAOFactory.createDAO(UserDAO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    showNewForm(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                default:
                    listPets(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        
        if (action == null) {
            saveOrUpdatePet(req, resp);
        } else if ("delete".equals(action)) {
            deletePet(req, resp);
        }
    }

    private void saveOrUpdatePet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String species = req.getParameter("species");
        String birthdateParam = req.getParameter("birthdate");
        String ownerIdParam = req.getParameter("ownerId");
        String weightParam = req.getParameter("weight");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("message", "Nome do pet é obrigatório");
            req.setAttribute("alertType", 0);
            loadOwners(req);
            req.getRequestDispatcher("/pet-form.jsp").forward(req, resp);
            return;
        }

        try {
            Pet pet = new Pet();
            if (idParam != null && !idParam.trim().isEmpty()) {
                pet.setId(Integer.parseInt(idParam));
            }
            pet.setName(name.trim());
            pet.setSpecies(species != null ? species.trim() : null);

            if (birthdateParam != null && !birthdateParam.trim().isEmpty()) {
                pet.setBirthdate(Date.valueOf(birthdateParam));
            }

            if (ownerIdParam != null && !ownerIdParam.trim().isEmpty()) {
                pet.setOwnerId(Integer.parseInt(ownerIdParam));
            }

            if (weightParam != null && !weightParam.trim().isEmpty()) {
                pet.setWeight(Float.parseFloat(weightParam));
            }

            if (pet.getId() == 0) {
                petDAO.insert(pet);
                req.getSession().setAttribute("message", "Pet cadastrado com sucesso");
                req.getSession().setAttribute("alertType", 1);
            } else {
                petDAO.update(pet);
                req.getSession().setAttribute("message", "Pet atualizado com sucesso");
                req.getSession().setAttribute("alertType", 1);
            }

            resp.sendRedirect(req.getContextPath() + "/pets");

        } catch (Exception e) {
            req.setAttribute("message", "Erro ao salvar pet: " + e.getMessage());
            req.setAttribute("alertType", 0);
            loadOwners(req);
            req.getRequestDispatcher("/pet-form.jsp").forward(req, resp);
        }
    }

    private void listPets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Pet> list = petDAO.getAll();
            req.setAttribute("listPets", list);
            loadOwners(req);
            req.getRequestDispatcher("/pets.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pet", null);
        loadOwners(req);
        req.getRequestDispatcher("/pet-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Pet existingPet = petDAO.getById(id);
            req.setAttribute("pet", existingPet);
            loadOwners(req);
            req.getRequestDispatcher("/pet-form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void deletePet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            petDAO.delete(id);
            req.getSession().setAttribute("message", "Pet excluído com sucesso");
            req.getSession().setAttribute("alertType", 1);
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erro ao excluir pet: " + e.getMessage());
            req.getSession().setAttribute("alertType", 0);
        }
        resp.sendRedirect(req.getContextPath() + "/pets");
    }

    private void loadOwners(HttpServletRequest req) {
        try {
            List<User> owners = userDAO.listAll();
            req.setAttribute("ownersList", owners);
        } catch (ModelException e) {
            req.setAttribute("error", "Erro ao carregar proprietários: " + e.getMessage());
        }
    }
}