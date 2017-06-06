package com.mycompany.coding2.model.viewBox;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

//import com.mycompany.coding2.R;
import com.mycompany.coding2.R;
import com.mycompany.coding2.model.signs.CustomSigns;
import com.mycompany.coding2.view.Subscriber;

import java.util.ArrayList;
import java.util.List;

import static com.mycompany.coding2.model.viewBox.IDs.*;

/**
 * Создаёт, хранит и предоставляет доступ к основным элементам интерфейса
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class Widgets implements SpinnerObservable {

    @SuppressLint("StaticFieldLeak")
    private static Widgets widgets;

    private List<Subscriber> subscribers = new ArrayList<>();
    private AppCompatActivity activity;
    private TextView fromNotationTV, toNotationTV;
    private Button[] buttons;
    private ImageButton btnChange;
    private Spinner fromNotationSpinner, toNotationSpinner;
    private HorizontalScrollView fromScrolling, toScrolling;

    private Widgets(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * @param activity активность, в которой хранятся элементы
     * @return элементы интерфейса
     */
    public static Widgets getWidgets(AppCompatActivity activity){
        if(widgets == null)
            widgets = new Widgets(activity)
                    .setToolbar()
                    .setTextFields()
                    .setSpinners()
                    .setButtons();
        return widgets;
    }

    /**
     * Оповещает Spinner`ы
     * @param spinner конкретный spinner
     * @param notation система счсления
     */
    @Override
    public void notifySpinner(Spinner spinner, int notation) {
        int fromNotation = 0, toNotation = 0;
        if(spinner == fromNotationSpinner)
            fromNotation = notation;
        else if(spinner == toNotationSpinner)
            toNotation = notation;
        for(Subscriber subscriber : subscribers)
            subscriber.setNotations(fromNotation, toNotation);
    }

    /**
     * Добавляет подписчика на обновления
     * @param subscriber подписчик
     */
    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Обновляет поля ввода, сдвигая текст до последнего введенного символа
     */
    public void reloadScrollText(){
        reloadScrollText(fromScrolling);
        reloadScrollText(toScrolling);
    }

    /**
     * @return TextView содержащий выражение, которое требуется перевести
     */
    public TextView getFromNotationTV() {
        return fromNotationTV;
    }

    /**
     * @return TextView содержащий выражение, переведенное в другую систему счисления
     */
    public TextView getToNotationTV() {
        return toNotationTV;
    }

    /**
     * @return Spinner содержащий систему счисления из которой переводят
     */
    public Spinner getFromNotationSpinner(){
        return fromNotationSpinner;
    }

    /**
     * @return Spinner содержащий систему счисления в которую требуется перевести
     */
    public Spinner getToNotationSpinner(){
        return toNotationSpinner;
    }

    /**
     * @return массив кнопок
     */
    public Button[] getButtons() {
        return buttons;
    }

    /**
     * @return кнопка для смены систем счислений
     */
    public ImageButton getReverseButton(){
        return btnChange;
    }

    private Widgets setToolbar() {
        initToolbar(TOOLBAR);
        return this;
    }

    private Widgets setTextFields(){
        fromNotationTV = initTextView(TEXT_FROM);
        fromScrolling = initScrollView(SCROLL_FROM);
        toNotationTV = initTextView(TEXT_TO);
        toScrolling = initScrollView(SCROLL_TO);
        return this;
    }

    private Widgets setSpinners(){
        fromNotationSpinner = initNumberPickers(SPINNER_FROM, 10);
        toNotationSpinner = initNumberPickers(SPINNER_TO, 2);
        return this;
    }

    private Widgets setButtons(){
        initButtonsArray();
        setOperandButtons();
        initReverseButton();
        initButtonListeners();
        return this;
    }

    private void setOperandButtons() {
        ((Button)activity.findViewById(BTN_ID_FRAC)).setText(String.valueOf(CustomSigns.COMMA));
        ((Button)activity.findViewById(BTN_ID_PLUS)).setText(String.valueOf(CustomSigns.PLUS));
        ((Button)activity.findViewById(BTN_ID_MINUS)).setText(String.valueOf(CustomSigns.MINUS));
        ((Button)activity.findViewById(BTN_ID_MULTI)).setText(String.valueOf(CustomSigns.MULTI));
        ((Button)activity.findViewById(BTN_ID_DIVIDE)).setText(String.valueOf(CustomSigns.DIVIDE));
    }

    private void initButtonsArray() {
        buttons = new Button[]{
                (Button)activity.findViewById(BTN_ID_0),
                (Button)activity.findViewById(BTN_ID_1),
                (Button)activity.findViewById(BTN_ID_2),
                (Button)activity.findViewById(BTN_ID_3),
                (Button)activity.findViewById(BTN_ID_4),
                (Button)activity.findViewById(BTN_ID_5),
                (Button)activity.findViewById(BTN_ID_6),
                (Button)activity.findViewById(BTN_ID_7),
                (Button)activity.findViewById(BTN_ID_8),
                (Button)activity.findViewById(BTN_ID_9),
                (Button)activity.findViewById(BTN_ID_A),
                (Button)activity.findViewById(BTN_ID_B),
                (Button)activity.findViewById(BTN_ID_C),
                (Button)activity.findViewById(BTN_ID_D),
                (Button)activity.findViewById(BTN_ID_E),
                (Button)activity.findViewById(BTN_ID_F),
                (Button)activity.findViewById(BTN_ID_EQUAL),
                (Button)activity.findViewById(BTN_ID_FRAC),
                (Button)activity.findViewById(BTN_ID_DELETE),
                (Button)activity.findViewById(BTN_ID_PLUS),
                (Button)activity.findViewById(BTN_ID_MINUS),
                (Button)activity.findViewById(BTN_ID_DIVIDE),
                (Button)activity.findViewById(BTN_ID_MULTI)
                //(Button)findViewById(BTN_ID_SOLUTION)
        };
    }

    private void initReverseButton(){
        btnChange = (ImageButton) activity.findViewById(BTN_ID_CHANGE);
    }

    private void initButtonListeners(){
        for (Button btn : buttons)
            btn.setOnClickListener((View.OnClickListener) activity);

        btnChange.setOnClickListener((View.OnClickListener)activity);

        activity.findViewById(BTN_ID_DELETE).setOnLongClickListener(
                (View.OnLongClickListener)activity);
    }

    private void initToolbar(@IdRes int resId){
        Toolbar toolbar = (Toolbar) activity.findViewById(resId);
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
    }

    private TextView initTextView(@IdRes int resId){
        TextView textView;
        textView = (TextView) activity.findViewById(resId);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setScrollBarSize(0);
        textView.setHorizontallyScrolling(true);
        return textView;
    }

    private HorizontalScrollView initScrollView(@IdRes int resId) {
        return (HorizontalScrollView) activity.findViewById(resId);
    }

    private Spinner initNumberPickers(@IdRes int resId, final int notation){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.layout.spinner_title,
                activity.getResources().getStringArray(R.array.systems));
        adapter.setDropDownViewResource(R.layout.spinner_list);

        final Spinner spinner = (Spinner) activity.findViewById(resId);
        spinner.setAdapter(adapter);
        spinner.setSelection(notation - 2);// Отнимает два, так как системы начинаются с 2, а id с 0
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int notation = Integer.parseInt(parent.getItemAtPosition(position).toString());
                notifySpinner(spinner, notation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return spinner;
    }

    private void reloadScrollText(final HorizontalScrollView scrollView){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }
}
