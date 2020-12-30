package com.wang.mapper;

import com.wang.entity.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/29 13:24
 */
public interface ProviderMapper {
    /**
     * 增加供应商
     * @param provider
     * @return
     * @throws Exception
     */
    public int add(Provider provider)throws Exception;


    /**
     * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
     * @param proName
     * @return
     * @throws Exception
     */
    public List<Provider> getProviderList(@Param("proName") String proName,
                                          @Param("proCode")String proCode)throws Exception;

    /**
     * 通过proId删除Provider
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteProviderById( int id)throws Exception;


    /**
     * 通过proId获取Provider
     * @param id
     * @return
     * @throws Exception
     */
    public Provider getProviderById(String id)throws Exception;

    /**
     * 修改用户信息
     * @param
     * @return
     * @throws Exception
     */
    public int modify(Provider provider)throws Exception;
}
