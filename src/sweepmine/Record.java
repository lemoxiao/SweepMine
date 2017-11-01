package sweepmine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * 
 * Created by Lemoxiao  ---2017-10-28
 * 
 * This is a Record Recorder.
 * 
 * read the data of winner's data,and save it to directory called Record. 
 * Then the class and function will primarily  be called by Message.java
 * 
 * 
 */

public class Record {

    BufferedWriter BW;
    BufferedReader BR;
    FileWriter out;
    FileReader in;

    public Record() {
    }

    /*
    ** set a number of the game
    */
    public void writeNumber(int level, int i) {
        try {
            out = new FileWriter("record//number" + (level + 1) + ".txt", true);
            BW = new BufferedWriter(out);
            BW.write(i + "<>");
            BW.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    ** Set the time of Sweeping mine
    */
    public void writeTime(int level, int i) {
        try {
            if (level == 0) {
                out = new FileWriter("record//firstLevel.txt", true);
                BW = new BufferedWriter(out);
                BW.write(i + "<>" + getTime() + "<>");
                BW.close();
                out.close();
            }
            if (level == 1) {
                out = new FileWriter("record//secondLevel.txt", true);
                BW = new BufferedWriter(out);
                BW.write(i + "<>" + getTime() + "<>");
                BW.close();
                out.close();
            }
            if (level == 2) {
                out = new FileWriter("record//thirdLevel.txt", true);
                BW = new BufferedWriter(out);
                BW.write(i + "<>" + getTime() + "<>");
                BW.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    ** record the Difficulty level of Sweeping mine
    */
    public void writeLevel(int i) {
        try {
            out = new FileWriter("record//level.txt");
            BW = new BufferedWriter(out);
            BW.write(i + "");
            BW.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /*
    ** set the number of squares and mines when user defines level by itself
    */
    public void writeLevel4(int row, int column, int mine) {
        try {
            out = new FileWriter("record//level4.txt");
            BW = new BufferedWriter(out);
            BW.write(row + "<>" + column + "<>" + mine + "<>");
            BW.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /*
    ** get the number of the game
    */
    public String readNumber(int level) {
        String s = "";
        int c;
        try {
            in = new FileReader("record//number" + (level + 1) + ".txt");
            BR = new BufferedReader(in);
            while ((c = BR.read()) != -1) {
                s = s + (char) c;
            }
            BR.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

     /*
    ** read the time of sweeping mine
    */
    public String readTime(int level) {
        String s = "";
        int c;
        try {
            if (level == 0) {
                in = new FileReader("record//firstLevel.txt");
                BR = new BufferedReader(in);
                while ((c = BR.read()) != -1) {
                    s = s + (char) c;
                }
                BR.close();
                in.close();
            }
            if (level == 1) {
                in = new FileReader("record//secondLevel.txt");
                BR = new BufferedReader(in);
                while ((c = BR.read()) != -1) {
                    s = s + (char) c;
                }
                BR.close();
                in.close();
            }
            if (level == 2) {
                in = new FileReader("record//thirdLevel.txt");
                BR = new BufferedReader(in);
                while ((c = BR.read()) != -1) {
                    s = s + (char) c;
                }
                BR.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

     /*
    ** Read the level of sweeping mine
    */
    public String readLevel() {
        String s = "";
        int c;
        try {
            in = new FileReader("record/level.txt");
            BR = new BufferedReader(in);
            while ((c = BR.read()) != -1) {
                s = s + (char) c;
            }
            BR.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    
    /*
    *
    * Reading the number of squares 
    *
    * read mines when user defines level by itself
    *
    */
    public String readLevel4() {
        String s = "";
        int c;
        try {
            in = new FileReader("record//level4.txt");
            BR = new BufferedReader(in);
            while ((c = BR.read()) != -1) {
                s = s + (char) c;
            }
            BR.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /*
    *
    * Clear every record
    *
    */

    public void clear() {
        try {
            for (int i = 1; i < 4; i++) {
                out = new FileWriter("record//number" + i + ".txt");
                out.write("");
                out.close();
            }
            out = new FileWriter("record//firstLevel.txt");
            out.write("");
            out.close();
            out = new FileWriter("record//secondLevel.txt");
            out.write("");
            out.close();
            out = new FileWriter("record//thirdLevel.txt");
            out.write("");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *
    * formate the win time
    *
    */
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Compelete Time display
        String time = sdf.format(date);
        return time;
    }
}
