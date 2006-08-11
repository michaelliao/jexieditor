/*
 * Created on 2004-7-18
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import java.util.*;

/**
 * CharFactory is responsible for create Char object. 
 * <b>NOTE</b> it is a singleton pattern.
 * 
 * @author Xuefeng
 */
public class CharFactory {

	// Singleton pattern:
	private static CharFactory instance = new CharFactory();

	// To store & cache all Char objects, may up to 65536 objects:
	private Hashtable char_map = new Hashtable();

	// To make sure it cannot be initialized by client directly:
	private CharFactory() {
		// put the static Char object to hash table:
		char_map.put(new Integer(Char.RETURN.charValue()), Char.RETURN);
		char_map.put(new Integer(Char.TABLE.charValue()), Char.TABLE);
		char_map.put(new Integer(Char.SPACE.charValue()), Char.SPACE);
	}

	/**
	 * Get the singleton instance.
	 * @return Char factory.
	 */
	public static CharFactory instance() {
		return instance;
	}

	/**
	 * Create a new Char object by the given char. 
	 * Once a Char object is created, it can be 
	 * used again and again by the later calls. 
	 * 
	 * @param c The char value.
	 * @return A Char object.
	 */
	public Char createChar(char c) {
		Char ch = (Char) char_map.get(new Integer(c));
		if( ch!=null )
			return ch;
		ch = new Char(c);
		char_map.put(new Integer(c), ch); // <key, value>
		return ch;
	}
}
