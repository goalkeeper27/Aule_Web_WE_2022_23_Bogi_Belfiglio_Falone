/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.model.Corso;
import it.univaq.aule_web.framework.data.DAO;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.CorsoDAO;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Alberto Bogi
 */
public class CorsoDAO_MySQL extends DAO implements CorsoDAO{
    
    private PreparedStatement sCorsoById, sCorsoByName, sCorsoByPartialName;
    
    
    public CorsoDAO_MySQL(DataLayer d){
        super(d);
    }
    
    public void init() throws DataException{
        super.init();
        
        try{
        this.sCorsoById = this.connection.prepareStatement("SELECT * FROM Corso WHERE ID = ?");
        this.sCorsoByName = this.connection.prepareStatement("SELECT * FROM Corso WHERE nome = ?");
        
        }
        catch(SQLException ex){
            throw new DataException("Error initializing the data layer", ex);
        }
        
        
    }

    @Override
    public Corso createCorso() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Corso getCorso(int key) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Corso getCorsoByName(String nome) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Corso getCorsoByPartialName(String nome) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
