/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.model.Attrezzatura;
import it.univaq.aule_web.data.model.Aula;
import it.univaq.aule_web.framework.data.DAO;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.AttrezzaturaDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stefano Belfiglio
 */
public class AttrezzaturaDAO_MySQL extends DAO implements AttrezzaturaDAO{
    
    private PreparedStatement sAttrezzaturaByAula;
    private PreparedStatement sAttrezzaturaByID;

    public AttrezzaturaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException{
        try{
            super.init();
            sAttrezzaturaByAula = this.connection.prepareStatement("SELECT ID AS AttrezzaturaID FROM attrezzatura WHERE ID_aula=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing aule_web data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {

        try {
           sAttrezzaturaByAula.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing PreparedStatement");
        }
        super.destroy();
    }

    @Override
    public Attrezzatura createAttrezzatura() {
        return new AttrezzaturaProxy(getDataLayer());
    }
    
    private AttrezzaturaProxy createAttrezzatura(ResultSet rs) throws DataException{
        AttrezzaturaProxy a = (AttrezzaturaProxy) createAttrezzatura();
        try{
            a.setKey(rs.getInt("ID"));
            a.setAulaKey(rs.getInt("ID_aula"));
            a.setNome(rs.getString("nome"));
            a.setNumeroDiSerie(rs.getInt("numero_di_serie"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create responsabile object form ResultSet", ex);
        }
        return a;
    }
    
    @Override
    public Attrezzatura getAttrezzatura(int attrezzatura_key) throws DataException {
        Attrezzatura a = null;
        try{
            sAttrezzaturaByID.setInt(1, attrezzatura_key);
            
            try (ResultSet rs = sAttrezzaturaByID.executeQuery()) {
                if (rs.next()) a = createAttrezzatura(rs);   
            }
        }catch (SQLException ex) {
            throw new DataException("Unable to load attrezzatura by ID", ex);
        }
        return a;
    }

    @Override
    public List<Attrezzatura> getAttrezzatureByAula(Aula aula) throws DataException {
        List<Attrezzatura> result = new ArrayList();
        
        try{
            sAttrezzaturaByAula.setInt(1, aula.getKey());
            
            try (ResultSet rs = sAttrezzaturaByID.executeQuery()) {
                if (rs.next()) result.add((Attrezzatura) getAttrezzatura(rs.getInt("ID")));  
            }
        }catch (SQLException ex) {
            throw new DataException("Unable to load attrezzatura by ID", ex);
        }
        return result;
    }




    
    
}
