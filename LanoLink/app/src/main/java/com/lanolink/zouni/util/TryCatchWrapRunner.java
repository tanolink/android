package com.lanolink.zouni.util;

import com.lanolink.zouni.exception.NetworkException;

import java.text.ParseException;

/**
 *
 * @param <T>
 */
public abstract class TryCatchWrapRunner<T> {

	public abstract T run() throws ParseException, NetworkException;

	public TaskResult<T> excute() {
		TaskResult<T> result = new TaskResult<T>();
		try {
			T runRet = run();
			result.setValue(runRet);
			result.setRetCode(TaskResultCode.OK);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			result.setRetCode(TaskResultCode.PARSE_ERROR);
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			result.setRetCode(e.getErrorCode());
		}

		return result;
	}
}
