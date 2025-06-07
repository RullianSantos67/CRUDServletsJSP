package controller;

import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = DAOFactory.createDAO(UserDAO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user;
        try {
            user = userDAO.findByEmailAndPassword(email, password);
        } catch (ModelException e) {
            throw new ServletException(e);
        }

        if (user != null) {
            // usa o mesmo nome que o menu verifica
            req.getSession().setAttribute("usuarioLogado", user);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute("errorMessage", "Credenciais inválidas");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
