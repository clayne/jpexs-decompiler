/*
 *  Copyright (C) 2010-2024 JPEXS, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.jpexs.decompiler.flash.ecma;

/**
 * ECMA type enumeration.
 * @author JPEXS
 */
public enum EcmaType {

    NULL(null),
    STRING("String"),
    NUMBER("Number"),
    UNDEFINED(null),
    OBJECT("Object"),
    BOOLEAN("Boolean");

    private final String clsName;

    private EcmaType(String clsName) {
        this.clsName = clsName;
    }

    public String getClassName() {
        return clsName;
    }

    public Object getProperty(Object val, String propName) {
        String cls = getClassName();
        if (cls == null) {
            return null;
        }
        if ("String".equals(cls)) {
            switch (propName) {
                case "length":
                    return EcmaScript.toString(val).length();
            }
        }
        return null;
    }
}
