package com.mycompany.coding2.model.converters;

import com.mycompany.coding2.model.NumSysInfo;
import com.mycompany.coding2.model.SolutionBuilder;
import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.model.signs.SystemSigns;

/**
 * Четвертое звено при переводе числа в другую систему счисления.
 * <p>
 *     Переводит число из 10-ой системы счисления в n-ую.
 * </p>
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
class ConvertToCustom implements IConvert{
    private String expression;
    private int toNotation;
    private static final int AMOUNT_OF_SYMBOLS_AFTER_COMMA = 5, MAX_NOTATION = 16;
    private SolutionBuilder solutionBuilder;

    /**
     * Конструирует конвертор и инициализирует необходимые поля
     * @param info основные данные о текущем состоянии выражения и систем счисления
     * @param solutionBuilder строитель решения
     */
    ConvertToCustom(NumSysInfo info, SolutionBuilder solutionBuilder){
        expression = info.getExpression();
        toNotation = info.getToNotation();
        this.solutionBuilder = solutionBuilder;
    }

    /**
     * Заполняет объект <code>info</code> новыми данными: изменяет выражение.
     * Конвертирует полученное выражение в 10-ой системе счисления в n-ую.
     * @return число в n-ой системе счисления
     */
    @Override
    public String convert() {
        if(toNotation != 10 && !expression.equals(ConverterNotation.error))
            convertToCustomNotation();
        return expression;
    }

    private void convertToCustomNotation() {
        //ВАЖНО!!! Integer.parseInt() работает только с запятыми вида ',' поэтому запятые заменяются на Custom
        expression = expression.replace(SystemSigns.COMMA, CustomSigns.COMMA) ;
        String comma = String.valueOf(CustomSigns.COMMA);
        String minus = String.valueOf(SystemSigns.MINUS);
        boolean isNegative = false;
        if(expression.contains(minus)){
            expression = expression.substring(1);
            isNegative = true;
        }
        if(expression.contains(comma)){
            String[] splitNum = getSplitNumByComma();
            String intPart = customNotationOfInteger(Integer.parseInt(splitNum[0]));
            String fracPart = customNotationOfFraction(Integer.parseInt(splitNum[1]));
            expression = intPart.concat(comma).concat(fracPart);
        }
        else
            try {
                expression = customNotationOfInteger(Integer.parseInt(expression));
            }catch (Exception e){
                expression = ConverterNotation.getBigNumberError();
            }
        if(isNegative) expression = (minus).concat(expression);
    }

    private String customNotationOfInteger(int number){
        String modulo = "";
        int integerPart = number;
        // Цикл осуществляет основную работу по переводу целой части
        // десятичного чсла в нужную систему счисления
        while(integerPart != 0){
            modulo = setDigit((integerPart % toNotation)) + modulo;
            integerPart /= toNotation;
        }
        if(modulo.equals("")) modulo = "0";
        return modulo;
    }

    private String customNotationOfFraction(int number){
        String modulo = "";
        int integerPart = number;
        // Допустимая длина целой части (остальная часть идет в остаток)
        int maxLength = (int)Math.pow(10, String.valueOf(number).length());
        // Цикл осуществляет основную работу по переводу дробной части в нужную систему счисления
        for(int i = 0; i < AMOUNT_OF_SYMBOLS_AFTER_COMMA; i++){
            integerPart *= toNotation;
            modulo += setDigit(integerPart / maxLength);
            integerPart %= maxLength;
            if (integerPart == 0) break;
        }
        return modulo;
    }

    private String[] getSplitNumByComma() {
        String[] splitNumByComma = expression.split(String.valueOf(CustomSigns.COMMA), 2);
        for(int i = 0; i < splitNumByComma.length; i++)
            if(splitNumByComma[i].equals("")) splitNumByComma[i] = "0";
        return splitNumByComma;
    }

    private String setDigit(int digit){
        if (digit >= 10 && digit < MAX_NOTATION)
            return String.valueOf((char) (digit + 55));
        return String.valueOf(digit);
    }
}
