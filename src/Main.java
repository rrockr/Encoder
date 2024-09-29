import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: Main [filename]");
            return;
        }

        String offsetChar;
        ReferenceTable table = new ReferenceTable(args[0]);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter plain text: ");
            String plainText = scanner.nextLine();

            offsetChar = getUserOffset(scanner, table);

            Encoder enc = new Encoder(offsetChar.charAt(0), table);
            String encodedText = enc.encode(plainText);
            System.out.println("Encoded Text: " + encodedText);

            String decodedText = enc.decode(encodedText);
            System.out.println("Decoded Text: " + decodedText);
        } catch (NoSuchElementException e) {
            System.err.println("No line found: " + e);
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Scanner is closed: " + e);
            e.printStackTrace();
        }
    }

    private static String getUserOffset(Scanner scanner, ReferenceTable table) {
        String offsetChar = "";
        Pattern pattern = Pattern.compile(table.getRegex());

        System.out.println("Enter offset character: ");
        while (!scanner.hasNext(pattern)) {
            System.out.println("Invalid offset character. Enter offset character: ");
            scanner.next();
        }
        offsetChar = scanner.next(pattern);

        return offsetChar;
    }
}
