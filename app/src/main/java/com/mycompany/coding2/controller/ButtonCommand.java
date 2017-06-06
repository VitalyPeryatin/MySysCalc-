package com.mycompany.coding2.controller;

import android.widget.Button;

import com.mycompany.coding2.controller.commands.change_notation.ChangeNotation;
import com.mycompany.coding2.controller.commands.Equal;
import com.mycompany.coding2.controller.commands.result.AnimateSimpleResult;
import com.mycompany.coding2.controller.commands.result.SimpleResult;
import com.mycompany.coding2.controller.commands.change_notation.AnimateChangeNotation;
import com.mycompany.coding2.controller.commands.delete_all.AnimateDelete;
import com.mycompany.coding2.controller.commands.Comma;
import com.mycompany.coding2.controller.commands.delete_all.DeleteAll;
import com.mycompany.coding2.controller.commands.DeleteOne;
import com.mycompany.coding2.controller.commands.Number;
import com.mycompany.coding2.controller.commands.Sign;
import com.mycompany.coding2.model.NumSysInfo;
import com.mycompany.coding2.model.converters.ConvertToValid;
import com.mycompany.coding2.model.converters.ConverterNotation;
import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.view.MainActivity;

/**
 * Класс содержит в себе все необходимы команды, которые передают кнопки.
 * Он руководит всеми командами, добавляя при необходимости какой-либо функционал.
 * Предоставляет простой доступ ко всем командам интерфейса.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class ButtonCommand {
    private Sign signCom;
    private Number numCom;
    private Comma comma;
    private DeleteOne deleteOne;
    private DeleteAll deleteAll;
    private SimpleResult simpleResult;
    private Equal equal;
    private ChangeNotation change;

    private MainActivity activity;
    private String expression;
    private boolean isAnimate = true;

    public ButtonCommand(MainActivity activity, Sign signCom, Number numCom,
                         Comma comma, DeleteOne deleteOne, DeleteAll deleteAll,
                         SimpleResult simpleResult, Equal equal, ChangeNotation change){
        this.activity = activity;
        this.signCom = signCom;
        this.numCom = numCom;
        this.comma = comma;
        this.deleteOne = deleteOne;
        this.deleteAll = deleteAll;
        this.simpleResult = simpleResult;
        this.equal = equal;
        this.change = change;
    }

    /**
     * Устанавливает флажок для анимации и предоставляет доступ для изменения возможности анимации
     * @param enabled возможность анимации
     */
    public void setAnimation(boolean enabled){
        isAnimate = enabled;
    }

    /**
     * Обновляет существующее выражение
     * @param expression выражение
     */
    public void updateExpression(String expression) {
        this.expression = expression;
    }

    /**
     * Добавляет новый операнд к выражению
     * @param operand операнд
     */
    public void addSign(String operand){
        signCom.setSign(operand);
        signCom.execute(expression);
        equal();
    }

    /**
     * Очищает весь экран
     */
    public void deleteAll(){
        if(isAnimate)
            new AnimateDelete(deleteAll, activity).delete();
        else
            deleteAll.delete();
    }

    /**
     * Меняет систему счисления
     */
    public void changeNotation(){
        if(isAnimate)
            new AnimateChangeNotation(change, activity).change();
        else
            change.change();
    }

    /**
     * Добавляет число к существующему выражению
     * @param button нажатая кнопка, содержащя число
     */
    public void addNumber(Button button){
        numCom.setBtnId(button.getId());
        numCom.execute(expression);
        equal();
    }

    /**
     * Добавляет запятую
     */
    public void addComma(){
        comma.execute(expression);
    }

    /**
     * Удаляет один символ
     */
    public void deleteOne(){
        deleteOne.execute(expression);
        equal();
    }

    /**
     * Выводиит конечный результат
     */
    public void equal(){
        String answer = getAnswer(activity.getNumSysInfo());
        if(answer.equals(ConverterNotation.error)) handleError();
        else equal.execute(answer);
    }

    /**
     * Упрощает введенное выражение
     */
    public void simplify(){
        NumSysInfo info = activity.getNumSysInfo();
        String expression = info.getExpression();
        String answer = simplifyExpression();
        info = new NumSysInfo(answer, info.getFromNotation(), info.getToNotation());
        ConvertToValid convertToValid = new ConvertToValid(activity.getResources(), info, null);
        if(convertToValid.checkValidity() == null && hasSign(expression)) {
            if (isAnimate)
                new AnimateSimpleResult(simpleResult, activity).result(answer);
            else
                simpleResult.result(answer);
        }
    }

    private String simplifyExpression(){
        NumSysInfo info = activity.getNumSysInfo();
        String expression = info.getExpression();
        String answer = getAnswer(info);
        info = new NumSysInfo(answer, info.getToNotation(), info.getFromNotation());
        return  getAnswer(info) + getLastSign(expression);
    }

    private String getAnswer(NumSysInfo info){
        return new ConverterNotation(activity.getResources(), info).convert();
    }

    private boolean hasSign(String s){
        if(s.length() > 1) {
            s = s.substring(1, s.length() - 1);
            String plus = String.valueOf(CustomSigns.PLUS);
            String minus = String.valueOf(CustomSigns.MINUS);
            String multi = String.valueOf(CustomSigns.MULTI);
            String divide = String.valueOf(CustomSigns.DIVIDE);
            return s.contains(plus) || s.contains(minus) || s.contains(multi) || s.contains(divide);
        }
        else return false;
    }

    private String getLastSign(String s){
        String sign = "";
        if(s.length() > 0) {
            sign = s.substring(s.length() - 1);
            if (sign.charAt(0) != CustomSigns.PLUS && sign.charAt(0) != CustomSigns.MINUS &&
                    sign.charAt(0) != CustomSigns.MULTI && sign.charAt(0) != CustomSigns.DIVIDE)
                sign = "";
        }
        return sign;
    }

    private void handleError(){
        boolean memory = isAnimate;
        isAnimate = false;
        deleteAll();
        isAnimate = memory;
        equal();
    }
}
