package org.snow.newwordprocessing;

import junit.framework.TestCase;
import org.snow.documentprocessing.DocumentsController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-22
 * Time: 下午9:26
 * To change this template use File | Settings | File Templates.
 */
public class NewWordsProcessingTest extends TestCase{
    public void testGetWordsSource(){
        WordsProcessor processor = new WordsProcessor();
        List<String> wordList = Arrays.asList("多", "芬", "洗发露");
        processor.getWordsSource(wordList);
        processor.getWordsSource("src/test/java/org/snow/testfile.txt");
        processor.processNewWords();
        Set<String> basicWords = DocumentsController.getInstance().getAllBasicWords();
        Set<String> candidateWords = DocumentsController.getInstance().getAllCandidateNewWords();

        assertEquals(true, basicWords.contains("多"));
        assertEquals(true, basicWords.contains("包装"));
        assertEquals(false, basicWords.contains("沐浴"));

        assertEquals(true, candidateWords.contains("多芬"));
        assertEquals(true, candidateWords.contains("新包装"));
    }
}
