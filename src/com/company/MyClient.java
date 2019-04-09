package com.company;
import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyClient {

    public List<MyWorker> Connect (String serverName, int serverPort, String message, String username, String pass) throws Exception{
        Socket s = new Socket(serverName, serverPort);
        //Scanner in = new Scanner(s.getInputStream());
        boolean toConnect = false;
        try{
            Object remote = Naming.lookup("rmi://localhost/validator");
            Validator reply = (Validator)remote;
            String resp = reply.validate(username, pass);
            System.out.println(resp);
            if (resp.contains("Welcome")) toConnect = true;
        } catch (MalformedURLException me){
            System.out.println("Not valid URL");
        } catch (RemoteException nbe){
            System.out.println("Could not find requested object on the server");
        }
        List<MyWorker> tempWorkers = new ArrayList<>();
        if (toConnect) {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(message + "\n");
            dos.writeUTF("bye\n");
            String line = "";
//        while (in.hasNextLine()) {
//            line = in.nextLine();
//            System.out.println("line: " + line);
//        }
            tempWorkers = (List<MyWorker>) in.readObject();
            dos.close();
        }
        return tempWorkers;
    }
}
