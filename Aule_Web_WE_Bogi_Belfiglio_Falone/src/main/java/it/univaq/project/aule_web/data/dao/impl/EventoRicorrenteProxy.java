/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.impl.EventoRicorrenteImpl;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.EventoDAO;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Bogi
 */
public class EventoRicorrenteProxy extends EventoRicorrenteImpl implements DataItemProxy{
    
    private boolean modified;
    protected DataLayer dataLayer;
    
    private int eventoKey;
    
    protected EventoRicorrenteProxy(DataLayer d){
        super();
        this.modified = false;
        this.dataLayer = d;
        this.eventoKey = 0;
    }
    
    @Override
    public Evento getEvento(){
        if(super.getEvento() == null && this.eventoKey > 0){
            try{
                super.setEvento(((EventoDAO)dataLayer.getDAO(Evento.class)).getEvento(this.eventoKey));
            }
            catch(DataException ex){
                Logger.getLogger(EventoRicorrenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getEvento();
    }
    
    @Override
    public void setDataEvento(LocalDate data) {
       super.setDataEvento(data);
       this.modified = true;
    }

    @Override
    public void setEvento(Evento evento) {
       super.setEvento(evento);
       if(evento != null){
           this.eventoKey = evento.getKey();
       }
       else{
           this.eventoKey = 0;
       }
       this.modified = true;
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }
    
    public void setEventoKey(int key){
        this.eventoKey = key;
        super.setEvento(null);
    }
    
}
