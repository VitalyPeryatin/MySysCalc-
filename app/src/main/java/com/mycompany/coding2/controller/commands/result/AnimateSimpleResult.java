package com.mycompany.coding2.controller.commands.result;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mycompany.coding2.R;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для анимации вывод результата на экране.
 * Также поддерживает возможность расширения вункционала.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class AnimateSimpleResult implements SimpleResultDecor {

    private SimpleResultDecor simpleResultDecor;
    private AppCompatActivity activity;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param simpleResultDecor содержит базовые методы
     * @param activity нужен для нахождения элементов на экране и для создания анимации
     */
    public AnimateSimpleResult(SimpleResultDecor simpleResultDecor, AppCompatActivity activity) {
        this.simpleResultDecor = simpleResultDecor;
        this.activity = activity;
    }

    /**
     * Реализует анимацию вывода упрощенного выражения на экран и поддерживает последующее расширение
     * @return возвращает самого себя
     */
    @Override
    public SimpleResultDecor result(String expression) {
        new AnimateFromNotationTV(expression).execute();
        return this;
    }

    /**
     * Класс содержащий всю работу для реализации анимации
     */
    private class AnimateFromNotationTV extends AsyncTask<Void, Animation, Void>{

        private static final int DURATION = 250;
        private String expression;

        /**
         * Инициализирует выражение для вывода на экран
         * @param expression упрощенное выражение
         */
        AnimateFromNotationTV(String expression){
            this.expression = expression;
        }

        /**
         * Включает первую половину анимации -> выводит результат -> включает вторую половину анимации
         */
        @Override
        protected Void doInBackground(Void... params) {
            Animation animStart = AnimationUtils.loadAnimation(activity, R.anim.alpha_result_start);
            publishProgress(animStart);
            sleep(DURATION);
            publishProgress();
            Animation animEnd = AnimationUtils.loadAnimation(activity, R.anim.alpha_result_end);
            publishProgress(animEnd);
            return null;
        }

        /**
         * Реализует передаваемую анимацию и выводит выражение на экран
         * @param animations передаваемая анимация
         */
        @Override
        protected void onProgressUpdate(Animation... animations) {
            super.onProgressUpdate(animations);
            if(animations.length == 0)
                simpleResultDecor.result(expression);
            else
                Widgets.getWidgets(activity).getFromNotationTV().startAnimation(animations[0]);
        }

        private void sleep(int duration){
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ignored) {}
        }
    }
}
