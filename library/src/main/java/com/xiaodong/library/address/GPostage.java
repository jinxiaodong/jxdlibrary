package com.xiaodong.library.address;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Creator : Created by Kevin.tian
 * Time : 2018/5/29
 * Description : 邮费对象
 */
public class GPostage implements Serializable {
    // 配送名称
    private String postageName;
    // 邮费描述
    private String postageDesc;
    // 小于
    private BigDecimal underUnit;
    // 小于【underUnit】邮费为【underUnitCost】
    private BigDecimal underUnitCost;
    // 大于等于
    private BigDecimal perUnit;
    // 大于等于【perUnit】邮费为【perUnitCost】
    private BigDecimal perUnitCost;
    // 是否是自提
    private Integer isTakeTheir;
    // 计费方式 10:按件数; 11:按重量; 12:按体积; 13:按金额
    private Integer chargeWay;

    public String getPostageName() {
        return postageName;
    }

    public void setPostageName(String postageName) {
        this.postageName = postageName;
    }

    public String getPostageDesc() {
        return postageDesc;
    }

    public void setPostageDesc(String postageDesc) {
        this.postageDesc = postageDesc;
    }

    public BigDecimal getUnderUnit() {
        return underUnit;
    }

    public void setUnderUnit(BigDecimal underUnit) {
        this.underUnit = underUnit;
    }

    public BigDecimal getUnderUnitCost() {
        return underUnitCost;
    }

    public void setUnderUnitCost(BigDecimal underUnitCost) {
        this.underUnitCost = underUnitCost;
    }

    public BigDecimal getPerUnit() {
        return perUnit;
    }

    public void setPerUnit(BigDecimal perUnit) {
        this.perUnit = perUnit;
    }

    public BigDecimal getPerUnitCost() {
        return perUnitCost;
    }

    public void setPerUnitCost(BigDecimal perUnitCost) {
        this.perUnitCost = perUnitCost;
    }

    public Integer getIsTakeTheir() {
        return isTakeTheir;
    }

    public void setIsTakeTheir(Integer isTakeTheir) {
        this.isTakeTheir = isTakeTheir;
    }

    public Integer getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(Integer chargeWay) {
        this.chargeWay = chargeWay;
    }
}
