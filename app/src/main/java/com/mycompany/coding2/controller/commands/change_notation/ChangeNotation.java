package com.mycompany.coding2.controller.commands.change_notation;

import com.mycompany.coding2.controller.commands.Command;
import com.mycompany.coding2.model.NumSysInfo;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для смены систем счисления. При их смене также оповещаются Spinner`ы,
 * хранящие данные о системе счсления.
 * Он реализует одну из команд и имеет возможность для расширения, добавляя новые функции.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class ChangeNotation implements Command, ChangeNotationDecor {
    private Widgets widgets;
    private NumSysInfo info;

    /**
     * Конструирует класс, предоставляя ему необходимые данные для смены систем счислений
     * @param widgets помогает найти необходимые элементы интерфейса на экране
     *                (в данном случае Spinner`ы)
     * @param info предоставляет данные о выражении и активных системах счислений
     */
    public ChangeNotation(Widgets widgets, NumSysInfo info) {
        this.widgets = widgets;
        this.info = info;
    }

    /**
     * Основной метод по выполнению смены систем счисления и оповещанию Spinner`ов
     * @param expression не используется
     */
    @Override
    public void execute(String expression) {

        int toNotation = widgets.getToNotationSpinner().getSelectedItemPosition();
        int fromNotation = widgets.getFromNotationSpinner().getSelectedItemPosition();
        widgets.getFromNotationSpinner().setSelection(toNotation);
        widgets.getToNotationSpinner().setSelection(fromNotation);
    }

    /**
     * Вызывает метод <code>execute</code>
     * <p>Необходим для расширения функцинала</p>
     * @return возвращает самого себя
     */
    @Override
    public ChangeNotationDecor change() {
        execute(null);
        return this;
    }
}
