package com.mycompany.coding2.Model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mycompany.coding2.R;

/**
 * Переводит числа в различные системы счисления
 * @author Vitaly
 * @version 1.0
 */
public class ConverterNotation {

	private String expression, oldExpression;
	private int fromNotation, toNotation;
	private int amountOfSymbolsAfterComma;
	private boolean RESULT_OK = false;
	private static final int MAX_NUMBER = 2147483647, MIN_NUMBER = -2147483648;
	private Context context;


	/**
	 * Передает параметры в метод установки параметров
	 * @param expression математическое выражение
	 * @param fromNotation то, из какой системы счисления
	 * @param toNotation то, в какую систему счислению
	 */
	public ConverterNotation(Context context, String expression,
							 int fromNotation, int toNotation, int amountOfSymbolsAfterComma){
		this.context = context;
		setParameters(expression, fromNotation, toNotation, amountOfSymbolsAfterComma);
	}

	/**
	 * Устанавливает параметры конвертера
	 * @param expression математическое выражение
	 * @param fromNotation то, из какой системы счисления
	 * @param toNotation то, в какую систему счислению
	 */
	public void setParameters(String expression,
							  int fromNotation, int toNotation, int amountOfSymbolsAfterComma){
		this.expression = expression;
		this.fromNotation = fromNotation;
		this.toNotation = toNotation;
		this.amountOfSymbolsAfterComma = amountOfSymbolsAfterComma;
		RESULT_OK = false;
	}

	/** Начинает процесс перевода числа в другую систему счисления */
	public void startTransfer() {
		String tenExpression;
		if(fromNotation == toNotation && !(expression.contains("+") || expression.contains("-") ||
				expression.contains("*") || expression.contains("/")) || expression.length() == 0) {
			RESULT_OK = true;
			return;
		}
		else if(fromNotation == 10) {
			tenExpression = expression;
		}
		else {
			tenExpression = getConvertToTenExpression();
		}
		try {
			tenExpression = tenExpression.replace(',', '.');
			float result = new MathParser().parse(tenExpression);
			result = (float) (Math.round(result*100000.0)/100000.0);
			if (result <= MIN_NUMBER || result >= MAX_NUMBER ) {
				expression = context.getResources().getString(R.string.big_number);
				RESULT_OK = true;
				return;
			}
			if(result % 1 == 0)
				expression = String.valueOf((int)result);
			else
				expression = String.valueOf(result);
		} catch (Exception ignored) {}
		if(toNotation == 10) {
			RESULT_OK = true;
			return;
		}
		if(expression.equals("0")){
			RESULT_OK = true;
			return;
		}
		convertToCustomNotation();
	}

	/**
	 * Получает результат переведа математического выражения
	 * @return результат переведа математического выражения
	 * @throws Exception вызывается исключение если результат еще не получен
	 */
	public String getResult() {
		oldExpression = expression;
		if (RESULT_OK) return expression;
		else return oldExpression;
	}

	public String getOldResult(){
		return oldExpression;
	}

	/**
	 * Переводит математическое выражение в десятичную систему счисления
	 * @return математическое выражение в десятичной системе счисления
	 */
	private String getConvertToTenExpression() {
		String tenExpression = "";
		String notTenExpression = expression;
		while (notTenExpression.length() > 0){
			// split[0] - число для перевода в десятичную систему счисления
			// split[1] - выражение, которое нужно перевести в дестичную систему счисления
			String[] split = notTenExpression.split("[-+*/]", 2);
			String sign = getSign(notTenExpression);
			// Если выражение имеет стандартный вид (12+3)
			tenExpression += convertToTenNotation(split[0])+sign;
			// Если конец выражения, то выходим из цикла
			if(sign.equals("")) break;
			// Если знак стоит в конце выражения, а числа нет (12+)
			if(!sign.equals("") && split[1].equals(""))split[1] = "0";
			notTenExpression = split[1];
		}
		return tenExpression;
	}

	/**
	 * Получает первый операнд в строке
	 * @param expression математическое выражение
	 * @return операнд
	 */
	@NonNull
	private String getSign(String expression){
		for(char symbol : expression.toCharArray())
			if(symbol == '-' || symbol == '+' || symbol == '*' || symbol == '/')
				return String.valueOf(symbol);
		return "";
	}

