-- @link https://stackoverflow.com/questions/26578313/how-do-i-create-a-sequence-in-mysql

-- =======================================================

CREATE TABLE SEQUENCES  
(  
  NM_SEQUENCE VARCHAR(32) NOT NULL UNIQUE,  
  VR_SEQUENCE BIGINT      NOT NULL  
);  
-- =======================================================
-- Creates a sequence sSeqName and set its initial value.
-- =======================================================

DROP PROCEDURE IF EXISTS CreateSequence;  

DELIMITER :)  
CREATE PROCEDURE CreateSequence( sSeqName VARCHAR(32), iSeqValue BIGINT )  
BEGIN  
  IF NOT EXISTS ( SELECT * FROM SEQUENCES WHERE (NM_SEQUENCE = sSeqName) ) THEN  
    INSERT INTO SEQUENCES (NM_SEQUENCE, VR_SEQUENCE)  
    VALUES (sSeqName   , iSeqValue  );  
  END IF;  
END :)  
DELIMITER ;  

-- CALL CreateSequence( 'MySequence', 0 );  
-- =======================================================================
-- Increments the sequence value of sSeqName by iIncrement and returns it.
-- If iIncrement is zero, returns the current value of sSeqName.
-- =======================================================================

DROP FUNCTION IF EXISTS GetSequenceVal;  

DELIMITER :)  
CREATE FUNCTION GetSequenceVal( sSeqName VARCHAR(32), iIncrement INTEGER )  
RETURNS BIGINT  -- iIncrement can be negative  
BEGIN  
  DECLARE iSeqValue BIGINT;  

  SELECT VR_SEQUENCE FROM SEQUENCES  
  WHERE  ( NM_SEQUENCE = sSeqName )  
  INTO   @iSeqValue;  

  IF ( iIncrement <> 0 ) THEN  
    SET @iSeqValue = @iSeqValue + iIncrement;  

    UPDATE SEQUENCES SET VR_SEQUENCE = @iSeqValue  
    WHERE  ( NM_SEQUENCE = sSeqName );  
  END IF;

  RETURN @iSeqValue;
END :)  
DELIMITER ;  

-- SELECT GetSequenceVal('MySequence', 1);  -- Adds 1 to MySequence value and returns it.
-- ===================================================================


