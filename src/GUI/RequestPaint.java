package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class RequestPaint extends JLabel
{
    public RequestPaint(String temp)
    {
        super(temp);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new Color(105,94,184));
        g2d.setStroke(new BasicStroke(6));
        g2d.draw(new Line2D.Double(0,0,0,this.getHeight()-1));
    }
}