	/**
	 * Переводит одно число в десятичную систему счисления
	 * @param number число для перевода в десятичную систему счисления
	 * @return число переведенное в десятичную систему счисления
	 */
	@NonNull
	private String convertToTenNotation(String number) {
		if (number.equals(""))return "";
		int pow = getBeginPow(number);
		double num = 0;
		// Цикл осуществляющий основную работу по переводу числа в дестичную систему счисления
		while(number.length() > 0){
			int digit = getOneDigit(number.charAt(0));
			if(digit!=-1)
				num += digit * Math.pow(fromNotation, pow--);
			number = number.substring(1);
		}
		return String.valueOf(num);
	}

	/**
	 * Получает число записанное в десятичном виде
	 * @param digit число записанное по правилам систем счисления
	 * @return число записанное в десятичном виде
	 */
	private int getOneDigit(char digit){
		if(digit >= 'A' && digit <= 'F') return digit - 55;
		else if(digit == ',') return -1;
		return Integer.parseInt(String.valueOf(digit));
	}

	/**
	 * Устанавливает вид числа соответствующий правилам записи систем счислния
	 * @param digit число в десятичном виде
	 * @return число, записанное по правилам систем счисления
	 */
    private String setOneDigit(int digit){
        if (digit >= 10 && digit <= 15)
            return String.valueOf((char) (digit + 55));
        return String.valueOf(digit);
	}

	/**
	 * Определяет начальную степень числа
	 * @param number число в котором нужно определить степень
	 * @return начальная степень числа
	 */
	private int getBeginPow(String number){
		int beginPow;
		if(number.contains(",")){
			int indexOfComma = number.indexOf(",");
			beginPow = indexOfComma - 1;
			expression = number.substring(0, indexOfComma) + number.indexOf(indexOfComma + 1);
		}
		else
			beginPow = number.length() - 1;
		return beginPow;
	}

	/** Переводит число в нужную систему счисления */
	private void convertToCustomNotation() {
		expression = expression.replace('.', ',') ;
		boolean isNegative = false;
		if(expression.contains("-")){
			expression = expression.substring(1);
			isNegative = true;
		}
		if(expression.contains(",")){
			String[] splitNum = getSplitNumByComma();
			String intPart = customNotationOfInteger(Integer.parseInt(splitNum[0]));
			String fracPart = getCustomNotationOfFraction(Integer.parseInt(splitNum[1]));
			expression = intPart.concat(",").concat(fracPart);
		}
		else
			try {
				expression = customNotationOfInteger(Integer.parseInt(expression));
			}catch (Exception e){
				if(!(expression.contains("+") || expression.contains("*") ||
						expression.contains("/") || expression.contains("-")))
					expression = context.getResources().getString(R.string.big_number);
				else
					expression = oldExpression;
			}
		if(isNegative) expression = ("-").concat(expression);
		RESULT_OK = true;
	}

	/**
	 * Разделяет число на целую и дробную часть
	 * @return целую и дробную часть
	 */
	private String[] getSplitNumByComma() {
		String[] splitNumByComma = expression.split(",", 2);
		for(int i = 0; i < splitNumByComma.length; i++)
			if(splitNumByComma[i].equals("")) splitNumByComma[i] = "0";
		return splitNumByComma;
	}

	/**
	 * Переводит целую часть числа в заданную систему счисления
	 * @param number целая часть числа, которую следует перевести
	 * @return целую часть числа в нужной системе счисления
	 */
	private String customNotationOfInteger(int number){
		String modulo = "";
		int integerPart = number;
		// Цикл осуществляет основную работу по переводу целой части
		// десятичного чсла в нужную систему счисления
		while(integerPart != 0){
			modulo = setOneDigit((integerPart % toNotation)) + modulo;
			integerPart /= toNotation;
		}
		return modulo;
	}

	/**
	 * Переводит дробную часть числа в заданную систему счисления
	 * @param number дробная часть числа, которую следует перевести
	 * @return дробную часть числа в нужной системе счисления
	 */
	private String getCustomNotationOfFraction(int number){
		String modulo = "";
		int integerPart = number;
		// Допустимая длина целой части (остальная часть идет  остаток)
		int maxLength = (int)Math.pow(10, String.valueOf(number).length());

		// Цикл осуществляет основную работу по переводу дробной части
		// десятичного чсла в нужную систему счисления
		for(int i = 0; i < amountOfSymbolsAfterComma; i++){
			integerPart *= toNotation;
			modulo += setOneDigit(integerPart / maxLength);
			integerPart %= maxLength;
			if (integerPart == 0) break;
		}
		return modulo;
	}

}
