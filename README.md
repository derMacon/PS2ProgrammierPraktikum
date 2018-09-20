# PS2ProgrammierPraktikum
Loesung fuer das Programmierpraktikum 2018 der FH-Wedel (Fach: Programmstrukturen 2)

Aktueller Stand der Gui:
![alt text](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/GUIScreenshot/200918.png)


Was noch fehlt: 
GUI - FXML
- rot box : dominos anpassen
- dunkler Hintergrund
- Spielstandanzeige fehlt
- Zuordnunganzeige fehlt
- Aktuelle Spieleranzeige fehlt
- Pane anpassen, dass wenn groesser 
- Neue Schrift
- Buttons: Bilder mit Pfeilen
- (Cursor Symbol wechseln wenn ueber Domino)

GUI - package
- Javadoc
- controller: stackoverflow quelle weg/Kommentare loeschen
- Kommentare vereinheitlichen
- Sterne aus Importen entfernen (IDEA Einstellungen)
- logger aus der JavafxGui loeschen


Connector - interface
- updatePlayer: mit ord Zahl
- showPointsForPlayer: mit ord Zahl, ohne Punktangabe (sind schon im Spieler)
- setDominoOnGui kann weg (doppelt)
- selectDomino(ord bank, int idx, int ordPlayer) Fehlt
- blurBank(int ordBank)
- deleteDomFromBank(int ordBank, int idx)
- setColorForArrows(int idx, Mode ...)

GUI2Game - Interface
- 2 getrennte fkt. fuer das Selektieren (auswaehlen des Dominos)
- Pfeiltasten, Menuepunkte fehlen
- Verwerfen muss gefuellt werden

Tiles - class
- javadoc
- imports am besten ohne Stern
- Konstante anpassen oder entfernen

Pos - class
- Konstante fuer Richtungen getNeighbors
- posFst rausnehmen

Entry - class
- Javadoc fehlt	

Bank - class
- javadoc fehlt
- Konstante pruefen current/next bank
- selectEntry mit isNotSelected versehen
- getEntry(int idx) fehlt
- deleteEntry(int idx) fehlt
- drawFromStack, groesse vom Stack pruefen
- pseudo Random objekt erstellen und bei Bank im Testkonstruktor setzen

board - class
- Konstante raus
- SingleTile.values() als finales attribut array und ord ueberpruefen

District - class
- isNextToDistrict... mit Domino als Param. ist falsch -> weg
- diamand operator beim initialisieren -> Und in Konstr. schieben.
- Districts wurden in keinem Konstruktor gesetzt.

- Grundsaetzlich nochmal nachschauen ob das Datenformat zum Speichern / Laden dasselbe wie beim Testen ist.
