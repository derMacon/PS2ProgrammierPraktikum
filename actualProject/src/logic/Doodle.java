package logic;

import gui.FXMLDocumentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.logicTransfer.Converter;
import logic.token.Tiles;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Doodle {

    public static void main(String[] args) {
        String tag = "Spielfeld";
        String input1 = "Spielfeld 1>\n87987 98 <";
        System.out.println(input1.replaceAll("\n", " ").matches(tag + ".*>.*"));
        String[] out = input1.split("\n");


    }

}
