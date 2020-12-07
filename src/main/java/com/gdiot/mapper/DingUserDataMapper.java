package com.gdiot.mapper;

import com.gdiot.entity.DingUserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 */
@Mapper
@Component
public interface DingUserDataMapper {
    /**
     * @param mDingDepPo
     * @return
     */
    int insertOne(DingUserPo mDingDepPo);

    /**
     * @param mDingDepPo
     * @return
     */
    int updateDetail(DingUserPo mDingDepPo);

    /**
     * @param user_id
     * @return
     */
    List<DingUserPo> selectOne(@Param("user_id") String user_id);
}
