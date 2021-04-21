package models.people;

import java.time.Duration;
import java.time.Instant;

public class Staff extends Person{
    private double payRate;
    private int hoursWorked;

    public Staff(){}

    public Staff(double payRate, int hoursWorked) {
        this.payRate = payRate;
        this.hoursWorked = hoursWorked;
    }

    public Staff(String firstName, String lastName, String address, String phoneNumber, double payRate) {
        super(firstName, lastName, address, phoneNumber);
        this.payRate = payRate;
        this.hoursWorked = 0;
    }

    public Staff(String firstName, String lastName, String address, String phoneNumber, double payRate, int hoursWorked) {
        super(firstName, lastName, address, phoneNumber);
        this.payRate = payRate;
        this.hoursWorked = hoursWorked;
    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double calculatePay(){
        double result = this.hoursWorked * this.payRate;
        return result;
    }

    public double payEmployee(){
        double amount = calculatePay();
        this.setHoursWorked(0);
        return amount;
    }

}
