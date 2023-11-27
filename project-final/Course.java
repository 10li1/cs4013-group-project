import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Course {
    private String courseCode;
    private String courseName;
    private int courseYear;
    private String courseSemester;
    private List<Module> modules;

    public Course(String courseCode, String courseName, int courseYear, String courseSemester) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.courseSemester = courseSemester;
        this.modules = new ArrayList<>();
    }

    public String getCourseCode(){
        return courseCode;
    }

    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public String getCourseName(){
        return courseName;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public int getCourseYear(){
        return courseYear;
    }

    public void setCourseYear(int courseYear){
        this.courseYear = courseYear;
    }

    public String getCourseSemester(){
        return courseSemester;
    }

    public void setCourseSemester(String courseSemester){
        this.courseSemester = courseSemester;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public void addModule(Module newModule) {
        this.modules.add(newModule);
    }

    public static void addNewCourse(Scanner in,String fileName) throws IOException {
        boolean codeExists = true;
        Course newCourse = null;
    
        while (codeExists) {
            System.out.print("Course Code: ");
            String courseCode = in.next();
            in.nextLine(); 
    
            List<List<String>> existingData = CSVReader.readData(fileName);
            codeExists = existingData.stream().anyMatch(data -> data.get(0).equals(courseCode));
            if (!codeExists) {
                System.out.print("Course Name: ");
                String courseName = in.nextLine();
                System.out.print("year: ");
                int courseYear = in.nextInt();
                System.out.print("Semester: ");
                String courseSemester = in.next();
                newCourse = new Course(courseCode, courseName, courseYear, courseSemester);
                System.out.print("Number of modules: ");
                int moduleCount = in.nextInt();
                in.nextLine();  // 清空输入流的剩余部分
    
                System.out.println("Please enter the module code and name " + moduleCount + " times as prompted:");
                for (int i = 0; i < moduleCount; i++) {
                    System.out.print("Module code: ");
                    String moduleCode = in.next();
                    System.out.print("Module name: ");
                    String moduleName = in.next();
                    in.nextLine();  
    
                    Module newModule = new Module(moduleCode, moduleName);
                    newCourse.addModule(newModule);
                }
    
                // save data in csv
                CSVWriter.writeCourseData(fileName, newCourse);
            } else {
                System.out.println("The Course Code " + courseCode + " already exists, please re-enter it");
            }
        }
    }
    
    public static void viewCourse(String fileName) throws IOException {
        List<List<String>> coursesData = CSVReader.readData(fileName);
        for (List<String> courseData : coursesData) {
            if (courseData.size() >= 5) {
                System.out.println("Course Code: " + courseData.get(0));
                System.out.println("Course Name: " + courseData.get(1));

                // 检查模块信息是否存在
                if (!courseData.get(4).isEmpty()) {
                    String[] modules = courseData.get(4).split(";");
                    System.out.println("Module Code: , Module Name: ");
                    for (String module : modules) {
                        String[] moduleDetails = module.split(":");
                        if (moduleDetails.length >= 1) {
                            System.out.println(moduleDetails[0].trim() + ", " + moduleDetails[1].trim());
                        }
                    }
                }
            } else {
                System.out.println("Invalid course data");
            }
        }
    }    
    
    public static void modifyCourse(Scanner in,String fileName) throws IOException {
        List<List<String>> coursesData = CSVReader.readData(fileName);
        System.out.print("Course Code: ");
        String courseCode = in.next();
        in.nextLine();  // 清空输入流的剩余部分
        boolean found = false;
        List<String> modifiedCourseData = null;
    
        for (int courseIndex = 0; courseIndex < coursesData.size(); courseIndex++) {
            List<String> courseData = coursesData.get(courseIndex);
            if (courseData.get(0).equals(courseCode)) {
                System.out.println("Course Information: " + courseData);
                System.out.print("Enter new course code: ");
                String newCourseCode = in.nextLine();
                System.out.print("Enter new course name: ");
                String newCourseName = in.nextLine();
                courseData.set(0, newCourseCode); // update new course code
                courseData.set(1, newCourseName); // new course name
                coursesData.set(courseIndex, courseData); 
                modifiedCourseData = courseData;
                found = true;
                break;
            }
        }
    
        if (found) {
            CSVWriter.saveData(fileName, courseCode, modifiedCourseData);
            System.out.println("Course information has been updated");
        } 
        else {
            System.out.println("Course code does not exist");
            System.out.println();
        }
    }    
    
    public static void modifyCourseModules(Scanner in, String fileName) throws IOException {
        List<List<String>> coursesData = CSVReader.readData(fileName);
        System.out.print("Course Code: ");
        String courseCode = in.next();
        in.nextLine();  
    
        boolean courseFound = false;
        for (List<String> courseData : coursesData) {
            if (courseData.get(0).equals(courseCode)) {
                courseFound = true;
                String courseName = courseData.get(1);
                int courseYear = Integer.parseInt(courseData.get(2)); 
                String courseSemester = courseData.get(3); 
                Course course = new Course(courseCode, courseName, courseYear, courseSemester);
    
                if (courseData.size() > 4) {
                    String[] modules = courseData.get(4).split(";"); 
                    for (String module : modules) {
                        String[] moduleInfo = module.split(":"); 
                        if (moduleInfo.length >= 2) { 
                            String moduleCode = moduleInfo[0].trim();
                            String moduleName = moduleInfo[1].trim(); 
                            course.addModule(new Module(moduleCode, moduleName));
                        }
                    }
                }
    
                System.out.println("Module Code, Module Name:");
                for (Module module : course.getModules()) {
                System.out.println(module.getModuleCode() + ", " + module.getModuleName());
                }
    
                System.out.println("1. Add a new module, 2. Modify an existing module");
                String choice = in.next();
                in.nextLine();  
    
                while (!choice.equals("1") && !choice.equals("2")) {
                    System.out.println("Invalid input, please try again ");
                    choice = in.next();
                    in.nextLine(); 
                }
    
                if (choice.equals("1")) {
                    // 添加新模块
                    System.out.print("Enter new module code: ");
                    String newModuleCode = in.nextLine();
                    System.out.print("Enter the new module name: ");
                    String newModuleName = in.nextLine();
                    course.addModule(new Module(newModuleCode, newModuleName));
                    CSVWriter.writeCourseData(fileName, course); 
                } 
                else if (choice.equals("2")) {
                    System.out.print("Please enter the Module Code to modify: ");
                    String moduleCode = in.nextLine().trim(); 
                    boolean moduleFound = false;
                    for (Module module : course.getModules()) {
                        if (module.getModuleCode().equalsIgnoreCase(moduleCode)) {
                            System.out.println("Current Module Information: " + module.getModuleCode() + " " + module.getModuleName());
                            System.out.print("Enter new module code: ");
                            String newModuleCode = in.nextLine().trim();
                            System.out.print("Enter new module name: ");
                            String newModuleName = in.nextLine().trim();
                            module.setModuleCode(newModuleCode); 
                            module.setModuleName(newModuleName); 
                            moduleFound = true;
                            break;
                        }
                    }
                    if (!moduleFound) {
                        System.out.println("No module was found with the code " + moduleCode);
                    } 
                    else {
                        CSVWriter.writeCourseData(fileName, course);
                    }
                }
            }
        }
        if (!courseFound) {
            System.out.println("Course code does not exist");
        }
    }    

    public static void deleteCourse(Scanner in, String fileName) throws IOException {
        List<List<String>> coursesData = CSVReader.readData(fileName);
        System.out.print("Course Code: ");
        String courseCode = in.next();
        in.nextLine(); 
        boolean removed = coursesData.removeIf(data -> data.get(0).equals(courseCode));

        if (removed) {
            CSVWriter.deleteData(fileName, coursesData);
            System.out.println("The course has been deleted");
        } else {
            System.out.println("Course Code does not exist");
        }
    }
}