# PS2ProgrammierPraktikum
Loesung fuer das Programmierpraktikum 2018 der FH-Wedel (Fach: Programmstrukturen 2)
### Aufgabenstellung:
Zu implementieren ist ein Dominospiel, bei dem vier Spieler jeweils ihre eigene Stadt gestalten. Ziel des Spiels ist es, möglichst viele Stadtteile mit Prestige zu gestalten. ([komplette Aufgabenstellung](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/InfosProgrammierpraktikum/Aufgabenstellung_SS18_%20CityDomino.pdf))
# Links
- Subversion Repository: https://stud.fh-wedel.de/repos/uebungen/ppss/ppss_52
- Link zur Website: http://intern.fh-wedel.de/mitarbeiter/klk/programmierpraktikum-java/

### Aktueller Stand der Gui: 

![alt text](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/GUIScreenshot/Intro121018.png)
![alt text](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/GUIScreenshot/Main141018.png)

[Verlauf der Entwicklung](https://github.com/derMacon/PS2ProgrammierPraktikum/tree/master/otherDocs/GUIScreenshot)

## Was noch fehlt: 

### Generell
- Grundsaetzlich nochmal nachschauen ob das Datenformat zum Speichern / Laden dasselbe wie beim Testen ist.
- Herausfinden wie man eine Precondition kennzeichnet
- Sterne aus Importen entfernen (IDEA Einstellungen)
- Wie soll Fehlerbehandlung beim korrupten Dateien aussehen? (Lösungsmöglichkeiten: Popup, Logger)
- Fragen ob Arrays.asList() erlaubt
- Fragen ob Collections.sort() erlaubt
- AI bei Punktegleichheit auf moeglichst wenig kleine Felder achten (Wichtig, nicht vergessen. Zugregeln nochmal nachschlagen)

### GUI - FXML
- rot box : dominos anpassen
- Buttons: Bilder mit Pfeilen
- (Cursor Symbol wechseln wenn ueber Domino)
- Fehlermeldungen beim Hovern ueber nicht erlaubt Stellen muessen entfernt werden

### GUI - package
- Rotation Pane muss beim Rotieren angepasst werden
- Javadoc
- Kommentare vereinheitlichen

### GUI2Game - Interface
- Verwerfen muss gefuellt werden

### Tiles - class
- javadoc

### Player - class 
- Nochmal ueberpruefen ob 3 Konstruktoren wirklich noetig sind

### game - class
- Farbliche Hervorhebung ueberdenken, vielleicht Schalter zwischen Schwarz/Weiss und Farbe
- selectFromBank mit Rueckgabetyp Bank um klarzustellen, dass die Bank modifiziert wird. Dasselbe gilt fuer drawFromStack().
- dispose fehlt
- setupnextround mit Aktualisierung der punkte
- boxclicked println muss weg
- domFits muss weg
- gendefaultplayertype for bei 1 starten
- setupNextRound erst kopieren dann ziehen
- botsdotheir muss nach oben

### Player - class
- Player constructor mit Board kann private sein (verschieben)
