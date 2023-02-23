package org.Http;

import org.spark.Request;
import org.spark.RequestMethod;
import org.spark.Spark;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Servidor HTTP encargado de tomar y procesar las peticiones
 */
public class HttpServer {

    private static HttpServer _instance = new HttpServer();
    private Map<String, RequestMethod> methods = new HashMap<>();

    /**
     * Constructor del servidor
     */
    private HttpServer (){
    }

    /**
     * Retorna la instancia del HTTPServer
     * @return instancia del HTTPServer
     */
    public static HttpServer getInstance() {
        return _instance;
    }

    /**
     * Conexion con el puerto e inicialización del html inicial
     * @param args
     * @throws IOException
     */
    public void run(String[] args) throws IOException {
        Spark spark = new Spark();
        ServerSocket serverSocket = null;
        String className = args[0];
        Request request = Request.getInstance();

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean first_line = true;
            String req = "/simple";
            String reqVerb = "";
            while ((inputLine = in.readLine()) != null) {
                if (first_line) {
                    req = inputLine.split(" ")[1];
                    reqVerb =  inputLine.split(" ")[0];
                    first_line = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            outputLine = htmlGetForm();
            if (Objects.equals(reqVerb, "GET")){
                if (req.equals("/")){
                    outputLine = htmlGetForm();
                } else if (request.hasMethod(req)){
                    try {
                        outputLine = "HTTP/1.1 200 OK\r\n" +
                                "Content-type: text/html\r\n" +
                                "\r\n" + request.getMethod(req).invoke(null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }else if (req.startsWith("/apps/")){
                    outputLine = spark.get(req.substring(5), methods.get("GET"));
                } else {
                    outputLine = spark.get("/error404.html", methods.get("GET"));
                }
            } else if (Objects.equals(reqVerb, "POST")){
                if (req.startsWith("/apps/")){
                    outputLine = spark.post(req.substring(7), methods.get("POST"));
                } else {
                    outputLine = spark.get("/error404.html", methods.get("GET"));
                }
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Añade funciones lambda de cada tipo de peticion HTTP
     * @param verb Tipo de peticion HTTP
     * @param method funcion lambda correspondiente al verbo
     */
    public void createContext(String verb, RequestMethod method){
        methods.put(verb, method);
    }

    /**
     * Creacion del HTML inicial que ve el usuario al cargar la pagina
     * @return HTML inicial
     */
    private String htmlGetForm(){
        return "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/html\r\n" +
                "\r\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>New API REST</h1>\n" +
                "        <h2>Available links:</h2>\n" +
                "        <ul> <li> /apps/prueba.html </li>  <li> /apps/prueba.js </li>  <li> /apps/prueba.jpg </li>  <li> /apps/prueba.css </li> </ul>\n" +
                "    </body>\n" +
                "</html>";

    }
}
