/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.impl.ResponsabileImpl;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;

/**
 *
 * @author Alberto Bogi
 */
public class ResponsabileProxy extends ResponsabileImpl implements DataItemProxy{
    
    private boolean modified;
    protected DataLayer dataLayer;
    
    protected ResponsabileProxy(DataLayer d){
        super();
        this.modified = false;
        this.dataLayer = d;
    }
    
    @Override
    public void setKey(Integer key){
        super.setKey(key);
        this.modified = true;
    }
    
     @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setCognome(String cognome) {
        super.setCognome(cognome);
        this.modified = true;
    }

    @Override
    public void setCodiceFiscale(String cf) {
       super.setCodiceFiscale(cf);
       this.modified = true;
    }

    @Override
    public void setEmail(String email) {
       super.setEmail(email);
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
    
}
