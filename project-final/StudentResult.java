import java.io.IOException;
import java.util.Scanner;

public class StudentResult{
    private static Scanner in;

    public StudentResult(){
        in = new Scanner(System.in);
    }

    public void run() {
        boolean more = true;
        boolean display = true;
        Result.generateStudentTranscript(null);
        while (more) {
            CourseManager courseManager = new CourseManager();
            courseManager.runApplication();
            if (display) {
                System.out.println("Student Result Portal");
                System.out.println("Press 1(Student) 2(Staff) 3(Admin) 4(Exit)");
            }
            String command = in.nextLine();
            System.out.println();
            if (command.equals("1")) {
                try {
                    Student.studentLogin(in, "studentInfo.csv");
                    display = false;
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            else if (command.equals("2")) {
                try {
                    Teacher.teacherLogin(in,"teacherInfo.csv");
                    display = false;
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            else if (command.equals("3")) {
                try {
                    Admin.adminLogin(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            else if (command.equals("4")) {
                more = false;
                return;
            } 
            else {
                display = false; 

            }
            if(!display){
            System.out.println("Please select 1(Student) 2(Staff) 3(Admin) 4(Exit)");
            }
        }
    }
    
}