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
        String pathSource = sce.getServletContext().getInitParameter("pathSource");
        String extension = sce.getServletContext().getInitParameter("extension");
        Map<UrlMethod, ClassMethod> listeInfoMethodeAndController = new HashMap<>();
        try {
            utils.ControllerUtils.findAllMethodesWithUrlMethod(controllersPackage, listeInfoMethodeAndController);
            sce.getServletContext().setAttribute("listeInfoMethodeAndController", listeInfoMethodeAndController);
            sce.getServletContext().setAttribute("pathSource", pathSource);
            sce.getServletContext().setAttribute("extension", extension);
        }
         catch(RuntimeException ee){
            System.out.println("erreur:"+ee.getMessage());
            throw ee;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Application démarrée !");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Code à exécuter lors de l'arrêt de l'application
        sce.getServletContext().removeAttribute("listeInfoMethodeAndController");
        System.out.println("Application arrêtée !");
    }

}
