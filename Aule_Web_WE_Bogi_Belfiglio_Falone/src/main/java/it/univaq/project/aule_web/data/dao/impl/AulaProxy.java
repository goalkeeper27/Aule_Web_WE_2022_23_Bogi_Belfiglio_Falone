/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.impl.AulaImpl;
import it.univaq.aule_web.data.model.Responsabile;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataItemProxy;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.ResponsabileDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Bogi
 */
public class AulaProxy extends AulaImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;
    private int responsabileKey;

    protected AulaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.responsabileKey = 0;
    }

    
    @Override
    public void setKey(Integer key){
        super.setKey(key);
        this.modified = true;
    }
    
    
    
    @Override
    public Responsabile getResponsabile(){
        if (super.getResponsabile() == null && this.responsabileKey > 0) {
            try {
                super.setResponsabile(((ResponsabileDAO) dataLayer.getDAO(Responsabile.class)).getResponsabile(responsabileKey));
            } catch (DataException ex) {
                Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return super.getResponsabile();
    }
    
    
    @Override 
    public void setResponsabile(Responsabile responsabile){
        super.setResponsabile(responsabile);
        if(responsabile != null)
            this.responsabileKey = responsabile.getKey();
        else
            this.responsabileKey = 0;
        
        this.modified = true;
    }
    
    @Override
    public void setNome(String nome){
        super.setNome(nome);
        this.modified = true;
    }
    
    @Override
    public void setLuogo(String luogo){
        super.setLuogo(luogo);
        this.modified = true;
    }
    
    @Override
    public void setEdificio(String edificio){
        super.setEdificio(edificio);
        this.modified = true;
    }
    
    @Override
    public void setPiano(int piano){
        super.setPiano(piano);
        this.modified = true;
    }

    @Override
    public void setCapienza(int capienza) {
        super.setCapienza(capienza);
        this.modified = true;
    }

    @Override
    public void setNumeroPreseElettriche(int numeroPreseElettriche) {
        super.setNumeroPreseElettriche(numeroPreseElettriche);
        this.modified = true;
    }

    @Override
    public void setNumeroPreseDiRete(int numeroPreseDiRete) {
        super.setNumeroPreseDiRete(numeroPreseDiRete);
        this.modified = true;
    }

    @Override
    public void setNoteGeneriche(String noteGeneriche) {
        super.setNoteGeneriche(noteGeneriche);
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
    
    public void setResponsabileKey(int key){
        this.responsabileKey = key;
        super.setResponsabile(null);
    }

}
