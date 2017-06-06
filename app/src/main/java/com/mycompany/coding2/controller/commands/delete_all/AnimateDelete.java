package com.mycompany.coding2.controller.commands.delete_all;

import android.animation.Animator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mycompany.coding2.R;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс предназначен для анимации очистки экрана.
 * Также поддерживает возможность расширения вункционала.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class AnimateDelete implements DeleteDecor {

    private DeleteDecor deleteDecor;
    private AppCompatActivity activity;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param deleteDecor содержит базовые методы
     * @param activity нужен для нахождения элементов на экране и для создания анимации
     */
    public AnimateDelete(DeleteDecor deleteDecor, AppCompatActivity activity){
        this.deleteDecor = deleteDecor;
        this.activity = activity;
    }

    /**
     * Реализует анимацию очистки экрана и поддерживает последующее расширение
     * @return возвращает самого себя
     */
    @Override
    public DeleteDecor delete() {
        String expression = Widgets.getWidgets(activity).getFromNotationTV().getText().toString();
        if(expression.length() > 0)
            new AnimateOutputLayout().execute();
        return this;
    }

    /**
     * Класс содержащий всю работу для реализации анимации
     */
    private class AnimateOutputLayout extends AsyncTask<Void, Integer, Void> {
        private final static int DURATION = 500;

        private Animator animEnter;
        private Animation animExit;
        private View outputLayout;

        /**
         * Инициализирует элемент для анимирования и первую половину анимацию
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            outputLayout = activity.findViewById(R.id.output_layout);
            setEnterAnim();
        }

        private void setEnterAnim(){
            int x = outputLayout.getRight();
            int y = outputLayout.getBottom();
            int startRadius = 0;
            int endRadius = (int) Math.hypot(outputLayout.getWidth(), outputLayout.getHeight());
            animEnter = ViewAnimationUtils.createCircularReveal(outputLayout, x, y, startRadius, endRadius);
            animEnter.setDuration(DURATION);
        }

        private void setExitAnim(){
            animExit = AnimationUtils.loadAnimation(activity, R.anim.alpha_delete);
            animExit.setDuration(DURATION);
            outputLayout.setAnimation(animExit);
        }

        /**
         *  Включает первую половину анимации и устанавливает вторую половину
         */
        @Override
        protected Void doInBackground(Void... params) {
            publishProgress(getColor(R.color.accentColor));
            sleep(DURATION);
            setExitAnim();
            publishProgress(-1);
            sleep(DURATION);
            return null;
        }

        @SuppressWarnings("deprecation")
        private int getColor(@ColorRes int colorRes){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                return activity.getResources().getColor(colorRes, activity.getTheme());
            else
                return activity.getResources().getColor(colorRes);
        }

        private void sleep(int duration){
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ignored) {}
        }

        /**
         * Очищает экран и включает вторую половину анимации
         * @param values цвет фона для анимирования
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0] == -1){
                deleteDecor.delete();
                animExit.start();
            }
            else {
                outputLayout.setBackgroundColor(values[0]);
                animEnter.start();
            }
        }

        /**
         *  Устанавливает элемент в неактивное положение
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outputLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
