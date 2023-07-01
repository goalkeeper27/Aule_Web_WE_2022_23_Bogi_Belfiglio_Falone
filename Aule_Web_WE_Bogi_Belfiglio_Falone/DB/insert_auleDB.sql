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

INSERT INTO responsabile (nome, cognome, codice_fiscale, email) VALUES ("Antonio", "Verdi", "VRDNTN975S213K", "antonio.verdi@univaq.it");
INSERT INTO responsabile (nome, cognome, codice_fiscale, email) VALUES("Mario", "Rossi", "MRSSI56BG876N", "mario.rossi@univaq.it");
INSERT INTO responsabile (nome, cognome, codice_fiscale, email) VALUES("Giuseppe", "Francoantonio", "GSPFRT567L8M", "giuseppe.francantonio@univaq.it");

INSERT INTO aula (nome, luogo, edificio, piano, capienza, numero_prese_elettriche, numero_prese_di_rete, note_generiche, ID_responsabile) VALUES ("Rossa", "via vetoio,1", "coppito1", 1, 60, 10, 5, "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", 1);
INSERT INTO aula (nome, luogo, edificio, piano, capienza, numero_prese_elettriche, numero_prese_di_rete, note_generiche, ID_responsabile) VALUES ("A1.2", "via vetoio,3", "Alan Turing", 1, 40, 10, 5, "aula piatta", 2);
INSERT INTO aula (nome, luogo, edificio, piano, capienza, numero_prese_elettriche, numero_prese_di_rete, note_generiche, ID_responsabile) VALUES ("A1.3", "via vetoio,5", "Alan Turing", 1, 20, 10, 5, "aula ristretta", 3);

INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (1, 2);
INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (2, 1);
INSERT INTO associazione_aula_gruppo(ID_aula, ID_gruppo) VALUES (3, 2);

INSERT INTO corso(nome, corso_di_laurea, tipo_laurea, anno_di_frequentazione) VALUES ("Matematica", "Informatica", "Triennale", 2);
INSERT INTO corso(nome, corso_di_laurea, tipo_laurea, anno_di_frequentazione) VALUES ("Fisica", "Informatica", "Triennale", 3);
INSERT INTO corso(nome, corso_di_laurea, tipo_laurea, anno_di_frequentazione) VALUES ("Informatica", "Informatica", "Triennale", 1);


INSERT INTO evento (nome, descrizione, tipologia, data_evento, ora_inizio, ora_fine, ricorrenza, data_fine_ricorrenza, ID_corso, ID_responsabile, ID_aula) VALUES ("LezMatematica", "Conti bbbbbbbbbbbbbbbbbbbbbbbb rrrrrrrrrrrrrrrrrrrrrrrrrrrr eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee wwwwwwwwwwwwwwwwwwwwwwwww aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa bbbbbbbbbbbbbbbbbbbb tttttttttttttttttttt", "LEZIONE", CURDATE(), "11:30:00", "13:30:00", "SETTIMANALE", curdate() + interval 2 week, 1, 1, 1);
INSERT INTO evento (nome, descrizione, tipologia, data_evento, ora_inizio, ora_fine, ricorrenza, data_fine_ricorrenza, ID_corso, ID_responsabile, ID_aula) VALUES ("LezFisica", " moto ddddddddddddddddddddddddddddddddddd ffffffffffffffffffffffffffffffffffffffffffff ggggggggggggggggggggggggggggggggggggggg hhhhhhhhhhhhhhhhhhhhhhhhhhhh moto ddddddddddddddddddddddddddddddddddd ffffffffffffffffffffffffffffffffffffffffffff ggggggggggggggggggggggggggggggggggggggg hhhhhhhhhhhhhhhhhhhhhhhhhhhh moto ddddddddddddddddddddddddddddddddddd ffffffffffffffffffffffffffffffffffffffffffff ggggggggggggggggggggggggggggggggggggggg hhhhhhhhhhhhhhhhhhhhhhhhhhhh", "LEZIONE", CURDATE(), "8:30:00", "10:30:00", "GIORNALIERA", curdate() + interval 12 day, 2, 2, 1);
INSERT INTO evento (nome, descrizione, tipologia, data_evento, ora_inizio, ora_fine, ricorrenza, data_fine_ricorrenza, ID_corso, ID_responsabile, ID_aula) VALUES ("EsInformatica", "algoritmi", "ESAME", curdate(), "14:30:00", "16:30:00", "GIORNALIERA", curdate() + interval 4 day, 3, 3, 2);


INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("PROIETTORE", "ABC111", 1);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MONITOR", "ABC222", 1);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("LAVAGNA", "ABC333", 1);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("PROIETTORE", "ABC123", 2);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MICROFONO", "ABC888", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MONITOR", "ABC999", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("LAVAGNA", "ABC777", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("PROIETTORE", "ABC227", null);
INSERT INTO attrezzatura(nome, numero_di_serie, ID_aula) values ("MICROFONO", "ABC756", null);

