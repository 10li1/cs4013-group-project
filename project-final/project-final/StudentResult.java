import java.io.IOException;
import java.util.Scanner;

/**
 * This class is used for the main page (login screen)
 * @author Peile Li, Tianxing Fan and Cheng Yang
 * @version Final verison
 */
public class StudentResult{
    private static Scanner in;
    /**
     * input
     */
    public StudentResult(){
        in = new Scanner(System.in);
    }

    /**
     * run method
     */
    public void run() {
        boolean more = true;
        boolean display = true;
        Result.generateStudentTranscript(null);
        while (more) {
            CourseManager courseManager = new CourseManager();
            courseManager.runApplication();
            if (display) {
                //chose operate
                System.out.println("Student Result Portal");
                System.out.println("Press 1(Student) 2(Staff) 3(Admin) 4(Exit)");
            }
            String command = in.nextLine();
            System.out.println();
            if (command.equals("1")) {
                //student login
                try {
                    Student.studentLogin(in, "./csv/studentInfo.csv");
                    display = false;
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            else if (command.equals("2")) {
                //teacher login
                try {
                    Teacher.teacherLogin(in,"./csv/teacherInfo.csv");
                    display = false;
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            else if (command.equals("3")) {
                //admin login
                try {
                    Admin.adminLogin(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            else if (command.equals("4")) {
                //exit program
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