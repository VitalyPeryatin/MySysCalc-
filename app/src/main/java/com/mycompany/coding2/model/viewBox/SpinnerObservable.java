package com.mycompany.coding2.model.viewBox;

import android.widget.Spinner;
import com.mycompany.coding2.view.Subscriber;

/**
 * Поставляет изменения в Spinner`ы
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
interface SpinnerObservable {

    /**
     * Оповещает Spinner`ы
     * @param spinner конкретный spinner
     * @param notation система счсления
     */
    void notifySpinner(Spinner spinner, int notation);

    /**
     * Добавляет подписчика на обновления
     * @param subscriber подписчик
     */
    void addSubscriber(Subscriber subscriber);
}
