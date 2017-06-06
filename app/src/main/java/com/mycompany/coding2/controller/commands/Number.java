package com.mycompany.coding2.controller.commands;

import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для добавления символов ввода нажатых кнопок к существующему выражению.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class Number implements Command {

    private Widgets widgets;
    private int btnId;
    private static final int NUMBER_AFTER_COMMA_LIMIT = 5;
    private int numberAfterComma = 0;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param widgets предоставляет доступ к элементам интерфейса
     *                (в данном случае к полю ввода)
     */
    public Number(Widgets widgets){
        this.widgets = widgets;
    }


    /**
     * Устанавливает id нажатой кнопки
     * @param btnId id нажатой кнопки
     */
    public void setBtnId(int btnId) {
        this.btnId = btnId;
    }

    /**
     * Добавляет символ нажатой кнопки к выражению
     * @param expression выражение
     */
    @Override
    public void execute(String expression) {
        for (int i = 0; i < 16; i++)
            if (widgets.getButtons()[i].getId() == btnId) {
                char number = (char) (i < 10 ? i + 48 : i + 55);

                if(Comma.isHasComma()) numberAfterComma++;
                else numberAfterComma = 0;

                if(numberAfterComma <= NUMBER_AFTER_COMMA_LIMIT)
                    widgets.getFromNotationTV().setText(expression.concat(String.valueOf(number)));
            }
    }
}
