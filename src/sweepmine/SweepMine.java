package sweepmine;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;


/**
 * 
 * 
 * Created by LemoXiao  ---2017-10-26
 * 
 * This is main part of the game,calling the other function file 
 * 
 * This is a Sweeping Part,Include the arithmetic and the graph of the Sweeping mine.
 * 
 * 
 * 
 */

public class SweepMine extends JFrame implements ActionListener, MouseListener {

    AudioClip[] aau;
    AudioClip aau1;
    boolean flag, tt, judgefirst;
    int row, column, mine;
    int flagCount, time;
    Font font1, font2;
    ImageIcon[] icon, iconChange;
    int num, num1, num2, mineEnd, bonEnd, level;
    int[][] isMine, isEnable, rightClick, right;
    int[] count1, count2;
    JPanel panel1, panel2, panel3, panel4, panel5;
    JButton[][] button;
    JButton[] bon;
    // JCheckBoxMenuItem startmusic;     //Initialize the game sound    
    JLabel label1, label2;
    JMenuBar bar;
    JMenu menu1, menu2, menu3;
    JMenuItem[] item;
    JToolBar toolbar;
    Random ron;
    Record record;
    String[] str;
    Timer timer;

    // Construction Method include the record\aau\font\icon\
    public SweepMine(String s) {
        super(s);
        setLook();
        record = new Record();
        aau = new AudioClip[5];
        font1 = new Font("微软雅黑", 0, 18);
        font2 = new Font("微软雅黑", 1, 16);
        //initialize the Pic
        icon = new ImageIcon[16];
        iconChange = new ImageIcon[16];
        for (int i = 0; i < 16; i++)
            icon[i] = new ImageIcon("icon/" + i + ".jpg");
        getLevel();
        music();
        setResizable(false);

    }


    // Set the number of row
    public void setRow(int row) {
        this.row = row;
    }

    // Set the number of Column
    public void setColumn(int column) {
        this.column = column;
    }

    // Set the number of mine
    public void setMine(int mine) {
        this.mine = mine;
    }

    // get the number of Row
    public int getRow() {
        return row;
    }

    // Get the number of Column
    public int getColumn() {
        return column;
    }

    // Get the number of Mine
    public int getMine() {
        return mine;
    }

