package com.gautam.dynamic_access_rule_evaluator.pattern;

import com.gautam.dynamic_access_rule_evaluator.context.UserContext;

public class OrExpression implements Expression {
    private final Expression expr1;
    private final Expression expr2;

    public OrExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public boolean interpret(UserContext context) {
        return expr1.interpret(context) || expr2.interpret(context);
    }
}
