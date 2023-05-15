package com.example.demo.processor.impl;

import cn.hutool.core.util.StrUtil;
import com.example.demo.consts.Constants;
import com.example.demo.processor.AbstraceNamedProcessor;
import com.example.demo.processor.INamedProcessor;
import org.springframework.stereotype.Service;

/**
 * 类名处理器（首字母大写+驼峰）
 * @author liqian483
 */
@Service("class" + Constants.METADATA_PROCESSOR_SUFFIX)
public class NamedClassCaseProcessor extends AbstraceNamedProcessor {

    @Override
    public String processName(String text) {
        return StrUtil.upperFirst(toCamelCase(text));
    }

}
