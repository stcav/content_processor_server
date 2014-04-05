/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence;

import java.io.IOException;
import java.sql.Date;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.unicauca.stcav.model.BackAnswer;
import org.unicauca.stcav.persistence.controller.ComunidadJpaController;
import org.unicauca.stcav.persistence.controller.ComunidadhasContenidoJpaController;
import org.unicauca.stcav.persistence.controller.ContenidohasEtiquetasJpaController;
import org.unicauca.stcav.persistence.controller.ContenidoJpaController;
import org.unicauca.stcav.persistence.controller.EtiquetasJpaController;
//import org.unicauca.stcav.persistence.controller.RankingContenidoJpaController;
import org.unicauca.stcav.persistence.controller.UsuarioJpaController;
import org.unicauca.stcav.persistence.controller.UsuariohasComunidadJpaController;
import org.unicauca.stcav.persistence.controller.exceptions.NonexistentEntityException;
import org.unicauca.stcav.persistence.controller.exceptions.PreexistingEntityException;
import org.unicauca.stcav.persistence.controller.exceptions.RollbackFailureException;
import org.unicauca.stcav.persistence.entities.Comunidad;
import org.unicauca.stcav.persistence.entities.ComunidadhasContenido;
import org.unicauca.stcav.persistence.entities.Contenido;
import org.unicauca.stcav.persistence.entities.ContenidohasEtiquetas;
import org.unicauca.stcav.persistence.entities.ContenidohasEtiquetasPK;
/*import org.unicauca.stcav.persistence.entities.RankingContenido;
import org.unicauca.stcav.persistence.entities.RankingContenidoPK;*/
import org.unicauca.stcav.persistence.entities.Usuario;
import org.unicauca.stcav.persistence.entities.UsuariohasComunidad;
import org.unicauca.stcav.processor.VideoProcessor;

/**
 *
 * @author Johan Tique
 */
public class BDMainController {

    /**
     * ************************** Metodos asociados a la entidad usuario
     * ****************************
     */
    /*
     * Metodo para validar un usuario con su login y contraseña, si el usuario
     * es valido devuelve el Id del mismo dentro del dato de un BackAnswer, de
     * no ser asi este devuelve los respectivos mensajes de
     error
     */
    public static BackAnswer validateUser(String log, String pwd) {
        Usuario u = findUsuarioByLogin(log);
        BackAnswer ba = new BackAnswer();
        if (u != null) {
            if (u.getPwd().equals(pwd)) {
                ba.setEstado(BackAnswer.SUCESSFUL);
                ba.setDato(u.getIdUsuario().toString());
                ba.setDescripcion("Validacion correcta");
                return ba;
            } else {
                ba.setEstado(BackAnswer.ERROR);
                ba.setDato("2");
                ba.setDescripcion("Contraseña incorrecta");
                return ba;
            }
        } else {
            ba.setEstado(BackAnswer.ERROR);
            ba.setDato("1");
            ba.setDescripcion("Login incorrecto");
            return ba;
        }
    }
    /*
     * Busca un usuario por el login
     */

    public static Usuario findUsuarioByLogin(String log) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        //Obtengo el controlador para el usuario
        UsuarioJpaController ujc = new UsuarioJpaController(psm.get_utx(), psm.get_emf());
        List<Usuario> temp_list = ujc.findUsuarioEntities();
        Usuario u = new Usuario();
        // Obtenemos un Iterador y recorremos la lista.
        Iterator iter = temp_list.iterator();
        while (iter.hasNext()) {
            u = (Usuario) iter.next();
            if (u.getLogin().equals(log)) {
                ujc = null;
                temp_list = null;
                //System.gc();
                return u;
            }
        }
        return null;
    }

    public static Vector getUserComunities(long idUser) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        UsuariohasComunidadJpaController ucjc = new UsuariohasComunidadJpaController(psm.get_utx(), psm.get_emf());
        //si funciona devuelvo no solo los id sino las comunidades completas
        ComunidadJpaController c = new ComunidadJpaController(psm.get_utx(), psm.get_emf());
        List<UsuariohasComunidad> temp_list = ucjc.findUsuariohasComunidadEntities();

        UsuariohasComunidad uc = new UsuariohasComunidad();
        Vector comunity = new Vector();
        //Vector temp_list = new Vector();

        Iterator iter = temp_list.iterator();
        while (iter.hasNext()) {

            uc = (UsuariohasComunidad) iter.next();
            if (uc.getUsuariohasComunidadPK().getUsuarioidUsuario() == idUser) {
                comunity.add(c.findComunidad(uc.getUsuariohasComunidadPK().getComunidadidComunidad()));

            }
        }

        return comunity;
    }
