import java.util.*;

// Student class to represent individual student data
class Student {
    private String name;
    private String studentID;
    private ArrayList<Double> grades;

    public Student(String name, String studentID) {
        this.name = name;
        this.studentID = studentID;
        this.grades = new ArrayList<>();
    }

    public void addGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            grades.add(grade);
        } else {
            System.out.println("❌ Invalid grade! Please enter a value between 0-100.");
        }
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0;
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public double getHighest() {
        if (grades.isEmpty()) return 0;
        double highest = grades.get(0);
        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }
        return highest;
    }

    public double getLowest() {
        if (grades.isEmpty()) return 0;
        double lowest = grades.get(0);
        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }
        return lowest;
    }

    public String getName() {
        return name;
    }

    public String getStudentID() {
        return studentID;
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }

    public int getGradeCount() {
        return grades.size();
    }

    public String getGradeLetters() {
        ArrayList<String> letterGrades = new ArrayList<>();
        for (double grade : grades) {
            if (grade >= 90) letterGrades.add("A");
            else if (grade >= 80) letterGrades.add("B");
            else if (grade >= 70) letterGrades.add("C");
            else if (grade >= 60) letterGrades.add("D");
            else letterGrades.add("F");
        }
        return letterGrades.toString();
    }

    @Override
    public String toString() {
        return "ID: " + studentID + " | Name: " + name + " | Grades: " + grades;
    }
}

// GradeTracker class to manage all students and generate reports
class GradeTracker {
    private ArrayList<Student> students;

    public GradeTracker() {
        this.students = new ArrayList<>();
    }

    public void addStudent(String name, String studentID) {
        for (Student s : students) {
            if (s.getStudentID().equals(studentID)) {
                System.out.println("❌ Student with ID " + studentID + " already exists!");
                return;
            }
        }
        students.add(new Student(name, studentID));
        System.out.println("✅ Student " + name + " added successfully!");
    }

    public Student findStudent(String studentID) {
        for (Student s : students) {
            if (s.getStudentID().equals(studentID)) {
                return s;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n❌ No students in the system yet.\n");
            return;
        }
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALL STUDENTS IN THE SYSTEM");
        System.out.println("=".repeat(70));
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("=".repeat(70) + "\n");
    }

    public void generateSummaryReport() {
        if (students.isEmpty()) {
            System.out.println("\n❌ No students in the system yet.\n");
            return;
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.println(String.format("%-15s | %-20s | %-12s | %-12s | %-12s | %-15s",
                "Student ID", "Name", "Average", "Highest", "Lowest", "Grade Letter"));
        System.out.println("=".repeat(100));

        double classAverage = 0;
        double classHighest = 0;
        double classLowest = 100;
        int totalGrades = 0;

        for (Student s : students) {
            if (s.getGradeCount() > 0) {
                double avg = s.getAverage();
                double highest = s.getHighest();
                double lowest = s.getLowest();

                classAverage += avg;
                if (highest > classHighest) classHighest = highest;
                if (lowest < classLowest) classLowest = lowest;
                totalGrades += s.getGradeCount();

                String letterGrade = getLetterGrade(avg);
                System.out.println(String.format("%-15s | %-20s | %-12.2f | %-12.2f | %-12.2f | %-15s",
                        s.getStudentID(), s.getName(), avg, highest, lowest, letterGrade));
            }
        }

        System.out.println("=".repeat(100));
        System.out.println("\n📊 CLASS STATISTICS:");
        System.out.println("   • Total Students: " + students.size());
        System.out.println("   • Total Grades Recorded: " + totalGrades);
        System.out.println("   • Class Average: " + String.format("%.2f", classAverage / students.size()));
        System.out.println("   • Class Highest Score: " + String.format("%.2f", classHighest));
        System.out.println("   • Class Lowest Score: " + String.format("%.2f", classLowest) + "\n");
    }

    private String getLetterGrade(double average) {
        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }

    public void displayStudentDetails(String studentID) {
        Student student = findStudent(studentID);
        if (student == null) {
            System.out.println("❌ Student not found!\n");
            return;
        }
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STUDENT DETAILS: " + student.getName());
        System.out.println("=".repeat(60));
        System.out.println("Student ID: " + student.getStudentID());
        System.out.println("All Grades: " + student.getGrades());
        System.out.println("Average Score: " + String.format("%.2f", student.getAverage()));
        System.out.println("Highest Score: " + String.format("%.2f", student.getHighest()));
        System.out.println("Lowest Score: " + String.format("%.2f", student.getLowest()));
        System.out.println("Letter Grade: " + getLetterGrade(student.getAverage()));
        System.out.println("Individual Grades (Letter): " + student.getGradeLetters());
        System.out.println("Total Grades: " + student.getGradeCount());
        System.out.println("=".repeat(60) + "\n");
    }
}

// Main class with menu-driven console interface
public class StudentGradeTracker {
    private static GradeTracker tracker = new GradeTracker();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("     🎓 STUDENT GRADE TRACKER SYSTEM 🎓");
        System.out.println("=".repeat(60));

        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice (1-6): ");
            choice = getIntInput();

            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    addGradeToStudent();
                    break;
                case 3:
                    tracker.displayAllStudents();
                    break;
                case 4:
                    viewStudentDetails();
                    break;
                case 5:
                    tracker.generateSummaryReport();
                    break;
                case 6:
                    System.out.println("\n👋 Thank you for using Student Grade Tracker! Goodbye!\n");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 1-6.\n");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MAIN MENU");
        System.out.println("-".repeat(60));
        System.out.println("1. Add a New Student");
        System.out.println("2. Add Grade to Existing Student");
        System.out.println("3. View All Students");
        System.out.println("4. View Individual Student Details");
        System.out.println("5. Generate Summary Report");
        System.out.println("6. Exit Program");
        System.out.println("-".repeat(60));
    }

    private static void addNewStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("❌ Name cannot be empty!\n");
            return;
        }

        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine().trim();

        if (studentID.isEmpty()) {
            System.out.println("❌ Student ID cannot be empty!\n");
            return;
        }

        tracker.addStudent(name, studentID);
    }

    private static void addGradeToStudent() {
        System.out.println("\n--- Add Grade to Student ---");
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine().trim();

        Student student = tracker.findStudent(studentID);
        if (student == null) {
            System.out.println("❌ Student not found!\n");
            return;
        }

        System.out.print("Enter Grade (0-100): ");
        double grade = getDoubleInput();

        if (grade < 0 || grade > 100) {
            System.out.println("❌ Grade must be between 0 and 100!\n");
            return;
        }

        student.addGrade(grade);
        System.out.println("✅ Grade added successfully!\n");
    }

    private static void viewStudentDetails() {
        System.out.println("\n--- View Student Details ---");
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine().trim();
        tracker.displayStudentDetails(studentID);
    }

    private static int getIntInput() {
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double getDoubleInput() {
        try {
            double value = Double.parseDouble(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
