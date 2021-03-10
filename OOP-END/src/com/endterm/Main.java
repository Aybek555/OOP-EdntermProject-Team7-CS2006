package com.endterm;

import com.endterm.data.interfaces.IDB;
import com.endterm.data.PostgresDB;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        IDB db = new PostgresDB();
        MyApplication application = new MyApplication(db);
        application.start();

    }
}


