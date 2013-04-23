package org.snow.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-21
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public class WordsUtil {

    public static Set<String> mergeWordList(Set<String> wordList1, Set<String> wordList2){
        Set<String> wordList = new HashSet<String>();
        for (String word: wordList1){
            if (wordList2.contains(word)){
                wordList.add(word);
            }
        }
        return wordList;
    }
}
