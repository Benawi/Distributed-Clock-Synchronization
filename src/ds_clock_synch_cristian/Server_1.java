/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_clock_synch_cristian;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server_1 extends Thread
{
    private final ServerSocket serverSocket;  
    public Server_1(int port) throws IOException 
    {
        serverSocket = new ServerSocket(port);
    }
    public void run()
    {
        while (true) 
        {
            try {
                String localHostName = java.net.InetAddress.getLocalHost().getHostName();
                Socket server = serverSocket.accept();  
                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());  
                int i=in.readInt();  
                for(int j=0;j<i;j++)
                {
                Thread.sleep((long)(100+new Random().nextInt(51)));  
                out.writeLong(System.currentTimeMillis());  
                }
                server.close();
            } catch (UnknownHostException ex) {
                Logger.getLogger(Server_1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Server_1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException 
    {
        // TODO code application logic here
        int port = 8001;
        Thread t = new Server_1(port); 
        t.start();
        System.out.print("Time server is ready!");
    }
    
}