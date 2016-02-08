/**
 *
 * @author Shaheed Ahmed Dewan Sagar
 *         AUST-12.01.04.085
 *         sdewan64@gmail.com
 */
package labhangman;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaheed Ahmed Dewan Sagar
 *         AUST-12.01.04.085
 *         sdewan64@gmail.com
 */
public class Server {

    static ServerSocket server;
    static int id = 0;
    static List<Info> allclients = new ArrayList<>();
                    
    public static void main(String[] args) {
        try {
            server = new ServerSocket(8010);
            Thread obligatoryThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("Hangman started : ");
                     System.out.println("Waiting for connection :  ");
                            
                    for(int i=0;i<2;i++){
                        try {
                            Socket client = server.accept();
                            Info c = new Info(id,new DataInputStream(client.getInputStream()),new DataOutputStream(client.getOutputStream()));
                            allclients.add(c);
                            System.out.println("now connected to : " +i);
                            c.dataOutputStream.writeUTF(""+id);
                            id++;
                            
                        } catch (IOException ex) {
                            Logger.getLogger(LabHangMan.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            obligatoryThread.start();
            try {
                obligatoryThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(LabHangMan.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Hangman started : ");
            System.out.println("Waiting for connection : : ");
            
            List<String> words = new ArrayList<>();
            System.out.println("The Word to be Guesses : ! \n ");
            BufferedReader br = new BufferedReader(new FileReader(new File("words.txt")));
            String cl;
            while((cl=br.readLine())!=null){
                words.add(cl);
            }
            int ind = new Random().nextInt(100);
            
            allclients.get(0).dataOutputStream.writeUTF(words.get(ind));
            allclients.get(1).dataOutputStream.writeUTF(words.get(ind));
            
            System.out.println(words.get(ind));
            
        } catch (IOException ex) {
            Logger.getLogger(LabHangMan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static class Info{
        
        public int id;
        public DataInputStream dataInputStream;
        public DataOutputStream dataOutputStream;

        public Info(int id, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            this.id = id;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
        }
    }
    
}
