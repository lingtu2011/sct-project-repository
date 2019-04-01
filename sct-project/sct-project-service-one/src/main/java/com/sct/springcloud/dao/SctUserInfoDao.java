package com.sct.springcloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sct.springcloud.entity.SctUserInfoVO;

@Mapper
public interface SctUserInfoDao{

	List<SctUserInfoVO> queryAll();

	List<SctUserInfoVO> queryById(@Param("id")String id);
}
