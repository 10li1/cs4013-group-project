import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Student {
    private String studentId;
    private String studentName;
    private String studentType; 
    private int year; 
    private String course; 
    private List<String> module; 
    private List<Course> selectedCourses;

    public Student(String studentId, String studentName, int year, String course, String studentType) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.year = year;
        this.course = course;
        this.studentType = studentType;
        this.module = new ArrayList<>();
        this.selectedCourses = new ArrayList<>();
    }

    public String getStudentName(){
        return studentName;
    }

    public void setStudentName(String studentName){
        this.studentName = studentName;
    }

    public void addSelectedCourse(Course course) {
        selectedCourses.add(course);
    }

    public List<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public void addModule(String modules) {
        module.add(modules);
    }

    public List<String> getModule() {
        return module;
    }

    public String getStudentId(){
        return studentId;
    }

    public void setStudentId(String studentId){
        this.studentId = studentId;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }
    
    public String getCourse(){
        return course;
    }

    public void setCourse(String course){
        this.course = course;
    }

    public String getStudentType(){
        return studentType;
    }

    public void setStudentType(String studentType){
        this.studentType = studentType;
    }
    /* 
    @Override
    public String toString() {
        return "Student Id: " + studentId + '\n' +
            " Name: " + studentName + '\n' +
            " Year: " + year + '\n' +
            " Course: " + course + '\n' +
            " student Type: " + studentType;
    }*/

    public static void studentLogin(Scanner in, String filePath) throws IOException {
        boolean search = true;
        while (search) {
            System.out.print("Please enter student ID: ");
            String studentId = in.next();
            System.out.println();
            List<List<String>> studentsData = CSVReader.readData(filePath);
            boolean found = false;
            for (List<String> studentData : studentsData) {
                if (studentData.get(0).equals(studentId)) {
                    found = true;
                    while (true) {
                        System.out.println("Please select:");
                        System.out.println("1. View student information");
                        System.out.println("2. View student results");
                        System.out.println("3. Exit");
                        String choice = in.next();
                        System.out.println();
                        if (choice.equals("1")) {
                            viewStudentInfo(studentId, "studentInfo_new.csv");
                        } else if (choice.equals("2")) {
                            viewStudentGrades(studentId, "studentQCA.csv");
                        } else if (choice.equals("3")) {
                            search = false;
                            return;
                        } else {
                            System.out.println("Invalid input, please try again");
                            System.out.println();
                        }
                    }
                }
            }
            if (!found) {
                System.out.println("student ID " + studentId + " does not exist");
                while (true) {
                    System.out.println("1. Try again 2. Exit");
                    String chosen = in.next();
                    System.out.println();
                    if (chosen.equals("1")) {
                        search = true; // 用户选择再次尝试
                        break;
                    } 
                    if (chosen.equals("2")) {
                        search = false; // 用户选择退出
                        break;
                    }
                    System.out.println("Invalid input, please try again");
                }
            }
        }
    }

    public static void registerStudent(String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean idExists = true;
        Student studentId = null;

        while (idExists) {
            System.out.print("student ID: ");
            String id = scanner.next();
            List<List<String>> existingData = CSVReader.readData(fileName);
            idExists = existingData.stream().anyMatch(data -> data.get(0).equals(id));
            if (idExists) {
                System.out.println("Student ID already exists");
                System.out.println();
            } else {
                studentId = Student.createNewStudent(scanner, id);
            }
        }
        List<List<String>> studentData = Arrays.asList(
                Arrays.asList(
                        studentId.getStudentId(),
                        studentId.getStudentName(),
                        Integer.toString(studentId.getYear()),
                        studentId.getCourse(),
                        studentId.getStudentType()
                )
        );
        CSVWriter.addData(fileName, studentData);
    }

    public static Student createNewStudent(Scanner in, String studentId) {
        System.out.print("Name:");
        in.nextLine(); 
        String studentName = in.nextLine();
    
        System.out.print("Year:");
        int year = Integer.parseInt(in.nextLine());
    
        System.out.print("Course:");
        String course = in.nextLine();
    
        System.out.print("Type:");
        String studentType = in.nextLine();
    
        System.out.println();
        return new Student(studentId, studentName, year, course, studentType);
    }

    public static void modifyStudent(String studentId, Scanner in, String fileName) throws IOException {
        List<List<String>> studentsData = CSVReader.readData(fileName);
        boolean found = false;

        for (List<String> studentData : studentsData) {
            if (studentData.get(0).equals(studentId)) {
                //[id, name, year, course, studentType]
                System.out.println("Current Student Information: ");
                System.out.println("Name: " + studentData.get(1));
                System.out.println("Year: " + studentData.get(2));
                System.out.println("Course: " + studentData.get(3));
                System.out.println("Type: " + studentData.get(4));
                System.out.println();

                System.out.print("New Year: ");
                in.nextLine();
                String newYear = in.nextLine();
                studentData.set(2, newYear);

                System.out.print("New Course: ");
                String newCourse = in.nextLine();
                studentData.set(3, newCourse);

                System.out.print("New Type: ");
                String newType = in.nextLine();
                studentData.set(4, newType);

                found = true;
                break;
            }
        }

        if (found) {
            for (List<String> studentData : studentsData) {
                if (studentData.get(0).equals(studentId)) {
                    CSVWriter.saveData(fileName, studentId, studentData);
                    System.out.println("Student ID " + studentId + " was successfully updated.");
                    System.out.println();
                    break;
                }
            }
        } else {
            System.out.println("Student ID does not exist");
        }
    }

    public static void viewStudent(String studentId, String fileName) throws IOException {
        List<List<String>> studentsData = CSVReader.readData(fileName);
        //boolean found = false;

        for (List<String> studentData : studentsData) {
            if (studentData.get(0).equals(studentId)) {
                // [id, name, year, course, studentType]
                System.out.println("Student ID: " + studentData.get(0));
                System.out.println("Name: " + studentData.get(1));
                System.out.println("Year: " + studentData.get(2));
                System.out.println("Course: " + studentData.get(3));
                System.out.println("Type: " + studentData.get(4));
                System.out.println();
                //found = true;
                break;
            }
        }

        /*if (!found) {
            System.out.println("Unable to find information for student with ID: " + studentId);
        }*/
    }
    
    public static void deleteStudent(String studentId, String fileName) throws IOException {
        List<List<String>> existingData = CSVReader.readData(fileName);
        boolean removed = existingData.removeIf(data -> data.get(0).equals(studentId));
    
        if (removed) {
            CSVWriter.deleteData(fileName, existingData);
            System.out.println("Student with ID " + studentId + " has been removed");
        } else {
            System.out.println("Student ID does not exist.");
        }
    }

    public static void viewStudentInfo(String studentId, String mergedDataFilePath) throws IOException {
        List<List<String>> mergedData = CSVReader.readData(mergedDataFilePath);
        boolean courseFound = false;
    
        for (List<String> dataRow : mergedData) {
            if (dataRow.get(0).equals(studentId)) {  // Assume the first element is the student ID
                // Format and output basic information
                System.out.println("Student ID: " + dataRow.get(0));
                System.out.println("Name: " + dataRow.get(1));
                System.out.println("Year: " + dataRow.get(2));
                System.out.println("Course: " + dataRow.get(3));
                System.out.println("Type: " + dataRow.get(4));
                System.out.println("Semester: " + dataRow.get(5));
                
                // Split module information and output
                String[] modules = dataRow.get(6).split(";");
                System.out.println("Modules Information: ");
                int moduleCount = 1; // Initialize a counter for modules
                for (String module : modules) {
                    // Split each module into module code and module name
                    String[] moduleDetails = module.split(":");
                    if (moduleDetails.length == 2) {
                        String moduleCode = moduleDetails[0].trim();
                        String moduleName = moduleDetails[1].trim();
                        System.out.println("  " + moduleCount + ". Module Code: " + moduleCode + " Module Name: " + moduleName);
                        moduleCount++; // Increment the counter for each module
                    }
                }
                System.out.println();
    
                courseFound = true;
                break;
            }
        }
        if (!courseFound) {
            Student.viewStudent(studentId, "studentInfo.csv");
        }
    }
    
    public static void viewStudentGrades(String studentId, String transcriptFilePath) throws IOException {
        List<List<String>> transcriptData = CSVReader.readData(transcriptFilePath);
        boolean gradesFound = false;
        List<String> repeatModules = new ArrayList<>();
    
        for (List<String> row : transcriptData) {
            if (row.get(0).equals(studentId)) {
                System.out.println("--Student Transcript--");
                System.out.println("ID: " + row.get(0));
                System.out.println("Module Information");
                // QCA is last element in csv file
                for (int i = 1; i < row.size() - 1; i++) {
                    String[] moduleInfo = row.get(i).split(":");
                    if (moduleInfo.length == 2) {
                        String[] gradeInfo = moduleInfo[1].split("=");
                        if (gradeInfo.length == 2) {
                            System.out.println("Module Code: " + moduleInfo[0] + " Grade: " + gradeInfo[1]);
                            if (gradeInfo[1].compareTo("D1") >= 0) {
                                repeatModules.add(moduleInfo[0]);
                            }
                        }
                    }
                }
                // display QCA
                double qca = Double.parseDouble(row.get(row.size() - 1));
                System.out.println("QCA: " + qca);
                if (qca >= 2) {
                    System.out.println("Congratulations on completing the semester");
                } else {
                    System.out.println("You do not meet the minimum grade standards for this semester.");
                }
                if (!repeatModules.isEmpty()) {
                    System.out.println("You need to take a repeat exam for: " + String.join(", ", repeatModules));
                }
                System.out.println();
                gradesFound = true;
                break;
            }
        }
    
        if (!gradesFound) {
            System.out.println("No results found for student ID " + studentId);
            System.out.println();
        }
    }    
}