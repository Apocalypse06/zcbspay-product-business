
package com.zcbspay.platform.business.concentrate.enums;
/**
 * 
 *合同收付类型
 *
 * @author lianhai
 */
public enum ContractTypeEnum {
    /**代收协议**/
    COLLECTION("CT00"),
    /**代付协议**/
    PAYMENT("CT01"),
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
