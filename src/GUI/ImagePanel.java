package GUI;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * put image on  a JPanel
 */
public class ImagePanel extends JPanel
{

    private BufferedImage image;

    public ImagePanel(String adress)
    {
        try
        {
            image = ImageIO.read(new File(adress));
        }
        catch (IOException ex)
        {
            // handle exception...
        }
    }

    /**
     * change the image of a JPanel
     * @param adress the address of the image
     */
    public void changeImage(String adress)
    {
        try
        {
            image = ImageIO.read(new File(adress));
        }
        catch (IOException ex)
        {
            // handle exception...
        }
        finally
        {
            paintComponent(this.getGraphics());
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if(g!=null)
        {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this); // see javadoc for more info on the parameters
        }
    }

}