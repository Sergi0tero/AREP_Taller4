package org.spark;

import java.io.IOException;

/**
 * Interfaz funcional para metodos HTTP
 */
public interface RequestMethod{
    /**
     * Corre elmetodo HTTP
     * @param res creador de la respuesta HTTP
     * @return Respuesta HTTP
     * @throws IOException
     */
    String runMethod(Response res) throws IOException;
}