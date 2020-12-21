package com.heiden.droolsdemo.service;

import com.heiden.droolsdemo.entity.QueryParam;

public interface RuleEngineService {
    void executeAddRule(QueryParam param);
    void executeRemoveRule(QueryParam param);
}
