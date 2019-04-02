package com.company;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MyServer implements Runnable {
    private Socket incoming;

    public MyServer(Socket i){
        incoming = i;
    }

    public void run(){
        try{
            try{
                InputStream inStream = incoming.getInputStream();
                ObjectOutputStream outStream = new ObjectOutputStream(incoming.getOutputStream());

                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true);
                //out.println("Connected to server.");
                //outStream.writeBytes("Connected to server.");

                boolean done = false;
                while (!done && in.hasNextLine()){
                    String line = in.nextLine();
                    //System.out.println("Echo: "+line);
                    if (line.trim().equalsIgnoreCase("BYE")) {
                        done = true;
                    }
                    else if (line.trim().equalsIgnoreCase("GET")){
                        MySQLDB db = new MySQLDB();
                        db.Connect();
                        //out.println("DB connected.");
                        //outStream.writeBytes("DB connected.");
                        List<MyWorker> tempWorkers;
                        tempWorkers = db.GetDataFromDB();
                        outStream.writeObject(tempWorkers);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                incoming.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
