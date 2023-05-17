package com.example.demo.service.impl;

import com.example.demo.enums.DbTypeEnum;
import com.example.demo.enums.GenerateTypeEnum;
import com.example.demo.enums.OutputTypeEnum;
import com.example.demo.service.IConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置service
 * @author liqian483
 */
@Slf4j
@Service
public class ConfigServiceImpl implements IConfigService {

    @Override
    public Map<String, Object> getConfig() {
        log.info("[ConfigController][config]enter");
        Map<String, Object> result = new HashMap<>();
        result.put("dbTypes", DbTypeEnum.values());
        result.put("outputTypes", OutputTypeEnum.values());
        result.put("generateTypes", GenerateTypeEnum.values());
        return result;
    }

}
