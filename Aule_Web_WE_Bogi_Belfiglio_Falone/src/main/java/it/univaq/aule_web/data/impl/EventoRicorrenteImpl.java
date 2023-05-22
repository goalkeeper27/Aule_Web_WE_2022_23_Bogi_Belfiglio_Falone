/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.aule_web.data.impl;

import it.univaq.aule_web.data.DataItemImpl;
import it.univaq.aule_web.data.model.Evento;
import it.univaq.aule_web.data.model.EventoRicorrente;
import java.time.LocalDate;

/**
 *
 * @author franc
 */
public class EventoRicorrenteImpl extends DataItemImpl<Integer> implements EventoRicorrente{
    LocalDate data_evento;
    public EventoRicorrenteImpl(){
        super();
        data_evento = null;
    }

    @Override
    public LocalDate getData_evento() {
        return data_evento;
    }

    @Override
    public void setData_evento(LocalDate data) {
       this.data_evento = data;
    }
}
