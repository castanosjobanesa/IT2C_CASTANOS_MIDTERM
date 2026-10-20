package com.scc.ui;

import com.scc.dao.ScenarioDao;
import com.scc.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final ScenarioDao dao;
    private final Scanner scanner;

    public ScenarioMenu() {
        this.dao = new ScenarioDao();
        this.scanner = new Scanner(System.in);
        this.dao.initializeDatabase(); // Ensures table is created on startup
    }

    public void displayMenu() {
        while (true) {
            System.out.println("=============================================");
            System.out.println("        FITNESS TRAINER SESSION SCHEDULER");
            System.out.println("=============================================");
            System.out.println("[1] Book Training Session");
            System.out.println("[2] View Booked Sessions");
            System.out.println("[3] Exit System");
            System.out.println("=============================================");
            System.out.print("Enter choice: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline buffer
            } else {
                System.out.println("Invalid choice. Please enter a valid option.\n");
                scanner.nextLine();
                continue;
            }

            System.out.println();

            switch (choice) {
                case 1 -> bookSession();
                case 2 -> viewSessions();
                case 3 -> {
                    System.out.println("Exiting system... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose [1], [2], or [3].\n");
            }
        }
    }

    private void bookSession() {
        System.out.println("--- BOOK TRAINING SESSION ---");

        System.out.print("Enter Client Name: ");
        String clientName = scanner.nextLine();

        System.out.print("Enter Trainer Name: ");
        String trainerName = scanner.nextLine();

        System.out.print("Enter Session Fee: ");
        double sessionFee;
        if (scanner.hasNextDouble()) {
            sessionFee = scanner.nextDouble();
            scanner.nextLine(); // Clear newline buffer
        } else {
            System.out.println("Invalid fee input.\n");
            scanner.nextLine();
            return;
        }

        System.out.print("Enter Status (SCHEDULED / COMPLETED): ");
        String status = scanner.nextLine();

        dao.addSession(clientName, trainerName, sessionFee, status);

        System.out.println();
        System.out.println("SUCCESS: Personal training session booked!");
        System.out.println();
    }

    private void viewSessions() {
        System.out.println("=========================================================================");
        System.out.println("                        TRAINER SESSION SCHEDULES");
        System.out.println("=========================================================================");
        System.out.println("+-----+-----------------------+-------------------+-------------+--------------+");
        System.out.printf("| %-3s | %-21s | %-17s | %-11s | %-12s |\n", "ID", "CLIENT NAME", "TRAINER NAME", "SESSION FEE", "STATUS");
        System.out.println("+-----+-----------------------+-------------------+-------------+--------------+");

        List<ScenarioEntity> sessions = dao.getAllSessions();

        if (sessions.isEmpty()) {
            System.out.printf("| %-67s |\n", "No booked sessions found.");
        } else {
            for (ScenarioEntity session : sessions) {
                System.out.printf("| %-3d | %-21s | %-17s | %-11.2f | %-12s |\n",
                        session.id(),
                        session.clientName(),
                        session.trainerName(),
                        session.sessionFee(),
                        session.status());
            }
        }

        System.out.println("+-----+-----------------------+-------------------+-------------+--------------+");
        System.out.println("Total Records Displayed: " + sessions.size());
        System.out.println();
    }
}