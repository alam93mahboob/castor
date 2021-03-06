-- Only for MSSQL SERVER 2008 (DATE type support)
DROP TABLE TEST2567_ENTITY
GO

CREATE TABLE TEST2567_ENTITY(
	SALESORDERNUMBER varchar(10),
	SEQNO varchar(3),
	LINEITEM varchar(2),
	PARTID varchar(32),
	PARTNAME varchar(29),
	PARTVERSION varchar(3),
	ORIGDATEREQD date,
	CURDATEREQD date,
	CUSTOMERNAME varchar(30),
	CUSTORDERNUMBER varchar(20),
	ORDERSTATE varchar(1),
	DATECREATED date,
	DATECHANGED date,
	ORIGQTYREQD float,
	CUSTOMERQTYREQD float,
	CURQTYREQD float,
	PRICEPERPART float,
	QTYSHIPPED float,
	LOADDATE date
)
GO
INSERT INTO TEST2567_ENTITY (
SALESORDERNUMBER, SEQNO, LINEITEM, PARTID, PARTNAME, PARTVERSION,
ORIGDATEREQD, CURDATEREQD, CUSTOMERNAME, CUSTORDERNUMBER, ORDERSTATE,
DATECREATED, DATECHANGED, ORIGQTYREQD, CUSTOMERQTYREQD, CURQTYREQD,
PRICEPERPART, QTYSHIPPED, LOADDATE
) VALUES (
'EM71130021', '001', '01', 'PART-30021', 'PART-30021', null,
'2005-01-03', '2005-01-03', 'EXTERN', '12701', 'B',
'2003-08-04', '2004-04-27', 0, 0, 12701,
0, 0, '2004-04-26'
);
