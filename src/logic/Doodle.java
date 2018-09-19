package logic;

import logic.token.Tiles;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Doodle {

    public static void main(String[] args) {
        for(Tiles currTile : Tiles.values()) {
            System.out.println(currTile + " (" + currTile.getFst().ordinal() + " / " + currTile.getSnd().ordinal() + ")");
        }
    }
}
