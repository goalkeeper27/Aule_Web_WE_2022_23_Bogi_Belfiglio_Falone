/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.framework.result;

import it.univaq.project.aule_web.data.model.Attrezzatura;
import it.univaq.project.aule_web.data.model.Aula;
import it.univaq.project.aule_web.data.model.Evento;
import it.univaq.project.aule_web.data.model.Gruppo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Alberto Bogi
 */
public class CSVResult {

    private static final String[] HEADERS_EVENTO = {"aula", "evento", "tipo", "data", "ora di inizio", "ora di fine"};
    private static final String[] HEADERS_AULA = {"nome", "luogo", "edificio", "piano", "capienza", "prese elettriche", "prese di rete", "note", "responsabile", "attrezzature", "gruppi"};

    public static File createCSVFile(List<Evento> eventi, String path) {
        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader(HEADERS_EVENTO).build();
        try ( CSVPrinter printer = new CSVPrinter(new FileWriter(path), csvFormat)) {
            for (Evento e : eventi) {
                printer.printRecord(e.getAula().getNome(), e.getNome(), e.getTipologia().toString(), e.getDataEvento(), e.getOraInizio(), e.getOraFine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new File(path);
    }

    public static File createCSVAulaFile(Aula aula, List<Attrezzatura> attrezzature, List<Gruppo> gruppi, String path) {
        List<String> nomiAttrezzature = new ArrayList<>();
        for (Attrezzatura attrezzatura : attrezzature) {
            nomiAttrezzature.add(attrezzatura.getNome());
        }

        List<String> nomiGruppi = new ArrayList<>();
        for (Gruppo gruppo : gruppi) {
            nomiGruppi.add(gruppo.getNome());
        }

        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader(HEADERS_AULA).build();
        try ( CSVPrinter printer = new CSVPrinter(new FileWriter(path), csvFormat)) {
            printer.printRecord(aula.getNome(), aula.getLuogo(), aula.getEdificio(), aula.getPiano(), aula.getCapienza(),
                    aula.getNumeroPreseElettriche(), aula.getNumeroPreseDiRete(), aula.getNoteGeneriche(), aula.getResponsabile().getEmail(),
                    String.join(",", nomiAttrezzature), String.join(",", nomiGruppi));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new File(path);

    }

    public static HashMap<String, String> readCSVAulaFile(String path) {
        try {
            Reader in = new FileReader("src/test/resources/book.csv");

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(HEADERS_AULA)
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in);

            for (CSVRecord record : records) {
                String author = record.get("author");
                String title = record.get("title");
               // assertEquals(AUTHOR_BOOK_MAP.get(author), title);

            }

        } catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
        catch (IOException ex){
                ex.printStackTrace();
            }
        return new HashMap<String, String>();
        }
    

}
