SET FOREIGN_KEY_CHECKS=0;
TRUNCATE responsabile;
TRUNCATE aula;
TRUNCATE associazione_aula_gruppo;
TRUNCATE corso;
TRUNCATE evento;
TRUNCATE attrezzatura;
TRUNCATE evento_ricorrente;
TRUNCATE amministratore;
TRUNCATE gruppo;

INSERT INTO Amministratore(username, psw) VALUES("Stefano130101", "00000000000000000000000000000000576377a442fa733d4284587721176163622e9d8983149269b06300bfcfe382cf");

INSERT INTO gruppo(nome, tipo, descrizione) VALUES("COPPITO", "POLO", "POLO DI COPPITO UNIVAQ");
INSERT INTO gruppo(nome, tipo, descrizione) VALUES("DISIM", "DIPARTIMENTO", "DIPERTIMENTO DI INGEGNERIA");
INSERT INTO gruppo(nome, tipo, descrizione) VALUES("ROIO", "POLO", "POLO DI ROIO UNIVAQ");

INSERT INTO responsabile (nome, cognome, codice_fiscale, email) VALUES ("ANTONIO", "VERDI", "VRDNTN97W52A131K", "antonio.verdi@univaq.it");
INSERT INTO responsabile (nome, cognome, codice_fiscale, email) VALUES("MARIO", "ROSSI", "MRORSS89W12E734T", "mario.rossi@univaq.it");
INSERT INTO responsabile (nome, cognome, codice_fiscale, email) VALUES("GIUSEPPE", "FRANCANTONIO", "GSPFRC75G12S667Y", "giuseppe.francantonio@univaq.it");

INSERT INTO aula (nome, luogo, edificio, piano, capienza, numero_prese_elettriche, numero_prese_di_rete, note_generiche, ID_responsabile) VALUES ("ROSSA", "VIA VETOIO,5", "RENATO RICAMO", 1, 60, 10, 5, "lorem ipsum ...", 1);
INSERT INTO aula (nome, luogo, edificio, piano, capienza, numero_prese_elettriche, numero_prese_di_rete, note_generiche, ID_responsabile) VALUES ("A1.2", "VIA VETOIO,5", "ALAN TURING", 1, 40, 10, 5, "xyz xyz, lorem ipsum, prova descrizione", 2);
INSERT INTO aula (nome, luogo, edificio, piano, capienza, numero_prese_elettriche, numero_prese_di_rete, note_generiche, ID_responsabile) VALUES ("A1.3", "VIA PROVA,10", "ALAN TURING", 1, 20, 10, 5, "....lorem ipsum....", 3);

INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (1, 2);
INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (1, 1);
INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (2, 1);
INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (2, 2);
INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (3, 1);
INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (3, 2);

INSERT INTO corso(nome, corso_di_laurea, tipo_laurea, anno_di_frequentazione) VALUES ("MATEMATICA DISCRETA", "INFORMATICA", "TRIENNALE", 2);
INSERT INTO corso(nome, corso_di_laurea, tipo_laurea, anno_di_frequentazione) VALUES ("FISICA", "INFORMATICA", "TRIENNALE", 2);
INSERT INTO corso(nome, corso_di_laurea, tipo_laurea, anno_di_frequentazione) VALUES ("INGLESE B2", "INFORMATICA", "Triennale", 3);


INSERT INTO evento (nome, descrizione, tipologia, data_evento, ora_inizio, ora_fine, ricorrenza, data_fine_ricorrenza, ID_corso, ID_responsabile, ID_aula) VALUES ("LEZIONE MATEMATICA", "lezione di matematica discreta, esercitazione per prove parziali", "LEZIONE", CURDATE(), "14:30:00", "17:30:00", "SETTIMANALE", curdate() + interval 1 month, 1, 1, 1);
INSERT INTO evento (nome, descrizione, tipologia, data_evento, ora_inizio, ora_fine, ricorrenza, data_fine_ricorrenza, ID_corso, ID_responsabile, ID_aula) VALUES ("PROVA PARZIALE DI FISICA", "prima prova parziali di fisica", "PARZIALE", CURDATE() + INTERVAL 1 DAY, "09:30:00", "12:30:00", "NESSUNA", null, 2, 2, 1);
INSERT INTO evento (nome, descrizione, tipologia, data_evento, ora_inizio, ora_fine, ricorrenza, data_fine_ricorrenza, ID_corso, ID_responsabile, ID_aula) VALUES ("ESERCITAZIONE INGLESE", "b2 english exam", "ESAME", curdate(), "14:30:00", "17:30:00", "NESSUNA", null, 3, 3, 2);


INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("PROIETTORE", "ABC111", 1);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MONITOR", "ABC222", 1);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("LAVAGNA", "ABC333", 2);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("PROIETTORE", "ABC123", 3);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MICROFONO", "ABC888", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MONITOR", "ABC999", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("LAVAGNA", "ABC777", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("PROIETTORE", "ABC227", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MICROFONO", "ABC756", null);

