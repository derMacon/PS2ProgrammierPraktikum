# PS2ProgrammierPraktikum
Loesung fuer das Programmierpraktikum 2018 der FH-Wedel (Fach: Programmstrukturen 2)

Aktueller Stand der Gui:


![alt text](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/GUIScreenshot/Intro240918.png)
![alt text](https://github.com/derMacon/PS2ProgrammierPraktikum/blob/master/otherDocs/GUIScreenshot/220918.png)


Was noch fehlt: 

Generell
- Grundsaetzlich nochmal nachschauen ob das Datenformat zum Speichern / Laden dasselbe wie beim Testen ist.
- Herausfinden wie man eine Precondition kennzeichnet
- Sterne aus Importen entfernen (IDEA Einstellungen)

GUI - FXML
- rot box : dominos anpassen
- Zuordnunganzeige fehlt
- Weissen Rand um die komplette Anzeige damit es im Gesamtkontext besser passt
- Aktuelle Spieleranzeige beim Verkleinern des Fensters nicht mehr richtig zu sehen
- Pane anpassen, dass wenn groesser es immer noch passt
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
- imports am besten ohne Stern

Pos - class
- posFst rausnehmen

Player - class 
- Nochmal ueberpruefen ob 3 Konstruktoren wirklich noetig sind

game - class
- standardBoardSize brauchen wir nicht als member-variable, nur local im initializierungsschritt
    -> Initialisierung fndet nicht im Konstruktor statt, muessen festgehalten werden um in startGame() zu initialisieren.

Email an Kaleck: 

Sehr geehrte Fr. Kaleck, 
ich hätte einige Frage bezüglich des Programmierpraktikums. 

1. Ist es gestattet Pop-Up Fenster zu implementieren und diese mit eigenen FXML Dokumenten bzw. Controllern zu versehen? In der Aufgabenstellung steht in "EINER FXML-Datei müssen alle Bedienelemente enthalten sein...".
2. Darf man beim Abspeichern des Spiels in eine Textdatei den Typ des Spielers (Menschlich, Standard Gegner, etc.) mit in die Datei schreiben? In der Aufgabenstellung stehen nämlich nur die Felder, Bänke und der Beutel. Wie soll man dies lösen falls es nicht gestattet sein sollte und man mehr als 2 Spielertypen implementieren / laden und speichern möchte?
3. Sollen die Fehlermeldungen wenn man einen Domino außerhalb des Boards hält bereits bei der Vorabnahme nicht mehr auftreten, oder reicht es dies erst bei der Abgabe des fertiges Projekts erledigt zu haben?
4. Muss man sich an die Angabe der Domino Darstellung halten oder darf man auch den Wert der Belegung des jeweiligen Dominos (wie er uns in der Datei zur Verfügung gestellt wurde) mit angeben? Dies könnte z.B. so aussehen,

statt:
<Beutel>
P0P0, P0P0, A1H0...

das hier:
<Beutel>
P0P0_VALUE1, P0P0_VALUE2, A1H0_VALUE32...

Viele Grüße, 
Silas Hoffmann (inf103088)
