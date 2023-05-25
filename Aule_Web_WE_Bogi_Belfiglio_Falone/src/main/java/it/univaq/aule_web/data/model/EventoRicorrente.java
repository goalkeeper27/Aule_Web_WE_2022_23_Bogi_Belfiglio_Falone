/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.model;

import it.univaq.aule_web.framework.data.DataItem;
import java.time.LocalDate;

/**
 *
 * @author Francesco Falone
 */
public interface EventoRicorrente extends DataItem<Integer>{
    
    LocalDate getDataEvento();
    
    void setDataEvento(LocalDate data);
    
    Evento getEvento();
    
    void setEvento(Evento evento);
    
}
