package com.example.demo.processor.impl;

import com.example.demo.consts.Constants;
import com.example.demo.processor.AbstraceNamedProcessor;
import org.springframework.stereotype.Service;

/**
 * 驼峰命名处理器
 * @author liqian483
 */
@Service("camel" + Constants.METADATA_PROCESSOR_SUFFIX)
public class NamedCamelCaseProcessor extends AbstraceNamedProcessor {

    @Override
    public String processName(String text) {
        return toCamelCase(text);
    }
}
