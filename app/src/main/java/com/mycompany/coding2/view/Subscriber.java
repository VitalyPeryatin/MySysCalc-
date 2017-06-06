package com.mycompany.coding2.view;

/**
 * Подписчик для обновления Spinner`ов
 * @author Vitaly
 * @version 1.0
 */

public interface Subscriber {
    void setNotations(int fromNotation, int toNotation);
}
