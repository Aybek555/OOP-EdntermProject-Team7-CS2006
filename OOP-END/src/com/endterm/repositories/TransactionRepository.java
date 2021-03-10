package com.endterm.repositories;

import com.endterm.entities.Card;
import com.endterm.data.interfaces.IDB;
import java.sql.*;
import java.util.Scanner;

public class TransactionRepository {
    private final IDB db;
    private CardRepository card_repo;
    private Scanner sc=new Scanner(System.in);

    public TransactionRepository(IDB db){
        this.db=db;
        card_repo=new CardRepository(db);
    }

    public boolean transferKZT(Card card1, Card card2){
        Connection con=null;
        try {
            con=db.getConnection();
            String sql="INSERT INTO transactioon (duedate,suminkzt,card_id) VALUES(?,?,?);";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setDate(1,Date.valueOf(java.time.LocalDate.now()));
            System.out.println("Enter sum: ");
            int sum = sc.nextInt();
            ps.setInt(2,sum);
            ps.setInt(3,card1.getId());




            String sql1="UPDATE card SET moneyinkzt=? WHERE card_id="+card1.getId();
            PreparedStatement ps1=con.prepareStatement(sql1);
            if(card1.getMik()-sum<0){
                return false;
            }else
            ps1.setInt(1,(card1.getMik()-sum));




            String sql2="UPDATE card SET moneyinkzt=? WHERE card_id="+card2.getId();
            PreparedStatement ps2=con.prepareStatement(sql2);
            ps2.setInt(1,(card2.getMik()+sum));





            ps.execute();
            ps1.execute();
            ps2.execute();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
