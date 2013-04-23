package org.snow.coupling.couplingmodel;

import org.snow.util.Props;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-19
 * Time: 下午2:56
 * To change this template use File | Settings | File Templates.
 */
public class CouplingModelFactory {
    private final static String MODEL = Props.getProperty("couplingModel", "CouplingModelBayes");

    public static iCouplingModel getCouplingModel(){
        String className = "org.snow.coupling.couplingmodel." + MODEL;
        try {
            Class couplingModel = Class.forName(className);
            return (iCouplingModel)couplingModel.newInstance();
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
