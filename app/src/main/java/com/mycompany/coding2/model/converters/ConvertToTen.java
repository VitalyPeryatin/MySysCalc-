package com.mycompany.coding2.model.converters;

import android.icu.text.NumberFormat;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mycompany.coding2.controller.commands.Number;
import com.mycompany.coding2.model.NumSysInfo;
import com.mycompany.coding2.model.SolutionBuilder;
import com.mycompany.coding2.model.signs.SystemSigns;

/**
 * Третье звено при переводе числа в другую систему счисления.
 * <p>
 *     Переводит выражение из n системы счисления в 10-ую и решает его.
 *     В итоге на выходе получается одно число в 10-ой системе счисления
 * </p>
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
class ConvertToTen implements IConvert{
    private String expression;
    private int fromNotation;
    private NumSysInfo info;
    private SolutionBuilder solutionBuilder;

    /**
     * Конструирует конвертор и инициализирует необходимые поля
     * @param info основные данные о текущем состоянии выражения и систем счисления
     * @param solutionBuilder строитель решения
     */
    ConvertToTen(NumSysInfo info, SolutionBuilder solutionBuilder) {
        this.info = info;
        expression = info.getExpression();
        fromNotation = info.getFromNotation();
        this.solutionBuilder = solutionBuilder;
    }

    /**
     * Заполняет объект <code>info</code> новыми данными: изменяет выражение.
     * Конвертирует полученное выражение в n-ой системе счисления в 10-ую и решает его.
     * @return число в 10-ой системе счисления
     */
    @Override
    public String convert() {
        if(expression.length() == 0)
            return expression;
        else {
            String result = getResultNumber();
            try {
                if(Math.abs(Double.parseDouble(result)) >= 2_147_483_647)
                    throw new NumberFormatException();
                info = new NumSysInfo(result, info.getFromNotation(), info.getToNotation());
                return new ConvertToCustom(info, solutionBuilder).convert();
            }catch (NumberFormatException e){
                return ConverterNotation.getBigNumberError();
            }
        }
    }

    private String getResultNumber(){
        String tenExpression;
        if(fromNotation == 10)
            tenExpression = expression;
        else
            tenExpression = getTenExpression();
        return countTenExpression(tenExpression);
    }

    private String countTenExpression(String tenExpression){
        try {
            float result = new MathParser().parse(tenExpression);
            if(result % 1 == 0)
                expression = String.valueOf((int)result);
            else
                expression = String.valueOf(result);
        } catch (Exception e) {
            expression = ConverterNotation.error;
        }
        return expression;
    }

    /**
     * Переводит математическое выражение в десятичную систему счисления
     * @return математическое выражение в десятичной системе счисления
     */
    private String getTenExpression() {
        String tenExpression = "";
        String notTenExpression = expression;
        if(!getSign(expression).equals("")) {
            while (notTenExpression.length() > 0) {
                // split[0] - число для перевода в десятичную систему счисления
                // split[1] - выражение, которое нужно перевести в дестичную систему счисления
                String signs = String.format("[%s%s%s%s]",
                        String.valueOf(SystemSigns.MINUS),
                        String.valueOf(SystemSigns.PLUS),
                        String.valueOf(SystemSigns.MULTI),
                        String.valueOf(SystemSigns.DIVIDE));
                String[] split = notTenExpression.split(signs, 2);
                tenExpression += convertToTenNotation(split[0]) + getSign(notTenExpression);
                if(split.length == 2)
                    notTenExpression = split[1];
                else
                    notTenExpression = "";
            }
            return tenExpression;
        }
        else
            return convertToTenNotation(expression);
    }

    /**
     * Получает первый операнд в строке
     * @param expression математическое выражение
     * @return операнд
     */
    @NonNull
    private String getSign(String expression){
        for(int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (symbol == SystemSigns.PLUS || symbol == SystemSigns.MINUS ||
                    symbol == SystemSigns.MULTI || symbol == SystemSigns.DIVIDE)
                return String.valueOf(symbol);
        }
        return "";
    }

    /**
     * Переводит одно число в десятичную систему счисления
     * @param number число для перевода в десятичную систему счисления
     * @return число переведенное в десятичную систему счисления
     */
    @NonNull
    private String convertToTenNotation(String number) {
        if (number.length() == 0) return "";
        int pow = getBeginPow(number);
        double num = 0;
        // Цикл осуществляющий основную работу по переводу числа в дестичную систему счисления
        while(number.length() > 0){
            int digit = getOneDigit(number.charAt(0));
            if(digit!=-1)
                num += digit * Math.pow(fromNotation, pow--);
            number = number.substring(1);
        }
        return String.valueOf(num);
    }

    /**
     * Переводит число записанное по правилам систем счислений в "дестичное" (A = 10)
     * @param digit число записанное по правилам систем счисления
     * @return число записанное в десятичном виде
     */
    private int getOneDigit(char digit){
        if(digit >= 'A' && digit <= 'F') return digit - 55;
        else if(digit == SystemSigns.COMMA) return -1;
        return Integer.parseInt(String.valueOf(digit));
    }

    /**
     * Определяет начальную степень числа
     * @param number число в котором нужно определить степень
     * @return начальная степень числа
     */
    private int getBeginPow(String number){
        String comma = String.valueOf(SystemSigns.COMMA);
        if(number.contains(comma))
            return number.indexOf(comma) - 1;
        else
            return number.length() - 1;
    }
}
