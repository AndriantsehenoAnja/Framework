package listener;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import utils.ClassMethod;
import utils.UrlMethod;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
@WebListener
public class AppListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext springContext = WebApplicationContextUtils
        .getRequiredWebApplicationContext(sce.getServletContext());
        String controllersPackage = sce.getServletContext().getInitParameter("controller");
        String pathSource = sce.getServletContext().getInitParameter("pathSource");
        String extension = sce.getServletContext().getInitParameter("extension");
        Map<UrlMethod, ClassMethod> listeInfoMethodeAndController = new HashMap<>();
        if (springContext == null) {
        System.out.println("[AppListner] Attention : Le contexte Spring n'est pas encore prêt. Chargement différé ou vérification requise.");
        // Optionnel : Lever une exception explicite ou configurer l'ordre des listeners dans le web.xml
        throw new IllegalStateException("Le contexte Spring d'origine est requis mais introuvable au démarrage.");
    }
        try {
            utils.ControllerUtils.findAllMethodesWithUrlMethod(springContext, controllersPackage, listeInfoMethodeAndController);
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
