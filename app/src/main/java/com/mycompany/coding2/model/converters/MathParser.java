package com.mycompany.coding2.model.converters;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс решающий простейшие примеры в 10-ой системе счисления
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
class MathParser {

    /**
     * Базовый метод для решения выражения
     * @param s выражение для решения
     * @return результат выражения
     * @throws Exception некорректный ввод математического выражения
     */
    float parse(String s) throws Exception {
        Result result = plusMinus(s);
        return Float.parseFloat(result.acc.setScale(5, RoundingMode.HALF_UP).toString());
    }

    private Result plusMinus(String s) throws Exception {
        Result current = mulDiv(s);
        BigDecimal acc = current.acc;

        while (current.rest.length() > 0) {
            if (!(current.rest.charAt(0) == '+' || current.rest.charAt(0) == '-')) break;

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = mulDiv(next);
            if (sign == '+') {
                acc = acc.add(current.acc);
            } else {
                acc = acc.add(current.acc.negate());
            }
        }
        return new Result(acc, current.rest);
    }

    private Result bracket(String s) throws Exception {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = plusMinus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                System.err.println("Error: not close bracket");
            }
            return r;
        }
        return num(s);
    }

    private Result mulDiv(String s) throws Exception {
        Result current = bracket(s);

        BigDecimal acc = current.acc;
        while (true) {
            if (current.rest.length() == 0) {
                return current;
            }
            char sign = current.rest.charAt(0);
            if ((sign != '*' && sign != '/')) return current;

            String next = current.rest.substring(1);
            Result right = bracket(next);

            if (sign == '*') {
                acc = acc.multiply(right.acc);
            } else
                //noinspection BigDecimalMethodWithoutRoundingCalled
                acc = acc.divide(right.acc, 5, BigDecimal.ROUND_HALF_UP);

            current = new Result(acc, right.rest);
        }
    }

    private Result num(String s) throws Exception {
        int i = 0;
        int dot_cnt = 0;
        boolean negative = false;
        if(s.charAt(0) == '-'){
            negative = true;
            s = s.substring(1);
        }
        // разрешаем только цифры и точку
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            // но также проверям, что в числе может быть только одна точка!
            if (s.charAt(i) == '.' && ++dot_cnt > 1) {
                throw new Exception("not valid number '" + s.substring(0, i + 1) + "'");
            }
            i++;
        }
        if( i == 0 ){ // что-либо похожее на число мы не нашли
            throw new Exception( "can't get valid number in '" + s + "'" );
        }

        BigDecimal dPart = new BigDecimal(s.substring(0, i));
        if(negative) dPart = dPart.negate();
        String restPart = s.substring(i);

        return new Result(dPart, restPart);
    }

    private class Result {

        BigDecimal acc;
        String rest;

        Result(BigDecimal v, String r) {
            this.acc = v;
            this.rest = r;
        }
    }
}