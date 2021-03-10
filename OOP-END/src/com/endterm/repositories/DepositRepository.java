package com.endterm.repositories;

import com.endterm.entities.Customer;
import com.endterm.data.interfaces.IDB;

import java.sql.*;
import java.util.Scanner;


public class DepositRepository {
    private final IDB db;
    private Scanner sc=new Scanner(System.in);
    private PaymentRepository pay_repo;
    private CardRepository card_repo;

    public DepositRepository(IDB db){
        this.db=db;
        pay_repo=new PaymentRepository(db);
        card_repo=new CardRepository(db);
    }

    public boolean openDeposit(Customer user){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="INSERT INTO Deposit (deposit_id,name,percent_rate,amountofmoney,date1,customer_id) VALUES (?,?,?,?,?,?);";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,(int)(Math.random()*10)+100);
            System.out.println("Choose option: \n1 Open Deposit\n2 Open Loan\n3 Open Mortgage");
            int option=sc.nextInt();
            if(option==1){
             ps.setString(2,"Deposit");

             ps.setInt(3,5);
             System.out.println("Final amount?");
             int sum=sc.nextInt();
                ps.setInt(4,sum);
                pay_repo.createPayment1(user,"Deposit",sum);
            }else if(option==2){
                ps.setString(2,"Loan");
                ps.setInt(3,10);
                System.out.println("How much do you want to apply for a loan?");
                int sum=sc.nextInt();
                ps.setInt(4,sum);
                pay_repo.createPayment1(user,"Loan",sum);
            }else if(option==3){
                ps.setString(2,"Mortgage");
                ps.setInt(3,10);
                System.out.println("How much do you want to apply for a mortgage?");
                int sum=sc.nextInt();
                ps.setInt(4,sum);
                pay_repo.createPayment1(user,"Mortgage",sum);
            }
            ps.setDate(5, Date.valueOf(java.time.LocalDate.now()));
            ps.setInt(6,user.getId());

            ps.execute();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



    public String getCustomersDeposits(Customer user){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="SELECT * FROM deposit WHERE customer_id=?;";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,user.getId());
            ResultSet rs=ps.executeQuery();
            String str=null;
            while(rs.next()){
                str+="ID: "+rs.getInt("id")+" Type: "+rs.getString("name")+" Total: "+rs.getInt("amountofmoney")+" Opened : "+rs.getDate("date1")+"\n";
            }
            return str;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getTotalSUMofDeposit(int id){
        Connection con=null;
        try {
            con=db.getConnection();
            String sql="SELECT amountofmoney FROM deposit WHERE deposit_id="+id;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet re=ps.executeQuery();
            if(re.next()){
                return re.getInt("amountofmoney");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public boolean closeDeposit(Customer user){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="DELETE FROM deposit WHERE customer_id=? AND deposit_id=?";
            PreparedStatement ps=con.prepareStatement(sql);
            System.out.println("Choose deposit: \n"+getCustomersDeposits(user));
            int dep_id=sc.nextInt();
            ps.setInt(1,user.getId());
            ps.setInt(2,dep_id);
            String sql1="UPDATE card SET sum=? WHERE customer_id="+user.getId();
            PreparedStatement ps1=con.prepareStatement(sql1);
            System.out.println("Select card: \n"+card_repo.getCustomerCards(user));
            int cardid= sc.nextInt();
            if(card_repo.getCustomerCardByID(user,cardid).getMik()-getTotalSUMofDeposit(dep_id)<0){
                return false;
            }
            ps1.setInt(1,card_repo.getCustomerCardByID(user,cardid).getMik()-getTotalSUMofDeposit(dep_id));
            ps.execute();
            ps1.execute();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
