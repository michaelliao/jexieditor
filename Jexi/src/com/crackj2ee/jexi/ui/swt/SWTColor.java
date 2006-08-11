/*
 * Created on 2004-7-31
 * Author: Xuefeng, Copyright (C) 2004, Xuefeng.
 */
package com.crackj2ee.jexi.ui.swt;

/**
 * The implementation of Color. 
 * 
 * @author Xuefeng
 */
final class SWTColor implements com.crackj2ee.jexi.ui.Color {

    // store the key:
    private Integer key;
    // store the color resource:
    private org.eclipse.swt.graphics.Color color;

    // the ref count:
    private int refCount = 0;

    // key is used for get from the hash table:
    SWTColor(Integer key, org.eclipse.swt.graphics.Color color) {
        this.key = key;
        this.color = color;
        addRef();
    }

    public static Integer toKey(int r, int g, int b) {
        return new Integer( (r<<16) | (g<<8) | b );
    }

    /**
     * Get the key that used in hash table. 
     * 
     * @return Integer key.
     */
    public Integer getKey() {
        return this.key;
    }

    /**
     * Compare two objects. 
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if(this==o)
            return true;
        if(o instanceof SWTColor) {
            return this.key==((SWTColor)o).key;
        }
        return false;
    }

    /**
     * get the hash code. 
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return key.intValue();
    }

    /**
     * Dispose this color. 
     * 
     * @see com.crackj2ee.jexi.ui.Color#dispose()
     */
    public void dispose() {
        removeRef();
        if(this.refCount>0)
            return;
        this.color.dispose();
        ((SWTColorFactory)com.crackj2ee.jexi.ui.ColorFactory.instance()).remove(this);
    }

    /**
     * Get the native color. 
     */
    public org.eclipse.swt.graphics.Color nativeColor() {
        return this.color;
    }

    protected void addRef() {
        this.refCount++;
    }

    protected void removeRef() {
        this.refCount--;
    }

    protected int refCount() {
        return this.refCount;
    }

    public void debug() {
        System.out.println("  Color=" + key.intValue() + ", ref=" + refCount);
    }
}
