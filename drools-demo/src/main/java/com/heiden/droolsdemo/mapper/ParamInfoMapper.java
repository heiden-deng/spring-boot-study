package com.heiden.droolsdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heiden.droolsdemo.entity.ParamInfo;

public interface ParamInfoMapper extends BaseMapper<ParamInfo> {
    void insertParam(ParamInfo paramInfo);
}
