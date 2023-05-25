/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.impl.GruppoImpl;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataItemProxy;
import it.univaq.aule_web.framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Bogi
 */
public class GruppoProxy extends GruppoImpl implements DataItemProxy {

    private boolean modified;
    protected DataLayer dataLayer;


    protected GruppoProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
    }

    
    
    @Override
    public void setTipoGruppo(String tipoGruppo) {
        super.setTipoGruppo(tipoGruppo);
        this.modified = true;
    }
    

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }


    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
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

}
