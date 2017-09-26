package de.maxhenkel.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraftforge.common.config.Configuration;

@Deprecated
public class ConfigTools {

	public static List<ItemStackSelector> getStackList(Configuration config, String name, String category,
			String comment, ItemStackSelector[] defaultValues) {

		String[] def = new String[defaultValues.length];
		for (int i = 0; i < def.length; i++) {
			def[i] = defaultValues[i].toString();
		}

		List<ItemStackSelector> stackList = new ArrayList<ItemStackSelector>();
		String[] array = config.getStringList(name, category, def, comment);

		if (array == null) {
			return stackList;
		}

		for (String s : array) {
			ItemStackSelector selector = ItemStackSelector.fromString(s);
			if (selector != null) {
				stackList.add(selector);
			}
		}

		return stackList;
	}

	public static List<BlockSelector> getBlockList(Configuration config, String name, String category,
			String comment, BlockSelector[] defaultValues) {

		String[] def = new String[defaultValues.length];
		for (int i = 0; i < def.length; i++) {
			def[i] = defaultValues[i].toString();
		}

		List<BlockSelector> stackList = new ArrayList<BlockSelector>();
		String[] array = config.getStringList(name, category, def, comment);

		if (array == null) {
			return stackList;
		}

		for (String s : array) {
			BlockSelector selector = BlockSelector.fromString(s);
			if (selector != null) {
				stackList.add(selector);
			}
		}

		return stackList;
	}
	
	public static List<FluidSelector> getFluidList(Configuration config, String name, String category,
			String comment, FluidSelector[] defaultValues) {

		String[] def = new String[defaultValues.length];
		for (int i = 0; i < def.length; i++) {
			def[i] = defaultValues[i].toString();
		}

		List<FluidSelector> stackList = new ArrayList<FluidSelector>();
		String[] array = config.getStringList(name, category, def, comment);

		if (array == null) {
			return stackList;
		}

		for (String s : array) {
			FluidSelector selector = FluidSelector.fromString(s);
			if (selector != null) {
				stackList.add(selector);
			}
		}

		return stackList;
	}
	
	public static Map<FluidSelector, Integer> getFluidMap(Configuration config, String name, String category,
			String comment, Map<FluidSelector, Integer> defaultValues) {

		Map<String, String> defStr = new HashMap<String, String>();
		for (Entry<FluidSelector, Integer> entry:defaultValues.entrySet()) {
			defStr.put(entry.getKey().toString(), entry.getValue().toString());
		}

		Map<FluidSelector, Integer> ret = new HashMap<FluidSelector, Integer>();
		Map<String, String> strMap = getMap(config, name, category, comment, defStr);

		if (strMap == null) {
			return ret;
		}

		for (Entry<String, String> entry:strMap.entrySet()) {
			FluidSelector selector = FluidSelector.fromString(entry.getKey());
			int value=0;
			try{
				value=Integer.parseInt(entry.getValue());
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			if (selector != null) {
				ret.put(selector, value);
			}
		}

		return ret;
	}
	
	public static Map<String, String> getMap(Configuration config, String name, String category, String comment, Map<String, String> def){
		try{
			String str=config.getString(name, category, mapToJSON(def).toString(), comment);
			JsonParser parser=new JsonParser();
			
			JsonObject obj=parser.parse(str).getAsJsonObject();
			
			return jsonToMap(obj);
		}catch(Exception e){
			e.printStackTrace();
			return def;
		}
	}
	
	public static JsonObject mapToJSON(Map<String, String> map){
		JsonObject obj=new JsonObject();
		for(Entry<String, String> entry:map.entrySet()){
			if(entry.getKey()!=null&&entry.getValue()!=null){
				obj.addProperty(entry.getKey(), entry.getValue());
			}
		}
		return obj;
	}
	
	public static Map<String, String> jsonToMap(JsonObject obj){
		Map<String, String> map=new HashMap<String, String>();
		
		for(Entry<String, JsonElement> entry:obj.entrySet()){
			map.put(entry.getKey(), entry.getValue().getAsString());
		}
		
		return map;
	}

}
