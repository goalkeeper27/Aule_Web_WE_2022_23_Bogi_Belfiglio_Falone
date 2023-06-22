/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.dao.AttrezzaturaDAO;
import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.framework.data.DAO;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.framework.data.OptimisticLockException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefano Belfiglio
 */
public class AttrezzaturaDAO_MySQL extends DAO implements AttrezzaturaDAO {

    private PreparedStatement sAttrezzaturaByAula, sAttrezzaturaDisponibile, sAttrezzaturaByID;
    private PreparedStatement iAttrezzatura, dAttrezzaturaByID, uAttrezzatura;
    
    private static final Logger LOGGER = Logger.getLogger(AttrezzaturaDAO_MySQL.class.getName());

    public AttrezzaturaDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sAttrezzaturaByAula = this.connection.prepareStatement("SELECT * FROM attrezzatura WHERE ID_aula=?");
            sAttrezzaturaByID = this.connection.prepareStatement("SELECT * FROM attrezzatura WHERE ID=?");
            sAttrezzaturaDisponibile = this.connection.prepareStatement("SELECT * FROM Attrezzatura WHERE ID_aula IS NULL");
            iAttrezzatura = this.connection.prepareStatement("INSERT INTO attrezzatura (nome,numero_di_serie,ID_aula) VALUES(?,?,?)");
            uAttrezzatura = this.connection.prepareStatement("UPDATE attrezzatura SET nome=?,numero_di_serie=?,ID_aula=?, versione=? WHERE ID=? AND versione=?");
            dAttrezzaturaByID = this.connection.prepareStatement("DELETE FROM attrezzatura WHERE ID = ?");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {

            sAttrezzaturaByAula.close();
            sAttrezzaturaByID.close();
            sAttrezzaturaDisponibile.close();
            iAttrezzatura.close();
            uAttrezzatura.close();
            dAttrezzaturaByID.close();

        } catch (SQLException ex) {
            throw new DataException("Errore nella chiusura degli statement", ex);
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
            attrezzatura.setNumeroDiSerie(rs.getString("numero_di_serie"));
            attrezzatura.setVersion(rs.getInt("versione"));
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

            try ( ResultSet rs = sAttrezzaturaByAula.executeQuery()) {
                while (rs.next()) {
                    result.add(this.getAttrezzatura(rs.getInt("ID")));
                }

            }

        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista di attrezzature", ex);
        }
        return result;
    }

    @Override
    public void storeAttrezzatura(Attrezzatura attrezzatura) throws DataException {
        try {
            if (attrezzatura.getKey() != null && attrezzatura.getKey() > 0) {
                if (attrezzatura instanceof DataItemProxy && !((DataItemProxy) attrezzatura).isModified()) {
                    return;
                }
                uAttrezzatura.setString(1, attrezzatura.getNome());
                uAttrezzatura.setString(2, attrezzatura.getNumeroDiSerie());

                if (attrezzatura.getAula() != null) {
                    uAttrezzatura.setInt(3, attrezzatura.getAula().getKey());
                } else {
                    uAttrezzatura.setNull(3, java.sql.Types.INTEGER);
                }

                long current_version = attrezzatura.getVersion();
                long next_version = current_version + 1;

                uAttrezzatura.setLong(4, next_version);
                uAttrezzatura.setInt(5, attrezzatura.getKey());
                uAttrezzatura.setLong(6, current_version);

                if (uAttrezzatura.executeUpdate() == 0) {
                    throw new OptimisticLockException(attrezzatura);
                } else {
                    attrezzatura.setVersion(next_version);
                }

            } else {
                iAttrezzatura.setString(1, attrezzatura.getNome());
                iAttrezzatura.setString(2, attrezzatura.getNumeroDiSerie());

                if (attrezzatura.getAula() != null) {
                    iAttrezzatura.setInt(3, attrezzatura.getAula().getKey());
                } else {
                    iAttrezzatura.setNull(3, java.sql.Types.INTEGER);
                }
                if (iAttrezzatura.executeUpdate() == 1) {
                    try ( ResultSet keys = iAttrezzatura.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            attrezzatura.setKey(key);
                        }
                    }
                }
            }
            if (attrezzatura instanceof DataItemProxy) {
                ((DataItemProxy) attrezzatura).setModified(false);
            }

        } catch (SQLException | OptimisticLockException ex) {
            //throw new DataException("Non è stato possibile memorizzare l'attrezzatura", ex);
            LOGGER.log(Level.SEVERE, "Si è verificata un'eccezione:");
        }
    }

    @Override
    public void deleteAttrezzaturaByID(Attrezzatura attrezzatura) throws DataException {
        try {
            if (attrezzatura.getKey() != null && attrezzatura.getKey() > 0) {
                dAttrezzaturaByID.setInt(1, attrezzatura.getKey());
                dAttrezzaturaByID.execute();
            }
        } catch (SQLException ex) {
            throw new DataException("Non è stato possibile eliminare l'attrezzatura", ex);
        }
    }

    @Override
    public List<Attrezzatura> getAttrezzaturaDisponibile() throws DataException {
        List<Attrezzatura> result = new ArrayList();
        try ( ResultSet rs = sAttrezzaturaDisponibile.executeQuery()) {
            while (rs.next()) {
                result.add(this.getAttrezzatura(rs.getInt("ID")));
            }

        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista di attrezzature", ex);
        }
        return result;
    }

}
