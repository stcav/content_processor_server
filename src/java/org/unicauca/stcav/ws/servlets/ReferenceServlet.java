/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.unicauca.stcav.ws.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.unicauca.stcav.jmx.ManagementAttributeParser;
import org.unicauca.stcav.jmx.mbean.MBeanServerController;
import org.unicauca.stcav.model.Layout;
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
    throws ServletException, IOException, MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, MalformedObjectNameException {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(-1);
        String res = null;
        int conteo=0;
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
            System.out.println("--> saving metric");
            // Setting the counts and time attributes changed of associated MBeanAttributeInfo
            // Attribute Counts
            conteo = (Integer) MBeanServerController.getAttribute(Layout.JMXDOMAIN, Layout.CONTENTPROCESSORSERVER, "ReferenceSession", "CrossedReferenceCounts");
            System.out.println("--> "+conteo);
            MBeanServerController.changeAttribute(Layout.JMXDOMAIN, Layout.CONTENTPROCESSORSERVER, "ReferenceSession", "CrossedReferenceCounts", String.valueOf(conteo+1));
            // Attribute Time
            MBeanServerController.changeAttribute(Layout.JMXDOMAIN, Layout.CONTENTPROCESSORSERVER, "ReferenceSession", "CrossedReferenceTime", String.valueOf(tol.get_tot_())); 
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
        try {
            processRequest(request, response);
        } catch (MBeanException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AttributeNotFoundException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReflectionException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (MBeanException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AttributeNotFoundException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReflectionException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(ReferenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
