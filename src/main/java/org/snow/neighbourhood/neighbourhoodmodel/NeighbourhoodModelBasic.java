package org.snow.neighbourhood.neighbourhoodmodel;

import org.snow.documentprocessing.DocumentsController;
import org.snow.util.Props;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-21
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class NeighbourhoodModelBasic implements iNeighbourhoodModel{
    protected DocumentsController documentsController = DocumentsController.getInstance();
    protected double emptyNeighbourhoodValueDefault = Double.parseDouble(Props.getProperty("emptyNeighbourhoodValueDefault", "1"));


    protected int getRightNumber(String word){
        Map<String, Integer> rightMapper = documentsController.getRightWordCount(word);
        if (rightMapper == null){
            return 0;
        }
        int total = 0;
        for (String rightWord: rightMapper.keySet()) {
            total = total + rightMapper.get(rightWord);
        }
        return total;
    }

    protected int getLeftNumber(String word){
        Map<String, Integer> leftMapper = documentsController.getLeftWordCount(word);
        if (leftMapper == null){
            return 0;
        }
        int total = 0;
        for (String leftWord: leftMapper.keySet()){
            total = total + leftMapper.get(leftWord);
        }
        return total;
    }
}
