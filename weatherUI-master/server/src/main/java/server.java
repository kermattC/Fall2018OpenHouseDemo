import java.io.*;
import java.net.*;

public class server {
    public static void main (String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(1521);
        while(true){
            try{
                Socket socket = ss.accept();
                serverThread st = new serverThread(socket);
                Thread handlerThread = new Thread(st);
                handlerThread.start();

                System.out.println("New Connection!!!");

            }catch (IOException e){
                System.err.println("Command not received");
            }
        }

    }
}