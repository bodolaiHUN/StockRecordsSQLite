# StockRecordsSQLite

X SQLite adatbázis,
Teendő adatbázis szinkronizálása webre
Teendő adatbázis elérése PCről is,  (pl. Google Drive)
X bárkód (scan), terméknév, darabszám, minimális darabszám, helye, vásárlás/szavatosság ideje, értékelés
Teendő adatbevitel átalakítása, dialog
Teendő scannelés, az értékek tárolása a mobilon, összehasonlítás a tárolttal, ha van, a terméknév mező autómatikus kitöltése- SQLite lekérdezés, Barcode.class, tárolja a barcode-termek-minDarab értékeket,  lekéri-kitölti az adott barcode-hoz tartozó 2 értéket, ha nincs, üres
Teendő ha az adatbázisban szereplő darabszám eléri a minimális darabszámot, értesítés, időben előrejelzés (pl. fogkrém 34 nap alatt elfogyott, értesíteni előtte)
X szavatossági idő lejátára figyelmeztetés beállítása
X készlet megjelenítése a mobilon, mind/fogyóban/megromlóban
Teendő feltöltéskor ellenőrizni egy lekérdezéssel, az adott termék létezik-e már az összesTermék osztályban, ha nem, eltárolni a bárkóddal együtt
Teendő feltöltéskor ellenőrizni, az adott bárkód szerepel-e már, ha nem, eltárolni
Teendő scannelés, megjelenítés, bevitel, stb. átalakítása Fragmentté
X vanEhalozat osztály


Termek összesítés:
 
·  lekérni a Termek adatbázis tartalmát egy listába
·  ciklussal égigmenni a Termekek elemein
·  az adott Termekkel lekérdezés a Stock adatbázisból, Termek, Darab, minDarab
·  ciklussal végigmenni a lista elemein, a Stock adatbázisból a ciklus aktuális elemével  a Darab lekérdezése, osszDarab változót ezzel növelni
·  ha a ciklus lefutott, megjeleníteni a Termek, osszDarab, minDarab változókat
·  ha a Darab kevesebb, mint a minDarab, jelölni
·  következő Termekkel ismételni a ciklust
