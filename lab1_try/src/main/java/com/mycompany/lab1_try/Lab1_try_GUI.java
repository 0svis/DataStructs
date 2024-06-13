/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1_try;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Lab1_try Project's GUI class
 * @author Osvaldas
 */
public class Lab1_try_GUI extends JFrame implements ActionListener {
    private JButton browseInputButton, browseOutputButton, processButton;
    private JTextField inputField, outputField;
    private JLabel inputLabel, outputLabel;

    public Lab1_try_GUI() {
        // Sets the frame properties
        setTitle("Duomenu strukturu pagrindai_LAB1");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creates components
        inputLabel = new JLabel("Input File:");
        inputField = new JTextField(20);
        browseInputButton = new JButton("Browse");
        outputLabel = new JLabel("Output File:");
        outputField = new JTextField(20);
        browseOutputButton = new JButton("Browse");
        processButton = new JButton("Process");

        // Adds action listeners
        browseInputButton.addActionListener(this);
        browseOutputButton.addActionListener(this);
        processButton.addActionListener(this);

        // Creates panels to organize components
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(browseInputButton);

        JPanel outputPanel = new JPanel(new FlowLayout());
        outputPanel.add(outputLabel);
        outputPanel.add(outputField);
        outputPanel.add(browseOutputButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(processButton);

        // Create a main panel to hold all sub-panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(outputPanel);
        mainPanel.add(buttonPanel);

        // Add the main panel to the frame
        add(mainPanel);

        // Show the frame
        setVisible(true);
    }
/**
 * overrides actionPerformed method
 * @param e pressed button information
 */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Checks which button is pressed
         */
        if (e.getSource() == browseInputButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                inputField.setText(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == browseOutputButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                outputField.setText(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == processButton) {
            String inputFilePath = inputField.getText();
            String outputFilePath = outputField.getText();

            Komanda komandos = new Komanda();
            Lab1_try.Skaitymas(inputFilePath, komandos);
            Komanda atnaujinta = new Komanda();
/**
 * Prints data into results file
 */
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath, false))) {
                Lab1_try.Spausdinti(komandos, "Pradiniai duomenys", writer);
                Lab1_try.Formuoti(komandos, atnaujinta, Lab1_try.Vidurkis(komandos));
                Lab1_try.Spausdinti(atnaujinta, "Rezultatai", writer);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error writing output file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        }
    }
}
