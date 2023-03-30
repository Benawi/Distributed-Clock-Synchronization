/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_clock_synch_cristian;

import ds_clock_synch_cristian.SynchClock.InternalInaccuratClock;
import ds_clock_synch_cristian.SynchClock.Update;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Client_1 {

    public static SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");
    public static long Timer;
    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        long clockinaccuracy;
        System.out.println("How inaccurate is the clock:");
        clockinaccuracy = sc.nextLong();
        SynchClock.InternalInaccuratClock C = new InternalInaccuratClock(clockinaccuracy);
        SynchClock.Update U = new Update();
        System.out.println("How often check the clock:");
        long CC = sc.nextLong();
        C.start();
        while (true) {
            Thread.sleep(CC);
            U.run();
        }
    }
}
