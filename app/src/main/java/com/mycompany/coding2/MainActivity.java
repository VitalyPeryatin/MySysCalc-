package com.mycompany.coding2;

import android.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.NumberPicker.*;
import com.mycompany.coding2.Model.*;

import android.view.View.OnClickListener;
import android.graphics.*;
import android.graphics.drawable.*;
import android.widget.AdapterView.*;

public class MainActivity extends Activity implements OnClickListener, OnValueChangeListener, OnLongClickListener
{

	@Override
	public boolean onLongClick(View p1)
	{
		textFrom.setText("");
		equal();
		return true;
	}

	public static  Toast toast;
	NumberPicker numPickFrom, numPickTo;
	EditText textFrom, textTo;
	Button[] btns;
	MenuItem auto;
	Button btnEqual, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, 
	btnA, btnB, btnC, btnD, btnE, btnF, btnDel;
	int fromSys = 10, toSys = 2;
	String from = "0";
	ConverterSystem converterSystem;
	@Override
	public void onClick(View p1)
	{
		StringBuilder getText = new StringBuilder(textFrom.getText().toString());
		if (getText.length() < 7)
		{
			switch (p1.getId())
			{

				case R.id.btn0:
					textFrom.setText(getText + "0");
					break;
				case R.id.btn1:
					textFrom.setText(getText + "1");
					break;
				case R.id.btn2:
					textFrom.setText(getText + "2");
					break;
				case R.id.btn3:
					textFrom.setText(getText + "3");
					break;
				case R.id.btn4:
					textFrom.setText(getText + "4");
					break;
				case R.id.btn5:
					textFrom.setText(getText + "5");
					break;
				case R.id.btn6:
					textFrom.setText(getText + "6");
					break;
				case R.id.btn7:
					textFrom.setText(getText + "7");
					break;
				case R.id.btn8:
					textFrom.setText(getText + "8");
					break;
				case R.id.btn9:
					textFrom.setText(getText + "9");
					break;
				case R.id.btnA:
					textFrom.setText(getText + "A");
					break;
				case R.id.btnB:
					textFrom.setText(getText + "B");
					break;
				case R.id.btnC:
					textFrom.setText(getText + "C");
					break;
				case R.id.btnD:
					textFrom.setText(getText + "D");
					break;
				case R.id.btnE:
					textFrom.setText(getText + "E");
					break;
				case R.id.btnF:
					textFrom.setText(getText + "F");
					break;

			}
		}
		switch (p1.getId())
		{
			case R.id.btnEqual:
				equal();
				break;
			case R.id.btnDelete:
				if (getText.length() > 0)
				{
					textFrom.setText(getText.toString().substring(0, getText.length() - 1));
				}
				break;
		}
		if (auto.isChecked())
		{
			equal();
		}

	}

	void equal()
	{
		from = textFrom.getText().toString();
		converterSystem = new ConverterSystem(from, fromSys, toSys);
		textTo.setText(String.valueOf(converterSystem.to).toString());
	}
	@Override
	public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal)
	{
		switch (numberPicker.getId())
		{
			case R.id.numPickFrom:
				fromSys = newVal;
				blockButtons(fromSys);
				break;
			case R.id.numPickTo:
				toSys = newVal;
				break;
		}
		if (auto.isChecked())
		{
			equal();
		}
	}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		toast = Toast.makeText(this, "Невозможный символ", Toast.LENGTH_SHORT);
		numPickFrom = (NumberPicker)findViewById(R.id.numPickFrom);
		numPickTo = (NumberPicker)findViewById(R.id.numPickTo);
		textFrom = (EditText)findViewById(R.id.textFrom);
		textTo = (EditText)findViewById(R.id.textTo);
		spotButtons();
		numPickFrom.setMaxValue(16);
		numPickFrom.setMinValue(2);
		numPickFrom.setValue(10);
		numPickFrom.setWrapSelectorWheel(false);
		numPickTo.setMaxValue(16);
		numPickTo.setMinValue(2);
		numPickTo.setValue(2);
		numPickTo.setWrapSelectorWheel(false);
		numPickFrom.setOnValueChangedListener(this);
		numPickTo.setOnValueChangedListener(this);
		blockButtons(fromSys);
		
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#AFEEEE")));
		bar.setLogo(R.drawable.image);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		auto =  menu.add(0, 1, 0, "Автоперевод");
		auto.setCheckable(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case 1:
				if (auto.isChecked())
					auto.setChecked(false);
				else
					auto.setChecked(true);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	void blockButtons(int from)
	{
		btns = new Button[]{btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnA, btnB, btnC, btnD, btnE, btnF};
		doEnable(btns);
		switch (from)
		{
			case 2:
				btn2.setEnabled(false);
			case 3:
				btn3.setEnabled(false);
			case 4:
				btn4.setEnabled(false);
			case 5:
				btn5.setEnabled(false);
			case 6:
				btn6.setEnabled(false);
			case 7:
				btn7.setEnabled(false);
			case 8:
				btn8.setEnabled(false);
			case 9:
				btn9.setEnabled(false);
			case 10:
				btnA.setEnabled(false);
			case 11:
				btnB.setEnabled(false);
			case 12:
				btnC.setEnabled(false);
			case 13:
				btnD.setEnabled(false);
			case 14:
				btnE.setEnabled(false);
			case 15:
				btnF.setEnabled(false);
		}
		btnColorDisenable(btns);
	}
	private void doEnable(Button[] btns)
	{
		for (Button btn:btns)
		{
			btn.setEnabled(true);
			btn.setBackgroundColor(Color.parseColor("#434343"));
		}
	}
	private void btnColorDisenable(Button[] btns)
	{
		for (Button btn:btns)
		{
			if (!btn.isEnabled())
			{
				btn.setBackgroundColor(Color.parseColor("#505050"));
			}
		}
	}
	private void spotButtons()
	{
		btnEqual = (Button)findViewById(R.id.btnEqual);
		btnEqual.setOnClickListener(this);
		btn0 = (Button)findViewById(R.id.btn0);
		btn0.setOnClickListener(this);
		btn1 = (Button)findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button)findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		btn3 = (Button)findViewById(R.id.btn3);
		btn3.setOnClickListener(this);
		btn4 = (Button)findViewById(R.id.btn4);
		btn4.setOnClickListener(this);
		btn5 = (Button)findViewById(R.id.btn5);
		btn5.setOnClickListener(this);
		btn6 = (Button)findViewById(R.id.btn6);
		btn6.setOnClickListener(this);
		btn7 = (Button)findViewById(R.id.btn7);
		btn7.setOnClickListener(this);
		btn8 = (Button)findViewById(R.id.btn8);
		btn8.setOnClickListener(this);
		btn9 = (Button)findViewById(R.id.btn9);
		btn9.setOnClickListener(this);
		btnA = (Button)findViewById(R.id.btnA);
		btnA.setOnClickListener(this);
		btnB = (Button)findViewById(R.id.btnB);
		btnB.setOnClickListener(this);
		btnC = (Button)findViewById(R.id.btnC);
		btnC.setOnClickListener(this);
		btnD = (Button)findViewById(R.id.btnD);
		btnD.setOnClickListener(this);
		btnE = (Button)findViewById(R.id.btnE);
		btnE.setOnClickListener(this);
		btnF = (Button)findViewById(R.id.btnF);
		btnF.setOnClickListener(this);
		btnDel = (Button)findViewById(R.id.btnDelete);
		btnDel.setOnClickListener(this);
		btnDel.setOnLongClickListener(this);
	}
}
