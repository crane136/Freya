package org.snow.until;

import junit.framework.TestCase;
import org.snow.util.Props;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-19
 * Time: 下午12:57
 * To change this template use File | Settings | File Templates.
 */
public class PropsTest extends TestCase{
    public void testGetProperties(){
        double value = Double.parseDouble(Props.getProperty("testProperty", "0"));

        assertEquals(1.0, value);
    }
}
