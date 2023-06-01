/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Gruppo;
import it.univaq.project.aule_web.framework.data.DAO;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.data.dao.AulaDAO;
import it.univaq.project.aule_web.data.dao.GruppoDAO;
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
public class AulaDAO_MySQL extends DAO implements AulaDAO {

    private PreparedStatement sAulaByID, sAuleByIDs, sAulaByNomeAndPosizione;
    private PreparedStatement iAula;
    private PreparedStatement uAula;
    private PreparedStatement dAula;

    public AulaDAO_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {

        try {
            super.init();

            sAulaByID = connection.prepareStatement("SELECT * FROM aula WHERE ID=?");
            // sAuleByIDs =  connection.prepareStatement("SELECT ID FROM aula");
            sAulaByNomeAndPosizione = connection.prepareStatement("SELECT * FROM aula WHERE nome=?, luogo=?,edificio=?,piano =?");
            iAula = connection.prepareStatement("INSERT INTO gruppo (nome,luogo,edificio,piano,capienza,numero_prese_elettriche,numero_prese_di_rete,note_generiche,ID_responsabile) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uAula = connection.prepareStatement("UPDATE gruppo SET nome=?,luogo=?,edificio=?,piano=?,capienza=?,numero_prese_elettriche=?,numero_prese_di_rete=?,note_generiche = ?,ID_responsabile =?, versione=? WHERE ID=? and versione=?");
            dAula = connection.prepareStatement("DELETE FROM gruppo WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            sAulaByID.close();
            // sAuleByIDs.close();
            iAula.close();
            uAula.close();
            dAula.close();

        } catch (SQLException ex) {

        }
        super.destroy();
    }

    @Override
    public Aula createAula() {
        return new AulaProxy(getDataLayer());
    }

    //helper
    private AulaProxy createAula(ResultSet rs) throws DataException {
        AulaProxy a = (AulaProxy) createAula();
        try {
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            a.setLuogo(rs.getString("luogo"));
            a.setEdificio(rs.getString("edificio"));
            a.setPiano(rs.getInt("piano"));
            a.setCapienza(rs.getInt("capienza"));
            a.setNumeroPreseElettriche(rs.getInt("numero_prese_elettriche"));
            a.setNumeroPreseDiRete(rs.getInt("numero_prese_di_rete"));
            a.setNoteGeneriche(rs.getString("note_generiche"));
            a.setResponsabileKey(rs.getInt("ID_responsabile"));
            a.setVersion(rs.getLong("versione"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare oggetto Aula", ex);
        }
        return a;
    }

    @Override

    public Aula getAula(int key) throws DataException {
        Aula aula = null;
        try {

            sAulaByID.setInt(1, key);

            try ( ResultSet rs = sAulaByID.executeQuery()) {
                if (rs.next()) {
                    aula = createAula(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Aula da ID", ex);
        }
        return aula;
    }
    
    @Override
    public Aula getAulaByNomeAndPosizione(String nome, String luogo, String edificio, int piano) throws DataException {
        Aula aula = null;
       try {
            sAulaByNomeAndPosizione.setString(1, nome);
            sAulaByNomeAndPosizione.setString(2, luogo);
            sAulaByNomeAndPosizione.setString(3, edificio);
            sAulaByNomeAndPosizione.setInt(4, piano);
            try (ResultSet rs =   sAulaByNomeAndPosizione.executeQuery()) {
                if (rs.next()) {
                   aula = createAula(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Aula dal nome e dalla posizione digitati", ex);
        }
        return aula;
    }

    /*
    @Override
    public List<Aula> getAuleByIDs(Aula aula) throws DataException {
        List<Aula> result = new ArrayList();

        try {
            sAuleByIDs.setInt(1, aula.getKey());
            try (ResultSet rs = sAuleByIDs.executeQuery()) {
                while (rs.next()) {
                    
                    result.add((Aula) getAula(rs.getInt("aulaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load articles by issue", ex);
        }
        return result;
    }
     */
    @Override
    public void storeAula(Aula aula) throws DataException {
        try {
            if (aula.getKey() != null && aula.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (aula instanceof DataItemProxy && !((DataItemProxy) aula).isModified()) {
                    return;
                }
                uAula.setString(1, aula.getNome());
                uAula.setString(2, aula.getLuogo());
                uAula.setString(3, aula.getEdificio());
                uAula.setInt(4, aula.getPiano());
                uAula.setInt(5, aula.getCapienza());
                uAula.setInt(6, aula.getNumeroPreseElettriche());
                uAula.setInt(7, aula.getNumeroPreseDiRete());
                uAula.setString(8, aula.getNoteGeneriche());
                if (aula.getResponsabile() != null) {
                    uAula.setInt(9, aula.getResponsabile().getKey());
                } else {
                    uAula.setNull(9, java.sql.Types.INTEGER);
                }

                 long versioneCorrente = aula.getVersion();
                long versioneSuccessiva = versioneCorrente + 1;

                uAula.setLong(10, versioneSuccessiva);
                uAula.setInt(11, aula.getKey());
                uAula.setLong(12, versioneCorrente);

                if (uAula.executeUpdate() == 0) {
                    throw new OptimisticLockException(aula);
                } else {
                    aula.setVersion( versioneSuccessiva);
                }
            } else { //insert
                iAula.setString(1, aula.getNome());
                iAula.setString(2, aula.getLuogo());
                iAula.setString(3, aula.getEdificio());
                iAula.setInt(4, aula.getPiano());
                iAula.setInt(5, aula.getCapienza());
                iAula.setInt(6, aula.getNumeroPreseElettriche());
                iAula.setInt(7, aula.getNumeroPreseDiRete());
                iAula.setString(8, aula.getNoteGeneriche());
                if (aula.getResponsabile() != null) {
                    uAula.setInt(9, aula.getResponsabile().getKey());
                } else {
                    uAula.setNull(9, java.sql.Types.INTEGER);
                }
                if (iAula.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try ( ResultSet keys = iAula.getGeneratedKeys()) {
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
                            aula.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            // dataLayer.getCache().add(Aula.class, aula);
                        }
                    }
                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//            //if possible, we return the just-inserted object RELOADED from the
//            //database through our API. In this way, the resulting
//            //object will ambed any data correction performed by
//            //the DBMS
//            if (key > 0) {
//                article.copyFrom(getArticle(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
            //if we have a proxy, reset its dirty attribute
            if (aula instanceof DataItemProxy) {
                ((DataItemProxy) aula).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store aula", ex);
        }
    }

    @Override
    public void deleteAula(Aula aula) throws DataException {
        try {
            if (aula.getKey() != null && aula.getKey() > 0) {
                dAula.setInt(1, aula.getKey());
                dAula.execute();}
        } catch (SQLException ex) {
            Logger.getLogger(AulaDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
 

    

}
