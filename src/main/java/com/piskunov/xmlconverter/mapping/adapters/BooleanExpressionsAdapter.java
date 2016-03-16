package com.piskunov.xmlconverter.mapping.adapters;

import com.piskunov.xmlconverter.mapping.InputData;
import com.piskunov.xmlconverter.mapping.MappingException;
import com.piskunov.xmlconverter.mapping.MappingRule;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Vladimir Piskunov on 2/28/16.
 */
public class BooleanExpressionsAdapter implements MappingAdapter {

    static Logger logger = Logger.getLogger(BooleanExpressionsAdapter.class.getName());


    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }


    @Override
    public List<String> process(MappingRule rule, InputData data) throws MappingException {

        List<String> resultValues = new ArrayList<>();

        if(expression == null)
            throw new MappingException(this.getClass().getSimpleName() + "Adapter Expression is not set for rule: " + rule.getTarget());

        String[] arguments = expression.split(" ");

        for (String s : arguments) {

            if (s.startsWith("$")) {

                String value = data.getPairs().get(s.replace("$", ""));

                if (value != null) {

                    try {
                        Float.parseFloat(value);
                        expression = expression.replace(s, value);

                    } catch (NumberFormatException e) {
                        try {
                            value = value.replace(",", ".");
                            Float.parseFloat(value);
                            expression = expression.replace(s, value);

                        } catch (NumberFormatException ee) {
                            throw new MappingException(this.getClass().getSimpleName() + " argument " + s.toUpperCase() + " is not a float/int: |" + value + "|");
                        }
                    }

                } else {
                    throw new MappingException(this.getClass().getSimpleName() + " argument is not found in source: " + s);
                }

            } else if (s.equals("less")) {
                expression = expression.replace("less", "<");
            } else if (s.equals("more")) {
                expression = expression.replace("more", ">");
            }

        }

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);

        resultValues.add(String.valueOf(exp.getValue(Boolean.class)));

        return resultValues;

    }
}
