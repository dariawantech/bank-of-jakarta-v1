--
-- Bank of Jakarta v1 (https://www.dariawan.com)
-- Copyright (C) 2018 Dariawan <hello@dariawan.com>
--
-- Creative Commons Attribution-ShareAlike 4.0 International License
--
-- Under this license, you are free to:
-- # Share - copy and redistribute the material in any medium or format
-- # Adapt - remix, transform, and build upon the material for any purpose,
--   even commercially.
--
-- The licensor cannot revoke these freedoms
-- as long as you follow the license terms.
--
-- License terms:
-- # Attribution - You must give appropriate credit, provide a link to the
--   license, and indicate if changes were made. You may do so in any
--   reasonable manner, but not in any way that suggests the licensor
--   endorses you or your use.
-- # ShareAlike - If you remix, transform, or build upon the material, you must
--   distribute your contributions under the same license as the original.
-- # No additional restrictions - You may not apply legal terms or
--   technological measures that legally restrict others from doing anything the
--   license permits.
--
-- Notices:
-- # You do not have to comply with the license for elements of the material in
--   the public domain or where your use is permitted by an applicable exception
--   or limitation.
-- # No warranties are given. The license may not give you all of
--   the permissions necessary for your intended use. For example, other rights
--   such as publicity, privacy, or moral rights may limit how you use
--   the material.
--
-- You may obtain a copy of the License at
--   https://creativecommons.org/licenses/by-sa/4.0/
--   https://creativecommons.org/licenses/by-sa/4.0/legalcode
--
-- PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
-- You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
--
-- PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
-- You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
--
-- Drop tables for the Bank of Jakarta online banking sample application.

ALTER TABLE TX DROP CONSTRAINT fk_tx_account_id;
ALTER TABLE CUSTOMER_ACCOUNT_XREF DROP CONSTRAINT fk_customer_id;
ALTER TABLE CUSTOMER_ACCOUNT_XREF DROP CONSTRAINT fk_account_id;
ALTER TABLE ACCOUNT DROP CONSTRAINT pk_account;
ALTER TABLE CUSTOMER DROP CONSTRAINT pk_customer;
ALTER TABLE TX DROP CONSTRAINT pk_tx;
ALTER TABLE NEXT_ID DROP CONSTRAINT pk_next_id;

DROP TABLE TX;
DROP TABLE CUSTOMER_ACCOUNT_XREF;
DROP TABLE ACCOUNT;
DROP TABLE CUSTOMER;
DROP TABLE NEXT_ID;
