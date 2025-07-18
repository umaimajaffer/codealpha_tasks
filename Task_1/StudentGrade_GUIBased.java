import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGrade extends JFrame {
    private JTextField studentNameField;
    private DefaultTableModel subjectTableModel;
    private JLabel avgLabel, highestScoreLabel, lowestScoreLabel, percentageLabel, finalGradeLabel;

    // Lists to store student data
    private ArrayList<String> studentNames = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> studentMarks = new ArrayList<>();
    private ArrayList<Integer> marksList = new ArrayList<>();

    private Color navy = new Color(44, 62, 80);
    private Color white = Color.WHITE;
    private Font uiFont = new Font("Times New Roman", Font.PLAIN, 16);

    public StudentGrade() {
        setTitle("Student Grading System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(navy);

        // Heading
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(navy);
        JLabel heading = new JLabel("Student Grade Calculator");
        heading.setForeground(white);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 24));
        headingPanel.add(heading);
        add(headingPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(navy);

        // Student Name Panel
        JPanel namePanel = new JPanel();
        namePanel.setBackground(navy);
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setForeground(white);
        nameLabel.setFont(uiFont);
        studentNameField = new JTextField(20);
        studentNameField.setFont(uiFont);
        namePanel.add(nameLabel);
        namePanel.add(studentNameField);
        centerPanel.add(namePanel, BorderLayout.NORTH);

        // Table for subjects
        String[] columns = {"Subject Name", "Total Marks", "Obtained Marks", "Grade"};
        subjectTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(subjectTableModel);
        subjectTableModel.addRow(new Object[]{"", "", "", ""}); // Default row
        JScrollPane tableScroll = new JScrollPane(table);
        centerPanel.add(tableScroll, BorderLayout.CENTER);
        table.setFont(uiFont);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
        table.getTableHeader().setBackground(navy);
        table.getTableHeader().setForeground(white);
        table.setGridColor(Color.LIGHT_GRAY);

        // Add subject button + Calculate Grades
        JButton addSubjectBtn = new JButton("Add Another Subject");
        JButton calcGradeBtn = new JButton("Calculate Grades");

        addSubjectBtn.addActionListener(e -> {
            subjectTableModel.addRow(new Object[]{"", "", "", ""});
        });
        calcGradeBtn.addActionListener(e -> calculateGrades());

        JPanel subjectButtonPanel = new JPanel();
        subjectButtonPanel.add(addSubjectBtn);
        subjectButtonPanel.add(calcGradeBtn);
        centerPanel.add(subjectButtonPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new GridLayout(4, 2));
        bottomPanel.setBackground(navy);
        
        avgLabel = new JLabel("Average: ");
        highestScoreLabel = new JLabel("Highest Score: ");
        lowestScoreLabel = new JLabel("Lowest Score: ");
        percentageLabel = new JLabel("Percentage: ");
        finalGradeLabel = new JLabel("Final Grade: ");
        
        // Set label styles
        for (JLabel label : new JLabel[]{avgLabel, highestScoreLabel, lowestScoreLabel, percentageLabel, finalGradeLabel}) {
            label.setForeground(white);
            label.setFont(uiFont);
            bottomPanel.add(label);
        }

        // Action Buttons
        JButton saveBtn = new JButton("Save");
        JButton anotherStuBtn = new JButton("Add Another Student");
        JButton summaryBtn = new JButton("View Summary");

        // Style buttons
        for (JButton btn : new JButton[]{saveBtn, anotherStuBtn, summaryBtn}) {
            btn.setBackground(navy);
            btn.setForeground(white);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Times New Roman", Font.BOLD, 16));
            btn.setPreferredSize(new Dimension(160, 40));
        }

        saveBtn.addActionListener(e -> saveStudents());
        anotherStuBtn.addActionListener(e -> clearForm());
        summaryBtn.addActionListener(e -> viewSummary());

        bottomPanel.add(saveBtn);
        bottomPanel.add(anotherStuBtn);
        bottomPanel.add(summaryBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Save button logic
    public void saveStudents() {
        if (subjectTableModel.getRowCount() < 3) {
    JOptionPane.showMessageDialog(this, "Please enter at least 3 subjects before saving the student.");
    return;
}

        String studentName = studentNameField.getText().trim();
        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the student's name.");
            return;
        }

        if (marksList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please calculate grades before saving.");
            return;
        }

        // Save student data
        studentNames.add(studentName);
        studentMarks.add(new ArrayList<>(marksList)); // Store a copy
        JOptionPane.showMessageDialog(this, "Student saved successfully!");
    }

    // Calculate grades and update UI
    public void calculateGrades() {
        if (subjectTableModel.getRowCount() < 3) {
    JOptionPane.showMessageDialog(this, "Please enter at least 3 subjects before calculating grades.");
    return;
}

        int rowCount = subjectTableModel.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Please add at least one subject.");
            return;
        }

        double totalObtained = 0;
        double totalMarks = 0;
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        marksList.clear(); // Clear previous data

        for (int i = 0; i < rowCount; i++) {
            Object subjectObj = subjectTableModel.getValueAt(i, 0);
            Object totalStrObj = subjectTableModel.getValueAt(i, 1);
            Object obtainedStrObj = subjectTableModel.getValueAt(i, 2);

            String subject = subjectObj == null ? "" : subjectObj.toString().trim();
            String totalStr = totalStrObj == null ? "" : totalStrObj.toString().trim();
            String obtainedStr = obtainedStrObj == null ? "" : obtainedStrObj.toString().trim();

            if (subject.isEmpty() || totalStr.isEmpty() || obtainedStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields for each subject.");
                return;
            }

            try {
                int total = Integer.parseInt(totalStr);
                int obtained = Integer.parseInt(obtainedStr);

                if (obtained > total) {
                    JOptionPane.showMessageDialog(this, "Obtained marks cannot be greater than total marks for " + subject);
                    return;
                }

                totalMarks += total;
                totalObtained += obtained;
                if (obtained > highest) highest = obtained;
                if (obtained < lowest) lowest = obtained;
                marksList.add(obtained);

                String grade = calculateGrade((double) obtained / total * 100);
                subjectTableModel.setValueAt(grade, i, 3);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for marks.");
                return;
            }
        }

        double average = totalObtained / rowCount;
        double percentage = (totalObtained / totalMarks) * 100;
        String finalGrade = calculateGrade(percentage);

        avgLabel.setText("Average: " + String.format("%.2f", average));
        highestScoreLabel.setText("Highest Score: " + highest);
        lowestScoreLabel.setText("Lowest Score: " + lowest);
        percentageLabel.setText("Percentage: " + String.format("%.2f", percentage) + "%");
        finalGradeLabel.setText("Final Grade: " + finalGrade);
    }

    // Grade calculator
    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }

    // Reset form for new student
    public void clearForm() {
        studentNameField.setText("");
        subjectTableModel.setRowCount(0);
        subjectTableModel.addRow(new Object[]{"", "", "", ""}); // Reset with one empty row
        avgLabel.setText("Average: ");
        highestScoreLabel.setText("Highest Score: ");
        lowestScoreLabel.setText("Lowest Score: ");
        percentageLabel.setText("Percentage: ");
        finalGradeLabel.setText("Final Grade: ");
        marksList.clear();
    }

    // View all saved student summaries
    public void viewSummary() {
    if (studentNames.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No student data saved yet.");
        return;
    }

    StringBuilder report = new StringBuilder("===== STUDENT SUMMARY REPORT =====\n\n");

    for (int i = 0; i < studentNames.size(); i++) {
        String name = studentNames.get(i);
        ArrayList<Integer> marks = studentMarks.get(i);

        int obtained = 0;
        int total = 0;

        // For each row, get total and obtained marks again
        for (int j = 0; j < marks.size(); j++) {
            obtained += marks.get(j);

            // Get total marks from table (from current form at save time)
            Object totalObj = subjectTableModel.getValueAt(j, 1);
            int totalMarks = 0;
            try {
                totalMarks = totalObj == null ? 0 : Integer.parseInt(totalObj.toString());
            } catch (Exception ignored) {}

            total += totalMarks;
        }

        double percentage = total == 0 ? 0 : (double) obtained / total * 100;
        String grade = calculateGrade(percentage);

        report.append("Student Name: ").append(name).append("\n");
        report.append("Marks: ").append(obtained).append("/").append(total).append("\n");
        report.append("Percentage: ").append(String.format("%.2f", percentage)).append("%\n");
        report.append("Final Grade: ").append(grade).append("\n");
        report.append("-----------------------------------\n");
    }

    JTextArea textArea = new JTextArea(report.toString());
    textArea.setEditable(false);
    textArea.setFont(uiFont);
    textArea.setBackground(navy);
    textArea.setForeground(white);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(500, 400));

    JOptionPane.showMessageDialog(this, scrollPane, "Student Summary", JOptionPane.INFORMATION_MESSAGE);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGrade().setVisible(true));
    }
}