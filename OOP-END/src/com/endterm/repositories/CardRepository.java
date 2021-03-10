package com.endterm.repositories;

import com.endterm.entities.Card;
import com.endterm.entities.Customer;
import com.endterm.data.interfaces.IDB;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CardRepository {
    private final IDB db;
    private final Scanner sc=new Scanner(System.in);

    public CardRepository(IDB db){
        this.db=db;
    }

    public boolean createCard(Customer user){
            Connection con = null;
            try {
                con = db.getConnection();
                String sql = "INSERT INTO card (card_number, moneyinkzt,moneyinrub,moneyinusd,cvv,customer_id) values (?,?,?,?,?,?);";
                PreparedStatement ps = con.prepareStatement(sql);
                System.out.println("Enter number for your card, Please enter 16 numbers at least");
                while (true) {
                    String str = sc.nextLine();
                    if (str.length() > 16) {
                        System.out.println("No more more than  16 digits!");
                        continue;
                    } else if (str.length() < 16) {
                        System.out.println("No less than 16 digits");
                        continue;
                    } else {
                        System.out.println("Card number created successfully!");
                        ps.setString(1, str);
                        break;
                    }
                }
                ps.setInt(2,0);
                ps.setInt(3,0);
                ps.setInt(4,0);

                System.out.println("Enter cvv code of your card, Maximum 3 digits");
                BigDecimal cvv;
                while(true){
                    cvv=sc.nextBigDecimal();
                    String str2=cvv+"";
                    if(str2.length()>3){
                        System.out.println("No more than 3 digits");
                        continue;
                    }else if(str2.length()<3){
                        System.out.println("No less than 3 digits");
                        continue;
                    }else{
                        System.out.println("cvv code created successfully");
                        break;
                    }
                }
                ps.setBigDecimal(5,cvv);
                ps.setInt(6,user.getId());
                ps.execute();
        return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Some exception occurred: " + e);
            }
        }
        return false;
    }


   /* public void setCardNum(String cn, Card card){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="UPDATE card SET card_number="+cn+"WHERE card_id="+card.getId();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.execute();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }*/

    public List<Card> getAllDBCards(){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT * FROM card";
            List<Card> cards=new LinkedList<>();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Card card=new Card(rs.getInt("card_id"),rs.getString("card_number"),rs.getBigDecimal("cvv"));
                cards.add(card);
            }
            return cards;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public int getBalance(int id){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT * FROM card WHERE card_id="+id;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt("moneyinkzt");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public Card getCardByID(int id){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT * FROM card WHERE card_id=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Card card=new Card(rs.getInt("card_id"),rs.getString("card_number"),rs.getBigDecimal("cvv"));
                card.setMik(rs.getInt("moneyinkzt"));
                return card;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean setKZTIntoCard(String card_number,int sum){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="UPDATE card SET moneyinkzt=? WHERE card_number="+card_number;
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,sum);
            return ps.execute();
        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean setRUBIntoCard(String card_number,int sum){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="UPDATE card SET moneyinrub=? WHERE card_number=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,sum);
            ps.setString(2,card_number);
            ps.execute();
            return true;
        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean setUSDIntoCard(String card_number,int sum){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="UPDATE card SET moneyinusd=? WHERE card_number=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,sum);
            ps.setString(2,card_number);
            ps.execute();
            return true;
        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Card> getCustomerCards(Customer customer){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT * FROM card WHERE customer_id=?";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1,customer.getId());
            ResultSet rs=ps.executeQuery();
            List<Card> cards=new ArrayList<>();
            while(rs.next()){
                Card c1=new Card(rs.getInt("card_id"),rs.getString("card_number"),rs.getBigDecimal("cvv"));
                cards.add(c1);
            }
            return cards;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Some exception occurred: " + e);
            }
        }
        return null;
    }

    public Card getCustomerCardByID(Customer user, int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT customer.customer_id,fname,lname,card_id, moneyinkzt, cvv FROM card inner join customer on customer.customer_id=card.customer_id WHERE card_id=? AND customer.customer_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Card card = new Card(rs.getInt("card_id"), rs.getBigDecimal("cvv"));
                card.setMik(rs.getInt("moneyinkzt"));
                return card;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Some exception occurred: " + e);
            }
        }
        return null;
    }

    public Card getCardByCardNumber(String card_number){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT * FROM card WHERE card_number=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,card_number);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Card card=new Card(rs.getString("card_number"),rs.getBigDecimal("cvv"));
                return card;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


}











































































































































