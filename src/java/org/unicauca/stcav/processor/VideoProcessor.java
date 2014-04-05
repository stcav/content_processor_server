/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.processor;

import java.io.IOException;
import org.unicauca.stcav.model.Layout;
import org.unicauca.stcav.persistence.BDMainController;
import org.unicauca.stcav.persistence.entities.Contenido;
import org.university.stcav.eva.model.MediaElement;
import org.university.stcav.eva.processor.Processor;

/**
 *
 * @author stcav
 */
public class VideoProcessor {

    public static void fix_to_time_content(Contenido c) throws InterruptedException, IOException {
        String contentName = c.getRutafuente();
        String posterName = changeExtension(contentName, "jpg");
        MediaElement me = Processor.get_mediaElement(contentName, Layout.PATHCONTENTREPOSITORY, false);
        System.out.println(me.getName());//***
        Processor.create_image_from_video(me, 2, Layout.PATHCONTENTPOSTER + posterName, Layout.PATHCONTENTREPOSITORY, true, false);
        c.setDuracion(Processor.do_TimeToSeconds(me.getDuration())*1.0);
        c.setRutascreenshot(posterName);
        BDMainController.modifyAdaptMetaInfContent(c);
    }
    
    private static String changeExtension(String file, String newFormat) {
        file = file.replace('.', ':');
        file = file.split(":")[0];
        System.out.println(file);
        file += "." + newFormat;
        System.out.println(" ******** " + file);
        return file;
    }
}
