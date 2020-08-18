package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;


/**
 * crate line between menu bars to divide them
 */
public class LineMenu extends JMenu
{
    public LineMenu(String temp)
    {
        super(temp);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new Color(71,71,71));
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(new Line2D.Double(this.getWidth()-1,0,this.getWidth()-1,this.getHeight()-1));
    }
}
