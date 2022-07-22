package com.example.demo.service.entity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.DataSourceVo;
import com.example.demo.model.DataSourceEntity;

import java.util.List;

/**
 * 数据源EntityService
 * @author bruce
 */
public interface IDataSourceEntityService extends IService<DataSourceEntity> {

	/**
	 * 获取数据源
	 * @param userId
	 * @param dbType
	 * @return
	 */
	List<DataSourceVo> listUserDataSources(int userId, Short dbType);



	/**
	 * 加载数据源
	 * @param datasourceId
	 * @param throwException
	 * @return
	 */
	DataSourceEntity loadById(int datasourceId, boolean throwException);

	/**
	 *
	 * @param userId
	 * @param datasourceId
	 * @return
	 */
	List<String> listUserTables(int userId, int datasourceId);

	/**
	 *
	 * @param userId
	 * @param datasourceId
	 * @return
	 */
	boolean deleteDatasource(int userId, int datasourceId);


	/**
	 *
	 * @param userId
	 * @param dataSourceVo
	 * @return
	 */
	DataSourceEntity createDatasource(int userId, DataSourceVo dataSourceVo);


}
