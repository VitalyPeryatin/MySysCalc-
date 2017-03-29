package com.mycompany.coding2;


import com.mycompany.coding2.Model.ConverterNotation;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Test
    public void testAll(){
        convertToTenNotation();
        convertToCustomNotation();
    }

    @Test
    public void convertToTenNotation(){
        ConverterNotation converter = new ConverterNotation("10D1", 16, 10);
        try {
            converter.startTransfer();
            assertEquals("4305", converter.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void convertToCustomNotation(){
        ConverterNotation converter = new ConverterNotation("16,8", 10, 15);
        try {
            converter.startTransfer();
            assertEquals("11.C", converter.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
