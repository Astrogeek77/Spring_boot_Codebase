package com.gautam.dynamic_access_rule_evaluator.controller;

import com.gautam.dynamic_access_rule_evaluator.context.UserContext;
import com.gautam.dynamic_access_rule_evaluator.pattern.Expression;
import com.gautam.dynamic_access_rule_evaluator.service.RuleParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    private final RuleParserService ruleParserService;

    @Autowired
    public AccessController(RuleParserService ruleParserService) {
        this.ruleParserService = ruleParserService;
    }

    @PostMapping("/evaluate")
    public Map<String, Object> evaluateAccess(@RequestBody AccessRequest request) {
        // 1. Build the Expression Tree from the rule
        Expression expressionTree = ruleParserService.parsePostfixRule(request.getRule());

        // 2. Initialize the Context with the user's actual roles
        UserContext context = new UserContext(request.getUserRoles());

        // 3. Interpret the tree against the context
        boolean isGranted = expressionTree.interpret(context);

        Map<String, Object> response = new HashMap<>();
        response.put("rule", request.getRule());
        response.put("userRoles", request.getUserRoles());
        response.put("accessGranted", isGranted);

        return response;
    }
}

// DTO Class
class AccessRequest {
    private String rule;
    private Set<String> userRoles;

    public String getRule() { return rule; }
    public void setRule(String rule) { this.rule = rule; }
    public Set<String> getUserRoles() { return userRoles; }
    public void setUserRoles(Set<String> userRoles) { this.userRoles = userRoles; }
}
