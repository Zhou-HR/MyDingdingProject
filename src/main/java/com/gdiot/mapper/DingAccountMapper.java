package com.gdiot.mapper;


import com.gdiot.entity.DingAccountPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 */
@Mapper
@Component
public interface DingAccountMapper {
    /**
     * @param mDingAccountPo
     * @return
     */
    int insertOne(DingAccountPo mDingAccountPo);

    /**
     * @param client
     * @return
     */
    int countAccountList(@Param("client") String client);

    /**
     * @param client
     * @param limit
     * @param offset
     * @return
     */
    List<DingAccountPo> selectAccountList(@Param("client") String client, @Param("limit") int limit, @Param("offset") int offset);
}
