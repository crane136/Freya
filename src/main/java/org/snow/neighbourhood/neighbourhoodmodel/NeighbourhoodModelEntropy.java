package org.snow.neighbourhood.neighbourhoodmodel;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-21
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public class NeighbourhoodModelEntropy extends NeighbourhoodModelBasic {
    @Override
    public double leftNeighbourhoodValue(String word) {
        double entropy = 0;
        int totalLeftWord = getLeftNumber(word);
        if (totalLeftWord == 0){
            return emptyNeighbourhoodValueDefault;
        }
        Map<String, Integer> leftWordMapper = documentsController.getLeftWordCount(word);
        for (String leftWord: leftWordMapper.keySet()){
            double leftWordNumberPro = ((double)leftWordMapper.get(leftWord)) / totalLeftWord;
            entropy = entropy + (-1.0 * Math.log(leftWordNumberPro) * leftWordNumberPro);
        }

        return entropy;
    }

    @Override
    public double rightNeighbourhoodValue(String word) {
        double entropy = 0;
        int totalRightWord = getRightNumber(word);
        if (totalRightWord == 0){
            return emptyNeighbourhoodValueDefault;
        }
        Map<String, Integer> rightWordMapper = documentsController.getRightWordCount(word);
        for (String rightWord: rightWordMapper.keySet()){
            double rightWordNumberPro = ((double)rightWordMapper.get(rightWord)) / totalRightWord;
            entropy = entropy + (-1.0 * Math.log(rightWordNumberPro) * rightWordNumberPro);
        }

        return entropy;
    }
}
