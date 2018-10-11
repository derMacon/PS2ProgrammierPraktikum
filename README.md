# PS2ProgrammierPraktikum
Loesung fuer das Programmierpraktikum 2018 der FH-Wedel (Fach: Programmstrukturen 2)

Aktueller Stand der Gui:


![alt text](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/GUIScreenshot/Intro240918.png)
![alt text](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/GUIScreenshot/290918.png)


Was noch fehlt: 

Generell
- Grundsaetzlich nochmal nachschauen ob das Datenformat zum Speichern / Laden dasselbe wie beim Testen ist.
- Herausfinden wie man eine Precondition kennzeichnet
- Sterne aus Importen entfernen (IDEA Einstellungen)
- Wie soll Fehlerbehandlung beim korrupten Dateien aussehen? (Lösungsmöglichkeiten: Popup, Logger)
- Fragen ob Arrays.asList() erlaubt
- Fragen ob Collections.sort() erlaubt

GUI - FXML
- rot box : dominos anpassen
- Buttons: Bilder mit Pfeilen
- (Cursor Symbol wechseln wenn ueber Domino)
- Fehlermeldungen beim Hovern ueber nicht erlaubt Stellen muessen entfernt werden

GUI - package
- Rotation Pane muss beim Rotieren angepasst werden
- Javadoc
- Kommentare vereinheitlichen

GUI2Game - Interface
- Verwerfen muss gefuellt werden

Tiles - class
- javadoc

Player - class 
- Nochmal ueberpruefen ob 3 Konstruktoren wirklich noetig sind

game - class
- Farbliche Hervorhebung ueberdenken, vielleicht Schalter zwischen Schwarz/Weiss und Farbe
- selectFromBank mit Rueckgabetyp Bank um klarzustellen, dass die Bank modifiziert wird. Dasselbe gilt fuer drawFromStack().

- dispose fehlt
- setupnextround mit Aktualisierung der punkte
- boxclicked println muss weg
- domFits muss weg
- gendefaultplayertype for bei 1 starten
- setupNextRound erst kopieren dann ziehen
- botsdotheir muss nach oben

Player - class
- Player constructor mit Board kann private sein (verschieben)
