package com.provider.mapper;

import com.commons.entity.Userdetail;
import com.commons.entity.UserdetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserdetailMapper {
    int countByExample(UserdetailExample example);

    int deleteByExample(UserdetailExample example);

    int deleteByPrimaryKey(Integer userdetailId);

    int insert(Userdetail record);

    int insertSelective(Userdetail record);

    List<Userdetail> selectByExample(UserdetailExample example);

    Userdetail selectByPrimaryKey(Integer userdetailId);

    int updateByExampleSelective(@Param("record") Userdetail record, @Param("example") UserdetailExample example);

    int updateByExample(@Param("record") Userdetail record, @Param("example") UserdetailExample example);

    int updateByPrimaryKeySelective(Userdetail record);

    int updateByPrimaryKey(Userdetail record);
}