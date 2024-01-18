import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    
    private static Solution solution;
    
    @BeforeEach
    void beforeEach() {
        solution = new Solution();
    }
    
    @Test
    @DisplayName("One letter in maxWidth of one")
    void one_letter_in_max_width_of_one() {
        assertCompare("a", "a", 1);
    }
    
    @Test
    @DisplayName("One letter in maxWidth of ten")
    void one_letter_in_max_width_of_ten() {
        assertCompare("a",
                "a         ", 10);
    }
    
    @Test
    @DisplayName("One word in maxWidth of its length")
    void one_word_in_max_width_of_its_length() {
        assertCompare("abcde",
                "abcde", 5);
    }
    
    @Test
    @DisplayName("Two letters in Max Width of one")
    void two_letters_in_max_width_of_one() {
        assertCompare("a b",
                 "a" + "\n" +
                              "b", 1);
    }
    
    @Test
    @DisplayName("Three letters in max width of three")
    void three_letters_in_max_width_of_three() {
        assertCompare("a b c",
                "a b" + "\n" +
                             "c  ", 3);
    }
    
    @Test
    @DisplayName("Two Words width 9")
    void two_words_width_9() {
        assertCompare("two words",
                "two words", 9);
    }

    @Test
    @DisplayName("Two Words width 8")
    void two_words_width_8() {
        assertCompare("two words",
                 "two     " + "\n" +
                              "words   ", 8);
    }
    
    @Test
    @DisplayName("four is by abracadabra, width 16")
    void four_is_by_abracadabra_width_16() {
        assertCompare("four is by abracadabra",
                "four    is    by" + "\n" +
                             "abracadabra     ", 16);
    }
    
    @Test
    @DisplayName("four is by abracadabra, width 15")
    void four_is_by_abracadabra_width_15() {
        assertCompare("four is by abracadabra",
                "four    is   by" + "\n" +
                             "abracadabra    ", 15);
    }

    @Test
    @DisplayName("This is an example width 16")
    void this_is_an_example_width_16() {
        assertCompare("This is an example",
                "This    is    an" + "\n" +
                             "example         ", 16);
    }
    
    @Test
    @DisplayName("This is an example of text justification. width 16")
    void this_is_an_example_of_text_justification_width_16() {
        assertCompare("This is an example of text justification.",
                "This    is    an" + "\n" +
                             "example  of text" + "\n" +
                             "justification.  ", 16);
    }

    @Test
    @DisplayName("Last line is left-justified")
    void last_line_is_left_justified() {
        assertCompare("acknowledgment shall be",
                "acknowledgment  " + "\n" +
                             "shall be        ", 16);
        assertCompare("12345 12",
                "12345 12        ", 16);
    }


    @Test
    @DisplayName("What must be acknowledgment shall be width 16")
    void what_must_be_acknowledgment_shall_be_width_16() {
        assertCompare("What must be acknowledgment shall be",
                "What   must   be" + "\n" +
                             "acknowledgment  " + "\n" +
                             "shall be        ", 16);

    }
    
    @Test
    @DisplayName("not trimming spaces IN TEST STRINGS is ok")
    void not_trimming_spaces_is_ok() {
        assertCompare(" abc", "abc", 3);
        assertCompare("abc ", "abc", 3);
        assertCompare(" abc ", "abc", 3);
    }
    

    @Test
    @DisplayName("middle line is fully-justified")
    void middle_line_is_fully_justified() {
        assertCompare("Science is what we justified",
                "Science  is  what we" + "\n" +
                             "justified           ", 20);
    }
    
    @Test
    @DisplayName("LeetCode test 3")
    void leet_code_test_3() {
        /* Science is what we understand well enough to explain to a computer. Art is everything else we do */
        assertCompare("Science is what we understand well enough to explain to a computer. Art is everything else we do",
                 "Science  is  what we" + "\n" +
                              "understand      well" + "\n" +
                              "enough to explain to" + "\n" +
                              "a  computer.  Art is" + "\n" +
                              "everything  else  we" + "\n" +
                              "do                  ", 20);
    }
    
    @Test
    @DisplayName("How long it runs")
    void how_long_it_runs() {
        String inText = "Science is what we understand well enough to explain to a computer. Art is everything else we do";
        double total = 0;
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            solution.fullJustify(wordsArrayFromText(inText), 20);
            long end = System.nanoTime();
            total += (end - start);
        }
        System.out.printf("%.4f milliseconds", total / 1e9);
    }
    
    
    private static List <String> expectedFromString(String text) {
        return List.of(text.split("\n"));
    }
    
    private static String[] wordsArrayFromText(String text) {
        return text.trim().split("\s");
    }
    
    private static void assertCompare(String inText, String outTextMustBe, int maxWidth) {
        assertLinesMatch(expectedFromString(outTextMustBe),
                solution.fullJustify(wordsArrayFromText(inText), maxWidth));
        
    }
    
}
