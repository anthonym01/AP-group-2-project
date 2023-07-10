package com.flowapp.populator;


import com.github.javafaker.Faker;
import com.flowapp.controller.Controller;
import com.flowapp.model.*;

import java.util.*;


// This class will generate data for the db for testing...
public class GenerateData {
    private static Controller controller;
    private static Customer customer = null;
    private static Employee employee = null;
    private static Message message = null;
    private static Faker faker;

    private static List<Customer> listOfCustomers;
    private static List<Employee> listOfEmployees;
    private static List<Message> listOfMessages;


    public GenerateData (){
        controller = new Controller();

        listOfCustomers = new ArrayList<>();
        listOfEmployees = new ArrayList<>();
        listOfMessages = new ArrayList<>();

        
        System.out.println("\n");
        bulkCustomerCreation(10); // Customers

        System.out.println("\n");
        bulkEmployeeCreation(10); // Employees

        System.out.println("\n");
        bulkMessageCreation(50); // Messages
    }

    /* BULK CREATION USING JAVA FAKER */
    // CUSTOMERS
    private static void bulkCustomerCreation(int amount){
        for(int i = 0; i < amount; i++){
            faker = new Faker();

            String id = String.valueOf(faker.idNumber().valid());
            String firstname = faker.name().firstName();
            String lastname = faker.name().lastName();
            String password = generatePassword(10);
            boolean isStaff = false;
            String email = faker.internet().emailAddress();
            String telephone = faker.phoneNumber().cellPhone();

            customer = new Customer(id, firstname, lastname, password, isStaff, email, telephone);
            listOfCustomers.add(customer);

            // Save the customer
            controller.create(customer);

            System.out.println("Customer: " + customer);
            System.out.println("Customer added: " + (1+i));
        }

        System.out.println("\n\nCustomers: " + listOfCustomers.size());
    }


    // EMPLOYEES
    private static void bulkEmployeeCreation(int amount){
        for(int i = 0; i < amount; i++){
            faker = new Faker();

            String id = String.valueOf(faker.idNumber().valid());
            String firstname = faker.name().firstName();
            String lastname = faker.name().lastName();
            String password = generatePassword(10);
            boolean isStaff = true;
            String role = randomizeRoleType();

            employee = new Employee(id, firstname, lastname, password, isStaff, role);
            listOfEmployees.add(employee);

            // Save the employee
            controller.create(employee);

            System.out.println("Employee: " + employee);
            System.out.println("Employee added: " + (1+i));
        }

        System.out.println("\n\nEmployee Members: " + listOfEmployees.size());
    }

    private static String randomizeRoleType(){
        String[] types = {"Representative", "Technichian"};

        Random random = new Random();

        int index;

        try{
            index = random.nextInt(types.length);
        }
        catch (Exception e){
            index = 0;
        }

        return types[index];
    }


    // MESSAGES
    private static void bulkMessageCreation(int amount){
        for(int i = 0; i < amount; i++){
            faker = new Faker();

            customer = randomizeCustomer(listOfCustomers);
            employee = randomizeEmployee(listOfEmployees);

            int messageID = 0;
            String messageType = randomizeIssueType();
            String category = randomizeCategory(messageType);
            String details = randomizeDetailsType(faker);
            Date dateCreated = new Date();

            message = new Message(messageID, messageType, category, details, dateCreated, false, null, null);
            customer.addMessage(message);
            employee.assignMessage(message);

            message.setCustomer(customer);
            message.setEmployee(employee);

            listOfMessages.add(message);

            // Save the message
            controller.create(message);

            System.out.println("Message: " + message);
            System.out.println("Message added: " + (1+i));

            try {
                Thread.sleep(500);
            } catch (Exception e) {}
        }

        System.out.println("\n\nMessage List: " + listOfMessages.size());
    }

    private static String randomizeIssueType(){
        String[] types = {"Make a Query", "Lodge a Complaint", "Request a Service"};

        Random random = new Random();

        int index;

        try{
            index = random.nextInt(types.length);
        }
        catch (Exception e){
            index = 0;
        }

        return types[index];
    }

    private static String randomizeCategory(String type){
        String[] categories = {"---"};

        if(type.equalsIgnoreCase("Make a Query")){
            categories = new String[] {"Career Path", "Language Support", "Job Opportunities", "Cultural Adjustment",
                    "Internship Opportunities", "Scholarship Applications", "Immigration Requirements"};
        }
        else if(type.equalsIgnoreCase("Lodge a Complaint")){
            categories = new String[] {"Harassment", "Discrimination", "Disciplinary Matters", "Financial Complaints"};
        }
        else if(type.equalsIgnoreCase("Request a Service")){
            categories = new String[] {"Service Delivery", "Campus Security", "Facilities Related"};
        }

        Random random = new Random();

        int index;

        try{
            index = random.nextInt(categories.length);
        }
        catch (Exception e){
            index = 0;
        }

        return categories[index];
    }

    private static String randomizeDetailsType(Faker faker){
        String[] types = {
                faker.shakespeare().asYouLikeItQuote(), faker.shakespeare().hamletQuote(),
                faker.shakespeare().kingRichardIIIQuote(), faker.shakespeare().romeoAndJulietQuote()
        };

        Random random = new Random();

        int index;

        try{
            index = random.nextInt(types.length - 1);
        }
        catch (Exception e){
            index = 0;
        }

        return types[index];
    }

    private static Customer randomizeCustomer(List<Customer> list){
        Random randomizer = new Random();

        return list.get(randomizer.nextInt(list.size()));
    }

    private static Employee randomizeEmployee(List<Employee> list){
        Random randomizer = new Random();

        return list.get(randomizer.nextInt(list.size()));
    }


    // Generate random password
    // Source: https://www.tutorialspoint.com/Generating-password-in-Java
    private static String generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$^&*()[]{}~";
        String numbers = "0123456789";

        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;

        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        return String.valueOf(password);
    }
    
}
