package sweepmine;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Lemoxiao  ---2017-10-24
 * This is a FailPane,When Press on the bomb ,it will resonper.
 */
public class FailPane extends JDialog implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SweepMine frame;
    Font font;
    JButton bon1, bon2, bon3;
    JLabel label;
    JPanel panel;
 
    /**
     * Set a pane for the failed user    Part 1
     * 
     */
    public FailPane(String s, SweepMine frame) {
        this.frame = frame;
        setTitle(s);
        addPart();
        setBounds(450, 200, 500, 200);
        close();
        setModal(true);
        setVisible(true);

    }
    
    /**
	 * Set Close Mode               Part 2
	 */
 
    public void close() {
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.newGame();
                dispose();
            }
        });
    }

    public void addPart() {
        panel = new JPanel();
        // panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        panel.setLayout(null);

        font = new Font("微软雅黑", 1, 18);

        label = new JLabel("I'm sorry, You lose !!");
        label.setFont(font);
        label.setBounds(100, 10, 300, 50);
        panel.add(label);

        bon1 = new JButton("New");
        bon1.setFont(font);
        bon1.addActionListener(this);
        bon1.setMargin(new Insets(0, 0, 0, 0));
        panel.add(bon1);
        bon1.setBounds(30, 100, 120, 40);

        bon2 = new JButton("Restart");
        bon2.setFont(font);
        bon2.addActionListener(this);
        bon2.setMargin(new Insets(0, 0, 0, 0));
        panel.add(bon2);
        bon2.setBounds(190, 100, 120, 40);

        bon3 = new JButton("Exit");
        bon3.setFont(font);
        bon3.addActionListener(this);
        bon3.setMargin(new Insets(0, 0, 0, 0));
        panel.add(bon3);
        bon3.setBounds(350, 100, 120, 40);

        add(panel, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e) {
       
        /**
	     ** Press the button and start a new game
	     */
        if (e.getSource() == bon1) {
            frame.newGame();
            dispose();
        }

        // Press the button and restart agme
        if (e.getSource() == bon2) {
            frame.restart();
            dispose();
        }
        //Press the button and exit the program
        if (e.getSource() == bon3) {
            frame.record.writeLevel(frame.level);
            if (frame.level == 3)
                frame.record.writeLevel4(frame.row, frame.column, frame.mine);
            System.exit(0);
        }

    }

}
