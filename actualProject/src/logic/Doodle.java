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

        String input = "Spielfeld 1>\nasdfasdf";
        System.out.println(input);

        input = input.replaceAll("[" + BOARD_IDENTIFIER + "|"
                + BANK_IDENTIFIER + "|" + STACK_IDENTIFIER + "]" + ".*" + TAG_CLOSER
                + "\n", "");

        System.out.println(input);

        try {
            System.out.println("ä");

            String filename = "inv_blankAfterPlayerReferenceNum";
            String temp = String.format(PATH_FORMAT, filename);
            File fileDir = new File(temp);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));

            String str;

            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }

            in.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