//**************************Ranking**************************************

    /*public static void createRanking(long idUser, long idContent, String rank) throws PreexistingEntityException, Exception {
        RankingContenidoJpaController rjc = new RankingContenidoJpaController(psm.get_utx(), psm.get_emf());
        RankingContenido rc = new RankingContenido();
        RankingContenidoPK rcPK = new RankingContenidoPK(idUser, idContent);
        
       rc.setRankingContenidoPK(rcPK);
       rc.setRarnking(rank);
       rjc.create(rc);


    }*/

// ************************* Solo CONTENIDO *****************************
    @SuppressWarnings("UseOfObsoleteCollectionType")
    public static Vector getContentUser(Long idUser) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Vector contents = new Vector();
        Contenido c = new Contenido();
        List<Contenido> temp_cont = cjc.findContenidoEntities();
        Iterator iter = temp_cont.iterator();
        while (iter.hasNext()) {
            c = (Contenido) iter.next();
            if (c.getUsuarioidUsuario() == idUser && c.getEstado().equals("completado")) {
                if (c.getDuracion() == 0) {
                    try {
                        VideoProcessor.fix_to_time_content(c);
                        contents.add(c);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    contents.add(c);
                }
            }
        }
        temp_cont = null;
        System.gc();
        return contents;
    }

    public static Long createContent(long idUser, String route) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Contenido c = new Contenido();
        c.setUsuarioidUsuario(idUser);
        c.setRutafuente(route);
        c.setEstado("sin_procesar");
        try {
            cjc.create(c);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return findContentbySrc(c.getRutafuente());
    }

    public static Long findContentbySrc(String route) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        List<Contenido> temp_list = cjc.findContenidoEntities();
        Contenido c = new Contenido();
        // Obtenemos un Iterador y recorremos la lista.
        Iterator iter = temp_list.iterator();
        while (iter.hasNext()) {
            c = (Contenido) iter.next();
            if (c.getRutafuente().equals(route)) {
                cjc = null;
                temp_list = null;
                return c.getIdContenido();
            }
        }

        return null;
    }

    public static void modifyContent(Long idcontent, String titulo, String sinopsis, String fuente, long fecha_creacion, double duracion) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Contenido c = cjc.findContenido(idcontent);
        c.setTitulo(titulo);
        c.setSinopsis(sinopsis);
        c.setFuente(fuente);
        java.sql.Date date = new Date(fecha_creacion);
        c.setFechacreacion(date);
        c.setDuracion(duracion);
        c.setRutascreenshot("default.jpg");
        try {
            cjc.edit(c);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyAdaptMetaInfContent(Contenido c) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        try {
            cjc.edit(c);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyStateContent(Long idcontent, String estado) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Contenido c = cjc.findContenido(idcontent);
        c.setEstado(estado);
        try {
            cjc.edit(c);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyDescriptorContent(Long idContent, String descriptor) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Contenido c = cjc.findContenido(idContent);
        c.setDescriptor(descriptor);
        try {
            cjc.edit(c);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyMetaInfContent(Long idContent, String titulo, String sinopsis, String fuente) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Contenido c = cjc.findContenido(idContent);
        c.setTitulo(titulo);
        c.setSinopsis(sinopsis);
        c.setFuente(fuente);
        try {
            cjc.edit(c);
            System.out.println("--------- EDITADO ----------");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getStateContent(Long idcontent) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Contenido c = cjc.findContenido(idcontent);
        return c.getEstado();
    }

    public static String getSrcContent(Long idcontent) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        Contenido c = cjc.findContenido(idcontent);
        return c.getRutafuente();
    }

    public static Vector getContentCommunity(Long idCommunity) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ComunidadhasContenidoJpaController chcjc = new ComunidadhasContenidoJpaController(psm.get_utx(), psm.get_emf());
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());

        List<ComunidadhasContenido> chcTemp = chcjc.findComunidadhasContenidoEntities();

        Vector contents = new Vector();

        Iterator iter = chcTemp.iterator();
        ComunidadhasContenido chc = new ComunidadhasContenido();

        while (iter.hasNext()) {
            chc = (ComunidadhasContenido) iter.next();

            if (chc.getComunidadhasContenidoPK().getComunidadidComunidad() == idCommunity && cjc.findContenido(chc.getComunidadhasContenidoPK().getContenidoidContenido()).getEstado().equals("completado")) {
                contents.add(cjc.findContenido(chc.getComunidadhasContenidoPK().getContenidoidContenido()));
            }
        }
        chcTemp = null;
        System.gc();
        return contents;
    }

    public static Vector getContentsBySearchInCommunity(long idCom, String token) {
        List<Contenido> temp_cont = getContentCommunity(idCom);
        Vector new_list = new Vector();
        Iterator iter = temp_cont.iterator();
        Contenido temp = new Contenido();

        while (iter.hasNext()) {
            temp = (Contenido) iter.next();
            if (temp.getTitulo().toLowerCase().startsWith(token.toLowerCase())) {
                new_list.add(temp);
            }
        }

        return arrangeContentsVector(new_list);
    }

    public static Vector getContentsBySearchFromUser(long idUser, String token) {
        List<Contenido> temp_cont = getContentUser(idUser);
        Vector new_list = new Vector();
        Iterator iter = temp_cont.iterator();
        Contenido temp = new Contenido();

        while (iter.hasNext()) {
            temp = (Contenido) iter.next();
            System.out.println("------------------" + temp.getTitulo() + " " + token.toLowerCase());

            if (temp.getTitulo().toLowerCase().startsWith(token.toLowerCase())) {
                new_list.add(temp);
            }
        }

        return arrangeContentsVector(new_list);
    }

    public static Vector arrangeContentsVector(Vector contents) {
        Vector temp_vec = new Vector();
        if (contents.size() > 0) {
            temp_vec.add(contents.get(0));
            for (int i = 1; i < contents.size(); i++) {
                Contenido temp = (Contenido) contents.get(i);
                for (int j = temp_vec.size() - 1; j > 1; j--) {
                    Contenido temp_ = (Contenido) temp_vec.elementAt(j);
                    if (temp.getTitulo().compareToIgnoreCase(temp_.getTitulo()) <= 0) {
                        temp_vec.add(i, temp);
                    }
                }
            }
            System.out.println("Extension del vector arrange " + temp_vec.size());
            return contents;
        }
        return null;
    }

    public static boolean communityContentAssociation(Long idContent, Long idCom) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ComunidadhasContenidoJpaController chcjc = new ComunidadhasContenidoJpaController(psm.get_utx(), psm.get_emf());
        ComunidadhasContenido chc = new ComunidadhasContenido(idCom, idContent);
        try {
            chcjc.create(chc);
            return true;
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BDMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

//************************** Solo Etiquetas **********************************
    public static void createReferenceTags(String tagPacket, Long idContent) throws PreexistingEntityException, Exception {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        String tags[] = tagPacket.split(",");
        ContenidohasEtiquetasJpaController chejc = new ContenidohasEtiquetasJpaController(psm.get_utx(), psm.get_emf());

        ContenidohasEtiquetas che = new ContenidohasEtiquetas();
        ContenidohasEtiquetasPK chePK = new ContenidohasEtiquetasPK();

        chePK.setContenidoidContenido(idContent);

        System.out.println(tags.length);
        for (int i = 0; i < tags.length; i++) {
            chePK.setEtiquetasidEtiquetas(Long.parseLong(tags[i]));
            che.setContenidohasEtiquetasPK(chePK);
            chejc.create(che);
            System.out.println(tags[i]);
        }

    }

    public static String getTagsContent(Long idContent) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidohasEtiquetasJpaController chejc = new ContenidohasEtiquetasJpaController(psm.get_utx(), psm.get_emf());
        EtiquetasJpaController ejc = new EtiquetasJpaController(psm.get_utx(), psm.get_emf());
        List<ContenidohasEtiquetas> cheTemp = chejc.findContenidohasEtiquetasEntities();
        Iterator iter = cheTemp.iterator();
        ContenidohasEtiquetas che = new ContenidohasEtiquetas();
        String Tags = "";
        while (iter.hasNext()) {
            che = (ContenidohasEtiquetas) iter.next();
            if (che.getContenidohasEtiquetasPK().getContenidoidContenido() == idContent) {
                Tags += ejc.findEtiquetas(che.getContenidohasEtiquetasPK().getEtiquetasidEtiquetas()).getNombre() + ",";
            }
        }
        System.out.println(Tags);
        //Tags=Tags.substring(Tags.length()-2);
        return Tags;
    }

    public static Contenido getContentById(Long id) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        return cjc.findContenido(id);

    }

    public static String getDescriptorContent(Long aLong) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        ContenidoJpaController cjc = new ContenidoJpaController(psm.get_utx(), psm.get_emf());
        return cjc.findContenido(aLong).getDescriptor();
    }

    public static List<Contenido> getContentProcessingUser(Long idUsr) {
        Contenido c = new Contenido();
        List cs = new ArrayList();
        Iterator iter = getContentUser(idUsr).iterator();
        while (iter.hasNext()) {
            c = (Contenido) iter.next();
            if (c.getEstado().equals("sin_procesar") || c.getEstado().equals("procesando")) {
                cs.add(c);
            }
        }
        return cs;
    }
}
