package com.gautam.dynamic_access_rule_evaluator.pattern;

import com.gautam.dynamic_access_rule_evaluator.context.UserContext;

public class RoleExpression implements Expression {
    private final String role;

    public RoleExpression(String role) {
        this.role = role;
    }

    @Override
    public boolean interpret(UserContext context) {
        return context.hasRole(role);
    }
}
