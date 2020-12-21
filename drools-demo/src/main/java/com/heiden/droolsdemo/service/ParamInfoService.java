package com.heiden.droolsdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heiden.droolsdemo.entity.ParamInfo;

public interface ParamInfoService extends IService<ParamInfo> {
    ParamInfo selectById(String paramId);
    void insertParam(ParamInfo paramInfo);
}
