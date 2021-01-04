BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "groceries" (
	"ID_groceries"	INTEGER,
	"tanggal_groceries"	TEXT,
	"item_qty"	INTEGER,
	PRIMARY KEY("ID_groceries" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "groceries_item" (
	"ID_groceries_item"	INTEGER,
	"groceries_item_qty"	INTEGER NOT NULL,
	"ID_groceries"	INTEGER NOT NULL,
	"ID_item"	INTEGER NOT NULL,
	FOREIGN KEY("ID_item") REFERENCES "item"("ID_item"),
	FOREIGN KEY("ID_groceries") REFERENCES "groceries"("ID_groceries"),
	PRIMARY KEY("ID_groceries_item" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "item" (
	"ID_item"	INTEGER,
	"nama_item"	TEXT NOT NULL,
	"kategori_item"	TEXT NOT NULL,
	"cara_penyimpanan"	TEXT NOT NULL,
	"tingkat_kesegaran"	TEXT NOT NULL,
	"waktu_kadaluarsa"	INTEGER NOT NULL,
	"satuan"	TEXT NOT NULL,
	PRIMARY KEY("ID_item" AUTOINCREMENT)
);
INSERT INTO "item" VALUES (1,'Wortel','sayuran','Simpan wortel di dalam kulkas','Wortel yang segar ditandai dengan permukaan yang halus dan warna yang cerah.',10,'kg');
INSERT INTO "item" VALUES (2,'Kentang','sayuran','Simpan kentang di tempat yang kering dan jauhkan dari sinar matahari','Kentang yang baik memiliki permukaan kulit yang halus.',7,'kg');
INSERT INTO "item" VALUES (3,'Tomat','sayuran','Simpan tomat ke dalam plastik, masukkan ke kulkas.','Pilih tomat yang memiliki ukuran besar dan memiliki kulit bersih.',14,'kg');
INSERT INTO "item" VALUES (4,'Daging Sapi','daging','Simpan daging di dalam freezer tanpa perlu mencucinya terlebih dahulu.','Daging yang segar memiliki warna merah terang.',90,'kg');
INSERT INTO "item" VALUES (5,'Apel','buah','Simpan apel di dalam kulkas.','Pilih apel yang memiliki tekstur keras.',14,'kg');
INSERT INTO "item" VALUES (6,'Udang','seafood','Bersihkan udang, simpan ke dalam freezer.','Udang yang segar ditandai dengan daging yang kencang dan keras.',90,'kg');
INSERT INTO "item" VALUES (7,'Telur','lain-lain','Simpan telur di dalam kulkas.','Pilih telur yang memiliki warna cangkang yang cerah dan pekat.',20,'kg');
COMMIT;
