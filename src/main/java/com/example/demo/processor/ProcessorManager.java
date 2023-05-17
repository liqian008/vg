package com.example.demo.processor;

import cn.hutool.core.util.StrUtil;
import com.example.demo.enums.DbTypeEnum;
import com.example.demo.holder.ApplicationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.consts.Constants.METADATA_PROCESSOR_SUFFIX;

/**
 * Processor处理类
 * @author liqian483
 */
@Service
public class ProcessorManager{

    @Autowired
    private ApplicationContextHolder applicationContextHolder;

    /**
     *
     * @param dbTypeVal
     * @return
     */
    public IMetadataDbProcessor loadDbProcessorByDbType(short dbTypeVal) {
        DbTypeEnum dbTypeEnum = DbTypeEnum.valueOf(dbTypeVal);
        String beanName = dbTypeEnum.getKey() + METADATA_PROCESSOR_SUFFIX;
        IMetadataDbProcessor result = loadBean(beanName, IMetadataDbProcessor.class);
        return result;
    }

    /**
     *
     * @param namedType
     * @return
     */
    public INamedProcessor loadNamedProcessor(String namedType) {
        String beanName = namedType + METADATA_PROCESSOR_SUFFIX;
        INamedProcessor result = loadBean(beanName, INamedProcessor.class);
        return result;
    }

    /**
     *
     * @param beanName
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T loadBean(String beanName, Class<T> clazz){
        T result = applicationContextHolder.getApplicationContext().getBean(beanName, clazz);
        return result;
    }



}
