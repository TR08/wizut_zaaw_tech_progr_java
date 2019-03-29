package com.company;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyServer implements Runnable {
    private Socket incoming;

    public MyServer(Socket i){
        incoming = i;
    }

    public void Start() throws Exception {
        ServerSocket ss = new ServerSocket(1111);

    }

    public void run(){
        try{
            try{
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true);
                out.println("Hello! Enter BYE to exit.");

                boolean done = false;
                while (!done && in.hasNextLine()){
                    String line = in.nextLine();
                    out.println("Echo: "+line);
                    if (line.trim().equals("BYE")) done = true;
                }
            }
            finally {
                incoming.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
