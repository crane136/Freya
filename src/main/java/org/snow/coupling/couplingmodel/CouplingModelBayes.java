package org.snow.coupling.couplingmodel;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-19
 * Time: 下午1:23
 * To change this template use File | Settings | File Templates.
 */
public class CouplingModelBayes extends CouplingModelBasic {
    /*
                                    P(xy)     P(xy)      N(xy) * (Nxy)
        value = p(x|y) * p(y|x) = ------- * ------- = -----------------
                                    P(y)      P(x)        N(x) * N(y)
     */

    @Override
    public double couplingValue(String leftWord, String rightWord, String originalWord) {
        int leftWordCount = getWordNumber(leftWord);
        int rightWordCount = getWordNumber(rightWord);
        int originalWordCount = getWordNumber(originalWord);

        return ((double)originalWordCount * originalWordCount) / (leftWordCount * rightWordCount);
    }
}
