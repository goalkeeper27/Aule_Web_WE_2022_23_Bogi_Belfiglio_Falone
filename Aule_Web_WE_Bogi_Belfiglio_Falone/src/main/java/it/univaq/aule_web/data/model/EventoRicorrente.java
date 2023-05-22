/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.model;

import it.univaq.aule_web.data.DataItem;
import java.time.LocalDate;

/**
 *
 * @author franc
 */
public interface EventoRicorrente extends DataItem<Integer>{
    LocalDate getData_evento();
    void setData_evento(LocalDate data);
    
}
