package com.mycompany.coding2.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataOfSolution implements Serializable {
    private boolean hasSolution = false;
    private String math = "", numberToTenNotation = "";
    private List<String>    integerToCustomNotation = new ArrayList<>(),
                            fractionToCustomNotation = new ArrayList<>();

    public void addMath(String text){
        math += text;
    }
}
