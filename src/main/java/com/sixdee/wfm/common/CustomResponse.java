package com.sixdee.wfm.common;

import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "ISE") 
})
public @interface CustomResponse {
	//TODO:WriteUp a Custom action response.
}