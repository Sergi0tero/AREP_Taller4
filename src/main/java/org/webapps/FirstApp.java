package org.webapps;

import org.Http.HttpServer;
import org.spark.RequestMethod;
import org.spark.Response;
import org.spark.Spark;

import java.io.IOException;

/**
 * Main que inicia y obtiene la instancia del servidor HTTP y añade los nuevos servicios
 */
public class FirstApp {

    /**
     * Crea los lambda de cada tipo de peticion e inicia el servidor
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Spark spark = new Spark();
        HttpServer server = HttpServer.getInstance();
        RequestMethod get = (req, res) -> {
            try{
                res.type("text/html");
                String header = res.getHeader();
                String body = res.getResponse();
                return header + body;
            } catch (Exception e){
                Response rs = new Response("/error404.html", "GET");
                rs.type("text/html");
                String header = rs.getHeader();
                String body = rs.getResponse();
                return header + body;
            }
        };
        RequestMethod post = (req, res) -> {
            res.type("application/json");
            return res.getHeader() + res.getResponse();
        };
        server.createContext("GET", get);
        server.createContext("POST", post);
        server.run(args);
    }
}
