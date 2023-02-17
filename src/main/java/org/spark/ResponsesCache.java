package org.spark;

import java.io.IOException;
import java.net.ResponseCache;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResponsesCache {
    String[] predefined = {"/error404.html", "/prueba.css", "/prueba.html", "/prueba.js", "/prueba.jpg"};
    Map<String, String> responses = new HashMap<>();
    public static ResponsesCache instance;

    private ResponsesCache() throws IOException {
        for (String service: predefined) {
            Path file = Paths.get("src/main/resources" + service);
            System.out.println("file " + file);
            byte[] fileArray = Files.readAllBytes(file);
            responses.put(service, new String(fileArray));
        }
    };

    public static ResponsesCache getInstance() throws IOException {
        if (instance == null){
            instance = new ResponsesCache();
        }
        return instance;
    }

}
