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



        String input = "Spielfeld 1>\n";
        String[] temp = input.split(">\n");
        System.out.println(input);


    }

}
