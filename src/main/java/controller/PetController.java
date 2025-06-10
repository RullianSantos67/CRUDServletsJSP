package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Pet;
import model.User;
import model.dao.DAOFactory;
import model.dao.PetDAO;
import model.dao.UserDAO;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/pets", "/pet/form", "/pet/insert", "/pet/update", "/pet/delete"})
public class PetController extends HttpServlet {

    private PetDAO petDAO;
    private UserDAO userDAO;
    private static final String BASE_URL = "/crud-manager-public3";

    @Override
    public void init() {
        petDAO = DAOFactory.createDAO(PetDAO.class);
        userDAO = DAOFactory.createDAO(UserDAO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
            case BASE_URL + "/pet/form":
                showForm(req, resp, "insert");
                break;
            case BASE_URL + "/pet/update":
                showForm(req, resp, "update");
                break;
            case BASE_URL + "/pet/delete":
                deletePet(req, resp);
                break;
            default:
                listPets(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getRequestURI();

        switch (action) {
            case BASE_URL + "/pet/insert":
                savePet(req, resp, false);
                break;
            case BASE_URL + "/pet/update":
                savePet(req, resp, true);
                break;
            default:
                listPets(req, resp);
        }
    }

    private void listPets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Pet> pets = petDAO.getAll();
            req.setAttribute("listPets", pets);
            loadOwners(req);
            
            ControllerUtil.transferSessionMessagesToRequest(req);
            ControllerUtil.forward(req, resp, "/pets.jsp");
        } catch (ModelException | SQLException e) {
            ControllerUtil.errorMessage(req, "Erro ao carregar pets: " + e.getMessage());
            ControllerUtil.redirect(resp, req.getContextPath() + "/pets");
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, String action) 
        throws ServletException, IOException {
        
        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                Pet pet = petDAO.getById(id);
                req.setAttribute("pet", pet);
            } catch (ModelException | SQLException e) {
                ControllerUtil.errorMessage(req, "Pet não encontrado: " + e.getMessage());
            }
        }
        
        loadOwners(req);
        req.setAttribute("action", action);
        ControllerUtil.forward(req, resp, "/pet-form.jsp");
    }

    private void savePet(HttpServletRequest req, HttpServletResponse resp, boolean isUpdate) 
        throws ServletException, IOException {
        
        Pet pet = extractPetFromRequest(req);

        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            ControllerUtil.errorMessage(req, "Nome do pet é obrigatório");
            showFormWithError(req, resp, pet, isUpdate ? "update" : "insert");
            return;
        }

        try {
            if (isUpdate) {
                petDAO.update(pet);
                ControllerUtil.sucessMessage(req, "Pet atualizado com sucesso");
            } else {
             
                petDAO.insert(pet);
                ControllerUtil.sucessMessage(req, "Pet cadastrado com sucesso");
            }
        } catch (ModelException | SQLException e) {
            ControllerUtil.errorMessage(req, "Erro ao salvar pet: " + e.getMessage());
            showFormWithError(req, resp, pet, isUpdate ? "update" : "insert");
            return;
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/pets");
    }

    private void deletePet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
          
            ControllerUtil.errorMessage(req, "ID inválido para exclusão");
            ControllerUtil.redirect(resp, req.getContextPath() + "/pets");
            return;
        }
        int id = Integer.parseInt(idParam);
        try {
            petDAO.delete(id);
            ControllerUtil.sucessMessage(req, "Pet excluído com sucesso");
        } catch (Exception e) {
            ControllerUtil.errorMessage(req, "Erro ao excluir pet: " + e.getMessage());
        }
        ControllerUtil.redirect(resp, req.getContextPath() + "/pets");
    }


    private void loadOwners(HttpServletRequest req) {
        try {
            List<User> owners = userDAO.listAll();
            req.setAttribute("ownersList", owners);
        } catch (ModelException e) {
            ControllerUtil.errorMessage(req, "Erro ao carregar usuários: " + e.getMessage());
        }
    }

    private Pet extractPetFromRequest(HttpServletRequest req) {
        Pet pet = new Pet();
        
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            pet.setId(Integer.parseInt(idParam));
        }
        
        pet.setName(req.getParameter("name"));
        pet.setSpecies(req.getParameter("species"));
        
        String birthdate = req.getParameter("birthdate");
        if (birthdate != null && !birthdate.isEmpty()) {
            pet.setBirthdate(Date.valueOf(birthdate));
        }
        
        String ownerId = req.getParameter("ownerId");
        if (ownerId != null && !ownerId.isEmpty()) {
            pet.setOwnerId(Integer.parseInt(ownerId));
        }
        
        String weight = req.getParameter("weight");
        if (weight != null && !weight.isEmpty()) {
            pet.setWeight(Float.parseFloat(weight));
        }
        
        return pet;
    }

    private void showFormWithError(HttpServletRequest req, HttpServletResponse resp, 
                                 Pet pet, String action) 
        throws ServletException, IOException {
        
        req.setAttribute("pet", pet);
        req.setAttribute("action", action);
        loadOwners(req);
        ControllerUtil.forward(req, resp, "/pet-form.jsp");
    }
}