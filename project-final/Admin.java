import java.io.IOException;
import java.util.List;
import java.util.Scanner;
/**
 * Admin class includes most of the actionable options
 */
public class Admin {
    private String adminId;
    
    /**
     * constructor with a specified admin id
     * 
     * @param adminId
     */
    public Admin(String adminId) {
        this.adminId = adminId;
    }

    /**
     * Display the operations that the administrator can perform when choosing to log in as an admin
     * 
     * @param in
     * @throws IOException
     */
    public static void adminLogin(Scanner in) throws IOException {
        boolean continues = true;
        while (continues) {
            System.out.println("Please select:");
            System.out.println("1. Student");
            System.out.println("2. Staff");
            System.out.println("3. Course");
            System.out.println("4. log out");
            String choice = in.next();
            System.out.println();
            in.nextLine(); 
            
            if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                do {
                    if (choice.equals("1")) {
                        System.out.println("Please select:");
                        System.out.println("1. Add Student");
                        System.out.println("2. View Student Information");
                        System.out.println("3. Modify student information");
                        System.out.println("4. Add or modify student grades");
                        System.out.println("5. Delete Student");
                        String choice1 = in.next();
                        System.out.println();
                        if(choice1.equals("1")){
                            Student.registerStudent("studentInfo.csv");
                            System.out.println("Student information is saved");
                        }

                        else if(choice1.equals("2")){
                            System.out.println("input student Id");                           
                            String studentId = in.next();
                            System.out.println();
                            try {
                            Student.viewStudent(studentId, "studentInfo.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice1.equals("3")){
                            System.out.println("input student Id");                           
                            String studentId = in.next();
                            System.out.println();
                            try {
                                Student.modifyStudent(studentId, in, "studentInfo.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice1.equals("4")){
                            System.out.println("input student number ");                           
                            String studentId = in.next();
                            System.out.println();
                            try {
                                inputGrades(studentId,in, "studentInfo_new.csv", "result.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice1.equals("5")){
                            System.out.println("Please enter the ID of the student to be deleted");                           
                            String studentId = in.next();
                            System.out.println();
                            try {
                                Student.deleteStudent(studentId, "studentInfo.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            System.out.println("Ineffective choices");
                        }
                    }

                    else if (choice.equals("2")) {
                        System.out.println("Please select:");
                        System.out.println("1. Add Teacher(Staff)");
                        System.out.println("2. View teacher Information");
                        System.out.println("3. Modify Teacher Information");
                        System.out.println("4. Delete Teacher");
                        String choice2 = in.next();
                        System.out.println();
                        
                        if(choice2.equals("1")){
                            Teacher.registerTeacher("teacherInfo.csv");
                            System.out.println("Teacher information saved");
                        }

                        else if(choice2.equals("2")){
                            System.out.println("input teacher Id");                           
                            String teacherId = in.next();
                            try {
                            Teacher.viewTeacher(teacherId, "teacherInfo.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice2.equals("3")){
                            System.out.println("input teacher Id");                           
                            String teacherId = in.next();
                            try {
                                Teacher.modifyTeacher(teacherId, in, "teacherInfo.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice2.equals("4")){
                            System.out.println("Please enter the ID of the staff be deleted");                           
                            String teacherId = in.next();
                            try {
                                Teacher.deleteTeacher(teacherId, "teacherInfo.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            System.out.println("Invalid input");
                            System.out.println();
                        }
                    }

                    else if (choice.equals("3")) {
                        System.out.println("Please select:");
                        System.out.println("1. View existing Courses");
                        System.out.println("2. Add Course");
                        System.out.println("3. Modify course information");
                        System.out.println("4. Add or modify Modules");
                        System.out.println("5. Delete course");
                        String choice3 = in.next();
                        System.out.println();
                        if(choice3.equals("1")){
                            try {
                                Course.viewCourse("course.csv"); // 显示所有专业及其模块
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice3.equals("2")){
                            System.out.println("please enter the Course information: ");
                            
                            try {
                                Course.addNewCourse(in,"course.csv"); // add new course to csv file
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice3.equals("3")){
                            System.out.println("please enter the Course you want to modify: ");
                            
                            try {
                                Course.modifyCourse(in, "course.csv"); // modify course from csv
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice3.equals("4")){
                            System.out.println("Please enter the course information: ");
                           
                            try {
                                Course.modifyCourseModules(in, "course.csv"); // modify module from csv
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else if(choice3.equals("5")){
                            System.out.println("Please enter the course to deleted: ");
                           
                            try {
                                Course.deleteCourse(in, "course.csv"); // delect course from csv
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            System.out.println("Invalid input, please try again");
                        }
                    }
                    
                    String response;
                    System.out.println("Do you want to continue?(Y/N)");
                    while (true) {
                        response = in.next().trim().toUpperCase();
                        System.out.println();   
                        if (response.equals("Y")) {
                            break; 
                        } 
                        else if (response.equals("N")) {
                            break; // if chose n to exit
                        } 
                        else {
                            System.out.println("Invalid input, please try again");
                            System.out.println();
                        }
                    }

                    if (response.equals("N")) {
                        break;
                    }
                } while (true);
            } 
            else if (choice.equals("4")) {
                continues = false; // user chose exit
            } 
            else {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    public static void inputGrades(String studentId, Scanner in, String studentCourseFilePath, String resultFilePath) throws IOException {
        List<List<String>> studentsData = CSVReader.readData(studentCourseFilePath);
        boolean found = false;
    
        for (List<String> studentData : studentsData) {
            if (studentData.get(0).equals(studentId)) {
                Result gradeManager = new Result();
                gradeManager.inputGradesAndSave(studentData, in, resultFilePath);
                found = true;
                break;
            }
        }
    
        if (!found) {
            System.out.println("Unable to find information for student with ID: " + studentId);
            System.out.println();
        }
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }
}