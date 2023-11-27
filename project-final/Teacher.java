import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Teacher {
    private String teacherId;
    private String teacherName;
    private String module;
    private String title;
    private String department;

    public Teacher(String teacherId, String teacherName, String module, String title, String department) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.module = module;
        this.title = title;
        this.department = department;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }
    
    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Staff Id: " + teacherId + '\n' +
            " Staff Name: " + teacherName + '\n' +
            " Teach Module: " + module + '\n' +
            " Title: " + title + '\n' +
            " Department: " + department;
    }

    public static void teacherLogin(Scanner in, String filePath) throws IOException {
        boolean search = true;
        while (search) {
            System.out.print("Please enter staff ID: ");
            String teacherId = in.next();
            System.out.println();
            List<List<String>> teachersData = CSVReader.readData("teacherInfo.csv");
            boolean found = false;
            for (List<String> teacherData : teachersData) {
                if (teacherData.get(0).equals(teacherId)) {
                    Teacher teacher = new Teacher(teacherData.get(0), 
                                                teacherData.get(1),
                                                teacherData.get(2), 
                                                teacherData.get(3),
                                                teacherData.get(4));
                    System.out.println(teacher.toString());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Staff ID does not exist");
                while (true) {
                    System.out.println("1.try again 2.exit");
                    String chosen = in.next();
                    System.out.println();
                    if (chosen.equals("1")) {
                        break;
                    } else if (chosen.equals("2")) {
                        search = false;
                        break;
                    }
                    else{
                        System.out.println("Invalid input, please try again");
                        System.out.println();
                    }
                }
            } 
            else {
                search = false;
            }
        }
    }

    public static void registerTeacher(String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean idExists = true;
        Teacher teacherId = null;

        while (idExists) {
            System.out.print("Staff ID: ");
            String id = scanner.next();
        
            List<List<String>> existingData = CSVReader.readData(fileName);
        
            idExists = existingData.stream().anyMatch(data -> data.get(0).equals(id));
            if (idExists) {
                System.out.println("Staff ID " + id + " already exists");
                System.out.println();
            } else {
                teacherId = Teacher.createNewTeacher(scanner, id);
            }
        }

        List<List<String>> teacherData = Arrays.asList(
                Arrays.asList(
                        teacherId.getTeacherId(),
                        teacherId.getTeacherName(),
                        teacherId.getModule(),
                        teacherId.getTitle(),
                        teacherId.getDepartment()
                )
        );
        CSVWriter.addData("teacherInfo.csv", teacherData);
    }

    public static Teacher createNewTeacher(Scanner in, String teacherId) {
        System.out.print("Name: ");
        String teacherName = in.next();
        System.out.print("Modules taught: ");
        String module = in.next();
        System.out.print("Level: ");
        String title = in.next();
        System.out.print("Department: ");
        String department = in.next();
        System.out.println();
        return new Teacher(teacherId, teacherName, module, title, department);
    }

    public static void modifyTeacher(String teacherId, Scanner in, String fileName) throws IOException {
        List<List<String>> teachersData = CSVReader.readData(fileName);
        boolean found = false;
    
        for (List<String> teacherData : teachersData) {
            if (teacherData.get(0).equals(teacherId)) {
                // [id, name, module, title, department]
                System.out.println("Current teacher information: ");
                System.out.println("Name: " + teacherData.get(1));
                System.out.println("Modules taught: " + teacherData.get(2));
                System.out.println("Level: " + teacherData.get(3));
                System.out.println("Department: " + teacherData.get(4));
                System.out.println();
                
                System.out.print("New modules taught: ");
                String newModule = in.next();
                teacherData.set(2, newModule);
               
                System.out.print("New level: ");
                String newTitle = in.next();
                teacherData.set(3, newTitle);

                found = true;
                break;
            }
        }
        if (found) {
            for (List<String> teacherData : teachersData) {
                if (teacherData.get(0).equals(teacherId)) {
                    CSVWriter.saveData(fileName, teacherId, teacherData);
                    System.out.println("Staff ID " + teacherId + " was successfully updated.");
                    System.out.println();
                    break;
                }
            }
        } else {
            System.out.println("Staff ID does not exist");
            System.out.println();
        }
    }

    public static void viewTeacher(String teacherId, String fileName) throws IOException {
        List<List<String>> teachersData = CSVReader.readData(fileName);
        boolean found = false;
    
        for (List<String> teacherData : teachersData) {
            if (teacherData.get(0).equals(teacherId)) {
                // [id, name, module, title, department]
                System.out.println("ID: " + teacherData.get(0));
                System.out.println("Name: " + teacherData.get(1));
                System.out.println("Modules taught: " + teacherData.get(2));
                System.out.println("Level: " + teacherData.get(3));
                System.out.println("Department: " + teacherData.get(4));
                found = true;
                break;
            }
        }
    
        if (!found) {
            System.out.println("Staff ID does not exist");
            System.out.println();
        }
    }
    
    public static void deleteTeacher(String teacherId, String fileName) throws IOException {
        List<List<String>> existingData = CSVReader.readData(fileName);
        boolean removed = existingData.removeIf(data -> data.get(0).equals(teacherId));
    
        if (removed) {
            CSVWriter.deleteData(fileName, existingData);
            System.out.println("Staff with ID " + teacherId + " has been removed");
            System.out.println();
        } else {
            System.out.println("Staff ID does not exist");
            System.out.println();
        }
    }
    
}