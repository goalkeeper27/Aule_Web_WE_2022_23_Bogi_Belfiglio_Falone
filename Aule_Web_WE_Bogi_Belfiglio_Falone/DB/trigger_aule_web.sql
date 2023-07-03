SET FOREIGN_KEY_CHECKS=0;

use aule_web;

DELIMITER $$



drop trigger if exists ricorrenza$$
CREATE TRIGGER Ricorrenza BEFORE INSERT ON Evento FOR EACH ROW
BEGIN
	IF NEW.ricorrenza <> "NESSUNA" and NEW.data_fine_ricorrenza IS NULL THEN
			SIGNAL SQLSTATE '45000' SET message_text = 'Non è stata definita una data di fine per
				la ricorrenza dell\'evento';
	END IF;
END$$


drop trigger if exists Definizione_corso$$
CREATE TRIGGER Definizione_corso BEFORE INSERT ON evento FOR EACH ROW
BEGIN
		if new.tipologia IN ("LEZIONE", "ESAME", "PARZIALE") and new.ID_corso IS NULL
        THEN
			SIGNAL SQLSTATE '45000' SET message_text = 'Non è stato specificato il corso dell\'evento';
		END IF;
END$$




DROP PROCEDURE IF EXISTS insert_evento_ricorrente$$
CREATE PROCEDURE insert_evento_ricorrente(ID INTEGER, data_evento_ricorrente DATE)
BEGIN
	insert into evento_ricorrente(data_evento, ID_evento) values (data_evento_ricorrente, ID);
END$$

DROP PROCEDURE IF EXISTS delete_eventi_ricorrenti$$
CREATE PROCEDURE delete_eventi_ricorrenti(ID INTEGER)
BEGIN
	DELETE FROM evento_ricorrente WHERE ID_evento = ID;
END$$


DROP TRIGGER IF EXISTS inserisci_eventi_ricorrenti$$
CREATE TRIGGER inserisci_eventi_ricorrenti AFTER INSERT ON Evento FOR EACH ROW
BEGIN
	DECLARE data_evento_ricorrente DATE;
	SET data_evento_ricorrente = new.data_evento;
    
	if(new.ricorrenza = "MENSILE") then
    SET data_evento_ricorrente = data_evento_ricorrente + interval 1 month;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            SET data_evento_ricorrente = data_evento_ricorrente + interval 1 month;
            
			end while;
	end if;
    
    if(new.ricorrenza = "GIORNALIERA") then
		SET data_evento_ricorrente = data_evento_ricorrente + interval 1 day;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            SET data_evento_ricorrente = data_evento_ricorrente + interval 1 day;
            
		end while;
	end if;
    
    if(new.ricorrenza = "SETTIMANALE") then
		set data_evento_ricorrente = data_evento_ricorrente + interval 1 week;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            set data_evento_ricorrente = data_evento_ricorrente + interval 1 week;
            
		end while;
	end if;
END$$

DROP TRIGGER IF EXISTS inserisci_eventi_ricorrenti_modifica_evento$$
CREATE TRIGGER inserisci_eventi_ricorrenti_modifica_evento AFTER UPDATE ON Evento FOR EACH ROW
BEGIN 
	DECLARE data_evento_ricorrente DATE;
	call delete_eventi_ricorrenti(NEW.ID);
	SET data_evento_ricorrente = NEW.data_evento;
    
	if(NEW.ricorrenza = "MENSILE") then
    SET data_evento_ricorrente = data_evento_ricorrente + interval 1 month;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            SET data_evento_ricorrente = data_evento_ricorrente + interval 1 month;
            
			end while;
	end if;
    
    if(NEW.ricorrenza = "GIORNALIERA") then
		SET data_evento_ricorrente = data_evento_ricorrente + interval 1 day;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            SET data_evento_ricorrente = data_evento_ricorrente + interval 1 day;
            
		end while;
	end if;
    
    if(NEW.ricorrenza = "SETTIMANALE") then
		set data_evento_ricorrente = data_evento_ricorrente + interval 1 week;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            set data_evento_ricorrente = data_evento_ricorrente + interval 1 week;
            
		end while;
	end if;
    
	

END$$

DROP TRIGGER IF EXISTS check_aula_before_update$$
CREATE TRIGGER check_aula_before_update BEFORE UPDATE ON Aula FOR EACH ROW
BEGIN 
    DECLARE result INT;
    SELECT count(ID) into result FROM Aula WHERE nome = NEW.nome AND NEW.nome <> OLD.nome;
    IF result > 0 THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'esiste già un\'aula con il seguente nome';
	END IF;
END$$

DROP TRIGGER IF EXISTS check_aula_before_insert$$
CREATE TRIGGER check_aula_before_insert BEFORE INSERT ON Aula FOR EACH ROW
BEGIN 
    DECLARE result INT;
    SELECT ID into result FROM Aula WHERE nome = NEW.nome;
    IF result IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'esiste già un\'aula con il seguente nome';
	END IF;
END$$


