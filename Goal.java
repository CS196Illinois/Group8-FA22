package com.example.goalkeeper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Goal {
    // FORMAT OF DATA: GOAL ID,NAME,STATUS,NOTES
    private int ID; // id of goal
    private String name; // goal name
    private boolean status; // status of goal (met or not)
    private String notes; // personal notes/description

    public Goal(int id) {
        try {
            File goalCSV = new File ("Project/all-csv-stuff/dataCSV.txt"); // Gets CSV file
            BufferedReader in = new BufferedReader(new FileReader(goalCSV));
            String line;
            while ((line = in.readLine()) != null) { // While current line is not equal to null
                String[] fields = line.split(","); // Seperate the line into a String array. Seperate at the commas.
                if (fields[0] == id) { // If the id matches the id in the constructor, initialize all the variables with those attributes
                    this.ID = Integer.parseInt(fields[0]);
                    this.name = fields[1];
                    this.status = Boolean.parseBoolean(fields[2]);
                    this.notes = fields[3];
                    break; // When Goal is found, break out of loop. (For efficiency)
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Gets the ID of the Goal
     * @return The Goal ID stored in the database
     */
    public int getID() {
        return ID;
    }
    public void setID(int id) {
        this.ID = id;
    }
    /**
     * Gets the name of the Goal
     * @return The Goal name stored in the database
     */
    public String getName() {
        return name;
    }
    public void setName(String name1) {
        this.name = name;
    }
    /**
     * Gets the status of the Goal
     * @return The Goal status stored in the database
     */
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean b) {
        this.status = b;
    }
    /**
     * Gets the notes of the Goal
     * @return The Goal notes stored in the database
     */
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes1) {
        this.notes = notes1;
    }
}
