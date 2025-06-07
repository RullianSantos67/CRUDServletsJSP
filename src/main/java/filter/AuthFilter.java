package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // URLs que não exigem login
        if (path.startsWith("/login") || path.startsWith("/resources/") 
            || path.equals("/login.jsp") || path.equals("/css/") 
            || path.equals("/js/") ) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("usuarioLogado") != null);

        if (loggedIn) {
            chain.doFilter(req, res);  // deixa passar
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
