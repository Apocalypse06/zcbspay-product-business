
package com.zcbspay.platform.business.concentrate.enums;
/**
 * 
 *合同收付类型
 *
 * @author lianhai
 */
public enum ContractTypeEnum {
    /**批量代收协议**/
    BATCHCOLLECTION("CT00"),
    /**批量代付代付协议**/
    BATCHPAYMENT("CT01"),
    /**实时代收协议**/
    REALTIMECOLLECTION("CT02"),
    /**实时代付协议**/
    REALTIMEPAYMENT("CT03"),
    /**代收付协议**/
    COLLECTIONORPAYMENT("CT99");
    private String code;
    
    private ContractTypeEnum(String code){
        this.code = code;
    }
    
    
    public static ContractTypeEnum fromValue(String value) {
        for(ContractTypeEnum status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return COLLECTIONORPAYMENT;
    }
    
    public String getCode(){
        return code;
    }
}
