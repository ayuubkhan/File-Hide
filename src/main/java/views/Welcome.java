package views;


import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;


public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the App");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 3 to delete your account");
        System.out.println("Press 0 to exit");
        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            case 3:
                deleteUser();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice! Please enter 1, 2, or 0.");
        }

    }

    public void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Enter your email");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the OTP");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0:
                    System.out.println("User Registered");
                    break;
                case 1:
                    System.out.println("User already exists");
            }
        } else {
            System.out.println("Wrong OTP");
        }
    }

    public void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email");
        String email = sc.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the OTP");
                String otp = sc.nextLine();
                if (otp.equals(genOTP)) {
                    String name = UserDAO.getUserName(email);
                    new UserView(name,email).home();
                } else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email to delete your account:");
        String email = sc.nextLine();

        try {

        if (UserDAO.isExists(email)) {
            System.out.println("Are you sure? Type 'YES' to confirm deletion:");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("YES")) {
                int result = UserDAO.deleteUser(email);
                if (result > 0) {
                    System.out.println("Your account and associated data have been deleted.");
                } else {
                    System.out.println("Something went wrong! Try again.");
                }
            } else {
                System.out.println("Account deletion cancelled.");
            }
        } else {
            System.out.println("User not found!");
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



}
