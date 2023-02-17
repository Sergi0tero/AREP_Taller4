package org.spark;


import java.io.IOException;

/**
 * Clase que simula spark
 */
public class Spark {

    /**
     * Funcion GET que retorna el archivo encontrado en la ruta pedida
     * @param path ruta a la que se quiere hacer GET
     * @param requestMethod Funcion lambda que implementa a RequestMethod
     * @return Respuesta HTTP
     * @throws IOException
     */
    public String get(String path, RequestMethod requestMethod) throws IOException {
        return requestMethod.runMethod(new Request(), new Response(path, "GET"));
    }

    /**
     * Funcion POST que retorna una respuesta HTTP con los elementos dados
     * @param path ruta a la que se hace el POST y contiene el QUERY con los elementos a añadir
     * @param requestMethod Funcion lambda que implementa a RequestMethod
     * @return Respuesta HTTP
     * @throws IOException
     */
    public String post(String path, RequestMethod requestMethod) throws IOException {
        String llave = path.split("=")[0];
        String valor = path.split("=")[1];
        return requestMethod.runMethod(new Request(), new Response(path, "POST"));
    }
}
