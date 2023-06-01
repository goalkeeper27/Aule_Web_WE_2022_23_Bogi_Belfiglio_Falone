/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.impl;

import it.univaq.project.aule_web.framework.data.DataItemImpl;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.EventoRicorrente;
import java.time.LocalDate;

/**
 *
 * @author Francesco Falone
 */
public class EventoRicorrenteImpl extends DataItemImpl<Integer> implements EventoRicorrente{
    
    private LocalDate dataEvento;
    private Evento evento;
    
    public EventoRicorrenteImpl(){
        super();
        dataEvento = null;
        evento = null;
    }

    @Override
    public LocalDate getDataEvento() {
        return dataEvento;
    }

    @Override
    public void setDataEvento(LocalDate data) {
       this.dataEvento = data;
    }
    
    @Override
    public Evento getEvento() {
        return evento;
    }

    @Override
    public void setEvento(Evento evento) {
       this.evento = evento;
    }

}
