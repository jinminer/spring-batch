/**
 * 
 */
package com.jinm.example.ch10.retry;

/**
 * 
 *
 * 2013-10-21下午10:09:41
 */
public class MockBRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3841087497940611523L;
	public MockBRuntimeException(){
		super();
	}
	public MockBRuntimeException(String message){
		super(message);
	}
}
