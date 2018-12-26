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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Doodle {
    private static final String PATH_FORMAT =
            "test" + File.separator + "fileTests" + File.separator + "testdata" + File.separator + "%s" + ".txt";



    /**
     * Index for the Description in the two-dim String array
     */
    public static final int DESCRIPTION_IDX = 0;

    /**
     * Index for the Data in the two-dim String array
     */
    public static final int DATA_IDX = 1;

    /**
     * Identifier for the boards
     */
    public static final String BOARD_IDENTIFIER = "Spielfeld";

    /**
     * Identifier for both banks
     */
    // TODO Problem mit dem ä beim Einlesen
    public static final String BANK_IDENTIFIER = "Bänke"; //"B�nke";

    /**
     * Identifier for the stack
     */
    public static final String STACK_IDENTIFIER = "Beutel";
    public static final String TAG_CLOSER = ">";


    public static void main(String[] args) {


        String input = "<Spielfeld 1>\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- CC P0 S2\n" +
                "-- -- I2 P0 --\n" +
                "-- -- -- -- --\n" +
                "<Spielfeld 2>\n" +
                "-- -- P0 -- --\n" +
                "-- -- I3 -- --\n" +
                "A0 S1 CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "<Spielfeld 3>\n" +
                "-- -- O0 -- --\n" +
                "-- -- I2 -- --\n" +
                "A0 S2 CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "<Spielfeld 4>\n" +
                "P0 O1 O0 -- --\n" +
                "-- -- I2 -- --\n" +
                "-- -- CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "<Bänke>\n" +
                "0 S0O1,2 I1P0\n" +
                "3 A1H0,- A1H0,- A1H0,1 P0S1\n" +
                "<Beutel>\n";



        System.out.println(input.startsWith("\uFEFF<"));

        String[] temp = input.split(">\n");
        System.out.println(input.matches("Spielfeld.*>(?s)[^>]*"));
        System.out.println(">".matches("[^>]*"));


    }

}
