package com.piousbox;

import com.piousbox.graphics.Fragmenter;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

/**
 * Provides access to private members in classes.
 */
public class PrivateAccessor {

    public static Object getPrivateField(Object o, String fieldName) {
        // Check we have valid arguments...
        Assert.assertNotNull(o);
        Assert.assertNotNull(fieldName);

        // Go and find the private field...
        final Field fields[] = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if (fieldName.equals(fields[i].getName())) {
                try {
                    fields[i].setAccessible(true);
                    return fields[i].get(o);
                } catch (IllegalAccessException ex) {
                    Assert.fail("IllegalAccessException accessing " + fieldName);
                }
            }
        }
        Assert.fail("Field '" + fieldName + "' not found");
        return null;
    }

    public static Object invokePrivateMethod(Object o, String methodName, Object[] params) {
        // Check we have valid arguments...
        Assert.assertNotNull(o);
        Assert.assertNotNull(methodName);
        Assert.assertNotNull(params);

        // Go and find the private method...
        final Method methods[] = o.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
            if (methodName.equals(methods[i].getName())) {
                try {
                    methods[i].setAccessible(true);
                    return methods[i].invoke(o, params);
                } catch (IllegalAccessException ex) {
                    Assert.fail("IllegalAccessException accessing " + methodName);
                } catch (InvocationTargetException ite) {
                    Assert.fail("InvocationTargetException accessing " + methodName);
                }
            }
        }
        Assert.fail("Method '" + methodName + "' not found");
        return null;
    }

    /**
     * Use invokePrivateMethod instead.
     * 
     * @param o
     * @param string
     * @param params
     * @return
     */
    public static Object invoke_trash(Object o, String string, Object[] params) {
        Object out = null;
        try {
            Class[] parameterTypes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                parameterTypes[i] = params[i].getClass();
            }
            Method m = o.getClass().getDeclaredMethod(string, parameterTypes);
            m.setAccessible(true);
            out = m.invoke(o, params);

        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PrivateAccessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PrivateAccessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PrivateAccessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PrivateAccessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PrivateAccessor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return out;
        }
    }
}
