package core;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
public class DispacherServelet extends HttpServelet{

    public void affichage(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        String servletPath = request.getPathInfo();

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

    }
}