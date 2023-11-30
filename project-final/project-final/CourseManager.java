import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseManager {
    /**
     * Merge data into a new file
     * 
     * @param studentFilePath   search data from studentInfo,csv
     * @param courseFilePath    search data from course.csv
     * @param outputFilePath    merge data in student_course.csv
     * @throws IOException      error info
     */
    public void mergeStudentCourseData(String studentFilePath, String courseFilePath, String outputFilePath) throws IOException {
        List<List<String>> courses = CSVReader.readData(courseFilePath);
        Map<String, List<String>> courseMap = new HashMap<>();
        for (List<String> course : courses) {
            if (course.size() < 5) continue; 
            String courseKey = course.get(1) + "," + course.get(2); 
            courseMap.put(courseKey, course);
        }
        List<List<String>> students = CSVReader.readData(studentFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            for (List<String> student : students) {
                if (student.size() < 5) continue; 
                String studentCourseKey = student.get(3) + "," + student.get(2); 
                List<String> courseInfo = courseMap.get(studentCourseKey);
                if (courseInfo != null) {
                    List<String> mergedInfo = new ArrayList<>(student);
                    mergedInfo.add(courseInfo.get(3)); 
                    mergedInfo.addAll(courseInfo.subList(4, courseInfo.size())); 
                    String mergedRow = String.join(",", mergedInfo);
                    writer.write(mergedRow);
                    writer.newLine();
                }
            }
        }
    }

    public void runApplication() {
        CourseManager courseManager = new CourseManager();
        String studentFilePath = "./csv/studentInfo.csv";
        String courseFilePath = "./csv/course.csv";
        String outputFilePath = "./csv/studentInfo_course.csv";
        //Update data, if existing data will be replaced
        //write empty data first 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, false))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save data
        try {
            courseManager.mergeStudentCourseData(studentFilePath, courseFilePath, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
