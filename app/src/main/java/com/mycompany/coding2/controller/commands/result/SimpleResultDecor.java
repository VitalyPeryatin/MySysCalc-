package com.mycompany.coding2.controller.commands.result;

/**
 * Помогает расширять функционал классов, связанных с упрощением выражения
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
interface SimpleResultDecor {

    /**
     * Метод, предназначенный для расщирения функционала
     * @param expression упрощенное выражение
     * @return возвращает самого себя
     */
    SimpleResultDecor result(String expression);
}
