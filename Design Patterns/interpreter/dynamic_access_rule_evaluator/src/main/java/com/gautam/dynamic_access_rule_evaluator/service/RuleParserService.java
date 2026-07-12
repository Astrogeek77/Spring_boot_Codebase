package com.gautam.dynamic_access_rule_evaluator.service;

import com.gautam.dynamic_access_rule_evaluator.pattern.AndExpression;
import com.gautam.dynamic_access_rule_evaluator.pattern.Expression;
import com.gautam.dynamic_access_rule_evaluator.pattern.OrExpression;
import com.gautam.dynamic_access_rule_evaluator.pattern.RoleExpression;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class RuleParserService {

    // Parses a rule in Postfix notation (e.g., "ADMIN USER AND")
    public Expression parsePostfixRule(String rule) {
        Stack<Expression> stack = new Stack<>();
        String[] tokens = rule.split(" ");

        for (String token : tokens) {
            if (token.equalsIgnoreCase("AND")) {
                Expression right = stack.pop();
                Expression left = stack.pop();
                stack.push(new AndExpression(left, right));
            } else if (token.equalsIgnoreCase("OR")) {
                Expression right = stack.pop();
                Expression left = stack.pop();
                stack.push(new OrExpression(left, right));
            } else {
                // If it's not an operator, it must be a role (Terminal)
                stack.push(new RoleExpression(token));
            }
        }

        // The final item in the stack is the root of the expression tree
        return stack.pop();
    }
}
