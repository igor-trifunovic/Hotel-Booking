-- Hotel.imageURL was added to the entity without a migration, so databases differ:
-- some have no column at all, others got one by hand (e.g. `imageURL`/`imageurl`).
-- This brings every database to the same end state: hotel.image_url VARCHAR(512) NULL.
-- MySQL has no ADD COLUMN IF NOT EXISTS, hence the information_schema check.

SET @has_image_url := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'hotel' AND LOWER(column_name) = 'image_url'
);

SET @has_imageURL := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'hotel' AND LOWER(column_name) = 'imageurl'
);

SET @ddl := CASE
    WHEN @has_image_url > 0 THEN 'ALTER TABLE hotel MODIFY COLUMN image_url VARCHAR(512) NULL'
    WHEN @has_imageURL  > 0 THEN 'ALTER TABLE hotel CHANGE COLUMN imageURL image_url VARCHAR(512) NULL'
    ELSE                         'ALTER TABLE hotel ADD COLUMN image_url VARCHAR(512) NULL AFTER description'
END;

PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;