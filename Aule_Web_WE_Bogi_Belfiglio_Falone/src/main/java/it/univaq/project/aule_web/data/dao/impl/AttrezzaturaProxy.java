/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.impl.AttrezzaturaImpl;
import it.univaq.aule_web.data.model.Aula;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataItemProxy;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.AulaDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class AttrezzaturaProxy extends AttrezzaturaImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;

    private int aulaKey;

    protected AttrezzaturaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.aulaKey = 0;
        this.dataLayer = d;
    }
    
    @Override
    public void setKey(Integer key){
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public Aula getAula() {
        if (super.getAula() == null && this.aulaKey > 0) {
            try {
                super.setAula(((AulaDAO) dataLayer.getDAO(Aula.class)).getAula(aulaKey));
            } catch (DataException ex) {
                Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAula();
    }
    
    @Override
    public void setAula(Aula aula){
        super.setAula(aula);
        if(aula != null){
            this.aulaKey = aula.getKey();
        }
        else{
            this.aulaKey = 0;
        }
        
        this.modified = true;
    }
    
    @Override
    public void setNome(String nome){
        super.setNome(nome);
        this.modified = true;
    }
    
    @Override
    public void setNumeroDiSerie(int numero){
        super.setNumeroDiSerie(numero);
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
