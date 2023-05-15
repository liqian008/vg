package com.example.demo.processor;

import cn.hutool.core.util.StrUtil;

/**
 * 抽象类
 * @author liqian483
 */
public abstract class AbstraceNamedProcessor implements INamedProcessor{

    /**
     * 生成className
     * @param text
     * @return
     */
    protected String toCamelCase(String text) {
        return StrUtil.toCamelCase(text);
    }
}
