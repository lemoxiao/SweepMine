package sweepmine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * 
 * Created by LemoXiao  ---2017-10-26
 * This is a level setting panel,include the number of the block and mines    
 * // set 4 radiobuttons  add to the button group ,add to the pane
 * // set 9 Label ,3 text windows, 2 Button:"confirm' and "cancel",add to the pane
 * //
 * 
 * supplement：we add the user-defined mode to add the fun of game
 * supplement: we add the shortcut key function to make the panel more similar likes the game 
 * 
 */

public class Setting extends JDialog implements ActionListener, KeyListener,
        Runnable {

	private static final long serialVersionUID = 1L;
	JButton bon1, bon2;
    ButtonGroup group;
    Font font;
    JLabel[] label;
    JRadioButton[] radio;
    JPanel panel;
    int level;
    String[] str, str1;
    TextField[] file;
    SweepMine mine;

    public Setting(SweepMine mine, int level, String s) {
        setTitle(s);
        setModal(true);
        this.level = level;
        this.mine = mine;
    }

    // Setting the panel font and location
    public void addPart() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        font = new Font("微软雅黑", 1, 14);

    // setting the radiobutton and add to the group and panel
    // set the location and actionlistener
        radio = new JRadioButton[4];
        str1 = new String[] { "10 Mine", "40 Mine", "99 Mine", "Define(U)" };
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radio[i] = new JRadioButton(str1[i]);
            // radio1.setBounds(30,30, 100, 20);
            radio[i].setFont(font);
            radio[i].addActionListener(this);
            // radio[].setSelected(true);
            group.add(radio[i]);
            panel.add(radio[i]);
            radio[i].addKeyListener(this);
        }
        radio[0].setBounds(30, 30, 100, 20);
        radio[1].setBounds(30, 110, 100, 20);
        radio[2].setBounds(30, 190, 100, 20);
        radio[3].setBounds(260, 10, 100, 20);
        radio[level].setSelected(true);

        // setting the label font\location  and add to the panel
        label = new JLabel[9];
        str = new String[] { "Base(B)", "9*9 Grid", "Inter.(I)", "16*16 Grid", "Advance(V)",
                "16*30 Grid", "Height(9-24):", "width(9-40):", "Mine(0-200):" };

        for (int i = 0; i < 9; i++) {
            label[i] = new JLabel(str[i]);
            label[i].setFont(font);
            label[i].setBackground(Color.white);
            panel.add(label[i]);
        }

        label[0].setBounds(50, 10, 100, 20);
        label[1].setBounds(50, 50, 100, 20);
        label[2].setBounds(50, 90, 100, 20);
        label[3].setBounds(50, 130, 100, 20);
        label[4].setBounds(50, 170, 100, 20);
        label[5].setBounds(50, 210, 100, 20);
        label[6].setBounds(280, 50, 100, 20);
        label[7].setBounds(280, 85, 100, 20);
        label[8].setBounds(280, 120, 100, 20);

        //add three textfield to set the hight\width\mine number 

        file = new TextField[3];
        for (int i = 0; i < 3; i++) {
            file[i] = new TextField();
            file[i].setFont(font);
            file[i].addActionListener(this);
            file[i].addKeyListener(this); 

            panel.add(file[i]);
        }
        file[0].setBounds(390, 50, 60, 25);
        file[1].setBounds(390, 85, 60, 25);
        file[2].setBounds(390, 120, 60, 25);
        if (level == 3) {
            setFile(true);
            setNumber(mine.getRow() - 2, mine.getColumn() - 2, mine.getMine());
        } else {
            setFile(false);
            setNumber(24, 40, 200);
        }

        //add two button to make the setting coming into force
        bon1 = new JButton("Confirm");
        bon1.setFont(font);
        bon1.addActionListener(this);
        bon1.setBounds(200, 300, 100, 30);
        panel.add(bon1);
        bon2 = new JButton("Cancel");
        bon2.setFont(font);
        bon2.addActionListener(this);
        bon2.setBounds(350, 300, 100, 30);
        panel.add(bon2);

        add(panel, BorderLayout.CENTER);
    }

    // Setting the usable the custom bar
    public void setFile(boolean flag) {
        file[0].setEditable(flag);
        file[1].setEditable(flag);
        file[2].setEditable(flag);
        file[0].setEnabled(flag);
        file[1].setEnabled(flag);
        file[2].setEnabled(flag);
    }

    // Setting the number of text
    public void setNumber(int a, int b, int c) {
        file[0].setText(a + "");
        file[0].setFont(font);
        file[1].setText(b + "");
        file[1].setFont(font);
        file[2].setText(c + "");
        file[2].setFont(font);
    }

    // select the level of difficulties  
    public void actionPerformed(ActionEvent e) {
        
        int a = 0;
        // Cancel Button
        if (e.getSource() == bon2) {
            a = 1;
            dispose();
        }
        // Basis
        if (radio[0].isSelected()) {
            setFile(false);
            setNumber(24, 40, 200);
            if (e.getSource() == bon1) {
                mine.firstLevel();
                dispose();
            }
        }

        // Intermediate
        if (radio[1].isSelected()) {
            setFile(false);
            setNumber(24, 40, 200);
            if (e.getSource() == bon1) {
                mine.secondLevel();
                dispose();
            }
        }

        // Advance
        if (radio[2].isSelected()) {
            setFile(false);
            setNumber(24, 40, 200);
            if (e.getSource() == bon1) {
                mine.thirdLevel();
                dispose();
            }
        }

        //  User-defined
        if (radio[3].isSelected()) {
            setFile(true);
            try {
                int s1 = Integer.parseInt(file[0].getText()) + 2;
                int s2 = Integer.parseInt(file[1].getText()) + 2;
                int s3 = Integer.parseInt(file[2].getText());
                if (e.getSource() == bon1) {
                    if (s1 > 10 && s1 < 27 && s2 > 10 && s2 < 43 && s3 > 9
                            && s3 < 901 && s3 < (s1 - 2) * (s2 - 2)) {
                        mine.setLevel(s1, s2, s3);
                        dispose();
                    } else if (s3 >= (s1 - 2) * (s2 - 2))
                        JOptionPane.showMessageDialog(this, "The number of mine is wrong !!!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(this, "The nubmer out of ran !", "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e1) {
                if (a == 0)
                    JOptionPane.showMessageDialog(this, "Please input the number !", "Error",
                            JOptionPane.ERROR_MESSAGE);
            }
        }

    }


    public void run() {
        addPart();
        setBounds(450, 150, 500, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // monitoring the shortcut key input and make key's function is working 
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 'B') {
            radio[0].setSelected(true);
            setFile(false);
        }
        if (e.getKeyCode() == 'I') {
            radio[1].setSelected(true);
            setFile(false);
        }
        if (e.getKeyCode() == 'V') {
            radio[2].setSelected(true);
            setFile(false);
        }
        if (e.getKeyCode() == 'U') {
            radio[3].setSelected(true);
            setFile(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            bon1.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            bon2.doClick();
        }

    }

    public void keyReleased(KeyEvent arg0) {

    }

    public void keyTyped(KeyEvent e) {

    }

}
