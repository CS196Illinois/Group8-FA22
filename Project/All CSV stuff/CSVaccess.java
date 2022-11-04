import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

//KNOWN LIMITATIONS: 
//1) EVERY TIME THE MAIN FUNCTION GETS RESET, idNumber = 1 AGAIN.
//2) EVERY TIME THE MAIN FUNCTION GETS RESET, THERE IS A LINE SKIP FOR SOME REASON
//3) PATH IS NOT RELATIVE TO THE PROJECT'S DIRECTORY, I COULD ONLY GET MY COMPUTER'S ABSOLUTE PATH TO WORK
//4) THERE MAY BE MANY BUGS THAT I HAVEN'T DISCOVERED YET
//5) IT WILL NOT BE EASY TO IMPLEMENT THIS INTO THE PROJECT BECAUSE OF COMPILING/DECOMPILING, WE CAN FIGURE THIS OUT LATER
//6) I AM WORKING WITH A .TXT FILE, NOT A .CSV FILE, BUT THEY ARE PRETTY MUCH THE SAME THING SO IT DOESN'T MATTER

public class ChangesToCSV {
	//YOU CAN EDIT THE PATH HERE, IT'S A STRING THAT GETS PASSED IN, IF YOU CAN FIGURE OUT A WAY TO GET THE PROJECT'S 
	//RELATIVE PATH TO WORK THAT WOULD BE AWESOME!
    private static final Path path = Paths.get("/Users/xy/Desktop/TestingProject/CSVxxx/Project/all-csv-stuff/csv.txt");
    
    //input is the goal name, goal status is incomplete by default, make sure the space remains after `false, ` as a notes placeholder
    private static int idNumber = 1;
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
    //integer input is the line index to which you want to add the String input to, (remember lineID = line number - 1) 
    //because we start indexing at zero
    public static void addNotes(int lineID, String input) throws IOException {
    	try {
    		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String text = lines.get(lineID);
            String[] arr = text.split(",");
            arr[3] = input;
            String s = String.join(",", arr);
            lines.set(lineID, s);
            Files.write(path, lines, StandardCharsets.UTF_8);
    	} catch (IOException ioe) {
    		System.out.println("SOMETHING WENT VERY WRONG");
            System.err.format("IOException: %s%n", ioe);
    	}
    }
    //either changes the status (which is a string) of an incomplete task (false) to a complete one (true) or the other way around
    public static void changeStatus(int lineID) throws IOException {
    	try {
    		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String text = lines.get(lineID);
            String[] arr = text.split(",");
            if (arr[2].equals("false")) {
            	arr[2] = "true";
            } else if (arr[2].equals("true")) {
            	arr[2] = "false";
            } else {
            	throw new AssertionError();
            }
            String s = String.join(",", arr);
            lines.set(lineID, s);
            Files.write(path, lines, StandardCharsets.UTF_8);
    	} catch (IOException ioe) {
    		System.out.println("SOMETHING WENT VERY WRONG");
            System.err.format("IOException: %s%n", ioe);
    	}
    }
    //THESE WERE MY TEST CASES
    //public static void main(String[] args) throws IOException {
      	//ChangesToCSV.addGoal("test1");
      	//ChangesToCSV.addGoal("test2");
        //ChangesToCSV.addGoal("test3");
        //ChangesToCSV.addGoal("test4");
        //ChangesToCSV.addNotes(2, "note");
        //ChangesToCSV.changeStatus(2);
    //}
}
