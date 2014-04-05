/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.unicauca.stcav.ws.servlets;

import flexjson.JSONSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import org.unicauca.stcav.persistence.entities.Contenido;

/**
 *
 * @author stcav
 */
@WebServlet(name="ProcessingContentServlet", urlPatterns={"/ProcessingContentServlet"})
public class ProcessingContentServlet extends HttpServlet {
   
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

        Long idUsr = Long.parseLong((String)session.getAttribute("idUser"));
        BackAnswer ba = new BackAnswer();
        List<Contenido> cs = BDMainController.getContentProcessingUser(idUsr);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessingContentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(cs.size()>0){
            ba.setEstado(0);
            ba.setDescripcion("Hay "+cs.size()+" contenido(s) editandose en este momento...");
        }else{
            ba.setEstado(1);
        }
        JSONSerializer js = new JSONSerializer();
        String res_json = js.serialize(ba);
        System.out.println(res_json);


        PrintWriter out = response.getWriter();
        try {
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
