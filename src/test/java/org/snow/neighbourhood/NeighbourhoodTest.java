package org.snow.neighbourhood;

import junit.framework.TestCase;
import org.snow.documentprocessing.DocumentVector;
import org.snow.documentprocessing.DocumentsController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-22
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
public class NeighbourhoodTest extends TestCase {
    private Neighbourhood neighbourhood = Neighbourhood.getInstance();
    private DocumentsController documentsController = DocumentsController.getInstance();

    public void testNeighbourhoodValueProcessing(){
        initialDocumentController();

        Set<String> newWords = neighbourhood.getNewWordsList();

        assertEquals(true, newWords.contains("多芬"));
        assertEquals(true, newWords.contains("名多海"));
        assertEquals(false, newWords.contains("新包装"));
        assertEquals(false, newWords.contains("多海"));
    }

    private void initialDocumentController(){
        documentsController.resetDocumentController();

        List<String> wordList0 = Arrays.asList("多", "芬", "沐浴露", "500ml", "新", "包装");
        DocumentVector document0 = new DocumentVector(wordList0, "document0");
        documentsController.addDocument(document0);

        List<String> wordList1 = Arrays.asList("多", "芬", "洗发露");
        DocumentVector document1 = new DocumentVector(wordList1, "document1");
        documentsController.addDocument(document1);

        List<String> wordList2 = Arrays.asList("名", "多", "海", "洗发露");
        DocumentVector document2 = new DocumentVector(wordList2, "document2");
        documentsController.addDocument(document2);

        List<String> wordList3 = Arrays.asList("名", "多", "海", "沐浴露", "250ml");
        DocumentVector document3 = new DocumentVector(wordList3, "document3");
        documentsController.addDocument(document3);

        documentsController.processDocuments();
        neighbourhood.neighbourhoodValueProcessing();
    }

}
