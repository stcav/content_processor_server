/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.model;

import java.util.Collection;

/**
 *
 * @author Johan Tique
 */
public class BackProcessor {

    private String[] formatVideo = {"avi", "mpg", "mov", "mp4", "r3d", "swf", "nuv", "avs", "txd", "gsm", "vqf", "dxa", "3gp", "m4a", "3g2", "mj2", "rl2", "ffm", "shn", "dts", "cdg", "vmd", "mlp", "mpc", "flv", "ass", "m4v", "vcl", "mpeg", "wmv"};
    private String formatPresentation = "pdf";

    public BackProcessor() {
    }

    public static void FormatSupport() {




    }

    public boolean isPresentation(String file) {
        file = file.replace('.', ':');
        file = file.split(":")[1];
        System.out.println(file);
        if (file.equals(formatPresentation)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isVideo(String file) {
        file = file.replace('.', ':');
        String segments[] = file.split(":");
        file = segments[segments.length-1];
        System.out.println(file);
        for (int i = 0; i < formatVideo.length; i++) {
            if (file.equals(formatVideo[i])) {
                return true;
            }
        }
        return false;
    }

    public String changeExtension(String file, String newFormat) {
        file = file.replace('.', ':');
        file = file.split(":")[0];
        System.out.println(file);
        file += "."+newFormat;
        System.out.println(" ******** " + file);
        return file;
    }
}
