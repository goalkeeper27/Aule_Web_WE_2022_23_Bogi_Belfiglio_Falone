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
public class AttrezzaturaDAO_MySQL extends DAO implements AttrezzaturaDAO {

    private PreparedStatement sAttrezzaturaByAula;
    private PreparedStatement sAttrezzaturaByID;

    public AttrezzaturaDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sAttrezzaturaByAula = this.connection.prepareStatement("SELECT * FROM attrezzatura WHERE ID_aula=?");
            sAttrezzaturaByID = this.connection.prepareStatement("SELECT * FROM attrezzatura WHERE ID = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            sAttrezzaturaByAula.close();
            sAttrezzaturaByID.close();
        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement");
        }
        super.destroy();
    }

    @Override
    public Attrezzatura createAttrezzatura() {
        return new AttrezzaturaProxy(getDataLayer());
    }

    private AttrezzaturaProxy createAttrezzatura(ResultSet rs) throws DataException {
        AttrezzaturaProxy attrezzatura = (AttrezzaturaProxy) createAttrezzatura();
        try {
            attrezzatura.setKey(rs.getInt("ID"));
            attrezzatura.setAulaKey(rs.getInt("ID_aula"));
            attrezzatura.setNome(rs.getString("nome"));
            attrezzatura.setNumeroDiSerie(rs.getInt("numero_di_serie"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Attrezzatura", ex);
        }
        return attrezzatura;
    }

    @Override
    public Attrezzatura getAttrezzatura(int key) throws DataException {
        Attrezzatura attrezzatura = null;
        try {
            sAttrezzaturaByID.setInt(1, key);

            try ( ResultSet rs = sAttrezzaturaByID.executeQuery()) {
                if (rs.next()) {
                    attrezzatura = createAttrezzatura(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Attrezzatura da ID", ex);
        }
        return attrezzatura;
    }

    @Override
    public List<Attrezzatura> getAttrezzatureByAula(Aula aula) throws DataException {
        List<Attrezzatura> result = new ArrayList();

        try {
            if (aula.getKey() != null && aula.getKey() > 0) {
                sAttrezzaturaByAula.setInt(1, aula.getKey());
            } else {
                throw new DataException("Aula non identificata. Impossibile trovare l'attrezzatura");
            }

            try ( ResultSet rs = sAttrezzaturaByID.executeQuery()) {
                if (rs.next()) {
                    result.add((Attrezzatura) this.getAttrezzatura(rs.getInt("ID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista di attrezzature", ex);
        }
        return result;
    }

}
