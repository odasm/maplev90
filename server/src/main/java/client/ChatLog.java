package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

import tools.MaplePacketCreator;

/**
 *
 * @author Soulfist
 */
public class ChatLog {

    BufferedWriter w = null;
    BufferedReader b = null;
    private static ChatLog instance = null;
    List<String> chat = new ArrayList<String>();

    private ChatLog() {
        try {
            w = new BufferedWriter(new FileWriter("chatlog.txt", true));
            b = new BufferedReader(new FileReader("chatlog.txt"));
            w.append("\n\n-----------------------------------------------------------------------------------------------------\n\n");
        } catch (IOException i) {
            i.printStackTrace(System.out);
        }
    }

    public static ChatLog getInstance() { //works together with disable()
        if (instance == null) {
            instance = new ChatLog();
        }
        return instance;
    }

    public List<String> getChat() {
        return chat;
    }

    public String generateTime() {
        return new Date().toString(); //deprecated class ftw
    }

    public void disable() {
        try {
            if (w != null) {
                w.close();
            }
            if (b != null) {
                b.close();
            }
            instance = null;
        } catch (IOException io) {
            io.printStackTrace(System.out);
        }
    }

    private boolean containsIllegal(String check) {
        String[] illegal = {"what", "the", "if", "is", "he", "she", "why", "when", "how", "because"}; //very limited, I know...
        for (int i = 0; i < illegal.length; i++) {
            if (check.equalsIgnoreCase(illegal[i]) || check.length() < 4) { //has to be a 4 letter word+
                return true;
            }
        }
        return false;
    }

    public void makeLog() {
        synchronized (w) {
            try {
                for (int i = 0; i < chat.size(); i++) {
                    w.newLine();
                    w.append(chat.get(i));
                }
            } catch (IOException io) {
                io.printStackTrace(System.out);
            }
            disable();
        }
        chat.clear();
    }

    public synchronized void add(String a) { //constantly adding
        chat.add(a);
    }

    public void viewRecentAsDrop(MapleClient c) {
        StringBuffer drop = new StringBuffer();
        try {
            if (chat.size() - 50 < 50) {
                return;
            }
            for (int i = chat.size() - 50; i < chat.size(); i++) {
                drop.append(chat.get(i));
            }
            //c.announce(MaplePacketCreator.getNPCTalk(9010000, (byte) 0, "#eSearch Results for " + the + " :#n\r\n"+printout, "00 00", (byte) 0));
        } catch (ConcurrentModificationException cme) {
            cme.printStackTrace(System.err);
        }
        drop = null;
    }

    public void searchLog(String what, MapleClient c) {
        StringBuffer printout = new StringBuffer();
        String the = what.toLowerCase(); //improvising 'equalsIgnoreCase'
        try {
            while (b.readLine() != null) {
                if (b.readLine().toLowerCase().contains(the) && !containsIllegal(the)) {
                    printout.append("\r\n").append(b.readLine());
                }
            }
            disable();
        } catch (IOException e) {
            System.err.println("Error occured in copying file data: " + e);
        } catch (NullPointerException npe) { //somehow file is now gone o-o... or they typed somethin weird
            npe.printStackTrace(System.err);
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(9010000, (byte) 0, "#eSearch Results for " + the + " :#n\r\n" + printout, "00 00", (byte) 0));
    }
}
