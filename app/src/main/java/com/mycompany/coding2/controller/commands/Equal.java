package com.mycompany.coding2.controller.commands;

import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для вывода конечного результата в поле ввода.
 * А также обновляет поля ввода и вывода.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class Equal implements Command {
    private Widgets widgets;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param widgets предоставляет доступ к элементам интерфейса
     *                (в данном случае к полю вывода)
     */
    public Equal(Widgets widgets){
        this.widgets = widgets;
    }

    /**
     * Выводит в поле вывода конечное выражение
     * @param expression конечное выражение
     */
    @Override
    public void execute(String expression) {
        widgets.getToNotationTV().setText(expression);
        widgets.reloadScrollText();
    }
}
