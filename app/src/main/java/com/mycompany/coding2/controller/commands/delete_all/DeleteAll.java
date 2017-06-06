package com.mycompany.coding2.controller.commands.delete_all;

import com.mycompany.coding2.controller.commands.Comma;
import com.mycompany.coding2.controller.commands.Command;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для очистки экрана.
 * Он реализует одну из команд и имеет возможность для расширения, добавляя новые функции.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class DeleteAll implements Command, DeleteDecor {

    private Widgets widgets;

    /**
     * Конструирует объект и инициализирует необходимые поля.
     * @param widgets помогает найти необходимые элементы интерфейса на экране
     *                (в данном случае поле ввода и поле вывода)
     */
    public DeleteAll(Widgets widgets) {
        this.widgets = widgets;
    }

    /**
     * Основной метод для очистки экрана.
     * @param expression не используется
     */
    @Override
    public void execute(String expression) {
        widgets.getFromNotationTV().setText("");
        widgets.getToNotationTV().setText("");
        Comma.setEnabled(false);
    }

    /**
     * Вызывает метод <code>execute</code>.
     * Метод предназначен для расширения функционала.
     * @return возвращает самого себя
     */
    @Override
    public DeleteDecor delete() {
        execute(null);
        return this;
    }
}
