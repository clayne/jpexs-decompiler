/*
 *  Copyright (C) 2010-2023 JPEXS, All rights reserved.
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
package com.jpexs.decompiler.flash.types.filters;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.exporters.commonshape.SVGExporter;
import com.jpexs.decompiler.flash.types.BasicType;
import com.jpexs.decompiler.flash.types.annotations.Reserved;
import com.jpexs.decompiler.flash.types.annotations.SWFType;
import com.jpexs.helpers.SerializableImage;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Blur filter based on a sub-pixel precise median filter
 *
 * @author JPEXS
 */
public class BLURFILTER extends FILTER {

    /**
     * Horizontal blur amount
     */
    @SWFType(BasicType.FIXED)
    public double blurX;

    /**
     * Vertical blur amount
     */
    @SWFType(BasicType.FIXED)
    public double blurY;

    /**
     * Number of blur passes
     */
    @SWFType(value = BasicType.UB, count = 5)
    public int passes;

    @Reserved
    @SWFType(value = BasicType.UB, count = 3)
    public int reserved;

    public BLURFILTER() {
        super(1);
    }

    @Override
    public SerializableImage apply(SerializableImage src, double zoom, int srcX, int srcY, int srcW, int srcH) {
        return Filtering.blur(src, (int) Math.round(blurX * zoom), (int) Math.round(blurY * zoom), passes);
    }

    @Override
    public double getDeltaX() {
        return blurX;
    }

    @Override
    public double getDeltaY() {
        return blurY;
    }

    @Override
    public String toSvg(Document document, Element filtersElement, SVGExporter exporter, String in) {
        return blurSvg(blurX, blurY, passes, document, filtersElement, exporter, in);
    }
}
