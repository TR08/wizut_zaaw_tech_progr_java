package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<MyWorker> workers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        boolean toRunOrNotToRun = true;

        //MyServer serv = new MyServer();

        while(toRunOrNotToRun){
            //System.out.print("\033[H\033[2J");
            //System.out.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("MENU\n");
            System.out.print("1 - Lista pracowników\n");
            System.out.print("2 - Dodaj pracownika\n");
            System.out.print("3 - Usuń pracownika\n");
            System.out.print("4 - Kopia zapasowa\n");
            System.out.print("5 - Pobierz dane z sieci\n");
            System.out.print("q - Wyjdź\n\n");
            System.out.print("Wybór> ");
            String choice = br.readLine();
            //System.out.print("###"+choice+"###\n");
            //System.out.print(choice.equals("q")+"###\n");
            //if (choice.equals("q")) break;
            switch (choice){
                case "q":
                    toRunOrNotToRun = false;
                    break;
                case "1":
                    new Main().ListWorkers(workers);
                    break;
                case "2":
                    MyWorker tempWorker = new Main().AddWorker();
                    if (tempWorker != null) workers.add(tempWorker);
                    break;
                case "3":
                    new Main().RemoveWorker(workers);
                    break;
                case "4":
                    new Main().BackupWorkers();
                    break;
                case "5":
                    new Main().GetFromTheNet();
                    break;
            }
        }
    }

    private void GetFromTheNet() throws Exception {
        System.out.print("Pobierz dane z sieci\n\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printLabel("Adres");
        String address = br.readLine();
        printLabel("Port");
        int port = Integer.parseInt(br.readLine());
        MyClient client1 = new MyClient();
        client1.Connect(address, port, "hello");
    }

    private void BackupWorkers() throws IOException, SQLException {
        System.out.print("Kopia zapasowa\n\n");
        printLabel("[Z]achowaj/[O]dtwórz");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = br.readLine();
        String choice = temp;
        System.out.print("-------------------------------------\n");
        List<MyWorker> tempWorkers = new ArrayList<>();
        MySQLDB db = new MySQLDB();
        db.Connect();
        if (choice.equals("Z")){

        }
        else if (choice.equals("O")){
            tempWorkers = db.GetDataFromDB();
            printLabel("Liczba znalezionych rekordów");
            System.out.print(tempWorkers.size()+"\n");
        }
        System.out.print("-------------------------------------\n");
        System.out.print("[Enter] - potwierdź\n");
        System.out.print("[q] - porzuć\n");
        temp = "asd";
        do {
            temp = br.readLine();
        } while (!(temp.equals("")) && !(temp.equals("q")));
        if (temp.equals("q")) {
            db.Close();
            return;
        }
        if (choice.equals("Z")){
            db.PutDataToDB(workers);
        }
        else if (choice.equals("O")) {
            workers = tempWorkers;
        }
        db.Close();
    }

    private void RemoveWorker(List<MyWorker> wrkrs) throws IOException {
        System.out.print("Usuń pracownika\n\n");
        printLabel("Podaj identyfikator");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = br.readLine();
        for(int i=0; i<wrkrs.size(); ++i){
            if (wrkrs.get(i).getId().equals(temp)) {
                System.out.print("-------------------------------------\n");
                printLabel("Imię");
                System.out.print(wrkrs.get(i).getName() + "\n");
                printLabel("Nazwisko");
                System.out.print(wrkrs.get(i).getSurname() + "\n");
                printLabel("Stanowisko");
                System.out.print(wrkrs.get(i).getPosition() + "\n");
                printLabel("Wynagrodzenie (zł)");
                System.out.print(wrkrs.get(i).getSalary() + "\n");
                printLabel("Telefon służbowy numer");
                System.out.print(wrkrs.get(i).getPhoneNum() + "\n");

                if (wrkrs.get(i).getPosition().equals("Dyrektor")) {
                    printLabel("Dodatek służbowy (zł)");
                    System.out.print(((WorkerDirector) (wrkrs.get(i))).getBusinessAllowance() + "\n");
                    printLabel("Karta służbowa numer");
                    System.out.print(((WorkerDirector) (wrkrs.get(i))).getCardNum() + "\n");
                    printLabel("Limit kosztów/miesiąc (zł)");
                    System.out.print(((WorkerDirector) (wrkrs.get(i))).getCostLimit() + "\n");
                } else {
                    printLabel("Prowizja (%)");
                    System.out.print(((WorkerTrader) (wrkrs.get(i))).getProvision() + "\n");
                    printLabel("Limit prowizji/miesiąc (zł)");
                    System.out.print(((WorkerTrader) (wrkrs.get(i))).getProvLimit() + "\n");
                }
                System.out.print("-------------------------------------\n");


                System.out.print("[Enter] - potwierdź\n");
                System.out.print("[q] - porzuć\n");

                temp = "asd";
                do {
                    temp = br.readLine();
                } while (!(temp.equals("")) && !(temp.equals("q")));
                if (temp.equals("q")) break;
                else wrkrs.remove(i);
                break;
            }
        }
    }

    private void ListWorkers(List<MyWorker> wrkrs) throws IOException {
        for(int i=0; i<wrkrs.size(); ++i){
            System.out.print("Dodaj pracownika\n\n");
            printLabel("Identyfikator");
            System.out.print(wrkrs.get(i).getId()+"\n");
            printLabel("Imię");
            System.out.print(wrkrs.get(i).getName()+"\n");
            printLabel("Nazwisko");
            System.out.print(wrkrs.get(i).getSurname()+"\n");
            printLabel("Stanowisko");
            System.out.print(wrkrs.get(i).getPosition()+"\n");
            printLabel("Wynagrodzenie (zł)");
            System.out.print(wrkrs.get(i).getSalary()+"\n");
            printLabel("Telefon służbowy numer");
            System.out.print(wrkrs.get(i).getPhoneNum()+"\n");

            if (wrkrs.get(i).getPosition().equals("Dyrektor")) {
                printLabel("Dodatek służbowy (zł)");
                System.out.print(((WorkerDirector)(wrkrs.get(i))).getBusinessAllowance()+"\n");
                printLabel("Karta służbowa numer");
                System.out.print(((WorkerDirector)(wrkrs.get(i))).getCardNum()+"\n");
                printLabel("Limit kosztów/miesiąc (zł)");
                System.out.print(((WorkerDirector)(wrkrs.get(i))).getCostLimit()+"\n");
            }
            else {
                printLabel("Prowizja (%)");
                System.out.print(((WorkerTrader)(wrkrs.get(i))).getProvision()+"\n");
                printLabel("Limit prowizji/miesiąc (zł)");
                System.out.print(((WorkerTrader)(wrkrs.get(i))).getProvLimit()+"\n");
            }

            System.out.print("\n");
            for (int j=0; j<40; ++j) System.out.print(" ");
            System.out.print("[Pozycja "+(i+1)+"/"+wrkrs.size()+"]\n");

            System.out.print("[Enter] - następny\n");
            System.out.print("[q] - powrót\n");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String temp = "asd";
            do {
                temp = br.readLine();
            } while (!(temp.equals("")) && !(temp.equals("q")));
            if (temp.equals("q")) break;
        }
    }

    private MyWorker AddWorker() throws IOException {
        System.out.print("Dodaj pracownika\n\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MyWorker newWorker;
        String temp = "";
        System.out.print("[D]yrektor/[H]andlowiec: ");
        do {
            temp = br.readLine();
        } while (!(temp.equals("D")) && !(temp.equals("H")));
        if (temp.equals("D")) newWorker = new WorkerDirector();
        else newWorker = new WorkerTrader();
        newWorker.setPosition(temp);

        System.out.print("-------------------------------------\n");

        temp = "";
        boolean isValid = true;
        //System.out.print("Identyfikator: ");
        printLabel("Identyfikator");
        do {
            temp = br.readLine();
            //validate
        } while (!isValid);
        newWorker.setId(temp);

        //System.out.print("Imię: ");
        printLabel("Imię");
        temp = br.readLine();
        newWorker.setName(temp);

        //System.out.print("Nazwisko: ");
        printLabel("Nazwisko");
        temp = br.readLine();
        newWorker.setSurname(temp);

        //System.out.print("Wynagrodzenie (zł): ");
        printLabel("Wynagrodzenie (zł)");
        int intTemp = readNumber();
        newWorker.setSalary(intTemp);

        //System.out.print("Telefon służbowy: ");
        printLabel("Telefon służbowy");
        temp = br.readLine();
        newWorker.setPhoneNum(temp);

        if(newWorker.getPosition().equals("Dyrektor")) {
            //System.out.print("Dodatek służbowy (zł): ");
            printLabel("Dodatek służbowy (zł)");
            intTemp = readNumber();
            ((WorkerDirector)newWorker).setBusinessAllowance(intTemp);

            //System.out.print("Karta służbowa numer: ");
            printLabel("Karta służbowa numer");
            temp = br.readLine();
            ((WorkerDirector)newWorker).setCardNum(temp);

            //System.out.print("Limit kosztów/miesiąc (zł): ");
            printLabel("Limit kosztów/miesiąc (zł)");
            intTemp = readNumber();
            ((WorkerDirector)newWorker).setCostLimit(intTemp);
        }
        else if (newWorker.getPosition().equals("Handlowiec")){
            //System.out.print("Prowizja (%): ");
            printLabel("Prowizja (%)");
            intTemp = readNumber();
            ((WorkerTrader)newWorker).setProvision(intTemp);

            //System.out.print("Limit prowizji/miesiąc (zł): ");
            printLabel("Limit prowizji/miesiąc (zł)");
            intTemp = readNumber();
            ((WorkerTrader)newWorker).setProvLimit(intTemp);
        }

        System.out.print("-------------------------------------\n");
        System.out.print("[Enter] - zapisz\n");
        System.out.print("[q] - porzuć\n");

        temp = "asd";
        do {
            temp = br.readLine();
        } while (!(temp.equals("")) && !(temp.equals("q")));
        if (temp.equals("q")) return null;
        return newWorker;
    }

    private int readNumber() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int intTemp = 0;
        boolean isValid = true;
        do {
            isValid = true;
            String temp = br.readLine();
            try {
                intTemp = Integer.parseInt(temp);
            }
            catch (NumberFormatException e){ isValid = false; }
        } while (!isValid);
        return intTemp;
    }

    private static void printLabel(String text){
        System.out.print(text);
        for (int i=0; i<30-text.length(); ++i) System.out.print(" ");
        System.out.print(":   ");
    }
}
