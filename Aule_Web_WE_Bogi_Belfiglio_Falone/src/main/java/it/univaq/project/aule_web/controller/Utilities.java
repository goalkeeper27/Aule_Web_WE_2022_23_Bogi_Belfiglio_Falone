/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Giuseppe Della Penna
 */
public class Utilities {
    public static List getHeaderList(HttpServletRequest request) {
        List<Pair> headers = new ArrayList();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.add(new Pair<>(name, (String) request.getHeader(name)));
        }
        return headers;
    }
    
    public static <K,V> List getListOfPairsByMap(HashMap<K, V> map){
        List<Pair> list = new ArrayList();
        for(HashMap.Entry<K, V> entry: map.entrySet()){
            list.add(new Pair(entry.getKey(), entry.getValue()));
        }
        return list;
    }
}
