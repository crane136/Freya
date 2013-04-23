package org.snow.neighbourhood.neighbourhoodmodel;

import org.snow.util.Props;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-21
 * Time: 下午8:37
 * To change this template use File | Settings | File Templates.
 */
public class NeighbourhoodModelFactory {
    public static final String MODEL = Props.getProperty("neighbourhoodModel", "NeighbourhoodModelEntropy");

    public static iNeighbourhoodModel getNeighbourhoodModel(){
        String className = "org.snow.neighbourhood.neighbourhoodmodel." + MODEL;
        try {
            Class neighbourhoodModel = Class.forName(className);
            return  (iNeighbourhoodModel)neighbourhoodModel.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }
}
