package moit101solo.motorphpayrollsystem;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class MotorPHPayrollSystem {

    static Scanner scanner = new Scanner(System.in);
    static List<String[]> payrollRecords = new ArrayList<>();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n====== MOTORPH PAYROLL SYSTEM ======");
            System.out.println("1. Add Employee Payroll");
            System.out.println("2. Export Payroll to CSV");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            choice = getIntInput();

            switch (choice) {
                case 1:
                    addEmployeePayroll();
                    break;
                case 2:
                    exportCSV();
                    break;
                case 3:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 3);
    }

    // Add employee payroll
    public static void addEmployeePayroll() {

        scanner.nextLine(); 

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.print("Enter hourly rate: ");
        double rate = getDoubleInput();

        System.out.print("Enter hours worked (1st cutoff): ");
        double h1 = getDoubleInput();

        System.out.print("Enter hours worked (2nd cutoff): ");
        double h2 = getDoubleInput();

        double cutoff1 = computeGrossPay(h1, rate);
        double cutoff2 = computeGrossPay(h2, rate);
        double monthlyGross = cutoff1 + cutoff2;

        double sss = monthlyGross * 0.045;
        double philhealth = monthlyGross * 0.03;
        double pagibig = monthlyGross * 0.02;
        double tax = monthlyGross * 0.10;

        double deductions = sss + philhealth + pagibig + tax;
        double netSecond = cutoff2 - deductions;

        displayPayroll(name, cutoff1, cutoff2, monthlyGross,
                sss, philhealth, pagibig, tax, deductions, netSecond);

        // Save record for CSV
        payrollRecords.add(new String[]{
            name,
            String.format("%.2f", cutoff1),
            String.format("%.2f", cutoff2),
            String.format("%.2f", monthlyGross),
            String.format("%.2f", deductions),
            String.format("%.2f", netSecond)
        });
    }

    // Input validation for integers
    public static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Input validation for decimals
    public static double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    // Compute salary
    public static double computeGrossPay(double hours, double rate) {
        return hours * rate;
    }

    // Display payroll
    public static void displayPayroll(String name,
                                      double c1,
                                      double c2,
                                      double gross,
                                      double sss,
                                      double ph,
                                      double pi,
                                      double tax,
                                      double deductions,
                                      double netSecond) {

        System.out.println("\n======= PAYROLL SUMMARY =======");
        System.out.println("Employee: " + name);

        System.out.printf("1st Cutoff Salary : P%,.2f\n", c1);
        System.out.printf("2nd Cutoff Salary : P%,.2f\n", c2);

        System.out.printf("Monthly Gross     : P%,.2f\n", gross);

        System.out.println("\nDeductions");
        System.out.printf("SSS        : P%,.2f\n", sss);
        System.out.printf("PhilHealth : P%,.2f\n", ph);
        System.out.printf("PagIBIG    : P%,.2f\n", pi);
        System.out.printf("Tax        : P%,.2f\n", tax);

        System.out.printf("\nTotal Deductions : P%,.2f\n", deductions);
        System.out.printf("Net 2nd Cutoff   : P%,.2f\n", netSecond);

        System.out.println("===============================");
    }

    // Export payroll to CSV
    public static void exportCSV() {

        if (payrollRecords.isEmpty()) {
            System.out.println("No payroll records to export.");
            return;
        }

        try {

            FileWriter writer = new FileWriter("motorph_payroll.csv");

            writer.append("Employee Name,1st Cutoff,2nd Cutoff,Monthly Gross,Total Deductions,Net 2nd Cutoff\n");

            for (String[] record : payrollRecords) {

                writer.append(String.join(",", record));
                writer.append("\n");
            }

            writer.flush();
            writer.close();

            System.out.println("Payroll exported to motorph_payroll.csv");

        } catch (IOException e) {
            System.out.println("Error exporting file.");
        }
    }
}/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


