package core;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.util.ArrayList;
import java.util.List;
import utils.ControllerUtils;
// import 


public class DispatcherServlet extends HttpServlet {
    List<String> listeControllers = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        try{
            String controllersPackage = getServletConfig().getInitParameter("controller");
            for(Class clazz:utils.ControllerUtils.getControllers(controllersPackage)){
                listeControllers.add(clazz.getName());
            }
            // listeControllers = ;
        }catch (Exception e) {
            throw new ServletException("Erreur lors de l'initialisation du DispatcherServlet", e);
        }
    }
    public void affichage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getPathInfo();
        for (String controller : listeControllers) {
            response.getWriter().println("Controller: " + controller);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                    String uri = request.getRequestURI();

        if (uri.endsWith(".html")
                || uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")
                || uri.endsWith(".gif")
                || uri.endsWith(".ico")
                || uri.endsWith(".svg")
                || uri.endsWith(".jsp")) {

            request.getServletContext()
                .getNamedDispatcher("default")
                .forward(request, response);
            return;
        }
        affichage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                    String uri = request.getRequestURI();

        if (uri.endsWith(".html")
                || uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")
                || uri.endsWith(".gif")
                || uri.endsWith(".ico")
                || uri.endsWith(".svg")
                || uri.endsWith(".jsp")) {

            request.getServletContext()
                .getNamedDispatcher("default")
                .forward(request, response);
            return;
        }
        affichage(request, response);

    }
}
