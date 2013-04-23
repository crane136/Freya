package org.snow.coupling;

import junit.framework.TestCase;
import org.snow.coupling.couplingmodel.iCouplingModel;
import org.snow.coupling.couplingmodel.CouplingModelFactory;
import org.snow.documentprocessing.DocumentVector;
import org.snow.documentprocessing.DocumentsController;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-19
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public class CouplingModelTest extends TestCase {
    private DocumentsController documentsController = DocumentsController.getInstance();

    public void testGetCouplingModel(){
        documentsController.resetDocumentController();

        List<String> wordList0 = Arrays.asList("多", "芬", "沐浴露", "500ml", "新", "包装");
        DocumentVector document0 = new DocumentVector(wordList0, "document0");
        documentsController.addDocument(document0);

        List<String> wordList1 = Arrays.asList("多", "芬", "洗发露");
        DocumentVector document1 = new DocumentVector(wordList1, "document1");
        documentsController.addDocument(document1);

        documentsController.processDocuments();
        iCouplingModel CouplingModel = CouplingModelFactory.getCouplingModel();

        assertEquals(1.0, CouplingModel.couplingValue("多", "芬", "多芬"));
        assertEquals(0.5, CouplingModel.couplingValue("芬", "沐浴露", "芬沐浴露"));

    }
}
