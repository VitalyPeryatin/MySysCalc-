package com.mycompany.coding2.controller.commands;

import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для удаления последнего символа. Он также следит за удалением запятой и
 * контроллирует доступ к постановке запятой.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class DeleteOne implements Command {

    private Widgets widgets;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param widgets предоставляет доступ к элементам интерфейса
     *                (в данном случае к полю ввода)
     */
    public DeleteOne(Widgets widgets){
        this.widgets = widgets;
    }

    /**
     * Удаляет один последний знак в выражении, контроллируя удаление запятой
     * @param expression выражение
     */
    @Override
    public void execute(String expression) {
        if (expression.length() > 0) {
            checkComma(expression);
            widgets.getFromNotationTV().setText(expression.substring(0, expression.length() - 1));
        }
    }

    private void checkComma(String expression){
        if(expression.charAt(expression.length() - 1) == CustomSigns.COMMA)
            Comma.setEnabled(false);
    }
}
