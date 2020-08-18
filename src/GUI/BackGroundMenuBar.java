package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * draw color on the backGround of a JMenuBar
 */
class BackgroundMenuBar extends JMenuBar
{
    private Color bgColor;

    /**
     * get the color and set it
     * @param temp the first color
     */
    public BackgroundMenuBar(Color temp)
    {
        super();
        bgColor = temp;
    }

    /**
     * change the color
     * @param temp the new color
     */
    public void changeColor(Color temp)
    {
        bgColor = temp;
        paintComponent(this.getGraphics());
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if(g != null)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(bgColor);
            g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

    }
}