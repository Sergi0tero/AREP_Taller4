package org.controller;

import org.anotations.Component;
import org.anotations.RequestMapping;

@Component
public class Controller {
    @RequestMapping("/prueba1")
    public static String prueba(){
        return "Funciona prueba de controller";
    }

    @RequestMapping("/prueba2")
    public static String prueba2(){
        return "Funciona prueba 2 de controller";
    }
}
