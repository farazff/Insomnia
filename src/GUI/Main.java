package GUI;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main
{
   public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
      MyWindow temp = new MyWindow();
      temp.showGUI();
   }
}