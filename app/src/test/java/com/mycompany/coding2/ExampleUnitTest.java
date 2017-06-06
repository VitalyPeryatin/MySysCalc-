package com.mycompany.coding2;

import com.mycompany.coding2.model.NumSysInfo;
import com.mycompany.coding2.model.converters.ConverterNotation;
import static com.mycompany.coding2.model.signs.CustomSigns.*;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    /**
     * Все тесты, связанные с конвертацией числа, находятся в одном месте, так как все они
     * однотипны и плодить методы для каждого теста не стоит.
     */
    @Test
    public void testConverters(){
        assertEquals(getAnswer("2", 10, 2), "10");
        assertEquals(getAnswer("10", 2, 10), "2");
        assertEquals(getAnswer("2", 2, 10), "Wrong notation");
        assertEquals(getAnswer(MINUS + "2", 2, 10), "Wrong notation");
        assertEquals(getAnswer("4005", 10, 16), "FA5");
        assertEquals(getAnswer("FA5", 16, 10), "4005");
        assertEquals(getAnswer("142", 5, 12), "3B");
        assertEquals(getAnswer("3B", 12, 5), "142");
        assertEquals(getAnswer("1,5", 9, 5), "1,23421");
        assertEquals(getAnswer("1,23421", 5, 9), "1,48886");
        assertEquals(getAnswer("1" + PLUS + "2" + MINUS + "3", 10, 2), "0");
        assertEquals(getAnswer("1,5" + PLUS + "2,5" + MINUS + "0,5", 10, 2), "11,1");
        assertEquals(getAnswer("1" + MINUS + "3", 10, 2), MINUS + "10");
        assertEquals(getAnswer("2" + MULTI + "5", 10, 2), "1010");
        assertEquals(getAnswer("10" + DIVIDE + "2", 10, 2), "101");
        assertEquals(getAnswer("10" + MINUS + "5", 10, 2), "101");
    }



    private String getAnswer(String expression, int fromNotation, int toNotation){
        NumSysInfo info = new NumSysInfo(expression, fromNotation, toNotation);
        return new ConverterNotation(info).convert();
    }
}
