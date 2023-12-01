import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is use for write data in csv file 
 * @author Tianxing Fan, Peile Li and Cheng Yang
 */
public class CSVWriter {
    public static void writeLine(FileWriter writer, List<String> values) throws IOException {
        boolean first = true;

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(",");
            }
            sb.append(value);
            first = false;
        }
        sb.append("\n");

        writer.append(sb.toString());
    }

    public static void saveData(String fileName, String studentId, List<String> newData) throws IOException {
        List<List<String>> data = CSVReader.readData(fileName);
        for (List<String> rowData : data) {
            if (rowData.get(0).equals(studentId)) {
                while (rowData.size() < newData.size()) {
                    rowData.add("");
                }
                // update
                for (int i = 0; i < newData.size(); i++) {
                    rowData.set(i, newData.get(i));
                }
                break;
            }
        }
        
        FileWriter csvWriter = new FileWriter(fileName, false);
        for (List<String> rowData : data) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }    

    public static void addData(String fileName, List<List<String>> data) throws IOException {
        FileWriter csvWriter = new FileWriter(fileName, true);
        for (List<String> rowData : data) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }    
    
    public static void deleteData(String fileName, List<List<String>> newData) throws IOException {
        FileWriter csvWriter = new FileWriter(fileName, false);
        for (List<String> rowData : newData) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static void writeCourseData(String fileName, Course course) throws IOException {
        File csvFile = new File(fileName);
        ArrayList<String> lines = new ArrayList<>();
        String line;
        boolean courseFound = false;
    
        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFile))) {
            while ((line = csvReader.readLine()) != null) {
                if (line.startsWith(course.getCourseCode() + ",")) {
                    StringBuilder newLine = new StringBuilder();
                    newLine.append(course.getCourseCode()).append(",");
                    newLine.append(course.getCourseName()).append(",");
                    newLine.append(course.getCourseYear()).append(",");
                    newLine.append(course.getCourseSemester());
                    if (!course.getModules().isEmpty()) {
                        newLine.append(",");
                        for (int i = 0; i < course.getModules().size(); i++) {
                            Module module = course.getModules().get(i);
                            newLine.append(module.getModuleCode()).append(":").append(module.getModuleName());
                            if (i < course.getModules().size() - 1) {
                                newLine.append(";");
                            }
                        }
                    }
                    lines.add(newLine.toString());
                    courseFound = true;
                } else {
                    lines.add(line);
                }
            }
        }
    
        if (!courseFound) {
            StringBuilder newLine = new StringBuilder();
            newLine.append(course.getCourseCode()).append(",");
            newLine.append(course.getCourseName()).append(",");
            newLine.append(course.getCourseYear()).append(",");
            newLine.append(course.getCourseSemester());
    
            if (!course.getModules().isEmpty()) {
                newLine.append(",");
                for (Module module : course.getModules()) {
                    newLine.append(module.getModuleCode()).append(":").append(module.getModuleName()).append(";");
                }
                newLine.deleteCharAt(newLine.length() - 1);
            }
            lines.add(newLine.toString());
        }
    
        try (FileWriter csvWriter = new FileWriter(fileName, false)) {
            for (String l : lines) {
                csvWriter.append(l).append("\n");
            }
        }
    }       
}

