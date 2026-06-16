package core;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class DispatcherServlet extends HttpServlet {

    public void affichage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getPathInfo();

        response.getWriter().println("Servlet Path: " + servletPath);
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
