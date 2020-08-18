package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * fill the rectangle
 */
public class JPanelPaint extends JPanel
{
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(new GradientPaint(0,0,new Color(105,95,184),100,90,Color.BLACK,true));
        g2d.fill(new Rectangle2D.Double(0,0,this.getWidth(),this.getHeight()));
    }
}
