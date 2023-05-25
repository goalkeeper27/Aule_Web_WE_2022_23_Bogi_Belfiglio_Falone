/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.impl.CorsoImpl;
import it.univaq.aule_web.data.model.enumerable.TipoLaurea;
import it.univaq.aule_web.framework.data.DataItemProxy;
import it.univaq.aule_web.framework.data.DataLayer;

/**
 *
 * @author Alberto Bogi
 */
public class CorsoProxy extends CorsoImpl implements DataItemProxy {
    
    private boolean modified;
    protected DataLayer dataLayer;
    
    protected CorsoProxy(DataLayer d){
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
    public void setTipoLaurea(TipoLaurea tipoLaurea) {
        super.setTipoLaurea(tipoLaurea);
        this.modified = true;
    }

    @Override
    public void setCorsoDiLaurea(String corsoDiLaurea) {
        super.setCorsoDiLaurea(corsoDiLaurea);
        this.modified = true;
        
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setAnnoDiFrequentazione(int annoDiFrequentazione) {
        super.setAnnoDiFrequentazione(annoDiFrequentazione);
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
