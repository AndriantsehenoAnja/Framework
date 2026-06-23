package core;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import utils.ControllerUtils;
import utils.ClassMethod;
import utils.UrlMethod;

public class DispatcherServlet extends HttpServlet {
    List<String> listeControllers = new ArrayList<>();
    Map<UrlMethod, ClassMethod> listeInfoMethodeAndController = new HashMap<>();
    // Map<String,ClassMethod> listeInfoMethodeAndController = new HashMap<>();
    @Override
    public void init() throws ServletException {
        try{
            String controllersPackage = getServletConfig().getInitParameter("controller");
            for(Class clazz:utils.ControllerUtils.getControllers(controllersPackage)){
                listeControllers.add(clazz.getName());
            }
            // listeInfoMethodeAndController = utils.ControllerUtils.findAllMethodes(controllersPackage);
            listeInfoMethodeAndController = utils.ControllerUtils.findAllMethodesWithUrlMethod(controllersPackage);
        }catch (Exception e) {
            throw new ServletException("Erreur lors de l'initialisation du DispatcherServlet", e);
        }
    }
    public void affichage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getRequestURI();
        String nameApplication = request.getContextPath();
        String url = servletPath.substring(nameApplication.length());
        String method = request.getMethod();

        response.getWriter().println(
            "<!doctype html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <title>Liste des controllers et methodes</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Liste des controllers et methodes</h1>\n" +
            "<ul>"
        );

        ClassMethod infoMethodeAndController = utils.ControllerUtils.findClassByUrlMethod(listeInfoMethodeAndController, url, method);

        if (infoMethodeAndController == null) {
            for (ClassMethod info : listeInfoMethodeAndController.values()) {
                response.getWriter().println("<li>" + info.toString() + "</li>");
            }
        } else {
            response.getWriter().println("<li>" + infoMethodeAndController.toString() + "</li>");
        }

        response.getWriter().println("</ul></body></html>");
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
