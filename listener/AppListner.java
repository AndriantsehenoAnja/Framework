package listener;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import utils.ClassMethod;
import utils.UrlMethod;

import java.util.HashMap;
import java.util.Map;

@WebListener
public class AppListner implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String controllersPackage = sce.getServletContext().getInitParameter("controller");
        Map<UrlMethod, ClassMethod> listeInfoMethodeAndController = new HashMap<>();
        try {
            utils.ControllerUtils.findAllMethodesWithUrlMethod(controllersPackage, listeInfoMethodeAndController);
            sce.getServletContext().setAttribute("listeInfoMethodeAndController", listeInfoMethodeAndController);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Application démarrée !");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Code à exécuter lors de l'arrêt de l'application
        System.out.println("Application arrêtée !");
    }
    
}
