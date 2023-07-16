package com.flowapp.driver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;



public class Utils {
    private static final String FONT = "Arial";
    private static String imagePath = "/images/";


    // Get an image from the resource path...
    public static Image getImage(String path){
        // Find the image file in the resources directory
        try {
            return ImageIO.read(Utils.class.getResource(imagePath + path));
        } 
        catch (IOException e) {
            // Add logger here...

            System.err.println(e.getMessage());
        }

        return null;
    }

    // Get an icon from the resource path...
    public static ImageIcon getIcon(String path){
        // Find the image file in the resources directory
        try {
            return new ImageIcon(ImageIO.read(Utils.class.getResource(imagePath + path)));
        } 
        catch (IOException e) {
            // Add logger here...

            System.err.println(e.getMessage());
        }

        return null;
    }

    // Get an icon
    public static Font getFont(int type, int size) {
        return new Font(FONT, type, size);
    }

    // Get menu item color that will indicate a highlight [custom]
    public static void highlightMenuItem(JMenuItem menuItem){
        menuItem.setForeground(Color.LIGHT_GRAY);
    }

    // Get menu item color that will indicate a no-highlight [custom]
    public static void removeMenuItemHighlight(JMenuItem menuItem){
        JMenuItem m = new JMenuItem();

        // Resetting the default colors
        menuItem.setForeground(m.getForeground());
    }

    // Date converter: Create a string format of dates...
    public static String convertDate(Date date){
        String pattern = "MMMM dd, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);
    }

    // This method will be used to manipulate the color of a toggle button
    public static void toggleSwitchIndicator(JButton toggleBtn, boolean selected){
        if(selected){
            toggleBtn.setBackground(Color.GREEN);
            toggleBtn.setForeground(Color.BLACK);

            toggleBtn.setText("Turn off Live Chat Availability");
        }
        else{
            toggleBtn.setBackground(Color.RED);
            toggleBtn.setForeground(Color.WHITE);

            toggleBtn.setText("Turn on Live Chat Availability");
        }

        toggleBtn.setSelected(!selected);
    }

    // Generate random id string for a chat
    // Source: https://www.tutorialspoint.com/Generating-password-in-Java
    public static String generateChatID(int length) {
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
