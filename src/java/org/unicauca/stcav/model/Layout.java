/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.unicauca.stcav.model;

/**
 *
 * @author johan
 */
public class Layout {
    public static String MAINPATH = "/home/johaned/javaspace/university/stcav/";
    public static String MAINDOMAINPATH = "/home/johaned/servers/stcav/glassfish-domain/docroot/";
    public static String PORT = "9107";
    public static String CONTENTPROCESSORSERVER = "ContentProcessorServer";
    public static String PATHUPLOADCONTENT = MAINPATH+"content_temp/";
    public static String PATHFULLCONTENT = "domain/docroot/ContentRepository/";
    public static String PATHSTACKVIDEOPROCESSOR= MAINPATH;
    public static String STACKVIDEOPROCESSOR= "stackvideoprocessor";
    public static String PATHMBEANDESCRIPTOR= MAINPATH+"gestv/InstrumentFolder/";
    public static String PATHCONTENTREPOSITORY= MAINDOMAINPATH+"ContentRepository/";
    public static String PATHCONTENTPOSTER= MAINDOMAINPATH+"PosterRepository/";

    //JMX Server Labels
    public static String JMXDOMAIN = "MediaServer";
    public static String REFERENCESESSION = "0:ReferenceSession";
    public static String CONTENTEDITING = "3:ContentEditing";
    public static String CONTENTRECORD = "1:ContentRecord";
    public static String CONTENTUPLOADING = "2:ContentUploading";
}