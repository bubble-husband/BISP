package com.provider.mapper;

import com.commons.entity.Sort;
import com.commons.entity.SortExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SortMapper {
    int countByExample(SortExample example);

    int deleteByExample(SortExample example);

    int deleteByPrimaryKey(Integer sortId);

    int insert(Sort record);

    int insertSelective(Sort record);

    List<Sort> selectByExample(SortExample example);

    Sort selectByPrimaryKey(Integer sortId);

    int updateByExampleSelective(@Param("record") Sort record, @Param("example") SortExample example);

    int updateByExample(@Param("record") Sort record, @Param("example") SortExample example);

    int updateByPrimaryKeySelective(Sort record);

    int updateByPrimaryKey(Sort record);
}