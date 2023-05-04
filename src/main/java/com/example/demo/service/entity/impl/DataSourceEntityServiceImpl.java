package com.example.demo.service.entity.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.exception.GenericRuntimeException;
import com.example.demo.mapper.DataSourceEntityMapper;
import com.example.demo.model.DataSourceVo;
import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.service.entity.IDataSourceEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据源EntityService
 * @author bruce
 */
@Slf4j
@Service
public class DataSourceEntityServiceImpl extends ServiceImpl<DataSourceEntityMapper, DataSourceEntity>
		implements IDataSourceEntityService {

	@Override public List<DataSourceVo> listUserDataSources(int userId, Short dbType) {

		LambdaQueryWrapper<DataSourceEntity> wrapper = new LambdaQueryWrapper();
		wrapper.eq(DataSourceEntity::getCreateUid, userId);
		if(dbType!=null && dbType>0){
			wrapper.eq(DataSourceEntity::getDbType, dbType);
		}
		List<DataSourceEntity> list = list(wrapper);

		List<DataSourceVo> result = new ArrayList<>();
		for(DataSourceEntity loopItem: list){
			DataSourceVo templateVo = DataSourceVo.convert(loopItem);
			result.add(templateVo);
		}
		return result;
	}

    @Override
    public DataSourceEntity loadById(int userId, int datasourceId, boolean throwException) {
		LambdaQueryWrapper<DataSourceEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(DataSourceEntity::getCreateUid, userId).eq(DataSourceEntity::getId, datasourceId);
//		List<DataSourceEntity> dataList = dataSourceEntityService.list(wrapper);
//		if(CollectionUtils.isNotEmpty(dataList)){
//
//		}
		//TODO 统一
		DataSourceEntity result = getOne(wrapper);
		return result;
    }


    /**
	 *
	 * @param datasourceId
	 * @param throwException
	 * @return
	 */
	@Override
	public DataSourceEntity loadById(int datasourceId, boolean throwException){
		DataSourceEntity result = this.getById(datasourceId);
		if(result==null && throwException){
			throw new GenericRuntimeException();
		}
		return result;
	}

//	/**
//	 *
//	 * @param userId
//	 * @param datasourceId
//	 * @return
//	 */
//	@Override public List<String> listUserTables(int userId, int datasourceId) {
//		log.info("[listUserTables]enter, userId:{}, datasourceId:{}", userId, datasourceId);
//		List<String> result = new ArrayList<>();
//		DataSourceEntity dataSourceEntity = loadById(datasourceId, true);
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			conn = getConnection(dataSourceEntity);
//			DatabaseMetaData metaData = conn.getMetaData();
//			ps = conn.prepareStatement("show tables");
//			rs = ps.executeQuery();
//			while(rs.next()){
//				String loopName = rs.getString(1);
//				result.add(loopName);
//			}
//		} catch (SQLException e) {
//
//		}finally {
//			if(conn!=null){
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if(ps!=null){
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if(rs!=null){
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return result;
//	}

	@Override public boolean deleteDatasource(int userId, int datasourceId) {
		log.info("[deleteDatasource]enter, userId:{}, datasourceId:{}", userId, datasourceId);
		LambdaQueryWrapper<DataSourceEntity> wrapper = new LambdaQueryWrapper();
		wrapper.eq(DataSourceEntity::getId, datasourceId).eq(DataSourceEntity::getCreateUid, userId);
		boolean result = remove(wrapper);
		log.info("[deleteDatasource]result:{}, userId:{}, datasourceId:{}", result, userId, datasourceId);
		return result;
	}

	/**
	 * 创建数据源
	 * @param userId
	 * @param dataSourceVo
	 * @return
	 */
	@Override public DataSourceEntity createDatasource(int userId, DataSourceVo dataSourceVo) {
		log.info("[createDatasource]enter, userId:{}, dataSourceVo:{}", userId, dataSourceVo);
		Date currentTime = new Date();
		DataSourceEntity result = DataSourceVo.convert(dataSourceVo);
		result.setId(null);
		result.setCreateUid(userId);
		result.setCreateTime(currentTime);
		save(result);
		return result;
	}




}
