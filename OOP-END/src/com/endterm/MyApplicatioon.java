package com.endterm;

import com.endterm.controllers.*;
import com.endterm.data.PostgresDB;
import com.endterm.data.interfaces.IDB;
import com.endterm.entities.Card;
import com.endterm.repositories.*;

import java.util.Scanner;

public class MyApplicatioon {
    private final IDB db = new PostgresDB();
    private final Scanner sc = new Scanner(System.in);

    public MyApplicatioon() {

    }

    private CustomerRepository customerRepository = new CustomerRepository(db);
    private CustomerController customerController = new CustomerController(customerRepository);

    private DepositRepository depositRepository = new DepositRepository(db);
    private DepositController depositController = new DepositController(depositRepository);

    private PaymentRepository paymentRepository = new PaymentRepository(db);
    private PaymentController paymentController = new PaymentController(paymentRepository);

    private TransactionRepository transactionRepository = new TransactionRepository(db);
    private TransactionController transactionController = new TransactionController(transactionRepository);

    private CardRepository cardRepository = new CardRepository(db);
    private CardController cardController = new CardController(cardRepository);


    public void Start() {
        while (true) {
            System.out.println("Welcome!\nChoose option: \n1 Registration\n2 Open credit card \n3 Open deposit\n4 Pay for deposit\n5 Transfer money\n0 Exit");
            int option = sc.nextInt();
            while (option != 1 && option != 2 && option != 3 && option != 0 && option != 4 && option != 5) {
                System.out.println("Choose only from possible options");
                option = sc.nextInt();
            }
            if (option == 1) {
                System.out.println(customerRepository.createCustomer());
            } else if (option == 2) {
                System.out.println("Opening credit card...\nEnter your IIN");
                String iin = sc.next();
                while (iin.length() != 12) {
                    System.out.println("Please enter correct IIN 12 characters");
                    iin = sc.next();
                }
                System.out.println(cardController.createCard(customerRepository.getCustomerByIIN(iin)));

            } else if (option == 3) {
                System.out.println("Creating credit card...\nEnter your IIN: ");
                String iin = sc.next();
                while (iin.length() != 12) {
                    System.out.println("Please enter correct IIN 12 characters");
                    iin = sc.next();
                }
                System.out.println(depositController.openDeposit(customerRepository.getCustomerByIIN(iin)));

            } else if (option == 4) {
                System.out.println("Starting payment...\nEnter your IIN: ");
                String iin = sc.next();
                while (iin.length() != 12) {
                    System.out.println("Please enter correct IIN 12 characters");
                    iin = sc.next();
                }
                System.out.println(paymentController.getPayments(customerRepository.getCustomerByIIN(iin)) + "Select payment: ");
                String type = sc.next();
                System.out.println(paymentController.doPayment(customerRepository.getCustomerByIIN(iin), type));
            } else if (option == 5) {
                System.out.println("Starting transaction...\nEnter your IIN: ");
                String iin = sc.next();
                while (iin.length() != 12) {
                    System.out.println("Please enter correct IIN 12 characters");
                    iin = sc.next();
                }
                System.out.println(cardRepository.getCustomerCards(customerRepository.getCustomerByIIN(iin)));
                System.out.println("Select card: ");
                int card_id = sc.nextInt();
                System.out.println("Enter card number where you wish transfer money: ");
                String card_number = sc.next();
                Card card = cardRepository.getCardByCardNumber(card_number);
                System.out.println(transactionController.transferKZT(cardRepository.getCustomerCardByID(customerRepository.getCustomerByIIN(iin), card_id), card));
            }else if(option==0){
                break;
            }
        }
    }
}

