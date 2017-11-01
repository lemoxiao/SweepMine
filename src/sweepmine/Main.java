package sweepmine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Created by Lemoxiao  ---2017-10-27
 * This is The MainFunction ,When the program start to running ,it will be used.
 */

public class Main {
    public static void main(String[] args) {
        
        //creat a example of frame for the mainpane
        final SweepMine frame = new SweepMine("Mine-Sweeping");

        //The adjust of Windows's Size
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);

        //add the closing mode default
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //add a listener for mainpane 
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (!frame.bon[1].getText().equalsIgnoreCase("0")) {
                    int a = JOptionPane.showConfirmDialog(null, "The game is in progress, whether to confirm exit game?","Confirm", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {
                        frame.record.writeLevel(frame.level);
                        if (frame.level == 3)
                            frame.record.writeLevel4(frame.row, frame.column, frame.mine);
                        System.exit(0);
                    }

                } else {
                    frame.record.writeLevel(frame.level);
                    if (frame.level == 3)
                        frame.record.writeLevel4(frame.row, frame.column, frame.mine);
                    System.exit(0);
                }
            }
        });

    }
}
