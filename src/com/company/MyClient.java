package com.company;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class MyClient {

    public List<MyWorker> Connect (String serverName, int serverPort, String message) throws Exception{
        Socket s = new Socket(serverName, serverPort);
        //Scanner in = new Scanner(s.getInputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(message+"\n");
        dos.writeUTF("bye\n");
        String line = "";
//        while (in.hasNextLine()) {
//            line = in.nextLine();
//            System.out.println("line: " + line);
//        }
        List<MyWorker> tempWorkers = (List<MyWorker>) in.readObject();
        dos.close();
        return tempWorkers;
    }
}
