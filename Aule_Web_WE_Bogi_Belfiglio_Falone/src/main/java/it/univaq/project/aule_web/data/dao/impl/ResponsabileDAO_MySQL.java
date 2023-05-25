/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.model.Responsabile;
import it.univaq.aule_web.framework.data.DAO;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.ResponsabileDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Stefano Belfiglio
 */
public class ResponsabileDAO_MySQL extends DAO implements ResponsabileDAO{
    
    private PreparedStatement sResponsabileByID;

    public ResponsabileDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    public void init() throws DataException{
        try{
            super.init();
            sResponsabileByID = connection.prepareStatement("SELECT * FROM responsabile WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {

        try {

            sResponsabileByID.close();

        } catch (SQLException ex) {
            throw new DataException("Error closing PrepareStatement");
        }
        super.destroy();
    }

    @Override
    public Responsabile getResponsabile(int responsabile_key) throws DataException {
        Responsabile r = null;
        try{
            sResponsabileByID.setInt(1, responsabile_key);
            try (ResultSet rs = sResponsabileByID.executeQuery()) {
                    if (rs.next()) r = createResponsabile(rs);   
                }
        } catch (SQLException ex) {
                throw new DataException("Unable to load article by ID", ex);
            }
        return r;
    }

    @Override
    public Responsabile createResponsabile() {
        return new ResponsabileProxy(getDataLayer());
    }
    
    private ResponsabileProxy createResponsabile(ResultSet rs) throws DataException{
        ResponsabileProxy r = (ResponsabileProxy) createResponsabile();
        try{
            r.setKey(rs.getInt("ID"));
            r.setNome(rs.getString("nome"));
            r.setCognome(rs.getString("cognome"));
            r.setCodiceFiscale(rs.getString("codice_fiscale"));
            r.setEmail(rs.getString("email"));
            r.setVersion(rs.getLong("versione"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create responsabile object form ResultSet", ex);
        }
        return r;
        
    }
    
}
