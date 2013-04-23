package org.snow.coupling;

import junit.framework.TestCase;
import org.snow.documentprocessing.DocumentVector;
import org.snow.documentprocessing.DocumentsController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-19
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class CouplingTest extends TestCase {

    private DocumentsController documentsController = DocumentsController.getInstance();
    private Coupling coupling = Coupling.getInstance();

    public void testCouplingValueProcessing(){
        initialDocumentController();

        Set<String> newWords = coupling.getNewWordsList();

        assertEquals(true, newWords.contains("多芬"));
        assertEquals(true, newWords.contains("名多海"));
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

        documentsController.processDocuments();
        coupling.couplingValueProcessing();
    }
}
