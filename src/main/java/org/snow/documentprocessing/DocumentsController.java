package org.snow.documentprocessing;

import org.snow.util.Props;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-17
 * Time: 下午9:46
 * To change this template use File | Settings | File Templates.
 */
public class DocumentsController {
    private final int OFFSET = Integer.parseInt(Props.getProperty("offset"));

    private static DocumentsController documentsController;

    private List<DocumentVector> documents;
    private int totalDocumentNumber;

    private Map<String, Integer> allBasicWordsCount;
    private Map<String, Integer> allCandidateNewWordsCount;
    private Map<String, Map<String, Integer>> rightWordMapper;
    private Map<String, Map<String ,Integer>> leftWordMapper;

    private DocumentsController(){
        documents = new ArrayList<DocumentVector>();
        totalDocumentNumber = 0;
    }


    public static synchronized DocumentsController getInstance() {
        if (documentsController == null){
            documentsController = new DocumentsController();
        }
        return documentsController;
    }

    public int getTotalDocumentNumber() {
        return totalDocumentNumber;
    }

    public Set<String> getAllBasicWords(){
        return allBasicWordsCount.keySet();
    }

    public Set<String> getAllCandidateNewWords(){
        return allCandidateNewWordsCount.keySet();
    }

    public int getWordCount(String word){
        if (allBasicWordsCount.containsKey(word)){
            return allBasicWordsCount.get(word);
        }
        if (allCandidateNewWordsCount.containsKey(word)){
            return allCandidateNewWordsCount.get(word);
        }

        return 0;
    }

    public Map<String, Integer> getRightWordCount(String word){
        return rightWordMapper.get(word);
    }

    public Map<String, Integer> getLeftWordCount(String word){
        return leftWordMapper.get(word);
    }

    public synchronized void addDocument(DocumentVector document){
        documents.add(document);
        ++totalDocumentNumber;
    }

    public void processDocuments(){
        allBasicWordsCount = new HashMap<String, Integer>();
        allCandidateNewWordsCount = new HashMap<String, Integer>();
        rightWordMapper = new HashMap<String, Map<String, Integer>>();
        leftWordMapper = new HashMap<String, Map<String, Integer>>();

        for (DocumentVector document: documents){
            int wordsCount = document.getTotalCount();
            for (int i = 0; i < wordsCount; ++i) {
                String word = document.getWord(i);
                if (word == null || word.equals(" ") || word.isEmpty()){
                    continue;
                }
                countBasicWords(word);
                processOffsetWord(word, i, document);
            }
        }
    }

    private void processOffsetWord(String currentWord, int position, DocumentVector document) {
        for (int i = 1; i < OFFSET + 1; ++i){
            String rightWord = document.getWord(position + i);
            if (rightWord == null || rightWord.equals(" ") || rightWord.isEmpty()){
                return;
            }
            currentWord = currentWord + rightWord;
            countNewWords(currentWord);
            processRightWord(position + i, document, currentWord);
            processLeftWord(position, document, currentWord);
        }
    }

    private void countBasicWords(String word) {
        if (!allBasicWordsCount.containsKey(word)){
            allBasicWordsCount.put(word, 1);
        } else {
            int count = allBasicWordsCount.get(word);
            ++count;
            allBasicWordsCount.put(word, count);
        }
    }

    private void countNewWords(String word){
        if (!allCandidateNewWordsCount.containsKey(word)){
            allCandidateNewWordsCount.put(word, 1);
        } else {
            int count = allCandidateNewWordsCount.get(word);
            ++count;
            allCandidateNewWordsCount.put(word, count);
        }
    }

    private void processRightWord(int position, DocumentVector document, String currentWord){
        Map<String, Integer> rightWordCount;
        String rightWord = document.getWord(position + 1);
        if (rightWord == null || rightWord.equals(" ") || rightWord.isEmpty()){
            return;
        }

        if (!rightWordMapper.containsKey(currentWord)){
            rightWordCount = new HashMap<String, Integer>();
            rightWordCount.put(rightWord, 1);
            rightWordMapper.put(currentWord, rightWordCount);
        } else {
            rightWordCount = rightWordMapper.get(currentWord);
            if (!rightWordCount.containsKey(rightWord)){
                rightWordCount.put(rightWord, 1);
            } else {
                int count = rightWordCount.get(rightWord);
                ++count;
                rightWordCount.put(rightWord, count);
            }
        }
    }

    private void processLeftWord(int position, DocumentVector document, String currentWord){
        Map<String, Integer> leftWordCount;
        String leftWord = document.getWord(position - 1);
        if (leftWord == null || leftWord.equals(" ") || leftWord.isEmpty()){
            return;
        }
        if (!leftWordMapper.containsKey(currentWord)){
            leftWordCount = new HashMap<String, Integer>();
            leftWordCount.put(leftWord, 1);
            leftWordMapper.put(currentWord, leftWordCount);
        } else {
            leftWordCount = leftWordMapper.get(currentWord);
            if (!leftWordCount.containsKey(leftWord)){
                leftWordCount.put(leftWord, 1);
            } else {
                int count = leftWordCount.get(leftWord);
                ++count;
                leftWordCount.put(leftWord, count);
            }
        }
    }

    public void resetDocumentController(){
        documents.clear();
        totalDocumentNumber = 0;
        if (allBasicWordsCount != null){
            allBasicWordsCount.clear();
        }
        if (allCandidateNewWordsCount != null){
            allCandidateNewWordsCount.clear();
        }
        if (rightWordMapper != null){
            rightWordMapper.clear();
        }
        if (leftWordMapper != null){
            leftWordMapper.clear();
        }
    }
}
