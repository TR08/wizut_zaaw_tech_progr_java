package com.company;
import java.rmi.*;

public interface Validator extends Remote{
    String validate(String username, String pass)
            throws RemoteException;
}