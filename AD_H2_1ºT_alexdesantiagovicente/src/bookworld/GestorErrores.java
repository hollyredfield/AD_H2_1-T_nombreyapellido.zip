
// GestorErrores.java
package bookworld;

import java.io.*;
import java.time.LocalDateTime;

public class GestorErrores {
    private static final String LOG_FILE = "errores.log";

    public static void logError(String mensaje, Exception e) {
        String error = String.format("[%s] %s: %s%n", 
            LocalDateTime.now(), mensaje, e.getMessage());
        
        System.err.println(error);
        
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(error);
        } catch (IOException ex) {
            System.err.println("Error al escribir en el log: " + ex.getMessage());
        }
    }
}