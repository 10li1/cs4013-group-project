import java.io.IOException;

/** 
 * this program only can run in main(primary) folder, e.g if code in sub-folder, put sub-folder in a compilers
 * if program in sub-folder but run a primary folder so all java file will Error
 * The reason for this error occurring is because We didn't classify it
 * all the csv file is in current folder, if in sub-folder, program can't reader it
 * 
 * @author Peile Li
 */
public class StudentResultSystem {
   /**  
    * @param args command-line parameter
    * @throws IOException  If an input/output exception is encountered during runtime
    */
   public static void main(String[] args) throws IOException {  
      StudentResult menu = new StudentResult();
      menu.run();
   }
}