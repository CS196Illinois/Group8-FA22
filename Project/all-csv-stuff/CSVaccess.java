
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ChangesToCSV {
	//YOU CAN EDIT THE PATH HERE, IT'S A STRING THAT GETS PASSED I
    //I wasn't able to test out whether or not relative path works, I used my PC's absolute path to the file when testing.
    private static final Path path = Paths.get("Project/all-csv-stuff/dataCSV.txt");
    
    //input is the goal name, goal status is incomplete by default, make sure the space remains after `false, ` as a notes placeholder
    private static int idNumber = 0;
    public static void addGoal(String input) {
        String extraLine = System.lineSeparator() + idNumber + "," + input + ",false, ";
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(extraLine);
            idNumber = idNumber + 1;	
        } catch (IOException ioe) {
        	System.out.println("SOMETHING WENT VERY WRONG");
            System.err.format("IOException: %s%n", ioe);
        }
    }
    //integer input is the line index to which you want to add the String input to, (remember IDofGoal = line number - 1) 
    //because we start indexing at zero, and because the first line will always have a boilerplate of the data format
    public static void updateNotes(int IDofGoal, String input) throws IOException {
    	try {
    		IDofGoal = IDofGoal + 1;
    		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String text = lines.get(IDofGoal);
            String[] arr = text.split(",");
            arr[3] = input;
            String s = String.join(",", arr);
            lines.set(IDofGoal, s);
            Files.write(path, lines, StandardCharsets.UTF_8);
    	} catch (IOException ioe) {
    		System.out.println("SOMETHING WENT VERY WRONG");
            System.err.format("IOException: %s%n", ioe);
    	}
    }
    //either changes the status (which is a string) of an incomplete task (false) to a complete one (true) or the other way around
    public static void changeStatus(int IDofGoal) throws IOException {
    	try {
    		IDofGoal = IDofGoal + 1;
    		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String text = lines.get(IDofGoal);
            String[] arr = text.split(",");
            if (arr[2].equals("false")) {
            	arr[2] = "true";
            } else if (arr[2].equals("true")) {
            	arr[2] = "false";
            } else {
            	throw new AssertionError();
            }
            String s = String.join(",", arr);
            lines.set(IDofGoal, s);
            Files.write(path, lines, StandardCharsets.UTF_8);
    	} catch (IOException ioe) {
    		System.out.println("SOMETHING WENT VERY WRONG");
            System.err.format("IOException: %s%n", ioe);
    	}
    }
    //removes a goal, all ID's that come after are shifted down by one
    //so if you have goals at ID's 0, 1, 2, 3 and you delete goal at ID 1, then:
    //                                  /  /
    //                             0, 1, 2
    // remember that IDofGoal's of goals begin at 1, the first line is a boilerplate of the data format
    public static void removeGoal(int IDofGoal) throws IOException {
    	if (IDofGoal == 0) {
    		throw new IOException("you can't delete the first line");
    	}
    	try {
    		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.set(IDofGoal, "");
            Files.write(path, lines, StandardCharsets.UTF_8);
            long count = Files.lines(path).count();
            List<String> newLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = IDofGoal; i < count - 1; i++) {
            	String text = newLines.get(i + 1);
            	int previousID = text.charAt(0);
            	int newID = previousID - 1;
            	char newIDChar = (char) newID;
            	StringBuilder newText = new StringBuilder(text);
            	newText.setCharAt(0, newIDChar);
            	newLines.set(i, newText.toString());
            	Files.write(path, newLines, StandardCharsets.UTF_8);
            }
    	} catch (IOException ioe) {
    		System.out.println("SOMETHING WENT VERY WRONG");
            System.err.format("IOException: %s%n", ioe);
    	}
    }
    //THESE WERE SOME OF MY TEST CASES
    //public static void main(String[] args) throws IOException {
      //	ChangesToCSV.addGoal("test1");
      //	ChangesToCSV.addGoal("test2");
      //	ChangesToCSV.addGoal("test3");
      //	ChangesToCSV.addGoal("test4");
      //	ChangesToCSV.addGoal("test5");
      //	ChangesToCSV.addGoal("test6");
      //	ChangesToCSV.addGoal("test7");
      //	ChangesToCSV.addGoal("test8");
      //	ChangesToCSV.addGoal("test9");
       // ChangesToCSV.removeGoal(3);
       // ChangesToCSV.updateNotes(4, "blah");
       // ChangesToCSV.updateNotes(5, "blahh");
    //}
}
