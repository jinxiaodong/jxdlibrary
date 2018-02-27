package com.project.xiaodong.fflibrary.task;



/**
 * interface of controller
 */
public interface IUIController {
	
	/**
	 * callback to return result
	 * @param id task id
	 * @param msg task result
	 */
	public void refreshUI(int id, MSG msg);

	/**
	 * IUIController identication
	 * @return string
	 */
	public String getIdentification();
}
