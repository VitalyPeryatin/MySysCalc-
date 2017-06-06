package com.mycompany.coding2.controller.commands;

/**
 * Интерфейс реализует все команды, которые обрабатываются подклассами
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public interface Command {

    /**
     * Обрабатывает каждую конкретную команду
     * @param expression выражение
     */
    void execute(String expression);
}
