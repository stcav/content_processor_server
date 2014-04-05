/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.ws.servlets;

import flexjson.JSONSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.unicauca.stcav.persistence.PersistenceServiceManager;
import org.unicauca.stcav.persistence.controller.ContenidoJpaController;
import org.unicauca.stcav.persistence.controller.EtiquetasJpaController;
import org.unicauca.stcav.persistence.entities.Contenido;
import org.unicauca.stcav.persistence.entities.Etiquetas;

/**
 *
 * @author JoGa
 */
@WebServlet(name = "ObjectImportServlet", urlPatterns = {"/ObjectImportServlet"})
public class ObjectImportServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=iso-8859-1");
        String jsonResponse = null;
        switch (Integer.parseInt(request.getParameter("dependencia"))) {
            case 0:
                jsonResponse = exportObjectsOneToOne(request, session);
                break;
            case 1:
                jsonResponse = exportObjectsOneToMany(request);
                break;
            case 2:
                jsonResponse = exportObjectsManyToMany(request);
                break;
            default:
                break;
        }
        if (!jsonResponse.equals(null)) {
            PrintWriter out = response.getWriter();
            try {
                out.println(jsonResponse);
            } finally {
                out.close();
            }
        }


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String exportObjectsOneToOne(HttpServletRequest request, HttpSession session) {
        int mainObject = Integer.parseInt(request.getParameter("mainObject"));
        switch (mainObject) {
            case 1: {
                long idContent;
                try {
                    idContent = Long.parseLong(request.getParameter("mainId"));
                } catch (Exception e) {
                    idContent = (Long) session.getAttribute("idContentTemp");
                }
                PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
                ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
                Contenido c = cjc.findContenido(idContent);
                JSONSerializer js = new JSONSerializer();
                String res_json = js.serialize(c);
                System.out.println(res_json);
                return res_json;
            }

            case 3: {
                PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
                EtiquetasJpaController ejc = new EtiquetasJpaController(psm.get_utx(), psm.get_emf());
                List<Etiquetas> es = ejc.findEtiquetasEntities();
                JSONSerializer js = new JSONSerializer();
                String res_json = js.serialize(es);
                System.out.println(res_json);
                return res_json;
            }

            default:
                break;
        }
        return null;

    }

    private String exportObjectsOneToMany(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String exportObjectsManyToMany(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
