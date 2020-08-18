package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * draw lone under Text fields to make better appearance
 */
public class LinedJTextField extends JTextField
{
    public LinedJTextField(String temp)
    {
        super(temp);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new Color(60,63,65));
        g2d.setStroke(new BasicStroke(8));
        g2d.draw(new Line2D.Double(0,this.getHeight(),this.getWidth(),this.getHeight()));

        super.paintComponent(g);
    }
}
