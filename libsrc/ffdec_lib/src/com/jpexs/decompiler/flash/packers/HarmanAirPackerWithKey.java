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
package com.jpexs.decompiler.flash.packers;

import com.jpexs.decompiler.flash.harman.HarmanBinaryDataEncrypt;
import com.jpexs.decompiler.flash.tags.DefineBinaryDataTag;
import com.jpexs.helpers.Helper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Harman AIR SDK packer.
 *
 * @author JPEXS
 */
public class HarmanAirPackerWithKey implements Packer {

    @Override
    public Boolean suitableForBinaryData(DefineBinaryDataTag dataTag) {
        if (dataTag.binaryData.getLength() < 32) {
            return false;
        }
        return null;
    }

    @Override
    public Boolean suitableForData(byte[] data) {
        if (data.length < 32) {
            return false;
        }
        return null;
    }

    @Override
    public boolean decrypt(InputStream is, OutputStream os, String key) throws IOException {
        byte[] encryptedData = Helper.readStream(is);
        byte[] decryptedData = HarmanBinaryDataEncrypt.decrypt(encryptedData, key);
        if (decryptedData == null) {
            return false;
        }
        os.write(decryptedData);
        return true;
    }

    @Override
    public boolean encrypt(InputStream is, OutputStream os, String key) throws IOException {
        byte[] data = Helper.readStream(is);
        byte[] encryptedData = HarmanBinaryDataEncrypt.encrypt(data, key);
        if (encryptedData == null) {
            return false;
        }
        os.write(encryptedData);
        return true;
    }

    @Override
    public String getName() {
        return "Harman AIR SDK with custom key";
    }

    @Override
    public String getIdentifier() {
        return "harmanair_key";
    }

    @Override
    public boolean usesKey() {
        return true;
    }        
}
