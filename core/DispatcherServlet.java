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
    Map<UrlMethod, ClassMethod> listeInfoMethodeAndController = new HashMap<>();
    String pathSource;
    String extension;
    @Override
    public void init() throws ServletException {
       listeInfoMethodeAndController = (Map<UrlMethod, ClassMethod>) this.getServletContext().getAttribute("listeInfoMethodeAndController");
       pathSource = (String) this.getServletContext().getAttribute("pathSource");
       extension = (String) this.getServletContext().getAttribute("extension");
    }
    
    public void affichage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getRequestURI();
        String nameApplication = request.getContextPath();
        String url = servletPath.substring(nameApplication.length());
        String method = request.getMethod();

        ClassMethod infoMethodeAndController = utils.ControllerUtils.findClassByUrlMethod(listeInfoMethodeAndController, url, method);

        if (infoMethodeAndController == null) {
            for (ClassMethod info : listeInfoMethodeAndController.values()) {
                response.getWriter().println(info.toString());
            }
        } else {

            Object result = infoMethodeAndController.execute();
            utils.ControllerUtils.execute(result, pathSource, extension, request, response);
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
                || uri.endsWith(".svg")) {

            request.getServletContext()
                .getNamedDispatcher("default")
                .forward(request, response);
            return;
        }
        if(uri.endsWith(".jsp")) {
            request.getServletContext().
            getNamedDispatcher("jsp").
            forward(request, response);
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
                || uri.endsWith(".svg")) {

            request.getServletContext()
                .getNamedDispatcher("default")
                .forward(request, response);
            return;
        }
        if(uri.endsWith(".jsp")) {
            request.getServletContext().
            getNamedDispatcher("jsp").
            forward(request, response);
            return;
        }
        affichage(request, response);

    }
}
