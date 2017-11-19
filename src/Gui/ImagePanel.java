package Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Chris on 11/18/2017.
 */
public class ImagePanel extends JPanel{
    Image myImage;

    public ImagePanel(Image toshow){
        myImage = toshow;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setPreferredSize(new Dimension(2200, 10000));
        g.drawImage(myImage, 0, 0, this);
    }
}
