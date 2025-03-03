package com.todoapp.gui;

import com.todoapp.models.Task;
import com.todoapp.services.TaskService;
import com.todoapp.utils.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddTaskDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionField;
    private JComboBox<String> priorityBox;
    private JTextField dueDateField;
    private JButton addButton, cancelButton;

    public AddTaskDialog(JFrame parent) {
        super(parent, "Add New Task", true);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));
        setLocationRelativeTo(parent);

        // Task Title
        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        // Task Description
        add(new JLabel("Description:"));
        descriptionField = new JTextArea(3, 20);
        add(new JScrollPane(descriptionField));

        // Priority (Dropdown)
        add(new JLabel("Priority:"));
        priorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        add(priorityBox);

        // Due Date (YYYY-MM-DD format)
        add(new JLabel("Due Date (YYYY-MM-DD):"));
        dueDateField = new JTextField();
        add(dueDateField);

        // Buttons
        addButton = new JButton("Add Task");
        cancelButton = new JButton("Cancel");

        add(addButton);
        add(cancelButton);

        // Action Listener for Add Task Button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String priority = (String) priorityBox.getSelectedItem();
                String dueDateStr = dueDateField.getText();

                // Convert String to Date
                Date dueDate = DateUtils.parseDate(dueDateStr);
                if (dueDate == null) {
                    JOptionPane.showMessageDialog(AddTaskDialog.this, "Invalid Date Format! Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create a new Task and save it
                Task newTask = new Task(0, title, description, priority, dueDate, "Pending");
                TaskService.addTask(newTask);

                JOptionPane.showMessageDialog(AddTaskDialog.this, "Task Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the dialog
            }
        });

        // Cancel Button Action
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}
