/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.framework.data.DAO;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.GruppoDAO;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.framework.data.OptimisticLockException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francesco Falone
 */
public class GruppoDAO_MySQL extends DAO implements GruppoDAO {


    private PreparedStatement sGruppoByID, sGruppoByTipoAndNome, sTipiGruppo, sAllGruppi, sGruppiByAula, sGruppiByPartialName;
    private PreparedStatement iGruppo;
    private PreparedStatement uGruppo;
    private PreparedStatement dGruppo;

    public GruppoDAO_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {

        try {
            super.init();
            sGruppoByID = connection.prepareStatement("SELECT * FROM Gruppo WHERE ID = ?");
            sGruppoByTipoAndNome = connection.prepareStatement("SELECT * FROM Gruppo WHERE tipo = ? AND nome = ?");
            sGruppiByPartialName = connection.prepareStatement("SELECT * FROM Gruppo G WHERE substring(G.nome,1,?) = ?");
            sTipiGruppo = connection.prepareStatement("SELECT DISTINCT tipo FROM gruppo; ");
            sAllGruppi = connection.prepareStatement("SELECT * FROM Gruppo");
            sGruppiByAula = connection.prepareStatement("SELECT G.* FROM gruppo G, associazione_aula_gruppo AG WHERE G.ID=AG.ID_gruppo and AG.ID_aula = ?");
            iGruppo = connection.prepareStatement("INSERT INTO gruppo (nome,tipo,descrizione) VALUES(?,?,?);", Statement.RETURN_GENERATED_KEYS);
            uGruppo = connection.prepareStatement("UPDATE gruppo SET nome=?,tipo=?,descrizione=?, versione=? WHERE ID=? AND versione=?;");
            dGruppo = connection.prepareStatement("DELETE FROM gruppo WHERE ID=?;");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            sGruppoByID.close();
            sGruppiByPartialName.close();
            sGruppoByTipoAndNome.close();
            sTipiGruppo.close();
            sAllGruppi.close();
            sGruppiByAula.close();
            iGruppo.close();
            uGruppo.close();
            dGruppo.close();

        } catch (SQLException ex) {

        }
        super.destroy();
    }

    @Override
    public Gruppo createGruppo() {
        return new GruppoProxy(getDataLayer());
    }

    private GruppoProxy createGruppo(ResultSet rs) throws DataException {
        GruppoProxy g = (GruppoProxy) createGruppo();
        try {
            g.setKey(rs.getInt("ID"));
            g.setNome(rs.getString("nome"));
            g.setTipoGruppo(rs.getString("tipo"));
            g.setDescrizione(rs.getString("descrizione"));
            g.setVersion(rs.getLong("versione"));
        } catch (SQLException ex) {
            throw new DataException("Errore creazione oggetto Gruppo", ex);
        }
        return g;
    }

    @Override
    public Gruppo getGruppo(int key) throws DataException {
        Gruppo gruppo = null;
        try {

            sGruppoByID.setInt(1, key);

            try ( ResultSet rs = sGruppoByID.executeQuery()) {
                if (rs.next()) {
                    gruppo = createGruppo(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Aula da ID", ex);
        }
        return gruppo;
    }
    
    @Override
    public Gruppo getGruppoByTipoAndNome(String tipo, String nome) throws DataException {
        Gruppo gruppo = null;
        try {
            sGruppoByTipoAndNome.setString(1, tipo);
            sGruppoByTipoAndNome.setString(2, nome);
            try ( ResultSet rs = sGruppoByTipoAndNome.executeQuery()) {
                if(rs.next()) {
                    gruppo = createGruppo(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Gruppo dal tipo e nome digitati", ex);
        }
        return gruppo;
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

                long versioneCorrente = gruppo.getVersion();
                long versioneSuccessiva = versioneCorrente + 1;

                uGruppo.setLong(4, versioneSuccessiva);
                uGruppo.setInt(5, gruppo.getKey());
                uGruppo.setLong(6, versioneCorrente);

                if (uGruppo.executeUpdate() == 0) {
                    throw new OptimisticLockException(gruppo);
                } else {
                    gruppo.setVersion(versioneSuccessiva);
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
                    try ( ResultSet keys = iGruppo.getGeneratedKeys()) {
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
                            //cache se vogliamo inserirla
                        }
                    }
                }
            }

            if (gruppo instanceof DataItemProxy) {
                ((DataItemProxy) gruppo).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile caricare Gruppo", ex);
        }
    }

    @Override
    public void deleteGruppo(Gruppo gruppo) throws DataException {
        try {
            if (gruppo.getKey() != null && gruppo.getKey() > 0) {
                dGruppo.setInt(1, gruppo.getKey());
                dGruppo.execute();
            }
        } catch (SQLException ex) {
            throw new DataException("Errore nell'eliminazione del gruppo", ex);
        }

    }

    @Override
    public List<String> getTipiGruppo() throws DataException {
        List<String> tipi = new ArrayList<>();

        try ( ResultSet rs = sTipiGruppo.executeQuery()) {
            while (rs.next()) {
                tipi.add(rs.getString("tipo"));
            }

        } catch (SQLException ex) {
            throw new DataException("error DB", ex);
        }

        return tipi;
    }

    @Override
    public List<Gruppo> getAllGruppi() throws DataException {

        List<Gruppo> gruppi = new ArrayList<>();

        try ( ResultSet rs = sAllGruppi.executeQuery()) {
            while (rs.next()) {
                Gruppo gruppo = createGruppo(rs);
                gruppi.add(gruppo);
            }

        } catch (SQLException ex) {
            throw new DataException("error DB", ex);
        }

        return gruppi;
    }

    @Override
    public List<Gruppo> getGruppiByAula(Aula aula) throws DataException {
        List<Gruppo> gruppi = new ArrayList<>();
        try {
            sGruppiByAula.setInt(1, aula.getKey());
            try ( ResultSet rs = sGruppiByAula.executeQuery()) {
                while (rs.next()) {
                    Gruppo gruppo = createGruppo(rs);
                    gruppi.add(gruppo);
                }
            }

        } catch (SQLException ex) {
            throw new DataException("error DB", ex);
        }

        return gruppi;
    }
    
    @Override
    public List<Gruppo> getGruppiByPartialName(String search) throws DataException {
        List<Gruppo> gruppi = new ArrayList<>();
        try {
            sGruppiByPartialName.setInt(1, search.length());
            sGruppiByPartialName.setString(2, search);
            try ( ResultSet rs = sGruppiByPartialName.executeQuery()) {
                while (rs.next()) {
                    gruppi.add(createGruppo(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("error DB", ex);
        }
        return gruppi;
    }


}
