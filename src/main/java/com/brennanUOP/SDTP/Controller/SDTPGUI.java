package com.brennanUOP.SDTP.Controller;
import com.brennanUOP.SDTP.Model.Admission;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SDTPGUI extends JFrame {
    private DefaultTableModel tableModel;

    public SDTPGUI(SDTPController apiController) {
        setTitle("API GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Admissions For Specific Patient");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(submitButton);
        panel.add(inputPanel, BorderLayout.CENTER);

        // Create the table model with column names
        String[] columnNames = {"Admission ID", "Admission Date", "Discharge Date", "Patient ID"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create the table
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                List<Admission> admissions = apiController.GetAdmissionsForSpecificPatientEndpoint(id);
                displayAdmissions(admissions);
            }
        });

        add(panel);
        setVisible(true);
    }

    private void displayAdmissions(List<Admission> admissions) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Check if admissions list is empty or null
        if (admissions == null || admissions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient not found. Invalid ID provided.", "No data found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Add new rows
        for (Admission admission : admissions) {
            Object[] rowData = {admission.getId(), admission.getAdmissionDate(), admission.getDischargeDate(), admission.getPatientID()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        // Create an instance of SDTPController to pass to SDTPGUI
        SDTPController apiController = new SDTPController();
        new SDTPGUI(apiController);
    }
}