    // Get the Difficult number of level and set the number of block
    public void getLevel() {
        if (!record.readLevel().equalsIgnoreCase("")) {
            int i = Integer.parseInt(record.readLevel());
            level = i;
            if (i == 0) {
                setRow(11);
                setColumn(11);
                setMine(10);
                fresh();
                addPart(50, 50);
                arrangeMine();
                countTime();
            }
            if (i == 1) {
                setRow(18);
                setColumn(18);
                setMine(40);
                fresh();
                addPart(35, 35);
                arrangeMine();
                countTime();
            }
            if (i == 2) {
                setRow(18);
                setColumn(32);
                setMine(99);
                fresh();
                addPart(35, 35);
                arrangeMine();
                countTime();
            }
            if (i == 3) {
                String s = record.readLevel4();
                String[] str = s.split("<>");
                setRow(Integer.parseInt(str[0]));
                setColumn(Integer.parseInt(str[1]));
                setMine(Integer.parseInt(str[2]));
                fresh();
                addPart4(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
                arrangeMine();
                countTime();
            }
        } else {
            setRow(11);
            setColumn(11);
            setMine(10);
            fresh();
            addPart(50, 50);
            arrangeMine();
            countTime();
            level = 0;
        }
    }

    // Set the default Appearance 
    public void setLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    //  Variable Initialization == clear the mine and present record 
    public void fresh() {
        isMine = new int[row][column];
        button = new JButton[row][column];
        isEnable = new int[row][column];
        rightClick = new int[row][column];
        right = new int[row][column];
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        time = 0;
        judgefirst = true;
    }

    // Change the size of photo,set the autoadaptation, the proportion of pic must be 1:1
    // Suggestion not add the high resolution radio pic for the artistic
    public void changeIcon(int width, int height) {
        ImageIcon icon11;
        for (int i = 0; i < 14; i++) {
            Image temp = icon[i].getImage().getScaledInstance(width, height,
                    icon[i].getImage().SCALE_DEFAULT);
            icon11 = new ImageIcon(temp);
            iconChange[i] = icon11;
        }
    }

    //Initialize the NorthPanel setting the layout adn the size and location
    public void addNorthPanel(int length) {
        panel2 = new JPanel();
        JPanel panel21 = new JPanel();
        JPanel panel22 = new JPanel();
        FlowLayout f = new FlowLayout();
        FlowLayout f1 = new FlowLayout();
        panel2.setLayout(f);
        f.setHgap(length);
        f.setVgap(0);
        panel21.setLayout(f1);
        panel22.setLayout(f1);
        f1.setHgap(15);

        // setting the button in north ,adjust the location\text\border\size……
        // add four buttons to northpanel
        bon = new JButton[4];
        for (int i = 0; i < 4; i++) {
            bon[i] = new JButton("");
            bon[i].setMargin(new Insets(0, 0, 0, 0));
            if (i < 2)
                panel21.add(bon[i]);
            else
                panel22.add(bon[i]);
        }
        bon[0].setPreferredSize(new Dimension(40, 40));
        bon[0].setIcon(icon[14]);
        bon[0].setBorder(new BevelBorder(BevelBorder.RAISED));
        bon[1].setPreferredSize(new Dimension(70, 35));
        bon[1].setBackground(Color.gray);
        bon[1].setFont(new Font("楷体", 1, 24));
        //bon[1].setForeground(Color.red);
        bon[1].setText(String.valueOf(time));
        bon[1].setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel2.add(panel21);

        bon[2].setPreferredSize(new Dimension(40, 40));
        bon[2].setIcon(icon[15]);
        bon[2].setBorder(new BevelBorder(BevelBorder.RAISED));
        bon[3].setPreferredSize(new Dimension(70, 35));
        bon[3].setBackground(Color.gray);
        bon[3].setFont(new Font("A楷体", 1, 24));
        bon[3].setText(mine + "");
        bon[3].setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel2.add(panel22);
    }

/*
    * Initialize The game Panel,set the size according the size of the panel
    * set the every block of game panel as a button 
    * use the icon-change function to set the button'pic  
    * use the dimension function to set the size and revalidate to readjust
 */
    public void addCenterPanel(int width, int height) {
        panel1 = new JPanel();
        panel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel1.setLayout(new GridLayout(row - 2, column - 2));
        changeIcon(width, height);
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                button[i][j] = new JButton();
                button[i][j].setPreferredSize(new Dimension(width, height));
                button[i][j].setMargin(new Insets(0, 0, 0, 0));
                button[i][j].setBorder(BorderFactory.createLineBorder(
                        Color.black, 1));
                button[i][j].setIcon(iconChange[10]);
                button[i][j].addActionListener(this);
                button[i][j].addMouseListener(this);
                panel1.add(button[i][j]);
            }
        }
        panel1.revalidate();
    }


    // Initialize Top Menu Bar and set the shortcut key
    public void addMenu() {
        // add the compenent
        menu1 = new JMenu("Game(G)");
        menu1.setFont(font1);
        menu1.setMnemonic('G');
        // menu2 = new JMenu("Back Music(M)");
        // menu2.setFont(font1);
        menu3 = new JMenu("About(O)");
        menu3.setFont(font1);

        str = new String[]{"New Game(N)", "Statistics(S)", "Options(O)", "Exit(X)", "About."};
        item = new JMenuItem[5];
        for (int i = 0; i < 5; i++) {
            item[i] = new JMenuItem(str[i]);
            item[i].setFont(font2);
            item[i].addActionListener(this);
        }
        item[0].setAccelerator(KeyStroke.getKeyStroke("F2"));
        item[0].setMnemonic('N');
        item[1].setAccelerator(KeyStroke.getKeyStroke("F4"));
        item[1].setMnemonic('S');
        item[2].setAccelerator(KeyStroke.getKeyStroke("F5"));
        item[2].setMnemonic('O');


        //the function of background music is not ready

        // startmusic = new JCheckBoxMenuItem("Background Music");
        // startmusic.setFont(font2);
        // startmusic.addActionListener(this);

        //add the Options to the menu bar when mouse in
        menu1.add(item[0]);
        menu1.addSeparator();
        menu1.add(item[1]);
        menu1.add(item[2]);
        menu1.addSeparator();
        menu1.add(item[3]);
        // menu2.add(startmusic);
        menu3.add(item[4]);

        bar = new JMenuBar();
        bar.setBorder(new BevelBorder(BevelBorder.RAISED));
        bar.add(menu1);
        // bar.add(menu2);
        bar.add(menu3);
        setJMenuBar(bar);// Add the MENU
    }


    // Add the Compenent
    public void addPart(int width, int height) {
        addMenu();
        /*
        ** Initialize the top panel
        */
        addNorthPanel(70);
        addCenterPanel(width, height);
        panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(45, panel1.getHeight()));
        panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(panel1.getWidth(), 40));
        panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(45, panel1.getHeight()));
        add(panel1, BorderLayout.CENTER);
        add(panel4, BorderLayout.SOUTH);
        add(panel3, BorderLayout.WEST);
        add(panel5, BorderLayout.EAST);
        add(panel2, BorderLayout.NORTH);
    }

    // Setting the showing of size and set location of frame
    public void showOnCenter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }


    // Confirm the distribution of Mine
    // Check the Repeat of the Mine
    public boolean check(int a, int[] b, int c) {
        for (int i = 0; i <= c; i++) {
            if (a == b[i])
                return true;
        }
        return false;
    }



    /* Set the arrange of mine
    *
    * The arithmetic of the mine's generation
    *
    * * * * * * * * *  * * * * * * * * * * * * * * * * * * * * * * * *
    *
    */
    public void arrangeMine() {
        count1 = new int[(row - 2) * (column - 2)];  //生成一个与实际扫雷的面板格数相同大小的一维数组
        //即为代表雷区分布的一维数组
        count2 = new int[mine];  //生成一个与雷数相同的数组
        num1 = 0;
        num2 = 0;
        int num3 = 0;
        //check the correct of Sweeping
        ron = new Random();
        for (int i = 0; i < mine; i++) {
            num2 = ron.nextInt((row - 2) * (column - 2)); //生成一个0-count1的长度的随机数
            count2[i] = num2;
            flag = check(num2, count2, i - 1); //检查count2中数组是否有num2相同的值
            while (flag) {
                num2 = ron.nextInt((row - 2) * (column - 2));
                count2[i] = num2;
                flag = check(num2, count2, i - 1);
            }
            //防止num2与count2[]中的数字发生重复
            count1[num2] = 10;
            //将count1
        }

        //通过random类用一维数组随机生成
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                isMine[i][j] = count1[num1];
                if (count1[num1] == 10) {
                    num3++;
                }
                num1++;
            }
        }
        System.out.println(num3);
    }

    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    
    // Count the number of mine
    public int countMine(int a, int b) {
        int number1 = 0;
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (isMine[i][j] == 10) {
                    number1++;
                }
            }
        }
        return number1;
    }

    // Count the number of red flag around the grid
    public int countFlag(int a, int b) {
        int number2 = 0;
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (right[i][j] == 1) {
                    number2++;
                }
            }
        }
        return number2;
    }

    // Mouse Click Right Result,first click
    public void openFlag(int a, int b) {
        int num3 = 0;
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (isEnable[i][j] != 1 && i >= 1 && j >= 1 && i < row - 1
                        && j < column - 1) {
                    if (isMine[i][j] != 10 && right[i][j] != 1) {
                        open(i, j);
                    }
                    if (isMine[i][j] == 10 && right[i][j] != 1)
                        num3++;
                    if (bonEnd == ((row - 2) * (column - 2)) - mine) {
                        win();
                        return;
                    }
                }
            }
        }
        if (num3 != 0) {
            fail();
        }
    }

    // automatization of mine detection when pressing Mouse left&&right click 
    public void openFlagFail(int a, int b, int c) {

        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (c == 1 && isEnable[i][j] != 1 && right[i][j] != 1 && i >= 1
                        && j >= 1 && i < row - 1 && j < column - 1) {
                    button[i][j].setIcon(iconChange[0]);
                    tt = true;
                }
                if (c == 0 && isEnable[i][j] != 1 && right[i][j] != 1 && i >= 1
                        && j >= 1 && i < row - 1 && j < column - 1) {
                    button[i][j].setIcon(iconChange[10]);
                    tt = false;
                }
            }
        }
    }


    // Open the grid 
    public void open(int a, int b) {
        num = 0;
        bonEnd++;
        isEnable[a][b] = 1;
        num = countMine(a, b);
        isMine[a][b] = num;
        button[a][b].setIcon(iconChange[num]);
        if (num == 0)
            isNull(a, b);

    }

    // Make the periphery of opening grid no mine
    public void isNull(int a, int b) {
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if ((i != a || j != b) && i >= 1 && j >= 1 && i < row - 1
                        && j < column - 1) {
                    if (isEnable[i][j] != 1 && isMine[i][j] != 10
                            && right[i][j] != 1) {
                        open(i, j);
                    }
                }
            }
        }
    }



    // Open all mine which is not opened
    public void openAllMine() {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (isMine[i][j] == 10) {
                    button[i][j].setIcon(iconChange[9]);
                }
            }
        }

    }

    // after judging winner ,play music,write the record of winner's information,show the win panel 
    public void win() {
        if (Integer.parseInt(bon[3].getText()) >= 0) {
            aau[2].play();
            timer.stop();
            record.writeNumber(level, 1);
            record.writeTime(level, Integer.parseInt(bon[1].getText()));
            new WinPane("Victory", this);
        }

    }

    //after judging loser,play music,open all mine,show the fail panel
    public void fail() {
        aau[3].play();
        openAllMine();
        timer.stop();
        record.writeNumber(level, 0);
        new FailPane("Game Over", this);
    }

    // Start a newgame, initialize all button and the number of flag\mine
    // time reset
    public void newGame() {
        aau[4].play();
        judgefirst = true;
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                button[i][j].setIcon(iconChange[10]);
                isEnable[i][j] = 0;
                right[i][j] = 0;
                rightClick[i][j] = 0;
                isMine[i][j] = 0;
            }
        }

        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        timer.stop();
        time = 0;
        bon[1].setText("0");
    }

    // Restart the game is similar to new game,
    //but we keep the last arrange information
    public void restart() {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                button[i][j].setIcon(iconChange[10]);
                isEnable[i][j] = 0;
                right[i][j] = 0;
                rightClick[i][j] = 0;
            }
        }
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        timer.stop();
        time = 0;
        bon[1].setText("0");
    }

    // 
    // Confirm the first step don't meet the mine and arrangemine 
    public void firstClick(int a, int b) {
        int i;
        do {
            i = countMine(a, b);
            judgefirst = false;
            if (i != 0) {
                arrangeMine();
                judgefirst = true;
            }
        } while (judgefirst);
        open(a, b);
    }

    // Basis
    public void firstLevel() {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(11);
        setColumn(11);
        setMine(10);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        addCenterPanel(50, 50);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 0;
    }

    // Intermediate
    public void secondLevel() {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(18);
        setColumn(18);
        setMine(40);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        addCenterPanel(35, 35);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 1;
    }

    // Advance
    public void thirdLevel() {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(18);
        setColumn(32);
        setMine(99);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        addCenterPanel(35, 35);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 2;
    }

    // User-defined 
    public void setLevel(int a, int b, int c) {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(a);
        setColumn(b);
        setMine(c);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        setMineSize(a, b);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 3;
        if (a * b / c <= 5)
            judgefirst = false;
    }

    // the beginning of layout is defined by user  
    public void addPart4(int a, int b) {
        if (a <= 12 && b <= 14)
            addPart(50, 50);
        else {
            if (a <= 14 && b <= 17)
                addPart(45, 45);
            else {
                if (a <= 16 && b <= 21)
                    addPart(40, 40);
                else {
                    if (a <= 18 && b <= 25)
                        addPart(35, 35);
                    else {
                        if (a <= 22 && b <= 34)
                            addPart(30, 30);
                        else
                            addPart(25, 25);
                    }
                }
            }
        }
    }

    // Setting the size of block by user called by setlevel function
    public void setMineSize(int a, int b) {
        if (a <= 12 && b <= 14)
            addCenterPanel(50, 50);
        else {
            if (a <= 14 && b <= 17)
                addCenterPanel(45, 45);
            else {
                if (a <= 16 && b <= 21)
                    addCenterPanel(40, 40);
                else {
                    if (a <= 18 && b <= 25)
                        addCenterPanel(35, 35);
                    else {
                        if (a <= 22 && b <= 34)
                            addCenterPanel(30, 30);
                        else
                            addCenterPanel(25, 25);
                    }
                }
            }
            
        }
    }

    // Time Keeping
    public void countTime() {
        timer = new Timer(1000, this);
    }


    // Read the music file and  defined variable
    public void music() {
        URL cb;
        File f;
        try {
            for (int i = 0; i < 5; i++) {
                f = new File("music/" + i + ".wav");
                cb = f.toURL();
                aau[i] = Applet.newAudioClip(cb);
            }
            /*
			 * aau.loop():loop play ////aau.play():singel cycle ///aau.stop():Stop play
			 */
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // Add the monitor of Action
    public void actionPerformed(ActionEvent e) {

        // New Game
        if (e.getSource() == item[0]) {
            newGame();
        }

        // Statistics 
        if (e.getSource() == item[1])
            new Thread(new Message("statistics  Information")).start();

        // Setting
        if (e.getSource() == item[2])
            new Thread(new Setting(this, level, "Options")).start();

        // Exit
        if (e.getSource() == item[3]) {
            if (!bon[1].getText().equalsIgnoreCase("0")) {
                int a = JOptionPane.showConfirmDialog(this, "The game is in progress, whether to confirm exit game?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    record.writeLevel(level);
                    if (level == 3)
                        record.writeLevel4(row, column, mine);
                    System.exit(0);
                }
            } else {
                record.writeLevel(level);
                if (level == 3)
                    record.writeLevel4(row, column, mine);
                System.exit(0);
            }
        }

        // About
        if (e.getSource() == item[4]) {
            JOptionPane.showMessageDialog(this, "2017 By Lemo-Xiao", "About",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // Time Keeping 
        if (e.getSource() == timer) {
            time++;
            bon[1].setText(time + "");}
        }

        // Setting BackGround-Music
    //     if (e.getSource() == startmusic) {
    //         if (startmusic.getState() == true) {
    //             // aau1.play();
    //             aau[1].loop();
    //         } else {
    //             aau[1].stop();
    //         }
    //     }
    // }

    public void mouseClicked(MouseEvent e) {
    }

    // The performance of Mouse' Moving in
    public void mouseEntered(MouseEvent e) {

        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (isEnable[i][j] != 1 && rightClick[i][j] % 3 == 0) {
                    if (e.getSource() == button[i][j])
                        button[i][j].setIcon(iconChange[13]);
                }
            }
        }

    }

    // the performance of Mouse' Moving away
    public void mouseExited(MouseEvent e) {

        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (isEnable[i][j] != 1 && rightClick[i][j] % 3 == 0) {
                    if (e.getSource() == button[i][j])
                        button[i][j].setIcon(iconChange[10]);
                }
            }
        }

    }

    // The pressing performance of Mouse Click rightly, ly or both
    public void mousePressed(MouseEvent e) {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                // Mouse Left Right Click
                if (e.getModifiersEx() == (e.BUTTON3_DOWN_MASK + e.BUTTON1_DOWN_MASK)
                        && e.getSource() == button[i][j] && isEnable[i][j] == 1) {
                    int number3 = countFlag(i, j);
                    if (isMine[i][j] == number3) {
                        openFlag(i, j);
                    } else {
                        aau[0].play();
                        openFlagFail(i, j, 1);
                    }
                }
                // Mouse Right Click
                if (e.getSource() == button[i][j] && isEnable[i][j] != 1
                        && e.getModifiersEx() == e.BUTTON3_DOWN_MASK) {
                    if (time == 0)
                        timer.start();
                    rightClick[i][j]++;
                    if (rightClick[i][j] % 3 == 1) {
                        button[i][j].setIcon(iconChange[11]);
                        right[i][j] = 1;
                        flagCount--;
                        bon[3].setText("" + flagCount);
                        if (isMine[i][j] == 10)
                            mineEnd++;
                        if (mineEnd == mine) {
                            win();
                        }
                    }
                    if (rightClick[i][j] % 3 == 2) {
                        button[i][j].setIcon(iconChange[12]);
                        right[i][j] = 0;
                        flagCount++;
                        bon[3].setText("" + flagCount);
                        if (isMine[i][j] == 10)
                            mineEnd--;
                        if (mineEnd == mine) {
                            win();
                        }
                    }
                    if (rightClick[i][j] % 3 == 0)
                        button[i][j].setIcon(iconChange[10]);
                }
            }
        }

        // Mouse Left Click 
        if (e.getModifiersEx() == e.BUTTON1_DOWN_MASK) {
            for (int i = 1; i < row - 1; i++) {
                for (int j = 1; j < column - 1; j++) {
                    if (e.getSource() == button[i][j]) {
                        if (time == 0)
                            timer.start();
                        if (judgefirst == true) {
                            firstClick(i, j);
                        } else {
                            if (isEnable[i][j] != 1 && right[i][j] != 1) {
                                if (isMine[i][j] != 10)
                                    open(i, j);
                                else {
                                    fail();
                                }
                                if (bonEnd == ((row - 2) * (column - 2)) - mine)
                                    win();
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                // Mouse click leftly,rightly or both
                if (e.getSource() == button[i][j] && isEnable[i][j] == 1
                        && tt == true) {
                    openFlagFail(i, j, 0);
                }
            }
        }

    }

}
