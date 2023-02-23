package org.spark;

import org.anotations.Component;
import org.anotations.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Request {
    Map<String, Method> requestMethods = new HashMap<>();
    public static Request instance;

    public static Request getInstance() throws IOException {
        if (instance == null){
            instance = new Request();
        }
        return instance;
    }

    private Request(){
        String path = "src/main/java/org/controller/";
        ArrayList<Class> classes = new ArrayList<>();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (File file : files) {
            try {
                String filePath = file.getPath().substring(0, file.getPath().length() - 5);
                Class newClass = Class.forName(filePath.replace("\\", ".").replace("src.main.java.", ""));
                if(newClass.isAnnotationPresent(Component.class)){
                    System.out.println("entro " + newClass);
                    classes.add(newClass);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        for (Class c : classes){
            Method[] methodArray = c.getMethods();
            //extraer metodos con anotacion requestMapping
            for (Method m : methodArray){
                //extraer una instancia del metodo
                if (m.isAnnotationPresent(RequestMapping.class)){
                    //extraer el valor del path
                    //poner en la tabla el metodo con llave path
                    requestMethods.put(m.getAnnotation(RequestMapping.class).value(), m);
                }
            }
        }
    }

    public Method getMethod(String method){
        return requestMethods.get(method);
    }

    public boolean hasMethod(String method){
        return requestMethods.containsKey(method);
    }
}
