package com.heiden.droolsdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heiden.droolsdemo.entity.ParamInfo;
import com.heiden.droolsdemo.mapper.ParamInfoMapper;
import com.heiden.droolsdemo.service.ParamInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("paramInfoService")
public class ParamInfoServiceImpl extends ServiceImpl<ParamInfoMapper,ParamInfo> implements ParamInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ParamInfoServiceImpl.class);

    @Resource
    private ParamInfoMapper paramInfoMapper;

    @Override
    public ParamInfo selectById(String paramId) {
        ParamInfo paramInfo = paramInfoMapper.selectById(paramId);
        if (paramInfo != null){
            logger.info("ParamInfoServiceImpl-Sign：{}",paramInfo.getParamSign());
        }

        return paramInfo;
    }

    @Override
    public void insertParam(ParamInfo paramInfo) {
        paramInfoMapper.insertParam(paramInfo);
    }
}
