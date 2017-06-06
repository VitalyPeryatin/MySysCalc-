package com.mycompany.coding2.controller.commands.change_notation;

import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mycompany.coding2.R;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс расширяет функционал класса для смены систем счислений.
 * Он добавляет анимацию к кнопке для смены систем после её нажатия.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class AnimateChangeNotation implements ChangeNotationDecor {

    private ChangeNotationDecor changeNotationDecor;
    private AppCompatActivity activity;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param changeNotationDecor предоставляет базовые методы
     * @param activity неоходим для получения виджетов и создания анимации
     */
    public AnimateChangeNotation(ChangeNotationDecor changeNotationDecor, AppCompatActivity activity) {
        this.changeNotationDecor = changeNotationDecor;
        this.activity = activity;
    }

    /**
     * Добавляет анимацию к кнопке смены систем счислений.
     * Метод позволяет расширять функционал.
     * @return возвращает самого себя
     */
    @Override
    public ChangeNotationDecor change() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.rotate);
        Widgets.getWidgets(activity).getReverseButton().startAnimation(animation);
        changeNotationDecor.change();
        return this;
    }
}
