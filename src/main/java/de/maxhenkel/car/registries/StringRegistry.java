package de.maxhenkel.car.registries;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StringRegistry<T> implements Iterable<T>{

	private Map<String, T> map;
	
	public StringRegistry() {
		this.map=new HashMap<>();
	}
	
	public StringRegistry<T> register(String name, T object) {
		map.put(name, object);
		return this;
	}
	
	public StringRegistry<T> unregister(String name) {
		map.remove(name);
		return this;
	}
	
	public T getEntry(String name) {
		return map.get(name);
	}
	
	public Collection<T> getAll(){
		return map.values();
	}

	@Override
	public Iterator<T> iterator() {
		return map.values().iterator();
	}
	
}
