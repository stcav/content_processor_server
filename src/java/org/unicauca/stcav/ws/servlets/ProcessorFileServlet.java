/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.ws.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.unicauca.stcav.communication.RestClient;
import org.unicauca.stcav.model.BackAnswer;
import org.unicauca.stcav.model.BackProcessor;
import org.unicauca.stcav.model.Layout;
import org.unicauca.stcav.model.ProcessItem;
import org.unicauca.stcav.persistence.BDMainController;
import org.unicauca.stcav.processor.FileProcessor;

/**
 *
 * @author JoGa
 */
@WebServlet(name = "ProcessorFileServlet", urlPatterns = {"/ProcessorFileServlet"})
public class ProcessorFileServlet extends HttpServlet {

    private int alto = 720;
    private int ancho = 1280;

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
        BackAnswer ba = (BackAnswer) session.getAttribute("upFile");

        if (ba.getDescripcion().equals("PDF")) {
            processorPDFMethod(ba.getDato());
            //Apartar el espacio y la indexacion en la BD
            /*Long idUser = Long.parseLong((String) session.getAttribute("idUser"));
            BDMainController.createContent(idUser,"route");*/
        } else {
            BackProcessor bp = new BackProcessor();
            String newData = bp.changeExtension(ba.getDato().split(",")[0], "mp4");// el split es necesario debido a que el getDato contiene: nombre_video,fecha_creacion,duracion
            //Creando la primera referencia del contenido en la BD
            Long idContentTemp = BDMainController.createContent(Long.parseLong((String) session.getAttribute("idUser")), newData);
            session.setAttribute("idContentTemp", idContentTemp);
            System.out.println("*** Id del contenido almacenado temporalmente: " + newData + " Id: " + idContentTemp + " Id User: " + session.getAttribute("idUser"));
            processorVideoMethod(ba.getDato().split(",")[0], newData, idContentTemp, session);
            //Asociando el contenido a la comunidad
            BDMainController.communityContentAssociation(idContentTemp, Long.parseLong((String)session.getAttribute("idCom")));
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

    private void processorPDFMethod(String data) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void processorVideoMethod(String data, String newData, Long idContent, HttpSession session) throws IOException {
        if (data != null) {
            RestClient rc = new RestClient();
            ProcessItem pi = new ProcessItem(ProcessItem.CONTENIDO,ProcessItem.ADAPTTOHDH264,idContent,data,1);
            Gson gson = new Gson();
            String postData = gson.toJson(pi);
            System.out.println(postData);
            //Mandando al EVAPROCESSOR
            FileProcessor.do_file_write(Layout.PATHSTACKVIDEOPROCESSOR+Layout.STACKVIDEOPROCESSOR, postData);
            //Mandando al EVASERVER
            //rc.post("http://localhost:19985/EVAServer/MediaProcessorServer", "data="+postData);
        }
    }
}
