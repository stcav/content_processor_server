package org.unicauca.stcav.ws.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
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
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.unicauca.stcav.jmx.mbean.MBeanServerController;
import org.unicauca.stcav.model.BackAnswer;
import org.unicauca.stcav.model.BackProcessor;
import org.unicauca.stcav.model.Layout;
import org.unicauca.stcav.model.TimeOfLife;

/**
 *
 * @author Johan Tique
 */
@WebServlet(name = "UploadFormatServlet", urlPatterns = {"/UploadFormatServlet"})
public class UploadFormatServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, MalformedObjectNameException {
        HttpSession session = request.getSession(); 
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        BackAnswer ba = new BackAnswer();
        TimeOfLife tol = new TimeOfLife();
        tol.set_home_time();
        int conteo=0;
        
        try {
            System.out.println("Comenzamos procesamiento ficheros");
            ba = procesaFicheros(request, out, session);

            if (ba.getEstado() == ba.SUCESSFUL) {
                /*Opercaion exitosa*/
                session.setAttribute("upFile", ba);
                response.sendRedirect("/nB2.html");
            } else {
                response.sendRedirect("/nB1e.html");
            }
        } finally {
            System.out.println("--> saving metric");
            // Setting the counts and time attributes changed of associated MBeanAttributeInfo
            // Attribute Counts
            conteo = (Integer) MBeanServerController.getAttribute(Layout.JMXDOMAIN, Layout.CONTENTPROCESSORSERVER, "ContentUploading", "ContentUploadingCounts");
            System.out.println("--> "+conteo);
            MBeanServerController.changeAttribute(Layout.JMXDOMAIN, Layout.CONTENTPROCESSORSERVER, "ContentUploading", "ContentUploadingCounts", String.valueOf(conteo+1));
            // Attribute Time
            MBeanServerController.changeAttribute(Layout.JMXDOMAIN, Layout.CONTENTPROCESSORSERVER, "ContentUploading", "ContentUploadingTime", String.valueOf(tol.get_tot_()));
            out.close();
        }

    }

    void depura(String cadena) {
        System.out.println("Consola: " + cadena);
    }

    public BackAnswer procesaFicheros(HttpServletRequest req, PrintWriter out,HttpSession session) {

        BackProcessor bp = new BackProcessor();
        BackAnswer ba = new BackAnswer();
        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(4096);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // Set overall request size constraint
            upload.setSizeMax(1024 * 1000000);
            // Parse the request
            List fileItems = upload.parseRequest(req);
            System.out.println("********request: "+req+" length: "+req.getContentLength());
            if (fileItems == null) {
                depura("La lista es nula");
                ba.setEstado(ba.ERROR);
                ba.setDato("1");
                ba.setDescripcion("El fichero no se cargo correctamente o Ud no sellecciono ningun archivo");
                return ba;
            }
            // Iteramos por cada fichero
            Iterator i = fileItems.iterator();
            FileItem actual = null;
            depura("estamos en la iteracion");
            while (i.hasNext()) {
                actual = (FileItem) i.next();
                byte[] b = actual.getName().getBytes("ISO-8859-1");
                Long currentTimeMillis = System.currentTimeMillis();
                String fileName = session.getAttribute("idUser")+"_"+currentTimeMillis+"_"+(new String(b,"ISO-8859-1")).replace('?', (char)((new Random()).nextInt(26) + 'a'));
                fileName= fileName.replace(" ", "_");
                fileName= fileName.replace(",", "_");
                fileName= fileName.replace("(", "_");
                fileName= fileName.replace(")", "_");
                fileName= fileName.replace("¡", "_");
                fileName= fileName.replace("!", "_");
                fileName= fileName.replace("?", "_");
                fileName= fileName.replace("¿", "_");
                //Revisamos la extension del archivo primer filtro del archivo subido
                if (!bp.isPresentation(fileName) && !bp.isVideo(fileName)) {
                    ba.setEstado(ba.ERROR);
                    ba.setDato("2");
                    ba.setDescripcion("Ud ha seleccionado un archivo con un formato no soportado");
                    return ba;
                }
                // construimos un objeto file para recuperar el trayecto completo
                File fichero = new File(fileName);
                depura("El nombre del fichero es " + fichero.getName());
                // nos quedamos solo con el nombre y descartamos el path
                fichero = new File(Layout.PATHUPLOADCONTENT + fichero.getName());
                // escribimos el fichero colgando del nuevo path
                actual.write(fichero);
                //Pasamos el fichero para referenciar la transcodificacion
                String dato = fichero.getName()+","+currentTimeMillis;
                ba.setEstado(ba.SUCESSFUL);
                ba.setDato(dato);
                //analizamos el formato del archivo
                if (bp.isPresentation(fileName)) {
                    ba.setDescripcion("PDF");
                } else {
                    ba.setDescripcion("VIDEO");
                }


            }

        } catch (Exception e) {
            depura("El error es: " + e.getMessage());
            ba.setEstado(ba.ERROR);
            ba.setDato("3");
            ba.setDescripcion(e.getMessage());
            return ba;
        }

        return ba;
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
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AttributeNotFoundException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReflectionException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AttributeNotFoundException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReflectionException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(UploadFormatServlet.class.getName()).log(Level.SEVERE, null, ex);
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
