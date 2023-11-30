import java.io.IOException;
public class StudentResultSystem {
   /**
    * this program only can run in main(primary) folder
    * if program in sub-folder, all java file will Error
    * The reason for this error occurring is because We didn't classify it
    * all the csv file is in current(primary) folder, if in sub-folder, program can't reader it
    *   
    * @param args
    * @throws IOException
    */
    public static void main(String[] args)
        throws IOException
   {  
      StudentResult menu = new StudentResult();
      menu.run();
   }
}