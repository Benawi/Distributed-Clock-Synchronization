/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_clock_synch_cristian;

import static ds_clock_synch_cristian.Client_1.SDF;
import static ds_clock_synch_cristian.Client_1.Timer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THECREW
 */
public class SynchClock {
    public static SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");
    public static long Timer;
    private static long NewTime;
    private static String serverName = "localhost";
    private static int serverPort = 8001;
    static class InternalInaccuratClock extends Thread {
        private long Drift;
        public InternalInaccuratClock(long how_inaacurate) {
            Drift = how_inaacurate;
            Timer = System.currentTimeMillis();
        }
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000 + Drift);
                    Timer += 1000;
                    System.out.println(SDF.format(Timer));

                } catch (InterruptedException ex) {
                    Logger.getLogger(Client_1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static class Update {

        public void run() throws IOException {
            
           Socket socket = new Socket(serverName, serverPort);
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            int i = 10;
            out.writeInt(10);
            for (int j = 0; j < i; j++) {
                NewTime += in.readLong();
            }

            NewTime /= i;

            Timer = NewTime;
            NewTime = 0;
            System.out.println("New Time is " + SDF.format(Timer));
            socket.close();
        }
    }

}
