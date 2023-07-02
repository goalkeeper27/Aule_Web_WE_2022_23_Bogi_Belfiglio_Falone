/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.model.Responsabile;
import it.univaq.project.aule_web.framework.data.DAO;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.ResponsabileDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResponsabileDAO_MySQL extends DAO implements ResponsabileDAO {

    private PreparedStatement sResponsabileByID, sResponsabileByEmail, sAllResponsabili, sResponsabiliByPartialEmail;
    private PreparedStatement iResponsabile, dResponsabileByEmail;

    public ResponsabileDAO_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        try {
            super.init();
            sResponsabileByID = this.connection.prepareStatement("SELECT * FROM responsabile WHERE ID=?");
            sResponsabileByEmail = this.connection.prepareStatement("SELECT * FROM responsabile WHERE email=?");
            sAllResponsabili = this.connection.prepareStatement("SELECT * FROM Responsabile");
            sResponsabiliByPartialEmail = connection.prepareStatement("SELECT * FROM Responsabile R WHERE substring(R.email,1,?) = ?");
            iResponsabile = this.connection.prepareStatement("INSERT INTO responsabile (nome,cognome,codice_fiscale,email) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS );
            dResponsabileByEmail = this.connection.prepareStatement("DELETE FROM responsabile WHERE email=?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            sResponsabileByID.close();
            sResponsabileByEmail.close();
            sResponsabiliByPartialEmail.close();
            sAllResponsabili.close();
            iResponsabile.close();
            dResponsabileByEmail.close();

        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public Responsabile getResponsabile(int responsabile_key) throws DataException {
        Responsabile responsabile = null;
        try {
            sResponsabileByID.setInt(1, responsabile_key);
            try ( ResultSet rs = sResponsabileByID.executeQuery()) {
                if (rs.next()) {
                    responsabile = createResponsabile(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il responsabile da ID", ex);
        }
        return responsabile;
    }

    @Override
    public Responsabile createResponsabile() {
        return new ResponsabileProxy(getDataLayer());
    }

    private ResponsabileProxy createResponsabile(ResultSet rs) throws DataException {
        ResponsabileProxy r = (ResponsabileProxy) createResponsabile();
        try {
            r.setKey(rs.getInt("ID"));
            r.setNome(rs.getString("nome"));
            r.setCognome(rs.getString("cognome"));
            r.setCodiceFiscale(rs.getString("codice_fiscale"));
            r.setEmail(rs.getString("email"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Responsabile dal ResultSet", ex);
        }
        return r;
    }

    @Override
    public void storeResponsabile(Responsabile responsabile) throws DataException {
        try {
            if (responsabile.getKey() == null) {
                iResponsabile.setString(1, responsabile.getNome());
                iResponsabile.setString(2, responsabile.getCognome());
                iResponsabile.setString(3, responsabile.getCodiceFiscale());
                iResponsabile.setString(4, responsabile.getEmail());

                if (iResponsabile.executeUpdate() == 1) {

                    try ( ResultSet keys = iResponsabile.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            responsabile.setKey(key);
                        }
                    }
                }
            }
            if (responsabile instanceof DataItemProxy) {
                ((DataItemProxy) responsabile).setModified(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Data error", ex);
        }
    }

    @Override
    public void deleteResponsabileByEmail(String email) throws DataException {
        try {
            if (email != null) {
                dResponsabileByEmail.setString(1, email);
                dResponsabileByEmail.execute();
            }
        } catch (SQLException ex) {
            throw new DataException("Non è stato possibile eliminare il responsabile", ex);
        }
    }

    @Override
    public Responsabile getResponsabileByEmail(String email) throws DataException {
        Responsabile responsabile = null;
        try {
            sResponsabileByEmail.setString(1, email);
            try ( ResultSet rs = sResponsabileByEmail.executeQuery()) {
                if (rs.next()) {
                    responsabile = createResponsabile(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il responsabile dalla email citata", ex);
        }
        return responsabile;
    }

    @Override
    public List<Responsabile> getAllResponsabili() throws DataException {
        List<Responsabile> responsabili = new ArrayList<>();
        try {
            try ( ResultSet rs = sAllResponsabili.executeQuery()) {
                while(rs.next()) {
                    responsabili.add(createResponsabile(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il responsabile dalla email citata", ex);
        }
        return responsabili;
    }
    
    @Override
    public List<Responsabile> getGruppiByPartialEmail(String search) throws DataException {
        List<Responsabile> responsabili = new ArrayList<>();
        try {
            sResponsabiliByPartialEmail.setInt(1, search.length());
            sResponsabiliByPartialEmail.setString(2, search);
            try ( ResultSet rs = sResponsabiliByPartialEmail.executeQuery()) {
                while (rs.next()) {
                    responsabili.add(createResponsabile(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("error DB", ex);
        }
        return responsabili;
    }

}
