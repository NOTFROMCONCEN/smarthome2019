package com.example.ddemo.toast;

import android.content.Context;
import android.widget.Toast;

public class DiyToast {
	public static Toast toast;

	/**
	 * @param context
	 * @param string
	 */
	public static void showToast(Context context, String string) {
		if (toast == null) {
			toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
		} else {
			toast.setText(string);
		}
		toast.show();
	}
}
