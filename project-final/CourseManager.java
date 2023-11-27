import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseManager {
    public void mergeStudentCourseData(String studentFilePath, String courseFilePath, String outputFilePath) throws IOException {
        List<List<String>> courses = CSVReader.readData(courseFilePath);
        Map<String, List<String>> courseMap = new HashMap<>();
        for (List<String> course : courses) {
            if (course.size() < 5) continue; 
            String courseKey = course.get(1) + "_" + course.get(2); 
            courseMap.put(courseKey, course);
        }

        List<List<String>> students = CSVReader.readData(studentFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            for (List<String> student : students) {
                if (student.size() < 5) continue; 
                String studentCourseKey = student.get(3) + "_" + student.get(2); 
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
        String studentFilePath = "studentInfo.csv";
        String courseFilePath = "course.csv";
        String outputFilePath = "studentInfo_new.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, false))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            courseManager.mergeStudentCourseData(studentFilePath, courseFilePath, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
