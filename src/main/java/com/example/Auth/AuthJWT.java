package com.example.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthJWT {
    Boolean isAuthenticated = false;
    List<String> credentials = new ArrayList<>();

    public List<String> authenticateUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");

        String username = scanner.nextLine();
        System.out.println("Enter your password: ");

        String password = scanner.nextLine();
        if (username.equals("admin") && password.equals("admin")){
            isAuthenticated = true;
            System.out.println("You are authenticated!");
            credentials.add(username);
            credentials.add(password);
            return credentials;
        } else {
            System.out.println("Authentication failed!");
            return credentials;
        }
    }
}
