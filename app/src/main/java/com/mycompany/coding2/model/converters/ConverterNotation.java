package com.mycompany.coding2.model.converters;

import android.content.res.Resources;
import com.mycompany.coding2.R;
import com.mycompany.coding2.model.NumSysInfo;

/**
 * Первое звено при переводе числа в другую систему счисления.
 * <p>
 *     Создаёт строитель решения и запускает цепочку конвертации
 * </p>
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class ConverterNotation implements IConvert {
	public static final String error = "error";
	private static String errorBigNumber;
	private IConvert converter;

	static String getBigNumberError() {
		return errorBigNumber;
	}

	/**
	 * Конструирует конвертор и инициализирует необходимые поля
	 * @param res ресурсы для получения строк
	 * @param info основные данные о текущем состоянии выражения и систем счисления
	 */
	public ConverterNotation(Resources res, NumSysInfo info){
		errorBigNumber = res.getString(R.string.big_number);
		converter = new ConvertToValid(res, info, null);
	}

	/**
	 * !!! Только для тестирования !!!
	 * Конструирует конвертор и инициализирует необходимые поля
	 * @param info основные данные о текущем состоянии выражения и систем счисления
	 */
	public ConverterNotation(NumSysInfo info){
		errorBigNumber = "Huge number";
		converter = new ConvertToValid("Wrong notation", info, null);
	}

	/**
	 * Конвертирует выражение
	 * @return преобразованное выражение
	 */
	@Override
	public String convert() {
		return converter.convert();
	}
}
