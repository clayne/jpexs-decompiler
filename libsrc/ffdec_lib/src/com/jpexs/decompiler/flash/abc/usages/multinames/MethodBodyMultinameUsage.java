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
package com.jpexs.decompiler.flash.abc.usages.multinames;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.types.traits.Traits;

/**
 * Method body multiname usage.
 * @author JPEXS
 */
public class MethodBodyMultinameUsage extends MethodMultinameUsage {

    public MethodBodyMultinameUsage(ABC abc, int multinameIndex, int scriptIndex, int classIndex, int traitIndex, int traitsType, boolean isInitializer, Traits traits, int parentTraitIndex) {
        super(abc, multinameIndex, scriptIndex, classIndex, traitIndex, traitsType, isInitializer, traits, parentTraitIndex);
    }

    @Override
    public String toString() {
        return super.toString() + " body";
    }

    @Override
    public boolean collides(MultinameUsage other) {
        return false;
    }

}
