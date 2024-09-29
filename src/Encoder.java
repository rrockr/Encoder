import java.util.HashMap;

public class Encoder {
    private Character offsetChar;
    private ReferenceTable table;

    public Encoder(Character offsetChar, ReferenceTable table) {
        this.offsetChar = offsetChar;
        this.table = table;
    }

    public String encode(String plainText) {
        HashMap<Character, Integer> characterMap = table.getCharacterMap();
        HashMap<Integer, Character> indexMap = table.getIndexMap();
        int mappingSize = table.getMappingSize();
        String plainTextUpper = plainText.toUpperCase();
        StringBuilder builder = new StringBuilder(plainText.length() + 1);
        Integer offset = characterMap.get(offsetChar);
        builder.append(offsetChar);

        for (int i = 0; i < plainTextUpper.length(); i++) {
            Character plainChar = plainTextUpper.charAt(i);
            Integer plainCharIndex = characterMap.get(plainChar);

            if (plainCharIndex != null && offset != null) {
                int wrappedIndex = (plainCharIndex - offset) % mappingSize;
                wrappedIndex = wrappedIndex >= 0 ? wrappedIndex : wrappedIndex + mappingSize;
                Character encodedChar = indexMap.get(wrappedIndex);
                builder.append(encodedChar);
            } else {
                builder.append(plainChar);
            }
        }

        return builder.toString();
    }

    public String decode(String encodedText) {
        HashMap<Character, Integer> characterMap = table.getCharacterMap();
        HashMap<Integer, Character> indexMap = table.getIndexMap();
        int mappingSize = table.getMappingSize();
        String encodedTextUpper = encodedText.toUpperCase();
        StringBuilder builder = new StringBuilder(encodedText.length() - 1);
        Character offsetChar = encodedText.charAt(0);
        Integer offsetIndex = characterMap.get(offsetChar);

        for (int i = 1; i < encodedTextUpper.length(); i++) {
            Character encodedChar = encodedTextUpper.charAt(i);
            Integer encodedCharIndex = characterMap.get(encodedChar);

            if (encodedCharIndex != null && offsetIndex != null) {
                Character plainChar = indexMap.get((encodedCharIndex + offsetIndex) % mappingSize);
                builder.append(plainChar);
            } else {
                builder.append(encodedChar);
            }
        }

        return builder.toString();
    }
}
