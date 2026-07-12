package com.gautam.dynamic_access_rule_evaluator.context;

import java.util.Set;

public class UserContext {
    private final Set<String> roles;

    public UserContext(Set<String> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }
}
