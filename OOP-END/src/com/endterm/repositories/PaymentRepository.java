package com.endterm.repositories;

import com.endterm.entities.Card;
import com.endterm.entities.Customer;
import com.endterm.data.interfaces.IDB;
import java.sql.*;
import java.util.Scanner;


public class PaymentRepository {
    private final IDB db;
    private Scanner sc = new Scanner(System.in);
    private CardRepository card_repo;

    public PaymentRepository(IDB db) {
        this.db = db;
        card_repo=new CardRepository(db);
    }

    public boolean createPayment(Customer user, String type) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO payment (paymentype,sum,pay_date,card_id) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, type);
            System.out.println("Enter sum: ");
            int sum = sc.nextInt();
            ps.setInt(2, sum);
            ps.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            ps.setInt(4,card_repo.getCustomerCards(user).get(0).getId());
            ps.execute();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean createPayment1(Customer user, String type,int sum) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO payment (paymentype,sum,pay_date,card_id) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, type);
            ps.setInt(2, sum);
            ps.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            ps.setInt(4,card_repo.getCustomerCards(user).get(0).getId());
            ps.execute();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String getPayments(Customer user){
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT paymentype,payment.sum FROM payment INNER JOIN card ON payment.card_id=card.card_id INNER JOIN customer ON card.customer_id=customer.customer_id WHERE  customer.customer_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,user.getId());
            ResultSet rs=ps.executeQuery();
            String str="";
            while(rs.next()){
                str+=rs.getString("paymentype")+" "+rs.getInt("sum")+"\n";
            }
            return str;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getPaymentSumByID(int id,String paymentype){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT sum FROM payment WHERE paymentype=? AND card_id=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,paymentype);
            ps.setInt(2,id);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                return rs.getInt("sum");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }


    public String getPaymentype(int id){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT paymentype FROM payment WHERE payment_id=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                return rs.getString("paymentype");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean doPayment(Customer user,String type){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="UPDATE card SET sum=? WHERE customer_id=? AND card_id=? ";
            PreparedStatement ps=con.prepareStatement(sql);


            if(card_repo.getCustomerCards(user).size()>1) {
                System.out.println(card_repo.getCustomerCards(user));
                System.out.println("Select your card: ");
                int card_id=sc.nextInt();
                ps.setInt(3,card_repo.getCustomerCardByID(user,card_id).getId());
                if(card_repo.getCustomerCardByID(user,card_id).getMik()-getPaymentSumByID(card_repo.getCustomerCardByID(user,card_id).getId(),type)<0){
                    return false;
                }
                ps.setInt(1,card_repo.getCustomerCardByID(user,card_id).getMik()-getPaymentSumByID(card_repo.getCustomerCardByID(user,card_id).getId(),type));
            }else if(card_repo.getCustomerCards(user).size()==1){
                Card c=card_repo.getCustomerCards(user).get(0);
                ps.setInt(3,card_repo.getCustomerCardByID(user,c.getId()).getId());
                if(card_repo.getCustomerCardByID(user,c.getId()).getMik()-getPaymentSumByID(c.getId(),type)<0){
                    return false;
                }
                ps.setInt(1,card_repo.getCustomerCardByID(user,c.getId()).getMik()-getPaymentSumByID(c.getId(),type));
            }
            ps.setInt(2,user.getId());
            ps.execute();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}

