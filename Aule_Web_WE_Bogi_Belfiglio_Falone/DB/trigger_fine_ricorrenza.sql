DROP TRIGGER IF EXISTS set_null_data_fine_ricorrenza;
DELIMITER //
CREATE TRIGGER set_null_data_fine_ricorrenza
BEFORE INSERT ON evento
FOR EACH ROW
BEGIN
    IF(NEW.ricorrenza = 'NESSUNA') THEN
        SET NEW.data_fine_ricorrenza = NULL;
    END IF;
END //
DELIMITER ;