package com.example.demo.model.vo;

import com.example.demo.enums.DbTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bruce
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbTypeVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Map<Short, DbTypeVo> dbTypeMap = new HashMap<>();
	static{
		synchronized (DbTypeVo.class){
			if(dbTypeMap==null){
				for(DbTypeEnum dbTypeEnum: DbTypeEnum.values()){
					DbTypeVo dbTypeVo = new DbTypeVo(dbTypeEnum.getValue(), dbTypeEnum.getName());
					dbTypeMap.put(dbTypeEnum.getValue(), dbTypeVo);
				}
			}
		}
	}

	/** 数据库 */
	private Short dbType;
	/** 数据库类型 */
	private String dbTypeName;

	/**
	 *
	 * @param dbType
	 * @return
	 */
	public static DbTypeVo loadDbType(Short dbType){
		DbTypeVo result = null;
		if(dbType!=null){
			result = DbTypeVo.dbTypeMap.get(dbType);
		}
		return result;
	}

}
