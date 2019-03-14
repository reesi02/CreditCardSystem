package com.reeves.simon.creditcardsystem.dto;

/**
 * Data Transport object which is used to carry any error message in a 
 * BAD_REQUEST response.
 * 
 * @author reesi02
 *
 */
public class ErrorResponse {
    private String msgCode;
    
    public String getMsgCode() {
        return this.msgCode;
    }
    
    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}
