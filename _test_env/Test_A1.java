/*
 * Author: William J. Horn
 */

// library imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Essay {
  /*
   * Test_A1.establishFile(<File> file, <boolean> required)
   * 
   * Create a file with the given path in the `file` argument, unless it
   * already exists. If the second argument `required` is true, then the
   * program will exit if the file does not exist. Regardless, if a file
   * does not exist but is used in the program, the program will create
   * the file.
   * 
   * @param file: The file path to establish
   * @param required: whether or not the file is required for the program to run
   */
  public static boolean establishFile(File file, boolean required) {
    try {
      // check if the file is required and the file doesn't exist
      // if true: create the file and exit
      // if false: continue
      if (required && !file.exists()) {
        System.out.println("Required file '" + file.getName() + "' does not exist (creating blank txt file)");
        file.createNewFile();
        System.exit(0);
      }

      // create the file or do nothing if it already exists
      boolean fileCreated = file.createNewFile();

      // if the file was created, then announce new file name
      if (fileCreated) {
        System.out.println("New file created: " + file.getName());
        return true;
      }

      // if file was not created, announce that it already exists
      System.out.println("File '" + file.getName() + "' already exists");
      return false;

    } catch(IOException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  /*
   * Test_A1.containsString(<String[]> list, <String> element)
   * 
   * Return true if a string array contains a string `element`
   * 
   * @param list: the string array to check
   * @param element: the string to check
   */
  public static boolean containsString(String[] list, String element) {
    for (String key : list) 
      if (key.equals(element)) return true;

    return false;
  }

  public static void main(String[] args) {
    // locate input & output files
    File inputFile = new File("data/input.txt");
    File outputFile = new File("data/output.txt");

    File happyAdjectivesFile = new File("data/words/happy_adjectives.txt");
    File sadAdjectivesFile = new File("data/words/sad_adjectives.txt");
    File angryAdjectivesFile = new File("data/words/angry_adjectives.txt");

    // make sure both input and output files exist before resuming execution
    establishFile(inputFile, true);
    establishFile(outputFile, false);
    establishFile(happyAdjectivesFile, true);
    establishFile(sadAdjectivesFile, true);
    establishFile(angryAdjectivesFile, true);

    // the source text of the files to read/write to
    String inputSource = "";
    String outputSource = "";

    // read in the input source text of the input file and assign it to `inputSource`
    try (Scanner reader = new Scanner(inputFile)) {
      while (reader.hasNextLine()) {
        // update input source var
        String nextLine = (reader.nextLine() + "\n");
        inputSource += nextLine;

        // split input source line into word breaks -- making parsing easier
        // for generating output
        String[] wordsInLine = nextLine.split(" ");

        // generate output based on input fragments
        for (String word : wordsInLine) {
          // length and last character of an input file line
          int wordLen = word.length() - 1;
          char lastChar = word.charAt(wordLen);

          // !! DO WORD CHECKING HERE !! //

          // append an input line element to the output source
          outputSource += word;

          // add a space in between words unless the line ends with a new line character (\n)
          if (lastChar != '\n') outputSource += " ";
        }
      }

      // preview the input vs. output sources
      System.out.println("Input: \n" + inputSource);
      System.out.println("Output: \n" + outputSource);

    } catch (FileNotFoundException e) {
      System.out.println("Error: " + e.getMessage());
    }

    // write the new modified input source to the output file
    try {
      // create the file writer & buffered writer to write to the output file
      FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());

      // create BufferWriter try-with-resources block to auto-close connection
      try (BufferedWriter bw = new BufferedWriter(fw)) {
        // write to the output file and close the connection
        bw.write(outputSource);

      } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
      }

    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}