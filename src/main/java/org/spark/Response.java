package org.spark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Clase encargada de retornar una respuesta HTTP
 */
public class Response {
    private String type = "application/json";
    private final String service;
    private String verb;
    private String directory = "src/main/resources";
//    private ResponsesCache cache = ResponsesCache.getInstance();

    /**
     * Constructor
     * @param service nombre del archivo que se queire consultar
     * @param verb Metodo HTTP
     * @throws IOException
     */
    public Response(String service, String verb) throws IOException {
        this.service = service;
        this.verb = verb;
    }

    /**
     * Header de la respuesta HTTP
     * @return String con el header del archivo
     */
    public String getHeader() {
        if (Objects.equals(verb, "GET")){
            return "HTTP/1.1 200 OK\r\n" +
                    "Content-type: " + type +"\r\n" +
                    "\r\n";
        }
        else {
            return "HTTP/1.1 201 CREATED\r\n" +
                    "Content-type: " + type +"\r\n" +
                    "\r\n";
        }
    }


    /**
     * Respuesta del archivo
     * @return String con el cuerpo del archivo
     */
    public String getResponse() throws IOException {

        if (Objects.equals(verb, "GET")){
            Path file = Paths.get(directory + service);
            byte[] fileArray = Files.readAllBytes(file);
            return new String(fileArray);
        } else {
            String key = service.split("=")[0];
            String val = service.split("=")[1];
            return "{" + key + ":" + val + "}";
        }
    }

    /**
     * Cambio del tipo de la respuesta
     * @param type tipo que se quiere la respuesta HTTP
     */
    public void type(String type) {
        this.type = type;
    }

    /**
     * Cambio del directorio del que se quieren obtener los archivos estaticos
     * @param directory Directorio donde se ubican archivos estaticos
     */
    public void setDirectory(String directory){
        this.directory = directory;
    }
}
