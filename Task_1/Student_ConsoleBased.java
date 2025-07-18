import java.util.*;

public class Student_ConsoleBased {

    public static String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<ArrayList<Integer>> studentMarks = new ArrayList<>();

        String continueChoice;
        do {
            System.out.print("Enter the name of the student: ");
            String name = sc.nextLine();
            studentNames.add(name);

            System.out.print("Enter the number of subjects: ");
            int sub_num = sc.nextInt();
            sc.nextLine(); 
            ArrayList<Integer> marks = new ArrayList<>();
            for (int i = 0; i <sub_num; i++) {
                System.out.print("Enter obtained marks of Subject " + (i + 1) + ": ");
                int mark = sc.nextInt();
                marks.add(mark);
            }

            studentMarks.add(marks); 

            int totalObtained = 0;
            int fullMarks = 100 *sub_num;

            System.out.println("\n========== Student Report for " + name + " ==========");
            System.out.printf("%-15s%-15s%-18s%-10s\n", "Subject Name", "Total Marks", "Obtained Marks", "Grade");
            System.out.println("-----------------------------------------------------------");

            for (int i = 0; i <sub_num; i++) {
                String subject = "Subject " + (i + 1);
                int obtained = marks.get(i);
                totalObtained += obtained;
                String grade = calculateGrade(obtained);
                System.out.printf("%-15s%-15d%-18d%-10s\n", subject, 100, obtained, grade);
            }

            double percentage = (double) totalObtained / fullMarks * 100;
            String finalGrade = calculateGrade(percentage);

            System.out.println("-----------------------------------------------------------");
            System.out.printf("Percentage: %.2f%%\n", percentage);
            System.out.println("Final Grade: " + finalGrade);
            System.out.println("===========================================================\n");

            System.out.print("Do you want to enter data for another student? (yes/no): ");
            sc.nextLine(); // consume leftover newline
            continueChoice = sc.nextLine().toLowerCase();

        } while (continueChoice.equals("yes"));

        System.out.print("\nDo you want to see the student summary report? (yes/no): ");
        String reportChoice = sc.nextLine().toLowerCase();

        if (reportChoice.equals("yes")) {
            System.out.println("\n===== STUDENT SUMMARY REPORT =====\n");

            for (int i = 0; i < studentNames.size(); i++) {
                String name = studentNames.get(i);
                ArrayList<Integer> marks = studentMarks.get(i);

                int sum = 0;
                for (int mark : marks) {
                    sum += mark;
                }
                double avg = (double) sum / marks.size();
                int max = Collections.max(marks);
                int min = Collections.min(marks);

                System.out.println("Student Name: " + name);
                System.out.println("Subject Scores: " + marks);
                System.out.println("Total: " + sum);
                System.out.println("Average: " + avg);
                System.out.println("Highest Score: " + max);
                System.out.println("Lowest Score: " + min);
                System.out.println("***___________________________***\n");
            }
        }

        sc.close();
    }
}
