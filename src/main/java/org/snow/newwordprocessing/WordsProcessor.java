package org.snow.newwordprocessing;

import org.snow.coupling.Coupling;
import org.snow.documentprocessing.DocumentVector;
import org.snow.documentprocessing.DocumentsController;
import org.snow.neighbourhood.Neighbourhood;
import org.snow.tokenizer.IKAnalyzer;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-22
 * Time: 下午8:37
 * To change this template use File | Settings | File Templates.
 */
public class WordsProcessor {
    private DocumentsController documentsController = DocumentsController.getInstance();
    private Coupling coupling = Coupling.getInstance();
    private Neighbourhood neighbourhood = Neighbourhood.getInstance();

    public void getWordsSource(List<String> words){
        DocumentVector documentVector = new DocumentVector(words);
        documentsController.addDocument(documentVector);
    }

    public void getWordsSource(List<String> words, String documentName){
        DocumentVector documentVector = new DocumentVector(words, documentName);
        documentsController.addDocument(documentVector);
    }

    public void getWordsSource(String fileName){
        File file = new File(fileName);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String para;
            List<String> wordList = new ArrayList<String>();
            while ((para = bufferedReader.readLine()) != null){
                wordList.addAll(IKAnalyzer.tokenizer(para));
                wordList.add("。");
            }
            getWordsSource(wordList, fileName.replaceFirst("^.*/", ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            try{
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public Set<String> processNewWords(){
        documentsController.processDocuments();
        coupling.couplingValueProcessing();
        neighbourhood.neighbourhoodValueProcessing(coupling.getNewWordsList());

        return neighbourhood.getNewWordsList();
    }
}
