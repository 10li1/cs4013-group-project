import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is use for read csv file
 * @author Peile Li, Tianxing Fan and Cheng Yang
 */
public class CSVReader {
    public static List<List<String>> readData(String fileName) throws IOException {
        List<List<String>> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<String> dataLine = new ArrayList<>();
            for (String value : values) {
                dataLine.add(value);
            }
            data.add(dataLine);
        }

        br.close();
        return data;
    }   
}