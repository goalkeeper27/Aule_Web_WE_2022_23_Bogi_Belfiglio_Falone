/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.model.Amministratore;
import it.univaq.aule_web.framework.data.DAO;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.AmministratoreDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Stefano Belfiglio
 */
public class AmministratoreDAO_MySQL extends DAO implements AmministratoreDAO {
    
    private PreparedStatement sAmministratoreByUsernameAndPassword;

    public AmministratoreDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException{
        try{
            super.init();
            sAmministratoreByUsernameAndPassword = this.connection.prepareStatement("SELECT * FROM amministratore WHERE username = ? AND password = ?");
        }catch (SQLException ex) {
            throw new DataException("Error initializing aule_web data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {

        try {
           sAmministratoreByUsernameAndPassword.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing PreparedStatement");
        }
        super.destroy();
    }
    
    @Override
    public Amministratore createAmministratore() {
        return new AmministratoreProxy(getDataLayer());
    }
    
    private AmministratoreProxy createAmministratore(ResultSet rs) throws DataException{
        AmministratoreProxy a = (AmministratoreProxy) createAmministratore();
        try{
            a.setKey(rs.getInt("ID"));
            a.setUsername(rs.getString("username"));
            a.setPassword(rs.getString("password"));  
        } catch (SQLException ex) {
            throw new DataException("Unable to create responsabile object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public Amministratore getAmministratoreByUsernameAndPassword(String username, String password) throws DataException {
        Amministratore a = null;
        try{
            sAmministratoreByUsernameAndPassword.setString(1, username);
            sAmministratoreByUsernameAndPassword.setString(2, password);
            try (ResultSet rs = sAmministratoreByUsernameAndPassword.executeQuery()) {
                if (rs.next()) a = createAmministratore(rs);   
            }
        }catch (SQLException ex) {
            throw new DataException("Unable to load amministratore by username and password", ex);
        }
        return a;
            
        }
    }
