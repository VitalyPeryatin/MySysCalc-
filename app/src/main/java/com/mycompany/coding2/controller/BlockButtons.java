package com.mycompany.coding2.controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Button;

import com.mycompany.coding2.R;
import com.mycompany.coding2.model.viewBox.Widgets;

/**
 * Класс блокирует кнопки, которые не должны быть активны взависимости от системы счисления из
 * которой производится перевод.
 * <p>Каждая кнопка отвечает за отдельное число, и если это число
 * больше или равно системе счисления из которой проиводится перевод, то кнопка с этим числом
 * блкируется. </p>
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class BlockButtons extends AsyncTask<Integer, BlockButtons.ButtonState, Void> {

    private Context context;
    private Widgets widgets;

    /**
     * Конструирует объект и инициализирует необходимые поля
     * @param context предоставляет доступ к ресурсам
     * @param widgets предоставляет доступ к элементам интерфейса
     *                (в данном случае к массиву кнопок)
     */
    public BlockButtons(Context context, Widgets widgets) {
        this.context = context;
        this.widgets = widgets;
    }

    /**
     * Блокируются все кнопки начиная с кнопки, отвечающей за наибольшее число, до
     * системы счисления переданной в параметрах
     * @param notation система счисления из которой производится перевод
     */
    @Override
    protected Void doInBackground(Integer... notation) {
        for(int i = 15; i >= 0; i--) {
            publishProgress(new ButtonState(i, true));
            if (i >= notation[0])
                publishProgress(new ButtonState(i, false));
        }
        return null;
    }

    /**
     * Блокирует кнопку, отвечающую за число переданное в параметрах.
     * Метод содержит базовые настройки для заблокированной кнопки.
     * @param states состояние кнопки
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onProgressUpdate(ButtonState... states) {
        if(states[0].isEnable())
            enableButton(states[0]);
        else
            disableButton(states[0]);
    }

    private void enableButton(ButtonState state){
        int i = state.getIndex();
        Button button = widgets.getButtons()[i];
        button.setEnabled(true);
        button.setBackground(context.getDrawable(R.drawable.number_btn));
        button.setTextColor(Color.WHITE);
    }

    private void disableButton(ButtonState state){
        int i = state.getIndex();
        int colorBackground = R.color.blockedBtnBackground;
        int colorText = R.color.blockedBtnText;
        Resources res = context.getResources();
        Button button = widgets.getButtons()[i];
        widgets.getButtons()[i].setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            button.setBackgroundColor(res.getColor(colorBackground, context.getTheme()));
        else
            button.setBackgroundColor(res.getColor(colorBackground));
        button.setTextColor(res.getColor(colorText));
    }

    class ButtonState{

        private int index;
        private boolean enable;

        public ButtonState(int index, boolean enable) {
            this.index = index;
            this.enable = enable;
        }

        public int getIndex() {
            return index;
        }

        public boolean isEnable() {
            return enable;
        }
    }
}
