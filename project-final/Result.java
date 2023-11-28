import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Result {
    private static final String[] GRADES = {"NG", "F", "D2", "D1", "C3", "C2", "C1", "B3", "B2", "B1", "A2", "A1"};
    private static final int[] SCORE_THRESHOLDS = {0, 1, 30, 35, 40, 48, 52, 56, 60, 64, 72, 80};
    private static final double[] QPV_POINTS = {0.00, 0.00, 1.20, 1.60, 2.00, 2.40, 2.60, 2.80, 3.00, 3.20, 3.60, 4.00};
    
    /**
     * Convert scores to grades
     *  
     * @param score 
     * @return  if no score return ng
     */
    public static String scoreToGrade(double score) {
        for (int i = SCORE_THRESHOLDS.length - 1; i >= 0; i--) {
            if (score >= SCORE_THRESHOLDS[i]) {
                return GRADES[i];
            }
        }
        return "NG"; 
    }

    /**
     * grade to QPV
     * 
     * @param grade get grade from list
     * @return 0
     */
    private static int indexOfGrade(String grade) {
        for (int i = 0; i < GRADES.length; i++) {
            if (GRADES[i].equals(grade)) {
                return i;
            }
        }
        return 0; 
    }


    /**
     * calculate qca by grade
     *  
     * @param grades   
     * @return  qca
     */
    public static double calculateQCA(List<String> grades) {
        double totalPoints = 0;
        for (String grade : grades) {
            int index = indexOfGrade(grade);
            totalPoints += QPV_POINTS[index];
        }
        return grades.isEmpty() ? 0 : totalPoints / grades.size();
    }

    /**
     * set student result 
     * 
     * @param studentCourse
     * @param scanner
     * @param resultFilePath
     * @throws IOException
     */
    public void inputGradesAndSave(List<String> studentCourse, Scanner scanner, String resultFilePath) throws IOException {
    String studentId = studentCourse.get(0);
    String course = studentCourse.get(3);
    String semester = studentCourse.get(5);  
    String modulesString = studentCourse.get(6); 
    String[] modules = modulesString.split(";");

    System.out.println("Please enter the grades for each module");
    StringBuilder grades = new StringBuilder(studentId + "," + course + "," + semester);

    for (String module : modules) {
        double score;
        while (true) {
            System.out.print(module + ": ");
            if (scanner.hasNextDouble()) {
                score = scanner.nextDouble();
                if (score >= 0) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid input, please try again");
            System.out.println();
        }
        grades.append(",").append(module).append("=").append(score);
    }

    System.out.println("Student grades have been updated, the information is as follows: ");
    System.out.println(grades.toString());
    System.out.println();
    List<String> lines = Files.readAllLines(Paths.get(resultFilePath));
    List<String> updatedLines = new ArrayList<>();
    boolean found = false;
    for (String line : lines) {
        if (line.startsWith(studentId + ",")) {
            updatedLines.add(grades.toString());
            found = true;
        } else {
            updatedLines.add(line);
        }
    }

    if (!found) {
        updatedLines.add(grades.toString());
    }
    Files.write(Paths.get(resultFilePath), updatedLines);
    }

    public void calculateAndSaveStudentGPA(String resultFilePath, String qcaFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(resultFilePath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(qcaFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) {
                    continue;
                }
                String studentId = data[0];
                List<String> grades = new ArrayList<>();
                StringBuilder gradeLine = new StringBuilder(studentId);
    
                for (int i = 2; i < data.length; i++) {
                    String[] moduleInfo = data[i].split(":");
                    if (moduleInfo.length != 2) {
                        continue; 
                    }
                    String[] scoreInfo = moduleInfo[1].split("=");
                    if (scoreInfo.length != 2) {
                        continue; 
                    }
                    try {
                        double score = Double.parseDouble(scoreInfo[1].trim());
                        String grade = scoreToGrade(score);
                        grades.add(grade);
                        gradeLine.append(",").append(moduleInfo[0]).append(":").append(scoreInfo[0]).append("=").append(grade);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
    
                double qca = calculateQCA(grades);
                gradeLine.append(",").append(String.format("%.2f", qca)); 
                writer.write(gradeLine.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateStudentTranscript(String studentId) {
        Result calculator = new Result();
        calculator.calculateAndSaveStudentGPA("result.csv", "studentQCA.csv");
    }
}
