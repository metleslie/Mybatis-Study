package com.wang.mapper;

import com.wang.entity.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/28 18:15
 */
public interface BillMapper {
    /**
     * 根据供应商Id查询订单数量
     * @param providerId
     * @return
     */
    //根据供应商Id查询订单数量
    public int getBillCountByProviderId(@Param("providerId") Integer providerId);


    /**
     * //增加订单
     * @param bill
     * @return
     */
    public int add(Bill bill);

    /**
     * 通过查询条件获取供应商列表-getBillList
     * @param productName
     * @param providerId
     * @param isPayment
     * @param startindex
     * @param pageSize
     * @return
     * @throws Exception
     */
    //通过查询条件获取供应商列表-getBillList
    public List<Bill> getBillList(@Param("productName") String productName,
                                  @Param("providerId") String providerId,
                                  @Param("isPayment") String isPayment,
                                  @Param("startindex") Integer startindex,
                                  @Param("pageSize") Integer pageSize)throws Exception;

    //通过条件查询，查询供货商数量,模糊查询
    public int getBillCount(@Param("productName") String productName,
                            @Param("providerId") String providerId,
                            @Param("isPayment") String isPayment)throws Exception;

    //通过delId删除Bill
    public int deleteBillById(@Param("id") Integer id)throws Exception;

    //通过billId获取Bill
    public Bill getBillById(@Param("id") Integer id)throws Exception;

    //修改订单信息
    public int modify(Bill bill)throws Exception;

    //根据供应商Id删除订单信息
    public int deleteBillByProviderId(@Param("providerId") Integer providerId)throws Exception;

}
