/*
 * Created on 2004-7-18
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.core;

import java.util.*;

/**
 * Assert class is used for checking at debug time. 
 * DO NOT need to initialize it.
 * 
 * @author Xuefeng
 */
public abstract class Assert {

	/**
	 * To make sure the object is not null.
	 * @param o The object to be tested.
	 */
	public static void checkNull(Object o) {
		assert (o != null) : "Null object detected.";
	}

	/**
	 * To make sure the index is in the bound of the collection.
	 * @param index The index to be tested.
	 * @param c The collection to be operated.
	 */
	public static void checkRange(int index, Collection c) {
		assert c != null : "Collection is null.";
		assert (index >= 0) && (index < c.size()) : "Index out of bound: " + index;
	}

	/**
	 * To make sure the boolean is true.
	 * @param b The boolean value to be tested.
	 */
	public static void checkTrue(boolean b) {
		assert b : "\"false\" value detected.";
	}

    /**
     * To make sure the two Objects are equals.
     * @param obj1 The object 1 to be tested.
     * @param obj2 The object 2 to be tested.
     */
    public static void checkEquals(Object obj1, Object obj2) {
        assert obj1.equals(obj2) : "equals() method return false.";
    }
}
