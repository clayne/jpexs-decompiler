/*
 *  Copyright (C) 2010-2013 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.action.model;

import com.jpexs.decompiler.flash.action.swf3.ActionGotoFrame;
import com.jpexs.decompiler.flash.helpers.HilightedTextWriter;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import java.util.List;

public class GotoFrameActionItem extends ActionItem {

    public int frame;

    public GotoFrameActionItem(GraphSourceItem instruction, int frame) {
        super(instruction, PRECEDENCE_PRIMARY);
        this.frame = frame;
    }

    @Override
    public HilightedTextWriter toString(HilightedTextWriter writer, ConstantPool constants) {
        hilight("gotoAndStop(", writer);
        hilight("" + (frame + 1), writer);
        return hilight(")", writer);
    }

    @Override
    public List<GraphSourceItem> toSource(List<Object> localData, SourceGenerator generator) {
        return toSourceMerge(localData, generator, new ActionGotoFrame(frame));
    }

    @Override
    public boolean hasReturnValue() {
        return false;
    }
}
