/**
 *
 * @author Shaheed Ahmed Dewan Sagar
 *         AUST-12.01.04.085
 *         sdewan64@gmail.com
 */
package labhangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaheed Ahmed Dewan Sagar
 *         AUST-12.01.04.085
 *         sdewan64@gmail.com
 */
public class Client {
    public static void main(String[] args){
        int id;
        try {
            Socket client = new Socket("localhost",8010);
            
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("Hangman started : ");
            id = Integer.parseInt(dataInputStream.readUTF());
            
                String wordToGuess = dataInputStream.readUTF();
                       wordToGuess = wordToGuess.toLowerCase();
                
                boolean guessed = false;
                
                String all  = "";
                
                for(int i=0;i<wordToGuess.length();i++){
                    System.out.print("*");
                }
                System.out.println();
                
                for(int i=0;i<wordToGuess.length();i++){
                    System.out.print("Guess "+(i+1)+" : ");
                    String inp = new Scanner(System.in).nextLine();
                    if(inp.length()==1){
                    all+=inp;}
                    else{
                    System.out.println(" enter a single character : ");
                    }
                    
                    char[] ca = wordToGuess.toCharArray();
                    char[] ic = all.toCharArray();
                    List<Character> ls = new ArrayList<>();
                    for(char s : ic){
                        ls.add(s);
                    }
                    String checker = "";
                    for(char c : ca){
                        if(ls.contains(c)){
                            System.out.print(c);
                            checker+=String.valueOf(c);
                        }else{
                            System.out.print("*");
                        }
                    }
                    System.out.println();
                    if(checker.equalsIgnoreCase(wordToGuess)){
                        System.out.println("Right Guess.You Are Free to go");
                        guessed = true;
                        break;
                    }
                }
                if(all.equalsIgnoreCase(wordToGuess)){
                    System.out.println("Right Guess.You Are Free to go !");
                    guessed = true;
                        
                }
                if(!guessed){
                    System.out.println("Wrong Guess.You are hunged !");
                }

            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
