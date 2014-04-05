/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.ws.servlets;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import flexjson.JSONSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.unicauca.stcav.jmx.InstrumentingController;
import org.unicauca.stcav.jmx.mbean.MBeanServerController;
import org.unicauca.stcav.jmx.model.MyDynamicMBean;
import org.unicauca.stcav.model.BackAnswer;
import org.unicauca.stcav.model.Layout;
import org.unicauca.stcav.model.ProcessItem;
import org.unicauca.stcav.model.TimeOfLife;
import org.unicauca.stcav.persistence.BDMainController;
import org.unicauca.stcav.persistence.controller.exceptions.PreexistingEntityException;
import org.unicauca.stcav.persistence.entities.Contenido;
import org.unicauca.stcav.processor.FileProcessor;

/**
 *
 * @author Administrador
 */
@WebServlet(name = "ContentProcessorServlet", urlPatterns = {"/ContentProcessorServlet"})
public class ContentProcessorServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    InstrumentingController ic;
    TimeOfLife tol;
    public ContentProcessorServlet() {
        try {
            ic = InstrumentingController.getInstance();
        } catch (UnknownHostException ex) {
            Logger.getLogger(JMXProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, MalformedObjectNameException {
        //adding support to quantify time of response (for managment skill support)
        TimeOfLife tol = new TimeOfLife();
        tol.set_home_time();
        //getting InstrumentingController instance for to apply change of counts and time managment attr
        //MyDynamicMBean contentRecordMBean = ic.find_myDynamicMBean(Layout.CONTENTRECORD);
        
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=iso-8859-1");
        String jsonResponse = null;
        int conteo=0;
        String mbai="";
       
        switch (Integer.parseInt(request.getParameter("operation"))) {
            case 0:// Contenidos del usuario
                mbai="UserContent";                
                jsonResponse = getContentsByIdUser(Long.parseLong((String) session.getAttribute("idUser")));
                break;
            case 1:// Contenidos de la comunidad
                jsonResponse = getContentsByIdComunity(Long.parseLong((String) session.getAttribute("idCom")));
                break;
            case 2://contenido por ID
                mbai="ContentPlay";
                jsonResponse = getContentById(Long.parseLong(request.getParameter("id")));
                break;
            case 3://contenidos por busqueda asociados a una comunidad
                jsonResponse = getContentsBySearchInCommunity(Long.parseLong((String) session.getAttribute("idCom")), request.getParameter("token"));
                break;
            case 4://contenidos por busqueda asociados a un usuario
                mbai="ContentSearching";
                jsonResponse = getContentsBySearchFromUser(Long.parseLong((String) session.getAttribute("idUser")), request.getParameter("token"));
                break;
            case 5://Modificar el contenido (estado,screenshot,duracion) por ID
                jsonResponse = modifyAdaptMetaInfContent((String) request.getParameter("content"));
                break;
            case 6://Modifcar metainfo de contenido por el idcontenttemp
                jsonResponse = modifyMetaInfContent(request.getParameter("titulo"), request.getParameter("sinopsis"), request.getParameter("fuente"), session);
                break;
            case 7://establecer descriptor de edicion
                jsonResponse = modifyDescriptorContent(request.getParameter("descriptor"), session);
                break;
            case 8://modificar metainfo del contenido por id
                mbai="ContentUpgrade";
                jsonResponse = modifyMetaInfContentById(request.getParameter("titulo"), request.getParameter("sinopsis"), request.getParameter("fuente"), Long.parseLong(request.getParameter("id")));
                break;
            case 9:// Contenidos del usuario
                jsonResponse = getContentsByIdUser(Long.parseLong(request.getParameter("idUser").trim()));
                break;
            case 10:// Contenidos de la comunidad
                System.out.println(request.getParameter("idCom"));
                jsonResponse = getContentsByIdComunity(Long.parseLong((String) request.getParameter("idCom")));
                break;
            case 11:// Contenidos de la comunidad
                System.out.println(request.getParameter("idCom"));///
                try {
                    jsonResponse = rankingContent(Long.parseLong((String) request.getParameter("idUser")), Long.parseLong((String) request.getParameter("idContent")), request.getParameter("rank"));////
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                break;
        }
        PrintWriter out = response.getWriter();
        try {
            out.println(jsonResponse);
        } finally {
            //set to response endtime  
            tol.set_end_time();
            if(!mbai.equals("")){
                //Setting the counts and time attributes changed of associated MBeanAttributeInfo
                //Attribute Counts
                //conteo = (Integer) MBeanServerController.getAttribute(Layout.CONTENTPROCESSORSERVER, Layout.CONTENTRECORD.split(":")[1],"UserContentCounts");
                //MBeanServerController.changeAttribute(Layout.CONTENTPROCESSORSERVER, Layout.CONTENTRECORD.split(":")[1],mbai+"Counts", String.valueOf(conteo++));
                //Attribute Time
                //MBeanServerController.changeAttribute(Layout.CONTENTPROCESSORSERVER, Layout.CONTENTRECORD.split(":")[1],mbai+"Time", String.valueOf(tol.get_tot_()));    
            }
            out.close();
        }

    }

    private String rankingContent(Long idUser, Long idCont, String rank) throws PreexistingEntityException, Exception {

        //BDMainController.createRanking(idUser, idCont, rank);
        return "200:OK";
    }

    private String getContentsByIdUser(Long idUser) {
        List<Contenido> temp_cont = BDMainController.getContentUser(idUser);
        JSONSerializer js = new JSONSerializer();
        String res_json = js.serialize(temp_cont);
        System.out.println(res_json);
        return res_json;
    }

    private String getContentsByIdComunity(Long idComunity) {
        List<Contenido> temp_cont = BDMainController.getContentCommunity(idComunity);
        JSONSerializer js = new JSONSerializer();
        String res_json = js.serialize(temp_cont);
        System.out.println(res_json);
        return res_json;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MBeanException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AttributeNotFoundException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReflectionException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MBeanException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AttributeNotFoundException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReflectionException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getContentById(long id) {
        Contenido temp_cont = BDMainController.getContentById(id);
        JSONSerializer js = new JSONSerializer();
        String res_json = js.serialize(temp_cont);
        System.out.println(res_json);
        return res_json;
    }

    private String getContentsBySearchInCommunity(long idCom, String token) {
        Vector contents = BDMainController.getContentsBySearchInCommunity(idCom, token);
        if (contents == null) {
            return "-1";
        }
        JSONSerializer js = new JSONSerializer();
        String res_json = js.serialize(contents);
        System.out.println(res_json);
        return res_json;
    }

    private String getContentsBySearchFromUser(long idUser, String token) {
        System.out.println("********** " + idUser + " " + token);
        Vector contents = BDMainController.getContentsBySearchFromUser(idUser, token);
        if (contents == null) {
            return "-1";
        }
        JSONSerializer js = new JSONSerializer();
        String res_json = js.serialize(contents);
        System.out.println(res_json);
        return res_json;
    }

    private String modifyAdaptMetaInfContent(String content) {
        Gson gson = new Gson();
        Contenido c = gson.fromJson(content, Contenido.class);
        BDMainController.modifyAdaptMetaInfContent(c);
        return "200:OK";
    }

    private String modifyMetaInfContent(String titulo, String sinopsis, String fuente, HttpSession session) {
        BDMainController.modifyMetaInfContent((Long) session.getAttribute("idContentTemp"), titulo, sinopsis, fuente);
        return "OK";
    }

    private String modifyDescriptorContent(String descriptor, HttpSession session) {
        BDMainController.modifyStateContent((Long) session.getAttribute("idContentTemp"), "editando");
        BDMainController.modifyDescriptorContent((Long) session.getAttribute("idContentTemp"), descriptor);
        /*boolean updateTime = false;
         while(!updateTime){
         if(BDMainController.getDescriptorContent((Long) session.getAttribute("idContentTemp"))!=null){
         updateTime = true;
         System.out.println("*********** Ya esta listo el descriptor");
         }
         try {
         Thread.sleep(1000);
         } catch (InterruptedException ex) {
         Logger.getLogger(ContentProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/
        ProcessItem pi = new ProcessItem(ProcessItem.CONTENIDO, ProcessItem.DESCRIPTORREADER, (Long) session.getAttribute("idContentTemp"), "NaN", 1);
        Gson gson = new Gson();
        String postData = gson.toJson(pi);
        System.out.println(postData);
        FileProcessor.do_file_write(Layout.PATHSTACKVIDEOPROCESSOR + Layout.STACKVIDEOPROCESSOR, postData);
        return "redirect:/nB1.html";
    }

    private String modifyMetaInfContentById(String titulo, String sinopsis, String fuente, long id) {
        System.out.println("******************** t: " + titulo + "s: " + sinopsis + "f: " + fuente);
        BDMainController.modifyMetaInfContent(id, titulo, sinopsis, fuente);
        return "OK";
    }
}
