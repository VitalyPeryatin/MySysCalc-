package com.mycompany.coding2.controller.commands.change_notation;

/**
 * Помогает расширять функционал классов, связанных со сменой систем счисления
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
interface ChangeNotationDecor {

    /**
     * Метод, предназначенный для расщирения функционала
     * @return возвращает самого себя
     */
    ChangeNotationDecor change();
}
