package org.snow.neighbourhood;

import org.snow.documentprocessing.DocumentsController;
import org.snow.neighbourhood.neighbourhoodmodel.NeighbourhoodModelFactory;
import org.snow.neighbourhood.neighbourhoodmodel.iNeighbourhoodModel;
import org.snow.util.Props;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-21
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
public class Neighbourhood {
    private static Neighbourhood neighbourhood;

    private double neighbourhoodThreshold = Double.parseDouble(Props.getProperty("neighbourhoodThreshold", "0"));
    private static DocumentsController documentsController = DocumentsController.getInstance();
    private boolean saveNeighbourhoodValue = Boolean.parseBoolean(Props.getProperty("saveNeighbourhoodValue", "false"));

    private iNeighbourhoodModel neighbourhoodModel;

    private Set<String> newWordsList;
    private Map<String, Double> rightNeighbourhoodValue;
    private Map<String, Double> leftNeighbourhoodValue;

    private Neighbourhood(){
        newWordsList = new HashSet<String>();
        neighbourhoodModel = NeighbourhoodModelFactory.getNeighbourhoodModel();
        if (saveNeighbourhoodValue){
            rightNeighbourhoodValue = new HashMap<String, Double>();
            leftNeighbourhoodValue = new HashMap<String, Double>();
        }
    }

    public static synchronized Neighbourhood getInstance() {
        if (neighbourhood == null){
            neighbourhood = new Neighbourhood();
        }
        return neighbourhood;
    }

    public Set<String> getNewWordsList() {
        return newWordsList;
    }

    public double getLeftNeighbourhoodValue(String word){
        return leftNeighbourhoodValue.get(word);
    }

    public double getRightNeighbourValue(String word){
        return rightNeighbourhoodValue.get(word);
    }

    public void neighbourhoodValueProcessing(){
        Set<String> allNewWords = documentsController.getAllCandidateNewWords();

        for (String word: allNewWords){
            if (neighbourhoodRightValueCalculate(word) > neighbourhoodThreshold
                    && neighbourhoodLeftValueCalculate(word) > neighbourhoodThreshold){
                newWordsList.add(word);
            }
        }
    }

    public void neighbourhoodValueProcessing(Set<String> candidateWordList){
        for (String word: candidateWordList){
            if (neighbourhoodRightValueCalculate(word) > neighbourhoodThreshold
                    && neighbourhoodLeftValueCalculate(word) > neighbourhoodThreshold){
                newWordsList.add(word);
            }
        }
    }

    private double neighbourhoodLeftValueCalculate(String word) {
        double leftValue = neighbourhoodModel.leftNeighbourhoodValue(word);
        if (saveNeighbourhoodValue){
            leftNeighbourhoodValue.put(word, leftValue);
        }
        return leftValue;  //To change body of created methods use File | Settings | File Templates.
    }

    private double neighbourhoodRightValueCalculate(String word) {
        double rightValue = neighbourhoodModel.rightNeighbourhoodValue(word);
        if (saveNeighbourhoodValue){
            rightNeighbourhoodValue.put(word, rightValue);
        }
        return rightValue;  //To change body of created methods use File | Settings | File Templates.
    }
}

