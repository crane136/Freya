package org.snow.documentprocessing;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-17
 * Time: 下午9:59
 * To change this template use File | Settings | File Templates.
 */
public class DocumentVectorTest extends TestCase {

    private DocumentVector documentVector;

    public void testCountWords(){
        List<String> wordList = Arrays.asList("玉兰油", "润肤霜", "玉兰油", "包装");
        documentVector = new DocumentVector(wordList);
        List<Integer> positions = documentVector.getWordPositions("玉兰油");

        assertEquals(4, documentVector.getTotalCount());
        assertEquals(0, (int)positions.get(0));
        assertEquals(2, (int)positions.get(1));
    }
}
