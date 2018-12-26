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


    public static void main(String[] args) {


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
