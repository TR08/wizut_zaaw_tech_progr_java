package com.company;
import java.io.*;
import java.net.*;

public class MyClient {
    //public static void main (String[] args) throws Exception{
    public void Connect (String serverName, int serverPort, String message) throws Exception{
//        if(args.length != 3){
//            System.out.println("Usage: java ClientSide Hostname port message");
//            System.exit(0);
//        }
//
//        String serverName = args[0];
//        int serverPort = Integer.parseInt(args[1]);
//        String message = args[2];

        Socket s = new Socket(serverName, serverPort);
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(message);
        dos.close();
    }
}
