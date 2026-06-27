package com.oms;

import com.oms.db.DBConnection;
import com.oms.ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        DBConnection.getInstance();

        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}