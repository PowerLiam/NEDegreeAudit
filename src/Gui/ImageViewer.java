package Gui;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Chris on 11/18/2017.
 */
public class ImageViewer extends JFrame {
    JPanel main;
    ImagePanel view;
    public ImageViewer(Image i){
        super();
        view = new ImagePanel(i);
        this.add(view);
        this.setVisible(true);
        this.setSize(2000, 2000);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
