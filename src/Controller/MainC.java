package Controller;
import GUI.MyWindow;
import javax.swing.*;
import java.io.IOException;

public class MainC
{
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        MyWindow gui = new MyWindow();
        gui.showGUI();

    }
}
