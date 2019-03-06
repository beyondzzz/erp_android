package com.jl.jlapp.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fyf on 2017/9/25/025.
 */

public class MimeTypeUtils {

    private static final Map<String, String> MIME_MAP = new HashMap<>();

    static {
        MIME_MAP.put(".3gp", "video/3gpp");
        MIME_MAP.put(".apk", "application/vnd.android.package-archive");
        MIME_MAP.put(".asf", "video/x-ms-asf");
        MIME_MAP.put(".avi", "video/x-msvideo");
        MIME_MAP.put(".bin", "application/octet-stream");
        MIME_MAP.put(".bmp", "image/bmp");
        MIME_MAP.put(".c", "text/plain");
        MIME_MAP.put(".class", "application/octet-stream");
        MIME_MAP.put(".conf", "text/plain");
        MIME_MAP.put(".cpp", "text/plain");
        MIME_MAP.put(".doc", "application/msword");
        MIME_MAP.put(".exe", "application/octet-stream");
        MIME_MAP.put(".gif", "image/gif");
        MIME_MAP.put(".gtar", "application/x-gtar");
        MIME_MAP.put(".gz", "application/x-gzip");
        MIME_MAP.put(".h", "text/plain");
        MIME_MAP.put(".htm", "text/html");
        MIME_MAP.put(".html", "text/html");
        MIME_MAP.put(".jar", "application/java-archive");
        MIME_MAP.put(".java", "text/plain");
        MIME_MAP.put(".jpeg", "image/jpeg");
        MIME_MAP.put(".jpg", "image/jpeg");
        MIME_MAP.put(".js", "application/x-javascript");
        MIME_MAP.put(".log", "text/plain");
        MIME_MAP.put(".m3u", "audio/x-mpegurl");
        MIME_MAP.put(".m4a", "audio/mp4a-latm");
        MIME_MAP.put(".m4b", "audio/mp4a-latm");
        MIME_MAP.put(".m4p", "audio/mp4a-latm");
        MIME_MAP.put(".m4u", "video/vnd.mpegurl");
        MIME_MAP.put(".m4v", "video/x-m4v");
        MIME_MAP.put(".mov", "video/quicktime");
        MIME_MAP.put(".mp2", "audio/x-mpeg");
        MIME_MAP.put(".mp3", "audio/x-mpeg");
        MIME_MAP.put(".mp4", "video/mp4");
        MIME_MAP.put(".mpc", "application/vnd.mpohun.certificate");
        MIME_MAP.put(".mpe", "video/mpeg");
        MIME_MAP.put(".mpeg", "video/mpeg");
        MIME_MAP.put(".mpg", "video/mpeg");
        MIME_MAP.put(".mpg4", "video/mp4");
        MIME_MAP.put(".mpga", "audio/mpeg");
        MIME_MAP.put(".msg", "application/vnd.ms-outlook");
        MIME_MAP.put(".ogg", "audio/ogg");
        MIME_MAP.put(".pdf", "application/pdf");
        MIME_MAP.put(".png", "image/png");
        MIME_MAP.put(".pps", "application/vnd.ms-powerpoint");
        MIME_MAP.put(".ppt", "application/vnd.ms-powerpoint");
        MIME_MAP.put(".prop", "text/plain");
        MIME_MAP.put(".rar", "application/x-rar-compressed");
        MIME_MAP.put(".rc", "text/plain");
        MIME_MAP.put(".rmvb", "audio/x-pn-realaudio");
        MIME_MAP.put(".rtf", "application/rtf");
        MIME_MAP.put(".sh", "text/plain");
        MIME_MAP.put(".tar", "application/x-tar");
        MIME_MAP.put(".tgz", "application/x-compressed");
        MIME_MAP.put(".txt", "text/plain");
        MIME_MAP.put(".wav", "audio/x-wav");
        MIME_MAP.put(".wma", "audio/x-ms-wma");
        MIME_MAP.put(".wmv", "audio/x-ms-wmv");
        MIME_MAP.put(".wps", "application/vnd.ms-works");
        MIME_MAP.put(".xml", "text/xml");
        MIME_MAP.put(".xml", "text/plain");
        MIME_MAP.put(".z", "application/x-compress");
        MIME_MAP.put(".zip", "application/zip");
        MIME_MAP.put("", "*/*");
    }

    public static String getExtension(File file) {
        String suffix = "";
        String name = file.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            suffix = name.substring(pos);
        }
        return suffix.toLowerCase();
    }

    public static String getMimeType(File file) {
        String suffix = getExtension(file);
        return MIME_MAP.get(suffix);
    }

}
