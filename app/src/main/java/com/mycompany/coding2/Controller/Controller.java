package com.mycompany.coding2.Controller;

import android.content.Context;
import android.util.Log;

import com.mycompany.coding2.Model.ConverterNotation;
import com.mycompany.coding2.Model.DataOfSolution;
import com.mycompany.coding2.View.MainActivity;

/**
 * Руководит проводит процессами
 * @author Vitaly
 * @version 1.0
 */
public class Controller {
    private ConverterNotation converter;

    public Controller(Context context){
        converter = new ConverterNotation(context, "0", 10, 2, 5);
    }

    public String getAnswer(String expression, int fromNotation, int toNotation, int accuracy){
        converter.setParameters(expression, fromNotation, toNotation, accuracy);
        try {
            converter.startTransfer();
            return converter.getResult();
        } catch (NumberFormatException e) {
            return converter.getOldResult();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public DataOfSolution getSolution(){
        return new DataOfSolution();
    }
}
