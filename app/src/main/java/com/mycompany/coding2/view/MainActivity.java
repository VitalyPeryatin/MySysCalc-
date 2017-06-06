package com.mycompany.coding2.view;

import android.content.Intent;
import android.net.Uri;
import android.os.*;

import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.*;
import android.view.View.*;
import android.widget.Button;

import com.mycompany.coding2.controller.BlockButtons;
import com.mycompany.coding2.controller.ButtonCommand;
import com.mycompany.coding2.controller.commands.Comma;
import com.mycompany.coding2.controller.commands.DeleteOne;
import com.mycompany.coding2.controller.commands.Equal;
import com.mycompany.coding2.controller.commands.Number;
import com.mycompany.coding2.controller.commands.Sign;
import com.mycompany.coding2.controller.commands.change_notation.ChangeNotation;
import com.mycompany.coding2.controller.commands.delete_all.DeleteAll;
import com.mycompany.coding2.controller.commands.result.SimpleResult;
import com.mycompany.coding2.model.NumSysInfo;
import com.mycompany.coding2.R;
import com.mycompany.coding2.model.viewBox.Widgets;

import android.view.View.OnClickListener;

import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_CHANGE;
import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_DELETE;
import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_DIVIDE;
import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_EQUAL;
import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_FRAC;
import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_MINUS;
import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_MULTI;
import static com.mycompany.coding2.model.viewBox.IDs.BTN_ID_PLUS;

/**
 * Отображает основной экран и предоставляет пользователю управление системой
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class MainActivity extends AppCompatActivity implements OnClickListener, OnLongClickListener, Subscriber {

	private ButtonCommand command;
	private static final String playMarketAddress =
			"https://play.google.com/store/apps/details?id=com.mycompany.coding2&hl=ru";

    private int fromNotation = 10, toNotation = 2;
	AsyncTask blockButtons;


	/**
	 * Создаёт activity, элемены интерфейса и инициализирует команды управления
	 * @param savedInstanceState не используется
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Widgets widgets = Widgets.getWidgets(this);
		widgets.addSubscriber(this);

		command = new ButtonCommand(
				this,
				new Sign(widgets),
				new Number(widgets),
				new Comma(widgets),
				new DeleteOne(widgets),
				new DeleteAll(widgets),
				new SimpleResult(widgets),
				new Equal(widgets),
				new ChangeNotation(widgets, getNumSysInfo()));

	}

	/**
	 * Создаёт меню
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Обрабатывает клики по пунктам меню
	 * @param item пункт меню
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.animate_permission:
				if(item.isCheckable()) {
					item.setChecked(!item.isChecked());
					command.setAnimation(item.isChecked());
				}
				break;
			case R.id.send_review:
				Uri uri = Uri.parse(playMarketAddress);
				Intent intentView = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intentView);
				break;
			case R.id.write_developer:
				Intent intentSend = new Intent(Intent.ACTION_SEND);
				intentSend.setType("plain/text");
				intentSend.putExtra(Intent.EXTRA_EMAIL, new String[]{"peryatin.vitalik37@gmail.com"});
				intentSend.putExtra(Intent.EXTRA_SUBJECT, "Приложение \"Калькулятор Систем Счисления\"");
				intentSend.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(intentSend);
				break;
		}
		return true;
	}

	/**
	 * Обрабатывает клики по кнопкам и отдаёт комманды объекту command, который выполняет команду.
	 * @param v нажатый элемент
	 */
	@Override
	public void onClick(View v) {
		String expression = Widgets.getWidgets(this).getFromNotationTV().getText().toString();
		command.updateExpression(expression);
			switch (v.getId()) {
                //case BTN_ID_SOLUTION:
                //    break;
				case BTN_ID_CHANGE:
					command.changeNotation();
					break;
				case BTN_ID_FRAC:
					command.addComma();
					break;
				case BTN_ID_PLUS:
				case BTN_ID_MINUS:
				case BTN_ID_DIVIDE:
				case BTN_ID_MULTI:
					command.addSign(((Button)v).getText().toString());
					break;
				case BTN_ID_DELETE:
					command.deleteOne();
					break;
				case BTN_ID_EQUAL:
					command.simplify();
					break;
				default:
					command.addNumber((Button) v);
					break;
			}
	}

	/**
	 * Обрабатывает долгие клики по кнопкам и отдаёт комманды объекту command, который выполняет команду.
	 * @param v долго нажатый элемент
	 */
	@Override
    public boolean onLongClick(View v) {
		switch (v.getId()) {
			case R.id.btnDelete:
				command.deleteAll();
				break;
		}
		return true;
	}

	/**
	 * Слушатель изменения систем счисления
	 * @param fromNotation система счислений, из которой переводят
	 * @param toNotation систем счислений, в которую переводят
	 */
	@Override
	public void setNotations(int fromNotation, int toNotation) {
		if(fromNotation >= 2) {
			this.fromNotation = fromNotation;
			blockButtons(fromNotation);
		}
		if(toNotation >= 2)
			this.toNotation = toNotation;
		command.equal();
	}

	/**
	 * @return объект с основной информацией о текущем выражении и системах счислений
	 */
	public NumSysInfo getNumSysInfo(){
		return new NumSysInfo(Widgets.getWidgets(this).getFromNotationTV().getText().toString(),
				fromNotation, toNotation);
	}

	private void blockButtons(int fromNotation){
		if(blockButtons != null)
			blockButtons.cancel(true);
		blockButtons = new BlockButtons(this, Widgets.getWidgets(this)).execute(fromNotation);
	}
}
