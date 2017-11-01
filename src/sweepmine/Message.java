package sweepmine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * 
 * 
 * Created by LemoXiao  ---2017-10-28
 * 
 * This is a Message panel ,Include the informaton of all winners and rank
 * 
 * Divide to three part by the different Level
 * 
 * 
 */


public class Message extends JDialog implements ActionListener, MouseListener,
        Runnable {
	private static final long serialVersionUID = 1L;
	Font font1, font2;
    JButton bon1, bon2;
    JPanel panel, panel1;
    String[] str1, str2;
    JList<?> list;
    JLabel[] label;
    Record record;

    // Constuct Method
    public Message(String s) {
        setTitle(s);
        setModal(true);
    }

    //restruct the run method
    //read the information of record
    public void run() {
        String s;
        record = new Record();

        addPart();

        s = record.readTime(0);
        if (s.equalsIgnoreCase("")) {
            noRecord();
        } else {
            show(s);
            bon2.setEnabled(true);
        }
        s = record.readNumber(0);
        if (!s.equalsIgnoreCase(""))
            countWin(s);

        setBounds(350, 150, 600, 300);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }


    //  Setting the panel  && Add component to panel
    //  panel includes {list, * Label , } 
    public void addPart() {
        //Font Setting
        font1 = new Font("微软雅黑", 0, 14);
        font2 = new Font("微软雅黑", 1, 13);

        //Panel Setting
        panel = new JPanel();
        panel.setLayout(null);
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel1.setBackground(Color.white);

        //List Setting
        str1 = new String[] { "Basis", "Inter.", "Advance" };
        list = new JList(str1);
        list.setFont(font1);
        list.setSelectedIndex(0);
        list.addMouseListener(this);
        list.setBounds(40, 40, 100, 60);
        panel.add(list);

        //Label Setting
        str2 = new String[] { "Fastest:", "All Point:", "Win Point:", "win rat:",
                "", "", "",
                "", "" };
        label = new JLabel[9];
        for (int i = 0; i < 9; i++) {
            label[i] = new JLabel(str2[i]);
            if (i < 4) {
                label[i].setFont(font1);
                panel.add(label[i]);
            } else {
                label[i].setFont(font2);
                panel1.add(label[i]);
            }
        }
        label[0].setBounds(200, 25, 100, 50);
        label[1].setBounds(450, 25, 100, 50);
        label[2].setBounds(450, 50, 100, 50);
        label[3].setBounds(450, 75, 100, 50);
        // label[4].setBounds(200, 45, 100, 50);
        panel.add(panel1);
        panel1.setBounds(200, 65, 220, 110);

        //Setting the Button of Confirm and Reset
        bon1 = new JButton("Confirm");
        bon1.setFont(font1);
        bon1.addActionListener(this);
        bon1.setBounds(250, 200, 100, 30);
        panel.add(bon1);
        bon2 = new JButton("Reset");
        bon2.setFont(font1);
        bon2.setEnabled(false);
        bon2.addActionListener(this);
        bon2.setBounds(400, 200, 100, 30);
        panel.add(bon2);
        add(panel, BorderLayout.CENTER);
    }


// range the time of sweeping mines
    public void sort(String[] str) {
        int min;
        int temp;
        String temp1;
        int[] num = new int[(str.length / 2)];

        for (int i = 0; i < str.length; i++) {
            if (i % 2 == 0) {
                num[i / 2] = Integer.parseInt(str[i]);
            }
        }

        for (int index = 0; index < num.length; index++) {
            min = index;
            for (int scan = index + 1; scan < num.length; scan++) {
                if (num[scan] < num[min])
                    min = scan;
            }
            temp = num[min];
            num[min] = num[index];
            num[index] = temp;
            temp1 = str[min * 2];
            str[min * 2] = str[index * 2];
            str[index * 2] = temp1;
            temp1 = str[min * 2 + 1];
            str[min * 2 + 1] = str[index * 2 + 1];
            str[index * 2 + 1] = temp1;
        }

    }


// Calculate the percent of winning the game
    public void countWin(String s) {
        String ss;
        double d;
        DecimalFormat df = new DecimalFormat("00%");
        String[] str = s.split("<>");
        int total = str.length;
        int win = 0;

        for (int i = 0; i < str.length; i++) {
            if (str[i].equalsIgnoreCase("1"))
                win++;
        }
        d = (double) win / total;
        ss = df.format(d);
        label[1].setText("All Point:" + total);
        label[2].setText("WIN Point:" + win);
        label[3].setText("win rat:" + ss);

    }

    // Judge Record is empty
    public void noRecord() {
        label[1].setText("All Point: 0");
        label[2].setText("WIN Point: 0");
        label[3].setText("win rat: 0%");
        for (int i = 4; i < 9; i++) {
            label[i].setText("");
        }
    }

    // Judge Record is not empty
    public void show(String s) {
        String[] str = s.split("<>");
        sort(str);
        for (int i = 0; i < 5; i++)
            label[i + 4].setText("");
        if (str.length >= 10) {
            for (int i = 0; i < 5; i++) {
                label[i + 4].setText((i + 1) + ":" + str[i * 2] + "  "
                        + str[i * 2 + 1]);
            }
        } else {
            for (int i = 0; i < str.length / 2; i++) {
                label[i + 4].setText((i + 1) + ":" + str[i * 2] + "  "
                        + str[i * 2 + 1]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Press Confirm then exit
        if (e.getSource() == bon1) {
            dispose();
        }
        // Reset the statistical information
        if (e.getSource() == bon2) {
            int n = JOptionPane.showConfirmDialog(this, "Reset the statistical information to 0",
                    "Reset the statistical information", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                record.clear();
                noRecord();
                bon2.setEnabled(false);
            }
        }

    }

    // The function about Mouse' pressing
    public void mousePressed(MouseEvent e) {
        String s;
        if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
            // read the information about Level.Basis
            if (list.getSelectedIndex() == 0) {
                s = record.readTime(0);
                if (s.equalsIgnoreCase("")) {
                    noRecord();
                } else {
                    show(s);
                    bon2.setEnabled(true);
                }
                s = record.readNumber(0);
                if (!s.equalsIgnoreCase(""))
                    countWin(s);
            }
            // read the information about Level.Intermediate 
            if (list.getSelectedIndex() == 1) {
                s = record.readTime(1);
                if (s.equalsIgnoreCase("")) {
                    noRecord();
                } else {
                    show(s);
                    bon2.setEnabled(true);
                }
                s = record.readNumber(1);
                if (!s.equalsIgnoreCase(""))
                    countWin(s);
            }
            // read the information about Level.Advance
            if (list.getSelectedIndex() == 2) {
                s = record.readTime(2);
                if (s.equalsIgnoreCase("")) {
                    noRecord();
                } else {
                    show(s);
                    bon2.setEnabled(true);
                }
                s = record.readNumber(2);
                if (!s.equalsIgnoreCase(""))
                    countWin(s);
            }
        }
    }


    //Event of MouseClicking Default Setting
    //Method restruction
    public void mouseClicked(MouseEvent arg0) {

    }

    public void mouseEntered(MouseEvent arg0) {

    }

    public void mouseExited(MouseEvent arg0) {

    }

    public void mouseReleased(MouseEvent arg0) {

    }

}
