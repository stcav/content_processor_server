/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.ws.servlets;

import flexjson.JSONSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.unicauca.stcav.model.BackAnswer;
import org.unicauca.stcav.persistence.BDMainController;

/**
 *
 * @author JoGa
 */
@WebServlet(name = "StateProcessorServlet", urlPatterns = {"/StateProcessorServlet"})
public class StateProcessorServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        Long idContentTemp = (Long) session.getAttribute("idContentTemp");

        boolean state = true;
        while (state) {
            try {
                if (BDMainController.getStateContent(idContentTemp).equals("completado")) {
                    state = false;
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StateProcessorServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        PrintWriter out = response.getWriter();
        BackAnswer ba = new BackAnswer(0, "ContentRepository/" + BDMainController.getSrcContent(idContentTemp), "<a href='nC.html'>Si desea editar su video pulse aqui</a>");
        JSONSerializer js = new JSONSerializer();
        String res_json = js.serialize(ba);
        try {
            System.out.println(" Esta es la ruta: "+ba.getDato());
            out.println(res_json);
        } finally {
            out.close();
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
}
