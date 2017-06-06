package com.mycompany.coding2.model.converters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mycompany.coding2.R;
import com.mycompany.coding2.model.NumSysInfo;
import com.mycompany.coding2.model.SolutionBuilder;
import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.model.signs.SystemSigns;

/**
 * Второе звено при переводе числа в другую систему счисления.
 * <p>
 *     Проверяет корректность введенного выражения и форматирует его при необходимости.
 * </p>
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class ConvertToValid implements IConvert {
    private String expression;
    private int fromNotation;
    private static String errorMessage;
    private NumSysInfo info;
    private SolutionBuilder solutionBuilder;

    /**
     * Конструирует конвертор и инициализирует необходимые поля
     * @param res ресурсы для получения строк
     * @param info основные данные о текущем состоянии выражения и систем счисления
     * @param solutionBuilder строитель решения
     */
    public ConvertToValid(Resources res, NumSysInfo info, SolutionBuilder solutionBuilder){
        errorMessage = res.getString(R.string.wrong_notation);
        this.info = info;
        expression = info.getExpression();
        fromNotation = info.getFromNotation();
        this.solutionBuilder = solutionBuilder;
    }

    /**
     * !!! Только для тестирования !!!
     * Конструирует конвертор и инициализирует необходимые поля
     * @param errorMessage сообщение при ошибке
     * @param info основные данные о текущем состоянии выражения и систем счисления
     * @param solutionBuilder строитель решения
     */
    ConvertToValid(String errorMessage, NumSysInfo info, SolutionBuilder solutionBuilder){
        ConvertToValid.errorMessage = errorMessage;
        this.info = info;
        expression = info.getExpression();
        fromNotation = info.getFromNotation();
        this.solutionBuilder = solutionBuilder;
    }

    /**
     * Проверяет выражение на корректность, преобразует его и продолжает цепочку
     * @return преобразованное выражение
     */
    @Override
    public String convert() {
        expression = rebuildToSystem(expression);
        if(checkValidity() != null) return checkValidity();
        trimSigns();
        info = new NumSysInfo(expression, info.getFromNotation(), info.getToNotation());
        expression = new ConvertToTen(info, solutionBuilder).convert();
        return rebuildToCustom(expression);
    }

    @NonNull
    private String rebuildToSystem(String s){
        return s
                .replace(CustomSigns.PLUS, SystemSigns.PLUS)
                .replace(CustomSigns.MINUS, SystemSigns.MINUS)
                .replace(CustomSigns.MULTI, SystemSigns.MULTI)
                .replace(CustomSigns.DIVIDE, SystemSigns.DIVIDE)
                .replace(CustomSigns.COMMA, SystemSigns.COMMA);
    }

    @NonNull
    private String rebuildToCustom(String s){
        return s
                .replace(SystemSigns.PLUS, CustomSigns.PLUS)
                .replace(SystemSigns.MINUS, CustomSigns.MINUS)
                .replace(SystemSigns.MULTI, CustomSigns.MULTI)
                .replace(SystemSigns.DIVIDE, CustomSigns.DIVIDE)
                .replace(SystemSigns.COMMA, CustomSigns.COMMA);
    }

    /**
     * Проверяет выражение на корректность
     * @return ошибка errorMessage при некорректном выражении
     */
    @Nullable
    public String checkValidity(){
        try{
            if(!isValidExpression()) throw new Exception(errorMessage);
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    private boolean isValidExpression(){ // TODO убрать говнокод со знаком || 
        boolean isValid = true;
        for(char symbol : expression.toCharArray()) {
            if ((symbol == SystemSigns.PLUS || symbol == SystemSigns.MINUS ||
                    symbol == SystemSigns.MULTI || symbol == SystemSigns.DIVIDE
                    || symbol == SystemSigns.COMMA)

                    ||

                    (symbol == CustomSigns.PLUS || symbol == CustomSigns.MINUS ||
                            symbol == CustomSigns.MULTI || symbol == CustomSigns.DIVIDE
                            || symbol == CustomSigns.COMMA))
                continue;
            if (getOneDigit(symbol) >= fromNotation) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private boolean strContainsOperands(String string){
        char[] operands = {SystemSigns.PLUS, SystemSigns.MINUS, SystemSigns.MULTI , SystemSigns.DIVIDE};
        for(char c : operands)
            if(string.contains(String.valueOf(c)))
                return true;
        return false;
    }

    private int getOneDigit(char digit){
        if(digit >= 'A' && digit <= 'F') return digit - 55;
        else if(digit == SystemSigns.COMMA) return -1;
        return Integer.parseInt(String.valueOf(digit));
    }

    private void trimSigns(){
        if(expression.length() > 1) {
            String lastChar = String.valueOf(expression.charAt(expression.length() - 1));
            if (strContainsOperands(lastChar))
                expression = expression.substring(0, expression.length() - 1);
        }
    }
}
