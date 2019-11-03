package com.example.cdemo.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * ×Ô¶¨ÒåToast
 * 
 * @author A
 * 
 */
public class DiyToast {
	public static Toast toast;

	public static void showToast(Context context, String string) {
		if (toast == null) {
			toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
		} else {
			toast.setText(string);
		}
		toast.show();
	}
}
