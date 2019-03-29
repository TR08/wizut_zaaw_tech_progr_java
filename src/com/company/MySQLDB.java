package com.company;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MySQLDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Employees?serverTimezone=" + TimeZone.getDefault().getID();
    static final String USER = "root";
    static final String PASS = "TR4991zut";
    private Connection conn = null;

    public void Connect(){
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<MyWorker> GetDataFromDB() throws SQLException {
        List<MyWorker> tempWorkers = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT * FROM workers";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            String pos = rs.getString("position");
            MyWorker wrk;
            if (pos.equals("Dyrektor")) wrk = new WorkerDirector();
            else wrk = new WorkerTrader();
            wrk.setId(rs.getString("id"));
            wrk.setName(rs.getString("name"));
            wrk.setSurname(rs.getString("surname"));
            wrk.setPosition(pos);
            wrk.setPhoneNum(rs.getString("phoneNum"));
            wrk.setSalary(rs.getInt("salary"));
            if (pos.equals("Dyrektor")){
                ((WorkerDirector)wrk).setCardNum(rs.getString("cardNum"));
                ((WorkerDirector)wrk).setBusinessAllowance(rs.getInt("businessAllowance"));
                ((WorkerDirector)wrk).setCostLimit(rs.getInt("costLimit"));
            }
            else if (pos.equals("Handlowiec")){
                ((WorkerTrader)wrk).setProvision(rs.getInt("provision"));
                ((WorkerTrader)wrk).setProvLimit(rs.getInt("provLimit"));
            }
            tempWorkers.add(wrk);
        }
        rs.close();
        stmt.close();
        return tempWorkers;
    }

    public void Close(){
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void PutDataToDB(List<MyWorker> workers) {
        try {
            String sql = "TRUNCATE workers";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            PreparedStatement insert = conn.prepareStatement("insert into workers (id, name, surname, position, phoneNum, salary, cardNum, businessAllowance, costLimit, provision, provLimit) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            conn.setAutoCommit(false);
            for (int j = 0; j < workers.size(); ++j) {
                String pos = workers.get(j).getPosition();
                insert.setString(1, workers.get(j).getId());
                insert.setString(2, workers.get(j).getName());
                insert.setString(3, workers.get(j).getSurname());
                insert.setString(4, pos);
                insert.setString(5, workers.get(j).getPhoneNum());
                insert.setInt(6, workers.get(j).getSalary());
                if (pos.equals("Dyrektor")) {
                    insert.setString(7, ((WorkerDirector) workers.get(j)).getCardNum());
                    insert.setInt(8, ((WorkerDirector) workers.get(j)).getBusinessAllowance());
                    insert.setInt(9, ((WorkerDirector) workers.get(j)).getCostLimit());
                    insert.setNull(10, Types.INTEGER);
                    insert.setNull(11, Types.INTEGER);
                } else {
                    insert.setNull(7, Types.VARCHAR);
                    insert.setNull(8, Types.INTEGER);
                    insert.setNull(9, Types.INTEGER);
                    insert.setInt(10, ((WorkerTrader) workers.get(j)).getProvision());
                    insert.setInt(11, ((WorkerTrader) workers.get(j)).getProvLimit());
                }
                insert.executeUpdate();
                conn.commit();
            }
        } catch (SQLException e){
            try{
                if(conn!=null)
                    conn.rollback();
                e.printStackTrace();
            }catch(SQLException e2){
                e2.printStackTrace();
            }
        }
    }
}
