
import java.util.*;

public class Student {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Store all student data
        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<ArrayList<Integer>> studentMarks = new ArrayList<>();

        String continueChoice;
        do {
            System.out.print("Enter the name of the student: ");
            String name = sc.nextLine();
            studentNames.add(name);

            System.out.print("Enter the number of subjects: ");
            int Sub_num = sc.nextInt();
            sc.nextLine(); // consume newline

            ArrayList<Integer> marks = new ArrayList<>();
            for (int i = 0; i < Sub_num; i++) {
                System.out.print("Enter the obtained marks of subject " + (i + 1) + ": ");
                int mark = sc.nextInt();
                marks.add(mark);
            }

            studentMarks.add(marks);

            System.out.print("\nDo you want to enter data for another student? (yes/no): ");
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

                // Print student report
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
