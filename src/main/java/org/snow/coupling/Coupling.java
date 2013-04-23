package org.snow.coupling;

import org.snow.coupling.couplingmodel.iCouplingModel;
import org.snow.coupling.couplingmodel.CouplingModelFactory;
import org.snow.documentprocessing.DocumentsController;
import org.snow.tokenizer.IKAnalyzer;
import org.snow.util.Props;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-17
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
public class Coupling {
    private double couplingThreshold = Double.parseDouble(Props.getProperty("couplingThreshold", "0"));
    private boolean saveCouplingValue = Boolean.parseBoolean(Props.getProperty("saveCouplingValue", "false"));

    private static Coupling coupling;

    private DocumentsController documentsController = DocumentsController.getInstance();

    private iCouplingModel couplingModel;
    private Set<String> newWordsList;
    private Map<String, Double> couplingValueMapper;

    private Coupling(){
        newWordsList = new HashSet<String>();
        couplingModel = CouplingModelFactory.getCouplingModel();
        if (saveCouplingValue){
            couplingValueMapper = new HashMap<String, Double>();
        }
    }

    public static synchronized Coupling getInstance() {
        if (coupling == null){
            coupling = new Coupling();
        }
        return coupling;
    }

    public Set<String> getNewWordsList(){
        return newWordsList;
    }

    public double getCouplingValue(String word){
        return couplingValueMapper.get(word);
    }

    public void couplingValueProcessing(){
        Set<String> allNewWords = documentsController.getAllCandidateNewWords();

        for (String word: allNewWords){
            if (couplingValueCalculate(word) > couplingThreshold){
                newWordsList.add(word);
            }
        }
    }

    public void couplingValueProcessing(Set<String> candidateWordList){
        for (String word: candidateWordList){
            if (couplingValueCalculate(word) > couplingThreshold){
                newWordsList.add(word);
            }
        }
    }

    private double couplingValueCalculate(String word) {
        String[] wordSegments = IKAnalyzer.tokenizer(word).toArray(new String[0]);
        double[] value = new double[wordSegments.length - 1];
        for (int i = 0; i < wordSegments.length - 1; ++i) {
            String leftSegment = null;
            String rightSegment = null;
            for (int left = 0; left < i + 1; ++left){
                if (leftSegment == null){
                    leftSegment = wordSegments[left];
                } else {
                    leftSegment = leftSegment + wordSegments[left];
                }
            }
            for (int right = i + 1; right < wordSegments.length; ++right){
                if (rightSegment == null){
                    rightSegment = wordSegments[right];
                } else {
                    rightSegment = rightSegment + wordSegments[right];
                }
            }
            value[i] = couplingModel.couplingValue(leftSegment, rightSegment, word);
        }
        double couplingValue = getValue(value);
        if (saveCouplingValue){
            couplingValueMapper.put(word, couplingValue);
        }
        return couplingValue;
    }

    private double getValue(double[] values){
        double currentValue = 0;
        for (double value: values){
            if (value > currentValue){
                currentValue = value;
            }
        }
        return currentValue;
    }
}
