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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.unicauca.stcav.model.BackAnswer;
import org.unicauca.stcav.model.Layout;
import org.unicauca.stcav.model.ProcessItem;
import org.unicauca.stcav.persistence.BDMainController;
import org.unicauca.stcav.persistence.entities.Comunidad;
import org.unicauca.stcav.persistence.entities.Contenido;
import org.unicauca.stcav.processor.FileProcessor;

/**
 *
 * @author diego
 */
@WebServlet(name = "UserProcessorServlet", urlPatterns = {"/UserProcessorServlet"})
public class UserProcessorServlet extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=iso-8859-1");
        String jsonResponse = null;

        switch (Integer.parseInt(request.getParameter("operation"))) {

            case 0:// Id del usuario
                jsonResponse = getIdUser(request.getParameter("login").trim(), request.getParameter("pwd").trim());
                break;
            case 1:// lista comunidades asociadas a usuario
                jsonResponse = getUserComunities(Long.parseLong((String) request.getParameter("idUser")));

                break;
            default:
                break;
        }
        PrintWriter out = response.getWriter();
        try {
            out.println(jsonResponse);

        } finally {
            out.close();
        }

    }

    private String getIdUser(String login, String pwd) {
        BackAnswer ba = BDMainController.validateUser(login, pwd);
        String id = ba.getDato();
        System.out.println(id);
        return id;
    }

    private String getUserComunities(long idUser) {
        List<Comunidad> temp_cont = BDMainController.getUserComunities(idUser);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
}
