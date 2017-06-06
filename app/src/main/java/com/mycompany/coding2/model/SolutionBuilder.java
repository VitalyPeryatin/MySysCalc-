package com.mycompany.coding2.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Помогает строить решения, предоставляя все метода для
 * систематизированного представления решения.
 *
 * @author Vitaly
 * @version 1.0
 * @since SDK 21
 */
public class SolutionBuilder {
    private Map<String, StringBuilder> solution = new HashMap<>();
    private StringBuilder stepGroup = new StringBuilder();
    private String expression;
    private static SolutionBuilder builder;

    private SolutionBuilder(String expression){
        this.expression = expression;
    }

    /**
     * Получает существующее решение при условии, что оно существует.
     * @return builder (себя же)
     */
    public static SolutionBuilder getBuilder(){
        if(builder == null) throw new NullPointerException("Create builder before getting");
        return builder;
    }

    /**
     * Создает builder для передаваемого выражения
     * @param expression выражение для которого строится решение
     * @return сконструированный объект builder
     */
    public static SolutionBuilder createBuilder(String expression){
        builder = new SolutionBuilder(expression);
        return getBuilder();
    }

    /**
     * Добавляет новый шаг для решения выражения
     * @param step новая строка решения
     */
    public void addStep(String step){
        stepGroup.append(step).append("\n");
    }

    /**
     * Создаёт новую логическую группу шагов для решения выражения
     * @param title заголовок для новой группы шагов
     */
    public void addGroup(String title){
        stepGroup = new StringBuilder();
        solution.put(title, stepGroup);
    }

    /**
     * @return решение в виде Map объекта
     */
    public Map<String, StringBuilder> getSolution(){
        return solution;
    }

    /**
     * @return выражение для которого строится решение
     */
    public String getExpression(){
        return expression;
    }
}
