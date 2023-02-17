package org.Http;

import org.controller.RequestMapping;
import org.spark.RequestMethod;
import org.spark.Spark;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        Map<String, Method> requestMethods = new HashMap<>();

        //cargar clase con forname
        Class argTypes = null;
        try {
            argTypes = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Method[] methodArray = argTypes.getMethods();
        //extraer metodos con anotacion requestMapping
        for (Method m : methodArray){
            //extraer una instancia del metodo
            if (m.isAnnotationPresent(RequestMapping.class)){
                //extraer el valor del path
                //poner en la tabla el metodo con llave path
                requestMethods.put(m.getAnnotation(RequestMapping.class).value(), m);
            }
        }
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
            String request = "/simple";
            String reqVerb = "";
            while ((inputLine = in.readLine()) != null) {
                if (first_line) {
                    request = inputLine.split(" ")[1];
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
                if (request.equals("/")){
                    outputLine = htmlGetForm();
                } else if (requestMethods.containsKey(request)){
                    try {
                        outputLine = "HTTP/1.1 200 OK\r\n" +
                                "Content-type: text/html\r\n" +
                                "\r\n" + requestMethods.get(request).invoke(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }else if (request.startsWith("/apps/")){
                    outputLine = spark.get(request.substring(5), methods.get("GET"));
                    System.out.println("entro error");
                } else {
                    outputLine = spark.get("/error404.html", methods.get("GET"));
                }
            } else if (Objects.equals(reqVerb, "POST")){
                if (request.startsWith("/apps/")){
                    outputLine = spark.post(request.substring(7), methods.get("POST"));
                } else {
                    System.out.println("entro else del post");
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
