/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ua.tqs_project_80124;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author sagomes
 */
public class Constants {
    
    protected static final Map<String,Integer> consts = new HashMap<>();
    public static final int AVEIRO = 1010500;
    public static final int BEJA = 1020500;
    public static final int BRAGA = 1030300;
    public static final int CASTELO_BRANCO = 1050200;
    public static final int COIMBRA = 1060300;
    public static final int EVORA = 1070500;
    public static final int LISBOA = 1110600;
    public static final int PORTO = 1131200;

    private static Random random;
    
    Constants(){
        consts.put("Aveiro", AVEIRO);
        consts.put("Beja", BEJA);
        consts.put("Braga",BRAGA);
        consts.put("CasteloBranco",CASTELO_BRANCO);
        consts.put("Coimbra", COIMBRA);
        consts.put("Evora", EVORA);
        consts.put("Porto", PORTO);
        consts.put("Lisboa", LISBOA);
    }
   
    public static String getLocal(int id){
        for (Map.Entry<String, Integer> entry:consts.entrySet()){
            if (id == entry.getValue()){
                return entry.getKey();
            }
        }
        return "no city";
    }
    
    public static long generateId(){
        long hash = 7;
        random = new Random();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt();
            hash = hash*31 + r;
        }
        return hash;
    }
    
   
}
