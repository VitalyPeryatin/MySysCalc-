package com.mycompany.coding2.controller.commands.result;

import android.util.Log;

import com.mycompany.coding2.controller.commands.Comma;
import com.mycompany.coding2.controller.commands.Command;
import com.mycompany.coding2.model.converters.ConvertToValid;
import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен вывода упрощенного ыражения на экран.
 * Он реализует одну из команд и имеет возможность для расширения, добавляя новые функции.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class SimpleResult implements Command, SimpleResultDecor {

    private Widgets widgets;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param widgets помогает найти необходимые элементы интерфейса на экране
     *                (в данном случае поле ввода)
     */
    public SimpleResult(Widgets widgets) {
        this.widgets = widgets;
    }

    /**
     * Основной метод дял вывода упрощенного выражения
     * @param expression упрощенное выражение
     */
    @Override
    public void execute(String expression) {
        Comma.setEnabled(expression.contains(String.valueOf(CustomSigns.COMMA)));
        widgets.getFromNotationTV().setText(expression);
    }

    /**
     * Вызывает метод <code>execute</code>.
     * Предназначен для расширения функционала.
     * @param expression упрощенное выражение
     * @return самого себя
     */
    @Override
    public SimpleResultDecor result(String expression) {
        execute(expression);
        return this;
    }
}
