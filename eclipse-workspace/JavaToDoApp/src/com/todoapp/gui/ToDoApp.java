package com.todoapp.gui;

import com.todoapp.services.TaskService;
import com.todoapp.models.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ToDoApp extends JFrame {
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private TaskService taskService;

    public ToDoApp() {
        taskService = new TaskService();

        setTitle("To-Do List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Priority"}, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Task");
        JButton updateButton = new JButton("Update Task");
        JButton deleteButton = new JButton("Delete Task");
        JButton sortButton = new JButton("Sort Tasks");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load tasks into table
        updateTaskList();

        // Button Listeners
        addButton.addActionListener(e -> addTaskAction());
        updateButton.addActionListener(e -> updateTaskAction());
        deleteButton.addActionListener(e -> deleteTaskAction());
        sortButton.addActionListener(e -> sortTaskAction());

        setVisible(true);
    }

    //  Add Task
    private void addTaskAction() {
        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();
        String[] priorityOptions = {"High", "Medium", "Low"};
        JComboBox<String> priorityBox = new JComboBox<>(priorityOptions);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Enter Task Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Enter Task Description:"));
        panel.add(descField);
        panel.add(new JLabel("Select Priority:"));
        panel.add(priorityBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String description = descField.getText().trim();
            String priority = (String) priorityBox.getSelectedItem();

            if (!title.isEmpty() && !description.isEmpty()) {
                taskService.addTask(title, description, priority);
                updateTaskList();
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //  Update Task
    private void updateTaskAction() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a task to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int taskId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentTitle = (String) tableModel.getValueAt(selectedRow, 1);
        String currentDesc = (String) tableModel.getValueAt(selectedRow, 2);
        String currentPriority = (String) tableModel.getValueAt(selectedRow, 3);

        JTextField titleField = new JTextField(currentTitle);
        JTextField descField = new JTextField(currentDesc);
        String[] priorityOptions = {"High", "Medium", "Low"};
        JComboBox<String> priorityBox = new JComboBox<>(priorityOptions);
        priorityBox.setSelectedItem(currentPriority);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Update Task Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Update Task Description:"));
        panel.add(descField);
        panel.add(new JLabel("Update Priority:"));
        panel.add(priorityBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            taskService.updateTask(taskId, titleField.getText(), descField.getText(), (String) priorityBox.getSelectedItem());
            updateTaskList();
        }
    }

    //  Delete Task
    private void deleteTaskAction() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int taskId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete Task", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            taskService.deleteTask(taskId);
            updateTaskList();
        }
    }

    //  Sort Tasks
    private void sortTaskAction() {
        List<Task> sortedTasks = taskService.getSortedTasks();
        tableModel.setRowCount(0);
        for (Task task : sortedTasks) {
            tableModel.addRow(new Object[]{task.getId(), task.getTitle(), task.getDescription(), task.getPriority()});
        }
    }

    // Refresh task list
    private void updateTaskList() {
        tableModel.setRowCount(0);
        List<Task> tasks = taskService.getTasks();
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getId(), task.getTitle(), task.getDescription(), task.getPriority()});
        }
    }

    public static void main(String[] args) {
        new ToDoApp();
    }
}
