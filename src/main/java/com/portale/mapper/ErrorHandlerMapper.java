package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ErrorHandlerMapper {

	void submitError(@Param("statuscode") int statuscode, @Param("message") String message,
			@Param("stacktrace") String stacktrace, @Param("entity") int entity, @Param("url") String url,
			@Param("querystring") String querystring, @Param("method") String method);

	List<String> getErrorList(@Param("limit") int limit, @Param("lastResultId") int lastResultId,
			@Param("search") String search);
}
