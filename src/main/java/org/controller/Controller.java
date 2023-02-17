package org.controller;

public class Controller {
    @RequestMapping("/prueba")
    public static String prueba(){
        return "Funciona prueba de controller";
    }
}
