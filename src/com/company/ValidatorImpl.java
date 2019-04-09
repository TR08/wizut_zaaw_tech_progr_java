package com.company;
import java.rmi.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("unchecked")
public class ValidatorImpl extends UnicastRemoteObject implements Validator {
    Map memberMap;
    public ValidatorImpl() throws RemoteException{
        memberMap = new HashMap();
        memberMap.put("me", "mypass");
    }

    public String validate(String username, String pass) throws RemoteException{
        if (getMemberMap().containsKey(username) && getMemberMap().get(username).equals(pass))
            return "Welcome "+username;
        return "Invalid login or password.";
    }

    public Map getMemberMap(){
        return memberMap;
    }
}
