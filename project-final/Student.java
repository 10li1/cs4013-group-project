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

    /**
     * constructor with a specified data
     * 
     * @param studentId     student id
     * @param studentName   student name
     * @param year          year in
     * @param course        student course
     * @param studentType   type e.g Bachelor(undergraduate), Master(postgraduate)
     */
    public Student(String studentId, String studentName, int year, String course, String studentType) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.year = year;
        this.course = course;
        this.studentType = studentType;
    }

    /**
     * get student name
     * 
     * @return student name
     */
    public String getStudentName(){
        return studentName;
    }

    /**
     * set student name 
     * 
     * @param studentName student name
     */
    public void setStudentName(String studentName){
        this.studentName = studentName;
    }

    /**
     * get student id
     * 
     * @return student id
     */
    public String getStudentId(){
        return studentId;
    }

    /**
     * set student id
     * 
     * @param studentId student id
     */
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }

    /**
     * student year in
     * 
     * @return year
     */
    public int getYear(){
        return year;
    }

    /**
     * set year
     * 
     * @param year year in
     */
    public void setYear(int year){
        this.year = year;
    }
    
    /**
     * get course name
     * 
     * @return course name
     */
    public String getCourse(){
        return course;
    }

    /**
     * set course name
     * 
     * @param course courst name
     */
    public void setCourse(String course){
        this.course = course;
    }

    /**
     * get student type
     * 
     * @return type
     */
    public String getStudentType(){
        return studentType;
    }

    /**
     * set student type
     * 
     * @param studentType type of student
     */
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

    /**
     * student login operate
     * 
     * @param in input
     * @param filePath search from csv
     * @throws IOException error input
     */
    public static void studentLogin(Scanner in, String filePath) throws IOException {
        boolean search = true;
        while (search) {
            System.out.println("--Student login-- ");
            System.out.print("student ID: ");
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
                            viewStudentInfo(studentId, "studentInfo_course.csv");
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
            //if not found this student
            if (!found) {
                System.out.println("student ID " + studentId + " does not exist");
                while (true) {
                    System.out.println("1. Try again 2. Exit");
                    String chosen = in.next();
                    System.out.println();
                    if (chosen.equals("1")) {
                        search = true; 
                        break;
                    } 
                    if (chosen.equals("2")) {
                        search = false; 
                        break;
                    }
                    System.out.println("Invalid input, please try again");
                }
            }
        }
    }

    /**
     * add new student and save in csv
     * check is id in the csv first, if not can set up new student
     * this method use for admin user
     * 
     * @param fileName save in studentinfo.csv
     * @throws IOException error input
     */
    public static void registerStudent(String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean idExists = true;
        Student studentId = null;
        while (idExists) {
            System.out.print("student ID: ");
            String id = scanner.next();
            List<List<String>> existingData = CSVReader.readData(fileName);
            //check id exist
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

    /**
     * set up student info
     * use for registerStudent method
     * 
     * @param in input info
     * @param studentId check id
     * @return new student info
     */
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

    /**
     * modify student info
     * check id first
     * 
     * @param studentId check id
     * @param in input
     * @param fileName save in csv
     * @throws IOException error input
     */
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
        //if id exist, can modify
        if (found) {
            for (List<String> studentData : studentsData) {
                if (studentData.get(0).equals(studentId)) {
                    CSVWriter.saveData(fileName, studentId, studentData);
                    System.out.println("Student ID " + studentId + " was successfully updated.");
                    System.out.println();
                    break;
                }
            }
        } 
        //if not found
        else {
            System.out.println("Student ID does not exist");
        }
    }

    /**
     * look student info
     * 
     * @param studentId check id
     * @param fileName find info in csv
     * @throws IOException error input
     */
    public static void viewStudent(String studentId, String fileName) throws IOException {
        List<List<String>> studentsData = CSVReader.readData(fileName);
        boolean found = false;
        //if found, show up info
        for (List<String> studentData : studentsData) {
            if (studentData.get(0).equals(studentId)) {
                // format in csv [id, name, year, course, studentType]
                System.out.println("Student ID: " + studentData.get(0));
                System.out.println("Name: " + studentData.get(1));
                System.out.println("Year: " + studentData.get(2));
                System.out.println("Course: " + studentData.get(3));
                System.out.println("Type: " + studentData.get(4));
                System.out.println();
                found = true;
                break;
            }
        }
        //if not found this id
        if (!found) {
            System.out.println("Unable to find information for student with ID: " + studentId);
        }
    }
    
    /**
     * delete student from csv
     * 
     * @param studentId check id exist
     * @param fileName delete student from studentinfo.csv
     * @throws IOException error input
     */
    public static void deleteStudent(String studentId, String fileName) throws IOException {
        List<List<String>> existingData = CSVReader.readData(fileName);
        boolean removed = existingData.removeIf(data -> data.get(0).equals(studentId));
        //only remove if id exist
        if (removed) {
            CSVWriter.deleteData(fileName, existingData);
            System.out.println("Student with ID " + studentId + " has been removed");
        } 
        else {
            System.out.println("Student ID does not exist.");
        }
    }

    /**
     * this method use for studnt view they own full info
     * 
     * @param studentId check login id
     * @param mergedDataFilePath    read data from studentinfo_course.csv, merged studentinfo.csv and course.csv
     * @throws IOException error input
     */
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
        //if can not find course info, just display person info
        if (!courseFound) {
            Student.viewStudent(studentId, "studentInfo.csv");
        }
    }
    
    /**
     * view student result method
     * use for student look up they own result and qca
     * 
     * @param studentId check id
     * @param transcriptFilePath find qca in studentqcs.csv
     * @throws IOException error input
     */
    public static void viewStudentGrades(String studentId, String transcriptFilePath) throws IOException {
        List<List<String>> transcriptData = CSVReader.readData(transcriptFilePath);
        boolean gradesFound = false;
        List<String> repeatModules = new ArrayList<>();
    
        for (List<String> row : transcriptData) {
            if (row.get(0).equals(studentId)) {
                System.out.println("--Student Transcript--");
                System.out.println("ID: " + row.get(0));
                System.out.println("Module Information");
                // dispaly module info
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
                // display QCA, QCA is last element in csv file
                double qca = Double.parseDouble(row.get(row.size() - 1));
                System.out.println("QCA: " + qca);
                if (qca >= 2) {
                    System.out.println("Congratulations on completing the semester");
                } 
                //need to repeat,qca<2
                else {
                    System.out.println("You do not meet the minimum grade standards for this semester.");
                }
                //list module need to repeat
                if (!repeatModules.isEmpty()) {
                    System.out.println("You need to take a repeat exam for: " + String.join(", ", repeatModules));
                }
                System.out.println();
                gradesFound = true;
                break;
            }
        }
        //no result to display
        if (!gradesFound) {
            System.out.println("No results found for student ID " + studentId);
            System.out.println();
        }
    }    
}