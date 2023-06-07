/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.data.dao.impl;

import it.univaq.project.aule_web.data.model.Corso;
import it.univaq.project.aule_web.data.model.enumerable.TipoLaurea;
import it.univaq.project.aule_web.framework.data.DAO;
import it.univaq.project.aule_web.framework.data.DataException;
import it.univaq.project.aule_web.framework.data.DataItemProxy;
import it.univaq.project.aule_web.framework.data.DataLayer;
import it.univaq.project.aule_web.framework.data.OptimisticLockException;
import it.univaq.project.aule_web.data.dao.CorsoDAO;
import it.univaq.project.aule_web.data.model.Evento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Alberto Bogi
 */
public class CorsoDAO_MySQL extends DAO implements CorsoDAO {

    private PreparedStatement sCorsoByID, sCorsoByName, sCorsoByPartialName, sAllCorsi;
    private PreparedStatement iCorso, uCorso, dCorso;

    public CorsoDAO_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        super.init();

        try {
            this.sCorsoByID = this.connection.prepareStatement("SELECT * FROM Corso WHERE ID = ?");
            this.sCorsoByName = this.connection.prepareStatement("SELECT * FROM Corso WHERE nome = ?");
            this.sCorsoByPartialName = this.connection.prepareStatement("SELECT * from Corso C WHERE substring(C.nome,1,?) = ?");
            this.sAllCorsi = this.connection.prepareStatement("SELECT * FROM corso");

            this.iCorso = this.connection.prepareStatement("INSERT INTO Corso(nome, corso_di_laurea, tipo_laurea, anno_di_frequentazione) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            this.uCorso = this.connection.prepareStatement("UPDATE Corso SET nome = ?, corso_di_laurea = ?, tipo_laurea = ?, anno_di_frequentazione = ?, versione = ? WHERE versione = ? AND ID = ?");
            this.dCorso = this.connection.prepareStatement("DELETE FROM Corso WHERE ID = ?");

        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del data layer", ex);
        }

    }

    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        //also closing PreparedStamenents is a good practice...
        try {

            sCorsoByID.close();
            sCorsoByName.close();
            sCorsoByPartialName.close();
            sAllCorsi.close();
            iCorso.close();
            uCorso.close();
            dCorso.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public Corso createCorso() {
        return new CorsoProxy(this.getDataLayer());
    }

    private CorsoProxy createCorso(ResultSet rs) throws DataException {
        CorsoProxy c = (CorsoProxy) this.createCorso();
        try {
            c.setKey(rs.getInt("ID"));
            c.setNome(rs.getString("nome"));
            if ("TRIENNALE".equals(rs.getString("tipo_laurea"))) {
                c.setTipoLaurea(TipoLaurea.TRIENNALE);
            } else if ("MAGISTRALE".equals(rs.getString("tipo_laurea"))) {
                c.setTipoLaurea(TipoLaurea.MAGISTRALE);
            } else {
                c.setTipoLaurea(TipoLaurea.CICLO_UNICO);
            }

            c.setCorsoDiLaurea(rs.getString("corso_di_laurea"));
            c.setAnnoDiFrequentazione(rs.getInt("anno_di_frequentazione"));
            c.setVersion(rs.getInt("versione"));
        } catch (SQLException ex) {
            throw new DataException("Error initializing the data layer", ex);
        }

        return c;
    }

    @Override
    public Corso getCorso(int key) throws DataException {
        Corso corso = null;
        try {
            sCorsoByID.setInt(1, key);
            try ( ResultSet rs = sCorsoByID.executeQuery()) {
                if (rs.next()) {
                    corso = createCorso(rs);
                    //e lo mettiamo anche nella cache
                    //and put it also in the cache
                    //dataLayer.getCache().add(Article.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricate il corso dall' attributo ID", ex);
        }
        return corso;
    }

    @Override
    public Corso getCorsoByName(String nome) throws DataException {
        Corso corso = null;
        try {
            sCorsoByName.setString(1, nome);
            try ( ResultSet rs = sCorsoByName.executeQuery()) {
                if (rs.next()) {
                    corso = createCorso(rs);
                    //e lo mettiamo anche nella cache
                    //and put it also in the cache
                    //dataLayer.getCache().add(Article.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il corso dal nome digitato", ex);
        }
        return corso;

    }

    @Override
    public Corso getCorsoByPartialName(String nomeParziale) throws DataException {
        Corso corso = null;
        try {
            sCorsoByPartialName.setString(1, nomeParziale);
            try ( ResultSet rs = sCorsoByPartialName.executeQuery()) {
                if (rs.next()) {
                    corso = createCorso(rs);
                    //e lo mettiamo anche nella cache
                    //and put it also in the cache
                    //dataLayer.getCache().add(Article.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare il corso dal nome parziale digitato", ex);
        }
        return corso;
    }
    
    @Override
    public List<Corso> getAllCorsi() throws DataException {
        List<Corso> corsi = new ArrayList<>();
        try{
            try ( ResultSet rs = sAllCorsi.executeQuery()) {
                while(rs.next()) {
                    corsi.add(createCorso(rs));
                }
            }
        }catch (SQLException ex) {
            throw new DataException("Errore nell'eliminazione del corso", ex);
        }
        return corsi;
    }

    @Override
    public void storeCorso(Corso corso) throws DataException {
        try {
            //se l'oggetto ha una chiave maggiore di zero, allora è un oggetto già estratto dal DB e quindi dobbiamo aggiornare
            if (corso.getKey() != null && corso.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (corso instanceof DataItemProxy && !((DataItemProxy) corso).isModified()) {
                    return;
                }
                uCorso.setString(1, corso.getNome());
                uCorso.setString(2, corso.getCorsoDiLaurea());

                switch (corso.getTipoLaurea().toString()) {
                    case "MAGISTRALE":
                        uCorso.setString(3, "MAGISTRALE");
                        break;

                    case "TRIENNALE":
                        uCorso.setString(3, "TRIENNALE");
                        break;

                    case "CICLO_UNICO":
                        uCorso.setString(3, "CICLO_UNICO");
                        break;
                }

                uCorso.setInt(4, corso.getAnnoDiFrequentazione());

                //può accadere che due persone accedano al DB e prendano lo stesso articolo, quindi due thread concorrenti che 
                //prendono l'articolo, ma i due thread non si riferiscono allo stesso oggetto
                //se io ho due accessi concorrenti non condividono la stessa cache e quindi non risolvo il problema della
                //peristenza, perchè se entrambi decidessero di modificare l'oggetto, ci sono disallineamenti diversi che
                //potrebbero accadere.
                //quindi il caching è utile nel nel caso dell'esecuzione di un singolo thread
                //Le due strategie di base sono: locking(bloccare le risorse in lettura e scrittura, come il semaforo)
                //Il locking pessimistico sta nel fatto che se due persone leggono la stessa entità, allora si considera
                //il caso peggiore, cioè entrambi vogliono modificare l'informazione. Questo però non si usa quasi mai.
                //Si usa il locking ottimistico: se tutti e due prendono una stessa risorsa, allora si tiene conto del fatto che
                //uno la modificherà, mentre l'altro andrà in lettura. Cioè se uno vuole modificare e scriver sul DB, allora controllo
                //se la risorsa è già stata modificata da qualcuno dopo aver letto la risorsa che si è modificata
                //per questo utilizziamo un attributo version, un numero che indica la versione dell'oggetto che abbiamo letto e ogni
                //volta che lo si modifica si incrementa il numero di versione
                long versioneCorrente = corso.getVersion();
                long versioneSuccessiva = versioneCorrente + 1;

                uCorso.setLong(5, versioneSuccessiva);
                uCorso.setLong(6, versioneCorrente);
                uCorso.setInt(7, corso.getKey());

                if (uCorso.executeUpdate() == 0) {
                    //Il lock ottimistico è fallito, cioè qualcuno ha modificato la risorsa dopo l'ultima lettura e prima di noi
                    //quindi la versione della risorsa sul DB non è la stessa che abbiamo modificato noi
                    throw new OptimisticLockException(corso);
                } else {
                    corso.setVersion(versioneSuccessiva);
                }
            } else { //insert
                iCorso.setString(1, corso.getNome());
                iCorso.setString(2, corso.getCorsoDiLaurea());
                switch (corso.getTipoLaurea().toString()) {
                    case "MAGISTRALE":
                        iCorso.setString(3, "MAGISTRALE");
                        break;

                    case "TRIENNALE":
                        iCorso.setString(3, "TRIENNALE");
                        break;

                    case "CICLO_UNICO":
                        iCorso.setString(3, "CICLO_UNICO");
                        break;
                }
                iCorso.setInt(4, corso.getAnnoDiFrequentazione());

                if (iCorso.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try ( ResultSet keys = iCorso.getGeneratedKeys()) {
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
                            corso.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            //dataLayer.getCache().add(Article.class, article);
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
            if (corso instanceof DataItemProxy) {
                ((DataItemProxy) corso).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store article", ex);
        }
    }

    @Override
    public void deleteCorso(Corso corso) throws DataException {
        try {
            if (corso.getKey() != null && corso.getKey() > 0) {
                dCorso.setInt(1, corso.getKey());
                dCorso.execute();

            }
        } catch (SQLException ex) {
            throw new DataException("Errore nell'eliminazione del corso", ex);
        }

    }


}
