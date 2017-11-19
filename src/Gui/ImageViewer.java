package Gui;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Liam on 11/18/2017.
 */
public class ImageViewer extends JFrame {
    ImagePanel view;
    JScrollPane main;
    public ImageViewer(Image i){
        super();
        view = new ImagePanel(i);
        main = new JScrollPane(view);
        main.setPreferredSize(new Dimension(2300, 2000));
        main.setVerticalScrollBar(main.getVerticalScrollBar());
        this.add(main);
        this.setVisible(true);
        this.setSize(2300, 2000);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


}
