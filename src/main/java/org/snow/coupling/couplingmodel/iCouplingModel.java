package org.snow.coupling.couplingmodel;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-19
 * Time: 下午1:18
 * To change this template use File | Settings | File Templates.
 */
public interface iCouplingModel {

    public double couplingValue(String leftWord, String rightWord, String originalWord);
}
