/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.univaq.project.aule_web.framework.result;

import it.univaq.project.aule_web.data.model.Evento;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author Alberto Bogi
 */

public class CSVResult {

    private static final String[] HEADERS = {"aula", "evento", "tipo", "data", "ora di inizio", "ora di fine"};

    

    public static File createCSVFile(List<Evento> eventi, String path) {
        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader(HEADERS).build();
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(path), csvFormat)) {
            for(Evento e: eventi)
                printer.printRecord(e.getAula().getNome(), e.getNome(), e.getTipologia().toString(), e.getDataEvento(), e.getOraInizio(), e.getOraFine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return new File(path);
    }

}
