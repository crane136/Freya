package org.snow.coupling.couplingmodel;

import org.snow.documentprocessing.DocumentsController;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-19
 * Time: 下午1:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class CouplingModelBasic implements iCouplingModel {
    protected DocumentsController documentsController = DocumentsController.getInstance();

    protected int getWordNumber(String word){
        return documentsController.getWordCount(word);
    }
}
