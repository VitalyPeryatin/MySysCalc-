package com.mycompany.coding2.controller.commands;

import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для постановки запятой. Он не ставит запятую, учитывая ситуации,
 * когда на экране ничего нет, поставлен операнд или в числе уже имеется одна запятая.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class Comma implements Command {
    private static boolean hasComma = false;
    private Widgets widgets;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param widgets предоставляет доступ к элементам интерфейса
     *                (в данном случае к полю ввода)
     */
    public Comma(Widgets widgets){
        this.widgets = widgets;
    }

    /**
     * Устанавливает возможность постановки запятой
     * @param enabled возможна ли постановка запятой
     */
    public static void setEnabled(boolean enabled){
        Comma.hasComma = enabled;
    }

    /**
     * При необходимости добавляет запятую к существующему выражению
     * @param expression выражение записанное в поле ввода
     */
    @Override
    public void execute(String expression) {
        char lastChar = ' ';
        if(expression.length() > 0)
            lastChar = expression.charAt(expression.length()-1);
        if(isNumber(lastChar) && !hasComma) {
            hasComma = true;
            widgets.getFromNotationTV().setText(
                    expression.concat(String.valueOf(CustomSigns.COMMA)));
        }
    }

    static boolean isHasComma() {
        return hasComma;
    }

    private boolean isNumber(char c){
        return c >= '0' && c <= '9' || c >= 'A' && c <= 'F';
    }
}
