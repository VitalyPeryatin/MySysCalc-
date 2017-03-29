package com.mycompany.coding2.Controller;

import android.util.Log;

import com.mycompany.coding2.Model.ConverterNotation;
import com.mycompany.coding2.Model.DataOfSolution;

/**
 * Руководит проводит процессами
 * @author Vitaly
 * @version 1.0
 */
public class Controller {
    private ConverterNotation converter = new ConverterNotation("0", 10, 2);
    private boolean hasSolution = false;
    private DataOfSolution solution = new DataOfSolution();

    public String getAnswer(String expression, int fromNotation, int toNotation){
        converter.setParameters(expression, fromNotation, toNotation);
        try {
            converter.startTransfer();
            return converter.getResult();
        } catch (Exception e) {
            Log.e("error", e.toString());
            return "";
        }
    }

    public DataOfSolution getSolution(){
        return new DataOfSolution();
    }
}
