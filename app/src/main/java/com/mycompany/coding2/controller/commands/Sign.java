package com.mycompany.coding2.controller.commands;

import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для добавления операнда к существующему выражению.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class Sign implements Command {
    private Widgets widgets;
    private String sign;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param widgets предоставляет доступ к элементам интерфейса
     *                (в данном случае к полю ввода)
     */
    public Sign(Widgets widgets){
        this.widgets = widgets;
    }

    private boolean isValidSign(char sign){
            char[] signs = {CustomSigns.PLUS, CustomSigns.MINUS, CustomSigns.MULTI, CustomSigns.DIVIDE};
            for (char s : signs)
                if (sign == s)
                    return true;
        return false;
    }

    /**
     * Добавляет операнд к существующему выражению, контроллируя наличие запятой и её доступ.
     * При наличии операнда на конце, суествующий операнд заменяется новый.
     * @param expression выражение
     */
    @Override
    public void execute(String expression) {
        if(expression.length() > 0) {
            char lastChar = expression.charAt(expression.length()-1);
            if(lastChar == CustomSigns.COMMA)
                return;
            else if (isValidSign(lastChar))
                expression = expression.substring(0, expression.length() - 1).concat(sign);
            else
                expression = expression.concat(sign);
            Comma.setEnabled(false);
        }
        widgets.getFromNotationTV().setText(expression);
    }

    /**
     * Устанавливает необходимый операнд
     * @param sign переданный операнд для добавления
     */
    public void setSign(String sign){
        if(sign.length() == 1 && isValidSign(sign.charAt(0)))
            this.sign = sign;
    }
}
