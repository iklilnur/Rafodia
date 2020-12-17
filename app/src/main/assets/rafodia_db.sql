BEGIN TRANSACTION;
DROP TABLE IF EXISTS "groceries";
CREATE TABLE IF NOT EXISTS "groceries" (
	"ID_groceries"	INTEGER,
	"tanggal_groceries"	TEXT,
	"item_qty"	INTEGER,
	PRIMARY KEY("ID_groceries" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "item";
CREATE TABLE IF NOT EXISTS "item" (
	"ID_item"	INTEGER,
	"nama_item"	TEXT NOT NULL,
	"kategori_item"	TEXT NOT NULL,
	"cara_penyimpanan"	TEXT NOT NULL,
	"tingkat_kesegaran"	TEXT NOT NULL,
	"waktu_kadaluarsa"	INTEGER NOT NULL,
	PRIMARY KEY("ID_item" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "groceries_item";
CREATE TABLE IF NOT EXISTS "groceries_item" (
	"ID_groceries_item"	INTEGER,
	"groceries_item_qty"	INTEGER NOT NULL,
	"ID_groceries"	INTEGER NOT NULL,
	"ID_item"	INTEGER NOT NULL,
	FOREIGN KEY("ID_groceries") REFERENCES "groceries"("ID_groceries"),
	FOREIGN KEY("ID_item") REFERENCES "item"("ID_item"),
	PRIMARY KEY("ID_groceries_item" AUTOINCREMENT)
);
INSERT INTO "item" VALUES (1,'wortel','SAYUR','Pilih Wortel yang segar, rendam dengan air dingin, bungkus dengan plastik, simpan dalam frezeer','wortel yang segar ditandai dengan permukaan yang halus dan tekstur yang tidak lembek',10);
COMMIT;
