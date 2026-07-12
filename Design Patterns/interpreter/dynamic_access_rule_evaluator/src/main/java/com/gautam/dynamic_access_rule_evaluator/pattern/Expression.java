package com.gautam.dynamic_access_rule_evaluator.pattern;


import com.gautam.dynamic_access_rule_evaluator.context.UserContext;

public interface Expression {
    boolean interpret(UserContext context);
}
