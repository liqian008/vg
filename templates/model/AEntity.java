<#assign hasDateType = false>
package ${basepackage}.model;


import java.io.Serializable;
import java.util.Date;

/**
 * ${table.remark } 实体类
 *
 * @author ${author}
 * @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
public class ${table.className!}Entity implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    <#list table.columns as column>
    /**
     * ${column.remark!}
     */
    private ${column.fieldType} ${column.fieldName};

    </#list>

}
