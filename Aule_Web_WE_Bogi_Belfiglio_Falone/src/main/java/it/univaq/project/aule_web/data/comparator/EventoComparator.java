/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.comparator;

import it.univaq.project.aule_web.data.model.Evento;
import java.util.Comparator;

/**
 *
 * @author Alberto Bogi
 */
public class EventoComparator implements Comparator<Evento>{

    @Override
    public int compare(Evento e1, Evento e2) {
        if(e1.getDataEvento().isBefore(e2.getDataEvento()))
            return -1;
        else if(e1.getDataEvento().isAfter(e2.getDataEvento()))
            return 1;
        else if(e1.getOraInizio().isBefore(e2.getOraInizio()))
            return -1;
        else if(e1.getOraInizio().isAfter(e2.getOraFine()))
            return 1;
        else
            return 0;
    }
       

    
    
}
