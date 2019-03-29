package com.company;

public class MyWorker {
    private String id, name, surname, position, phoneNum;
    private int salary;

    public void setId(String val){
        id = val;
    }

    public void setName(String val){
        name = val;
    }

    public void setSurname(String val){
        surname = val;
    }

    public void setPosition(String val){
        switch(val){
            case "D":
            case "Dyrektor":
                position = "Dyrektor";
                break;
            case "H":
            case "Handlowiec":
                position = "Handlowiec";
                break;
        }
    }

    public void setPhoneNum(String val){
        phoneNum = val;
    }

    public void setSalary(int val){
        salary = val;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getPosition(){
        return position;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public int getSalary(){
        return salary;
    }
}
