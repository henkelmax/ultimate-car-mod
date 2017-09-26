package de.maxhenkel.tools.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class JsonConfig {

	private File file;
	private JSONObject jsonObject;

	public JsonConfig(String filename) {
		this(new File(filename));
	}
	
	public JsonConfig(File file) {
		this.file = file;

		try {
			FileInputStream inputStream = new FileInputStream(file);
			this.jsonObject = new JSONObject(IOUtils.toString(inputStream));
			inputStream.close();
		} catch (FileNotFoundException e) {
			this.jsonObject = new JSONObject();
		} catch (JSONException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			this.jsonObject = new JSONObject();
		}

	}

	public boolean put(String key, Object value) {
		try {
			jsonObject.put(key, value);
			this.save();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean putStringList(String key, List<String> value) {
		try {
			return put(key, new JSONArray(value));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public Object get(String key, Object defValue) {
		Object o = null;

		try {
			o = jsonObject.get(key);
		} catch (JSONException e) {
		}

		if (o == null) {
			put(key, defValue);
			return defValue;
		}

		return o;
	}
	
	public JSONArray getJsonArray(String key, JSONArray defValue) {
		JSONArray o = null;

		try {
			o = jsonObject.getJSONArray(key);
		} catch (JSONException e) {
		}

		if (o == null) {
			put(key, defValue);
			return defValue;
		}

		return o;
	}
	
	public JSONObject getJsonObject(String key, JSONObject defValue) {
		JSONObject o = null;

		try {
			o = jsonObject.getJSONObject(key);
		} catch (JSONException e) {
		}

		if (o == null) {
			put(key, defValue);
			return defValue;
		}

		return o;
	}

	public int getInt(String key, int defValue) {
		try {
			return jsonObject.getInt(key);
		} catch (JSONException e) {
			put(key, defValue);
			return defValue;
		}
	}

	public String getString(String key, String defValue) {
		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			put(key, defValue);
			return defValue;
		}
	}

	public boolean getBoolean(String key, boolean defValue) {
		try {
			return jsonObject.getBoolean(key);
		} catch (JSONException e) {
			put(key, defValue);
			return defValue;
		}
	}

	public double getDouble(String key, double defValue) {
		try {
			return jsonObject.getDouble(key);
		} catch (JSONException e) {
			put(key, defValue);
			return defValue;
		}
	}

	public long getLong(String key, long defValue) {
		try {
			return jsonObject.getLong(key);
		} catch (JSONException e) {
			put(key, defValue);
			return defValue;
		}
	}

	public List<String> getStringList(String key, List<String> defValue) {
		List<String> l = new ArrayList<String>();
		JSONArray jArray;
		try {
			jArray = jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			put(key, defValue);
			return defValue;
		}

		for (int i = 0; i < jArray.length(); i++) {
			try {
				l.add(jArray.getString(i));
			} catch (JSONException e) {
			}
		}
		return l;
	}

	public String[] getStringArray(String key, String[] defValue) {
		JSONArray jArray;
		try {
			jArray = jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			put(key, defValue);
			return defValue;
		}

		String[] out = new String[jArray.length()];

		for (int i = 0; i < jArray.length(); i++) {
			try {
				out[i] = jArray.getString(i);
			} catch (JSONException e) {
			}
		}
		return out;
	}

	private void save() throws IOException, JSONException {
		File f = new File(file.getAbsolutePath());

		if(f.getParentFile()!=null){
			f.getParentFile().mkdirs();
		}

		IOUtils.write(jsonObject.toString(1), new FileOutputStream(f));
	}
	
	@Override
	public String toString() {
		return "Config: " +file.getAbsolutePath();
	}

}
