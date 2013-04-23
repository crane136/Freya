package org.snow.tokenizer;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-17
 * Time: 下午5:57
 * To change this template use File | Settings | File Templates.
 */
public class IKAnalyzerTest extends TestCase {
    public void testTokenizer(){
        String sentence = "OLAY玉兰油润肤霜50g(新包装)";
        List<String> wordList = IKAnalyzer.tokenizer(sentence);
        assertEquals(6, wordList.size());
        assertEquals("玉兰油", wordList.get(1));
        assertEquals(false, wordList.contains("美白"));
    }

    public void testAddDicWords(){
        List<String> dicWord = new ArrayList<String>();
        dicWord.add("册丝");
        IKAnalyzer.addDicWords(dicWord);
        String sentence = "OLAY玉兰油美白润肤霜50g(册丝新包装)";
        List<String> wordList = IKAnalyzer.tokenizer(sentence);
        assertEquals(true, wordList.contains("册丝"));
    }
}
