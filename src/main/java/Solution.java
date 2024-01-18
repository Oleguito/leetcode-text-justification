import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    
    public List <String> fullJustify(String[] words, int maxWidth) {
        return parse(words, maxWidth);
    }
    
    private static List<String> parse(String[] words, int width) {
        
        List<List<String>> allRows = convertToListListString(words, width);
        int size = allRows.size();
        
        List<String> lastRow = allRows.remove(size - 1);
        List<String> previousRows = createEmptyRow();
        
        previousRows = processPreviousRows(width, size, previousRows, allRows);
        previousRows.add(processedLastRow(width, lastRow));
        
        return previousRows;
    }
    
    private static List <String> processPreviousRows(int width, int size, List <String> previousRows, List <List <String>> allRows) {
        if(size > 1) {
            previousRows = allRows
                    .stream()
                    .map(wordsRow -> {
                        String withSpaces = introduceSpacesInBetween(wordsRow, width);
                        withSpaces = trimLeft(withSpaces);
                        withSpaces = padWithSpacesToTheRight(withSpaces, width);
                        return withSpaces;
                    })
                    .collect(Collectors.toList());
        }
        return previousRows;
    }
    
    private static String processLastRow(List <String> lastRow, int width) {
        if(lastRow.size() == 1) return padWithSpacesToTheRight(lastRow.get(0), width);
        return String.join(" ", lastRow);
    }
    
    private static ArrayList <String> createEmptyRow() {
        return new ArrayList <String>();
    }
    
    private static String processedLastRow(int width, List <String> lastRow) {
        return padWithSpacesToTheRight(
                processLastRow(lastRow, width),
                width);
    }
    
    private static String introduceSpacesInBetween(List <String> wordsRow, int width) {
        int needDelimiters = wordsRow.size() - 1;
        if(needDelimiters == 0) return theOnlyWord(wordsRow);
        
        int totalDelimitersWidth = getTotalDelimitersWidth(wordsRow, width);
        
        int[] delimiterWidths = new int[needDelimiters];
        for (int i = 0; i < needDelimiters; i++) {
            delimiterWidths[i] = 1;
        }
        totalDelimitersWidth -= needDelimiters;
        while(totalDelimitersWidth > 0) {
            for (int i = 0; i < delimiterWidths.length; i++) {
                if(totalDelimitersWidth == 0) break;
                delimiterWidths[i]++;
                totalDelimitersWidth--;
            }
        }
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < needDelimiters; i++) {
            result.append(wordsRow.get(i));
            result.append(getDelimiterBySpacesWidth(delimiterWidths[i]));
        }
        result.append(wordsRow.get(wordsRow.size() - 1));
        
        return result.toString();
    }
    
    private static String prependExtraSingleSpaceIntoFirstDelimiterIfNecessary(int totalDelimitersWidth, int needDelimiters, String delimiter, String result) {
        if(     (totalDelimitersWidth / needDelimiters) % 2 != 0 &&
                delimiter.length() * needDelimiters < totalDelimitersWidth
        ) {
            result = result.replaceFirst("\s+", delimiter + " ");
        }
        return result;
    }
    
    private static String theOnlyWord(List <String> wordsRow) {
        return wordsRow.get(0);
    }
    
    private static int getTotalDelimitersWidth(List <String> wordsRow, int width) {
        return width - getLengthOfAllWordsInRow(wordsRow);
    }
    
    private static int getSingleDelimiterWidth(List <String> wordsRow, int width, int needSpaces) {
        if(needSpaces != 0) {
            return getTotalDelimitersWidth(wordsRow, width) / needSpaces;
        }
        return 0;
    }
    
    private static String getDelimiterBySpacesWidth(int spaceWidth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaceWidth; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
    
    private static int getLengthOfAllWordsInRow(List <String> wordsRow) {
        return wordsRow.stream()
                .mapToInt(word -> word.length())
                .sum();
    }
    
    private static List<List<String>> convertToListListString(String[] words, int width) {
        
        List<List<String>> result = new ArrayList <>();
        result.add(new ArrayList <>());
        int whichRow = 0;
        int wordIndex = 0;
        int currentWidth = 0;
        String currentWord = words[wordIndex];
        
        while(nextWordExists(words, wordIndex)) {
            while(canAddWordToRow(currentWord, width, currentWidth)) {
                
                addWordToRow(result, whichRow, currentWord);
                
                currentWidth += checkIncludeFirstSpace(currentWidth);
                currentWidth += getWordLength(words, wordIndex);
                
                if(nextWordIsBeyondWordsLength(words, wordIndex)) {
                    /* Этот инкремент выведет из внешнего цикла */
                    wordIndex++;
                    break;
                }
                currentWord = words[++wordIndex];
                
            }
            if(nextWordExists(words, wordIndex)) {
                whichRow++;
                result.add(new ArrayList <>());
                currentWidth = 0;
            }
        }
        
        return result;
    }
    
    private static void addWordToRow(List <List <String>> result, int row, String word) {
        result.get(row).add(word);
    }
    
    private static int getWordLength(String word) {
        return word.length();
    }
    
    private static int getWordLength(String[] words, int index) {
        return words[index].length();
    }
    
    private static boolean nextWordIsBeyondWordsLength(String[] words, int wordIndex) {
        return wordIndex + 1 >= words.length;
    }
    
    private static boolean nextWordExists(String[] words, int wordIndex) {
        return wordIndex < words.length;
    }
    
    private static boolean canAddWordToRow(String word, int width, int currentWidth) {
        
        int includeFirstSpace = checkIncludeFirstSpace(currentWidth);
        
        int newLen = includeFirstSpace + currentWidth + getWordLength(word);
        if(     newLen + 1 <= width ||
                newLen == width
        ) {
            return true;
        }
        return false;
    }
    
    private static int checkIncludeFirstSpace(int currentWidth) {
        return currentWidth == 0 ? 0 : 1;
    }
    
    private static String trimLeft(String input) {
        int i = 0;
        while (i < getWordLength(input) && Character.isWhitespace(input.charAt(i))) {
            i++;
        }
        return input.substring(i);
    }
    
    private static String padWithSpacesToTheRight(String from, int targetWidth) {
        int spacesToAdd = targetWidth - getWordLength(from);
        StringBuilder sb = new StringBuilder();
        sb.append(from);
        for (int i = 0; i < spacesToAdd; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
    
}
