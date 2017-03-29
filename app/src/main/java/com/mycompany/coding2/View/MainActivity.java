package com.mycompany.coding2.View;

import android.content.Intent;
import android.os.*;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.view.View.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import com.mycompany.coding2.Controller.Controller;
import com.mycompany.coding2.Model.*;
import com.mycompany.coding2.R;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnLongClickListener, IDs {
	private TextView fromNotationTV, toNotationTV;
	private Button[] buttons;
	private boolean isSign = true;
	private int fromNotation = 10, toNotation = 2;
	private Spinner fromNotationSpinner, toNotationSpinner;
	private HorizontalScrollView scrollingFrom, scrollingTo;
	private boolean hasComma = false;
    private Toolbar toolbar;
    private Controller controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        controller = new Controller();

        initToolbar();
		initTextFields();
		initNumberPickers();
		initButtons();
	}

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initTextFields(){
		fromNotationTV = (TextView) findViewById(R.id.textFrom);
		toNotationTV = (TextView) findViewById(R.id.textTo);
		fromNotationTV.setMovementMethod(new ScrollingMovementMethod());
		toNotationTV.setMovementMethod(new ScrollingMovementMethod());
		fromNotationTV.setScrollBarSize(0);
		toNotationTV.setScrollBarSize(0);
		fromNotationTV.setHorizontallyScrolling(true);
		toNotationTV.setHorizontallyScrolling(true);

		scrollingFrom = (HorizontalScrollView) findViewById(R.id.scrollingFrom);
		scrollingTo = (HorizontalScrollView) findViewById(R.id.scrollingTo);
	}

	private void initNumberPickers(){
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_title,
				getResources().getStringArray(R.array.systems));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		fromNotationSpinner = (Spinner) findViewById(R.id.spinner_from);
		fromNotationSpinner.setAdapter(adapter);
		fromNotationSpinner.setSelection(fromNotation - 2);
		fromNotationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				fromNotation = Integer.parseInt(parent.getItemAtPosition(position).toString());
				blockButtons(fromNotation);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		toNotationSpinner = (Spinner) findViewById(R.id.spinner_to);
		toNotationSpinner.setAdapter(adapter);
		toNotationSpinner.setSelection(toNotation - 2);
		toNotationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				toNotation = Integer.parseInt(parent.getItemAtPosition(position).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void initButtons() {
		buttons = new Button[]{
				(Button)findViewById(BTN_ID_0),
				(Button)findViewById(BTN_ID_1),
				(Button)findViewById(BTN_ID_2),
				(Button)findViewById(BTN_ID_3),
				(Button)findViewById(BTN_ID_4),
				(Button)findViewById(BTN_ID_5),
				(Button)findViewById(BTN_ID_6),
				(Button)findViewById(BTN_ID_7),
				(Button)findViewById(BTN_ID_8),
				(Button)findViewById(BTN_ID_9),
				(Button)findViewById(BTN_ID_A),
				(Button)findViewById(BTN_ID_B),
				(Button)findViewById(BTN_ID_C),
				(Button)findViewById(BTN_ID_D),
				(Button)findViewById(BTN_ID_E),
				(Button)findViewById(BTN_ID_F),
				(Button)findViewById(BTN_ID_EQUAL),
				(Button)findViewById(BTN_ID_FRAC),
				(Button)findViewById(BTN_ID_DELETE),
				(Button)findViewById(BTN_ID_PLUS),
				(Button)findViewById(BTN_ID_MINUS),
				(Button)findViewById(BTN_ID_DEVIDE),
				(Button)findViewById(BTN_ID_MULTI),
                (Button)findViewById(BTN_ID_SOLUTION)
		};

		for (Button btn : buttons)
			btn.setOnClickListener(this);

		findViewById(BTN_ID_DELETE).setOnLongClickListener(this);

		blockButtons(fromNotation);

		final Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
		final ImageButton btnChange = (ImageButton) findViewById(BTN_ID_CHANGE);
		btnChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnChange.startAnimation(animation);
				int memory = fromNotation;
				fromNotation = toNotation;
				toNotation = memory;
				fromNotationSpinner.setSelection(fromNotation -2);
				toNotationSpinner.setSelection(toNotation -2);
			}
		});
	}

	@Override
	public void onClick(View v) {
		String newExpression;
		if (getText().length() < 50) {
			switch (v.getId()) {
                case BTN_ID_SOLUTION:
                    DataOfSolution solution = controller.getSolution();
                    if(solution!=null) {
                        Intent intent = new Intent(this, SolutionActivity.class);
                        intent.putExtra("solution", solution);
                        startActivity(intent);
                    }
                    break;
				case BTN_ID_FRAC:
					if(!hasComma) {
						hasComma = true;
						newExpression = getText().concat(",");
						fromNotationTV.setText(newExpression);
					}
					break;
				case BTN_ID_PLUS:
				    if(getText().length() == 0)
				        newExpression = getText();
					else if(isSign)
					    newExpression = getText().substring(0, getText().length()-1).concat("+");
					else
					    newExpression = getText().concat("+");
					fromNotationTV.setText(newExpression);
					hasComma = false;
					isSign = true;
					break;
				case BTN_ID_MINUS:
                    if(getText().length() == 0)
                        newExpression = getText();
					else if(isSign)
					    newExpression = getText().substring(0, getText().length()-1).concat("-");
					else
					    newExpression = getText().concat("-");
					fromNotationTV.setText(newExpression);
					hasComma = false;
					isSign = true;
					break;
				case BTN_ID_DEVIDE:
                    if(getText().length() == 0)
                        newExpression = getText();
				    else if(isSign)
					    newExpression = getText().substring(0, getText().length()-1).concat("/");
					else
					    newExpression = getText().concat("/");
					fromNotationTV.setText(newExpression);
					isSign = true;
					hasComma = false;
					break;
				case BTN_ID_MULTI:
                    if(getText().length() == 0)
                        newExpression = getText();
                    else if(isSign)
					    newExpression = getText().substring(0, getText().length()-1).concat("*");
					else
					    newExpression = getText().concat("*");
					fromNotationTV.setText(newExpression);
					hasComma = false;
					isSign = true;
					break;
				case BTN_ID_DELETE:
					delete();
					equal();
					break;
				case BTN_ID_EQUAL:
					equal();
					break;
				default:
					for (int i = 0; i < 16; i++)
						if (buttons[i].getId() == v.getId()) {
							char number = (char) (i < 10 ? i + 48 : i + 55);
							newExpression = getText().concat(String.valueOf(number));
							fromNotationTV.setText(newExpression);
						}
					isSign = false;
					break;
			}

		}

		scrollingFrom.post(new Runnable() {
			@Override
			public void run() {
				scrollingFrom.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		scrollingTo.post(new Runnable() {
			@Override
			public void run() {
				scrollingTo.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		if(!isSign)equal();
	}

	private void delete(){
		int length = getText().length();
		try{
			if(getText().charAt(getText().length()-1)== ',') hasComma = false;
		}catch (Exception ignored) {
			hasComma = false;
		}
		if (length > 0)
			fromNotationTV.setText(getText().substring(0, length - 1));
		try {
			char lastChar = getText().charAt(length - 2);
			if(length == 0 || lastChar == '-' || lastChar == '+' ||
					lastChar == '*' || lastChar == '/')
				isSign = true;
		}
		catch (Exception e){
			isSign = true;
		}
	}

	private void equal() {
        String answer = controller.getAnswer(getText(), fromNotation, toNotation);
        toNotationTV.setText(answer);
	}

	@NonNull
    private String getText(){
		return fromNotationTV.getText().toString();
	}

	@Override
    public boolean onLongClick(View v) {
		switch (v.getId()) {
			case R.id.btnDelete:
				removeAll();
				break;
		}
		return true;
	}

	private void removeAll(){
		fromNotationTV.setText("");
		toNotationTV.setText("");
		fromNotationTV.scrollTo(0, 0);
		toNotationTV.scrollTo(0, 0);
		hasComma = false;
	}

	private void blockButtons(final int from) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				blocker.sendEmptyMessage(from);
			}
		}).start();
	}

	private Handler blocker = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			for (int i = 0; i < 16; i++) {
				buttons[i].setEnabled(true);
                buttons[i].setBackground(getDrawable(R.drawable.number_btn));
            }

			for(int i = 15; i >= msg.what; i--) {
				buttons[i].setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    buttons[i].setBackgroundColor(getResources().getColor(R.color.blockedBtn, getTheme()));
                else
                    buttons[i].setBackgroundColor(getResources().getColor(R.color.blockedBtn));
			}
		}
	};
}
