/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.aule_web.data.model.Gruppo;
import it.univaq.aule_web.framework.data.DAO;
import it.univaq.aule_web.framework.data.DataException;
import it.univaq.aule_web.framework.data.DataItemProxy;
import it.univaq.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.GruppoDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author franc
 */
public class GruppoDAO_MySQL  extends DAO implements GruppoDAO{
    
     private PreparedStatement gruppoByTipoAndNome;
     private PreparedStatement iGruppo;
     private PreparedStatement uGruppo;
     private PreparedStatement dGruppo;
    public GruppoDAO_MySQL(DataLayer d) {
        super(d);
    }
    public void init() throws DataException {

        try {
            super.init();
            
            gruppoByTipoAndNome = connection.prepareStatement("SELECT * FROM Gruppo WHERE tipo = ? AND nome=?;");
            iGruppo = connection.prepareStatement("INSERT INTO gruppo (nome,tipo,descrizione) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uGruppo = connection.prepareStatement("UPDATE gruppo SET nome=?,tipo=?,descrizione=? versione=? WHERE ID=? and versione=?");
            dGruppo = connection.prepareStatement("DELETE FROM gruppo WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            gruppoByTipoAndNome.close();
            iGruppo.close();
            
            
        } catch (SQLException ex) {
            
        }
        super.destroy();
    }

    
       
    
    
    
    
    @Override
    public Gruppo createGruppo() {
       return new GruppoProxy(getDataLayer());
    }
    
    //helper
    private GruppoProxy createGruppo(ResultSet rs) throws DataException {
        GruppoProxy g = (GruppoProxy) createGruppo();
        try {
            g.setKey(rs.getInt("ID"));
            g.setNome(rs.getString("nome"));
            g.setDescrizione(rs.getString("descrizione"));
            g.setVersion(rs.getLong("versione"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create gruppo object form ResultSet", ex);
        }
        return g;
    }

    @Override
    public Gruppo getGruppoByTipoAndNome(String tipo, String nome) throws DataException {
       try {
            gruppoByTipoAndNome.setString(1, tipo);
            gruppoByTipoAndNome.setString(2, nome);
            try (ResultSet rs = gruppoByTipoAndNome.executeQuery()) {
                if (rs.next()) {
                    return createGruppo(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Gruppo By Tipo e Nome", ex);
        }
        return null;
    }

    @Override
    public void storeGruppo(Gruppo gruppo) throws DataException {
        try {
            if (gruppo.getKey() != null && gruppo.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (gruppo instanceof DataItemProxy && !((DataItemProxy) gruppo).isModified()) {
                    return;
                }
                uGruppo.setString(1, gruppo.getNome());
                uGruppo.setString(2, gruppo.getTipoGruppo());
                uGruppo.setString(3, gruppo.getDescrizione());
            
               
                long current_version = gruppo.getVersion();
                long next_version = current_version + 1;

                uGruppo.setLong(4, next_version);
                uGruppo.setInt(5, gruppo.getKey());
                uGruppo.setLong(6, current_version);

                if (uGruppo.executeUpdate() == 0) {
                    throw new OptimisticLockException(gruppo);
                } else {
                    gruppo.setVersion(next_version);
                }
            } else { //insert
                iGruppo.setString(1, gruppo.getNome());
                iGruppo.setString(2, gruppo.getTipoGruppo());
                iGruppo.setString(3, gruppo.getDescrizione());
                
                if (iGruppo.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iGruppo.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            int key = keys.getInt(1);
                            //aggiornaimo la chiave in caso di inserimento
                            //after an insert, uopdate the object key
                            gruppo.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            
                        }
                    }
                }
            }


            if (gruppo instanceof DataItemProxy) {
                ((DataItemProxy) gruppo).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store article", ex);
        }
    }

    
    @Override
    public void deleteGruppo(Gruppo gruppo) throws DataException {
         try {
             dGruppo.setInt(1, gruppo.getKey());
             dGruppo.execute();
         } catch (SQLException ex) {
             Logger.getLogger(GruppoDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    
}
