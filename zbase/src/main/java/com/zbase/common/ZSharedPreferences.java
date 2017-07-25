package com.zbase.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Set;

/**
 * **********************************************************
 * <p/>
 * 说明：pf文件存储
 * <p/>
 * 作者：郑晓辉
 * <p/>
 * 创建日期：2013-05-19
 * <p/>
 * 描述 :自定义pf文件存储，保存各种简单类型
 * <p/>
 * **********************************************************
 */
public class ZSharedPreferences {

	private final static String TAG = ZSharedPreferences.class.getSimpleName();//定义单个配置文件的文件名，这里就是类名
	public final static String SHARED_PREF_NAME = TAG;//定义单个配置文件的文件名

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;

	private static ZSharedPreferences mySharedPreferences = null;
	public static final String FIRST_LAUNCH = "first_launch"; //是否第一次启动APP

	public ZSharedPreferences(Context context) {
		try {
			if (context != null) {
				sharedPreferences = context.getSharedPreferences(
						ZSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);//定义单个配置文件的文件名
				editor = sharedPreferences.edit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ZSharedPreferences getInstance(Context context) {
		if (mySharedPreferences == null) {
			mySharedPreferences = new ZSharedPreferences(context);
		}

		return mySharedPreferences;
	}

	public void putString(String entry, String value) {
		if (editor != null) {
			editor.putString(entry.toString().trim(), value);
			editor.commit();
		}
	}

	public void putInt(String entry, int value) {
		if (editor != null) {
			editor.putInt(entry.toString().trim(), value);
			editor.commit();
		}
	}

	public void putFloat(String entry, float value) {
		if (editor != null) {
			editor.putFloat(entry.toString().trim(), value);
			editor.commit();
		}
	}

	public void putStringSet(String entry, Set<String> value) {
		if (editor != null) {
			editor.putStringSet(entry.toString().trim(), value);
			editor.commit();
		}
	}

	public void putBoolean(String entry, Boolean value) {
		if (editor != null) {
			editor.putBoolean(entry.toString().trim(), value);
			editor.commit();
		}
	}

	public void putJsonBean(String entry, Object object) {
		if (editor != null) {
			editor.putString(entry.toString().trim(), new Gson().toJson(object));
			editor.commit();
		}
	}

	public String getString(String entry, String defaultValue) {
		if (editor == null) {
			return defaultValue;
		}
		try {
			return sharedPreferences.getString(entry.toString().trim(), defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public int getInt(final String entry, int defaultValue) {
		if (editor == null) {
			return defaultValue;
		}
		try {
			return sharedPreferences.getInt(entry.toString(), defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public float getFloat(final String entry, float defaultValue) {
		if (editor == null) {
			return defaultValue;
		}
		try {
			return sharedPreferences.getFloat(entry.toString(), defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}


	public Set<String> getStringSet(final String entry, Set<String> defaultValue) {
		if (editor == null) {
			return defaultValue;
		}
		try {
			return sharedPreferences.getStringSet(entry.toString(), defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}


	public boolean getBoolean(final String entry, boolean defaultValue) {
		if (editor == null) {
			return defaultValue;
		}
		try {
			return sharedPreferences.getBoolean(entry.toString(), defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public <T> T getJsonBean(String entry, Class<T> cls) {
		if (editor == null) {
			return null;
		}
		try {
			String jsonString = sharedPreferences.getString(entry.toString().trim(), "");
			if (!TextUtils.isEmpty(jsonString)) {
				T t = new Gson().fromJson(jsonString, cls);
				return t;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 清空SharedPreferences里所有数据,就是清空整个配置文件
	 */
	public void cleanAll() {
		if (editor != null) {
			editor.clear();
			editor.commit();
		}
	}

	/**
	 * 删除SharedPreferences里指定key对应的数据项,就是删除配置文件的一条配置
	 */
	public void remove(String key) {
		if (editor != null) {
			editor.remove(key);
			editor.commit();
		}
	}

	/**
	 * 是否第一次启动app
	 * @return
     */
	public boolean isFirstLaunchApp(){
		return getBoolean(FIRST_LAUNCH, true);
	}

	/**
	 * 设置已经启动过，不是第一次
	 */
	public void setHasFirstLaunchApp(){
		putBoolean(FIRST_LAUNCH, false);
	}

}
