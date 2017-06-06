package com.mycompany.coding2.model;

/**
 * Неизменяемый объект содержащий в себе основные данные о текущем состоянии
 * выражения и систем счислений.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class NumSysInfo {
    private String expression;
    private int fromNotation, toNotation;

    public NumSysInfo(String expression, int fromNotation, int toNotation) {
        this.fromNotation = fromNotation;
        this.toNotation = toNotation;
        this.expression = expression;
    }

    /**
     * @return систма счисления из которой производится перевод
     */
    public int getFromNotation() {
        return fromNotation;
    }

    /**
     * @return систма счисления в которую производится перевод
     */
    public int getToNotation() {
        return toNotation;
    }

    /**
     * @return выражение, требующее перевод
     */
    public String getExpression() {
        return expression;
    }
}
