/*
 *  Copyright (C) 2010-2022 JPEXS, All rights reserved.
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
package com.jpexs.decompiler.flash.action.swf4;

import com.jpexs.decompiler.flash.BaseLocalData;
import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.action.ActionScriptObject;
import com.jpexs.decompiler.flash.action.LocalDataArea;
import com.jpexs.decompiler.flash.action.model.DirectValueActionItem;
import com.jpexs.decompiler.flash.action.model.GetPropertyActionItem;
import com.jpexs.decompiler.flash.ecma.EcmaScript;
import com.jpexs.decompiler.flash.ecma.Undefined;
import com.jpexs.decompiler.flash.types.annotations.SWFVersion;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SecondPassData;
import com.jpexs.decompiler.graph.TranslateStack;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JPEXS
 */
@SWFVersion(from = 4)
public class ActionGetProperty extends Action {

    public ActionGetProperty() {
        super(0x22, 0);
    }

    @Override
    public String toString() {
        return "GetProperty";
    }

    @Override
    public boolean execute(LocalDataArea lda) {
        if (!lda.stackHasMinSize(2)) {
            return false;
        }

        int index = EcmaScript.toInt32(lda.pop());
        String target = EcmaScript.toString(lda.pop());
        Object movieClip = lda.stage.getMember(target);
        if (movieClip instanceof ActionScriptObject) {
            lda.push(((ActionScriptObject) movieClip).getProperty(index));
            return true;
        }
        lda.push(Undefined.INSTANCE);
        return true;
    }

    @Override
    public void translate(SecondPassData secondPassData, boolean insideDoInitAction, GraphSourceItem lineStartAction, TranslateStack stack, List<GraphTargetItem> output, HashMap<Integer, String> regNames, HashMap<String, GraphTargetItem> variables, HashMap<String, GraphTargetItem> functions, int staticOperation, String path) {
        GraphTargetItem index = stack.pop();
        GraphTargetItem target = stack.pop();
        int indexInt = 0;
        if (index instanceof DirectValueActionItem) {
            Object value = ((DirectValueActionItem) index).value;
            if (value instanceof Long) {
                indexInt = (int) (long) (Long) value;
            } else if (value instanceof Double) {
                indexInt = (int) Math.round((Double) value);
            } else if (value instanceof Float) {
                indexInt = (int) Math.round((Float) value);
            } else if (((DirectValueActionItem) index).isString()) {
                try {
                    indexInt = Integer.parseInt(((DirectValueActionItem) index).toString());
                } catch (NumberFormatException nfe) {
                    Logger.getLogger(ActionGetProperty.class.getName()).log(Level.SEVERE, "Invalid property index: {0}", index.toString());
                }
            }
        } else {
            Logger.getLogger(ActionGetProperty.class.getName()).log(Level.SEVERE, "Invalid property index: {0}", index.getClass().getSimpleName());
        }
        stack.push(new GetPropertyActionItem(this, lineStartAction, target, indexInt));
    }

    @Override
    public int getStackPopCount(BaseLocalData localData, TranslateStack stack) {
        return 2;
    }

    @Override
    public int getStackPushCount(BaseLocalData localData, TranslateStack stack) {
        return 1;
    }
}
