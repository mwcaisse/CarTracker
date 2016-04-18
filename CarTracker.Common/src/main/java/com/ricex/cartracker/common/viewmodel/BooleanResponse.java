package com.ricex.cartracker.common.viewmodel;

/** Boolean wrapper for Entity Response
 * 
 * @author Mitchell Caisse
 *
 */
public class BooleanResponse extends EntityResponse<Boolean> {

	public BooleanResponse(Boolean data) {
		super(data);
	}
	
	public BooleanResponse(String errorMessage){
		super(false, errorMessage);
	}

}
