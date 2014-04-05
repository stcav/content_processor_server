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
import org.unicauca.stcav.model.BackAnswer;
import org.unicauca.stcav.persistence.BDMainController;
import org.unicauca.stcav.persistence.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author JoGa
 */
@WebServlet(name = "ContentLabeling", urlPatterns = {"/ContentLabeling"})
public class ContentLabeling extends HttpServlet {

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

        String Titulo = request.getParameter("Titulo");
        String Sinopsis = request.getParameter("Sinopsis");
        String Fuente = request.getParameter("Fuente");
        String Etiquetas_d = request.getParameter("Etiquetas_d");
        String Etiquetas_n = request.getParameter("Etiquetas_n");

        System.out.println("T: " + Titulo + " S: " + Sinopsis + " F: " + Fuente + " Ee: " + Etiquetas_d + " En: " + Etiquetas_n);
        PrintWriter out = response.getWriter();
        if (Titulo.equals("") || Sinopsis.equals("") || Fuente.equals("") || (Etiquetas_d.equals("") && Etiquetas_n.equals(""))) {
            try {
                out.println("Por favor llene todos los campos");
            } finally {
                out.close();
            }

        } else {
            Long idContentTemp = (Long) session.getAttribute("idContentTemp");
            BackAnswer ba = (BackAnswer) session.getAttribute("upFile");
            //Modicamos el contenido con el nuevo rotulo
            BDMainController.modifyContent(idContentTemp, Titulo, Sinopsis, Fuente, Long.parseLong(ba.getDato().split(",")[1]),0);

            //Añadimos las referencias a las etiquetas existentes
            if (!Etiquetas_d.equals("")) {
                try {
                    BDMainController.createReferenceTags(Etiquetas_d, idContentTemp);
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(ContentLabeling.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ContentLabeling.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //Añadimos las etiquetas nuevas referenciadas
            

            try {
                out.println("redirect:nB3.html");
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
}
