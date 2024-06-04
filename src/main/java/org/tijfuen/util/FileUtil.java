package org.tijfuen.util;

import java.io.File;

public class FileUtil {
    public static void crearDirectorioSiNoExiste(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void crearDirectorioSiNoExiste() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


}
