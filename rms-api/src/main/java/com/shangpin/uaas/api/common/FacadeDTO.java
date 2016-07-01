package com.shangpin.uaas.api.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FacadeDTO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 375666303126465024L;
	private String callId;
	private T val;
	private ErrorDTO err;
	
	public FacadeDTO(String callId){
		this.callId=callId;
	}
}