DROP TRIGGER IF EXISTS check_corso_before_insert$$
CREATE TRIGGER check_corso_before_insert BEFORE INSERT ON Corso FOR EACH ROW
BEGIN 
    DECLARE result INT;
    SELECT ID into result FROM Corso WHERE nome = NEW.nome;
    IF result IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'esiste già un corso con il seguente nome';
	END IF;
END$$


DROP TRIGGER IF EXISTS check_responsabile_before_insert$$
CREATE TRIGGER check_responsabile_before_insert BEFORE INSERT ON Responsabile FOR EACH ROW
BEGIN 
    DECLARE result INT;
    DECLARE codice_fiscale char(16); 
    SET codice_fiscale = NEW.codice_fiscale;
    SELECT 	LENGTH(codice_fiscale) = 16
			AND codice_fiscale REGEXP '^[A-Z0-9]+$'
			AND SUBSTRING(codice_fiscale, 1, 6) REGEXP '^[A-Z]+$'
			AND SUBSTRING(codice_fiscale, 7, 2) REGEXP '^[0-9]+$'
			AND SUBSTRING(codice_fiscale, 9, 1) REGEXP '^[A-Z]+$'
			AND SUBSTRING(codice_fiscale, 10, 2) REGEXP '^[0-9]+$'
			AND SUBSTRING(codice_fiscale, 12, 1) REGEXP '^[A-Z]+$'
			AND SUBSTRING(codice_fiscale, 13, 3) REGEXP '^[0-9]+$'
			AND SUBSTRING(codice_fiscale, 16, 1) REGEXP '^[A-Z]+$'
			INTO result;
    IF result = 0 THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'Il codice fiscale non rispetta la struttura corretta';
	END IF;	
    SET result = null;
    SELECT ID into result FROM Responsabile WHERE email = NEW.email;
    IF result IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'questa mail è usata già da un utente';
	END IF;
    SET result = null;
    SELECT NEW.email LIKE '%@univaq.it' INTO result;
    IF result = 0 THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'la mail deve appartenere al dominio dell\'università';
	END IF;
END$$

DROP TRIGGER IF EXISTS check_evento_before_insert$$
CREATE TRIGGER check_evento_before_insert BEFORE INSERT ON Evento FOR EACH ROW
BEGIN 
    DECLARE result INT;
    SELECT ID into result FROM Evento WHERE nome = NEW.nome;
    IF result IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'un evento con lo stesso nome è già stato programmato';
	END IF;
    SET result = null;
    SELECT ID INTO result FROM Evento WHERE NEW.data_evento = data_evento AND ID_aula = NEW.ID_aula AND ( 
		(NEW.ora_inizio BETWEEN ora_inizio AND ora_fine) OR (NEW.ora_fine BETWEEN ora_inizio AND ora_fine) OR
        (NEW.ora_inizio < ora_inizio AND NEW.ora_fine > ora_fine))  LIMIT 1;
	
    IF result IS NOT NULL THEN 
		SIGNAL SQLSTATE '45000' SET message_text = 'la fascia oraria scelta è già stata programmata';
	END IF;
END$$

DROP TRIGGER IF EXISTS check_evento_before_update$$
CREATE TRIGGER check_evento_before_update BEFORE UPDATE ON Evento FOR EACH ROW
BEGIN 
    DECLARE result INT;
    SELECT ID into result FROM Evento WHERE nome = NEW.nome AND NEW.nome <> OLD.nome;
    IF result IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'un evento con lo stesso nome è già stato programmato';
	END IF;
    SET result = null;
    SELECT ID INTO result FROM Evento WHERE NEW.data_evento = data_evento AND NEW.nome <> nome AND ( 
		(NEW.ora_inizio BETWEEN ora_inizio AND ora_fine) OR (NEW.ora_fine BETWEEN ora_inizio AND ora_fine) OR
        (NEW.ora_inizio < ora_inizio AND NEW.ora_fine > ora_fine))  LIMIT 1;
	
    IF result IS NOT NULL THEN 
		SIGNAL SQLSTATE '45000' SET message_text = 'la fascia oraria scelta è già stata programmata';
	END IF;
END$$

DROP TRIGGER IF EXISTS check_gruppo_before_insert$$
CREATE TRIGGER check_gruppo_before_insert BEFORE INSERT ON Gruppo FOR EACH ROW
BEGIN
	DECLARE result INT;
    SELECT ID INTO result FROM Gruppo WHERE NEW.nome = nome;
    
    IF result IS NOT NULL THEN 
		SIGNAL SQLSTATE '45000' SET message_text = 'questo nome è già associato ad un\'organizzazione universitaria';
	END IF;
END$$

DROP TRIGGER IF EXISTS check_gruppo_before_update$$
CREATE TRIGGER check_gruppo_before_update BEFORE UPDATE ON Gruppo FOR EACH ROW
BEGIN
	DECLARE result INT;
    SELECT ID INTO result FROM Gruppo WHERE NEW.nome = nome AND NEW.nome <> OLD.nome;
    
    IF result IS NOT NULL THEN 
		SIGNAL SQLSTATE '45000' SET message_text = 'questo nome è già associato ad un\'organizzazione universitaria';
	END IF;
END$$

    
    

DELIMITER ;
SET FOREIGN_KEY_CHECKS=0;

