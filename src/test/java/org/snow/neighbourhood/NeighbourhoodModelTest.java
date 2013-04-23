package org.snow.neighbourhood;

import junit.framework.TestCase;
import org.snow.documentprocessing.DocumentVector;
import org.snow.documentprocessing.DocumentsController;
import org.snow.neighbourhood.neighbourhoodmodel.NeighbourhoodModelFactory;
import org.snow.neighbourhood.neighbourhoodmodel.iNeighbourhoodModel;
import org.snow.util.Props;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-21
 * Time: 下午10:49
 * To change this template use File | Settings | File Templates.
 */
public class NeighbourhoodModelTest extends TestCase {
    private DocumentsController documentsController = DocumentsController.getInstance();
    private iNeighbourhoodModel neighbourhoodModel = NeighbourhoodModelFactory.getNeighbourhoodModel();

    public void testLeftNeighbourhoodValue(){
        initialDocumentController();

        double EntropyDuofen = -Math.log(0.5) * 0.5 * 2;

        assertEquals(EntropyDuofen, neighbourhoodModel.rightNeighbourhoodValue("多芬"));
        assertEquals(Double.parseDouble(Props.getProperty("emptyNeighbourhoodValueDefault")), neighbourhoodModel.rightNeighbourhoodValue("新包装"));
        assertEquals(0.0, neighbourhoodModel.leftNeighbourhoodValue("多海"));
        assertEquals(0.0, neighbourhoodModel.rightNeighbourhoodValue("多海"));
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

    }
}
