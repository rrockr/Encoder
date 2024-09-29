import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ReferenceTable {
    private HashMap<Integer, Character> indexMap;
    private HashMap<Character, Integer> characterMap;
    private int mappingSize;
    private String inputFilePath;
    private Pattern referenceRegex;

    public ReferenceTable(String inputFilePath) {
        indexMap = new HashMap<>();
        characterMap = new HashMap<>();
        mappingSize = 0;
        this.inputFilePath = inputFilePath;

        populateTable();
    }

    public int getMappingSize() {
        return this.mappingSize;
    }

    public Pattern getRegex() {
        return this.referenceRegex;
    }

    public HashMap<Integer, Character> getIndexMap() {
        return this.indexMap;
    }

    public HashMap<Character, Integer> getCharacterMap() {
        return this.characterMap;
    }

    public void populateTable() {
        StringBuilder regexBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            int counter = 0;
            String s = reader.readLine();
            while (s != null) {
                char refChar = s.charAt(0);
                indexMap.put(counter, refChar);
                characterMap.put(refChar, counter);
                counter++;
                s = reader.readLine();
                regexBuilder.append(refChar);
            }
            mappingSize = counter;
            referenceRegex = Pattern.compile(String.format("[%s]", regexBuilder.toString()));

        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e);
            e.printStackTrace();
            System.exit(1);
        } catch (PatternSyntaxException e) {
            System.err.println("Invalid regex: " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
