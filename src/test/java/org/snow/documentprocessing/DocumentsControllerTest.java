package org.snow.documentprocessing;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-18
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
public class DocumentsControllerTest extends TestCase {
    private DocumentsController documentsController = DocumentsController.getInstance();


    public void testAddDocument(){
        documentsController.resetDocumentController();

        List<String> wordList = Arrays.asList("玉兰油", "润肤霜", "玉兰油", "包装", "润肤霜");
        DocumentVector document0 = new DocumentVector(wordList, "document0");
        DocumentVector document1 = new DocumentVector(wordList, "document0");
        DocumentVector document2 = new DocumentVector(wordList, "document0");

        documentsController.addDocument(document0);
        documentsController.addDocument(document1);
        documentsController.addDocument(document2);

        assertEquals(3, documentsController.getTotalDocumentNumber());
    }

    public void testProcessDocument(){
        documentsController.resetDocumentController();

        List<String> wordList0 = Arrays.asList("多", "芬", "沐浴露");
        DocumentVector document0 = new DocumentVector(wordList0, "document0");
        documentsController.addDocument(document0);

        List<String> wordList1 = Arrays.asList("多", "芬", "洗发露");
        DocumentVector document1 = new DocumentVector(wordList1, "document1");
        documentsController.addDocument(document1);

        List<String> wordList2 = Arrays.asList("多", "芬", "洗发露", "包装");
        DocumentVector document2 = new DocumentVector(wordList2, "document2");
        documentsController.addDocument(document2);

        List<String> wordList3 = Arrays.asList("玉兰", "油", "洗面奶");
        DocumentVector document3 = new DocumentVector(wordList3, "document3 ");
        documentsController.addDocument(document3);

        List<String> wordList4 = Arrays.asList("玉兰", "油", "洗面奶", "新品");
        DocumentVector document4 = new DocumentVector(wordList4, "document4");
        documentsController.addDocument(document4);

        List<String> wordList5 = Arrays.asList("你好", "五月", " ", "天");
        DocumentVector document5 = new DocumentVector(wordList5, "document5");
        documentsController.addDocument(document5);

        documentsController.processDocuments();

        assertEquals(6, documentsController.getTotalDocumentNumber());
        assertEquals(true, documentsController.getAllBasicWords().contains("五月"));
        assertEquals(true, documentsController.getAllCandidateNewWords().contains("你好五月"));
        assertEquals(false, documentsController.getAllCandidateNewWords().contains("五月天"));


        Map<String, Integer> rightDuofen = documentsController.getRightWordCount("多芬");
        Set<String> rightWords = rightDuofen.keySet();

        assertEquals(2, rightWords.size());
        assertEquals(2, (int)rightDuofen.get("洗发露"));
        assertEquals(1, (int)rightDuofen.get("沐浴露"));

        Map<String, Integer> leftYulanyou = documentsController.getLeftWordCount("油洗面奶");
        Set<String> leftWords = leftYulanyou.keySet();
        assertEquals(1, leftWords.size());
        for (String word: leftWords){
            assertEquals("玉兰", word);
            assertEquals(2, (int)leftYulanyou.get("玉兰"));
        }

    }
}
