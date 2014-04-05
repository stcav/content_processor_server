/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.unicauca.stcav.ws.servlets;

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
import org.unicauca.stcav.model.TimeOfLife;

/**
 *
 * @author johan
 */
@WebServlet(name="ReferenceServlet", urlPatterns={"/ReferenceServlet"})
public class ReferenceServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(-1);
        String res = null;
        TimeOfLife tol = new TimeOfLife();
        tol.set_home_time();

        switch(Integer.parseInt(request.getParameter("operation"))){
            case 1:{
                System.out.println("----> "+request.getParameter("userID")+" <----");
                session.setAttribute("idUser", request.getParameter("userID").trim());
                System.out.println("Referencia a usuario con ID: *"+(String)session.getAttribute("idUser")+"*");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
            }
            case 2:{
                session.setAttribute("idCom", request.getParameter("comID").trim());
                System.out.println("CPS: Referencia a comunidad con ID: *"+(String)session.getAttribute("idCom")+"*");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
            }
            case 3:{
                session.setAttribute("idUser", request.getParameter("userID").trim());
                session.setAttribute("idCom", request.getParameter("comID").trim());
                System.out.println("Referencia a usuario con ID: *"+(String)session.getAttribute("idUser")+"*");
                System.out.println("Referencia a comunidad con ID: *"+(String)session.getAttribute("idCom")+"*");
                break;
            }
            default:{
                break;
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            tol.set_end_time();
            out.println(res);
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
