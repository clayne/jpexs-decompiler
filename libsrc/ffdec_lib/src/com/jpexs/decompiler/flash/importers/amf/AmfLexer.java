/* The following code was generated by JFlex 1.6.0 */

/*
 *  Copyright (C) 2010-2016 JPEXS, All rights reserved.
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
package com.jpexs.decompiler.flash.importers.amf;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.0
 * from the specification file <tt>C:/Dropbox/Programovani/JavaSE/FFDec/libsrc/ffdec_lib/lexers/amf.flex</tt>
 */
public final class AmfLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;
  public static final int CHARLITERAL = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\7\1\3\1\2\1\54\1\55\1\1\16\7\4\0\1\3\1\0"+
    "\1\50\1\10\1\6\2\0\1\52\2\0\1\5\1\22\1\46\1\12"+
    "\1\20\1\4\1\11\3\16\4\17\2\13\1\47\6\0\4\15\1\21"+
    "\1\15\21\6\1\14\2\6\1\44\1\23\1\45\1\0\1\6\1\0"+
    "\1\33\1\51\1\15\1\27\1\30\1\31\2\6\1\32\1\6\1\37"+
    "\1\26\1\6\1\24\1\40\2\6\1\36\1\34\1\35\1\25\1\6"+
    "\1\41\1\53\2\6\1\42\1\0\1\43\1\0\6\7\1\56\32\7"+
    "\2\0\4\6\4\0\1\6\2\0\1\7\7\0\1\6\4\0\1\6"+
    "\5\0\27\6\1\0\37\6\1\0\u01ca\6\4\0\14\6\16\0\5\6"+
    "\7\0\1\6\1\0\1\6\21\0\160\7\5\6\1\0\2\6\2\0"+
    "\4\6\1\0\1\6\6\0\1\6\1\0\3\6\1\0\1\6\1\0"+
    "\24\6\1\0\123\6\1\0\213\6\1\0\5\7\2\0\246\6\1\0"+
    "\46\6\2\0\1\6\6\0\51\6\6\0\1\6\1\0\55\7\1\0"+
    "\1\7\1\0\2\7\1\0\2\7\1\0\1\7\10\0\33\6\4\0"+
    "\4\6\15\0\6\7\5\0\1\6\4\0\13\7\1\0\1\7\3\0"+
    "\53\6\37\7\4\0\2\6\1\7\143\6\1\0\1\6\10\7\1\0"+
    "\6\7\2\6\2\7\1\0\4\7\2\6\12\7\3\6\2\0\1\6"+
    "\17\0\1\7\1\6\1\7\36\6\33\7\2\0\131\6\13\7\1\6"+
    "\16\0\12\7\41\6\11\7\2\6\4\0\1\6\2\0\1\7\30\6"+
    "\4\7\1\6\11\7\1\6\3\7\1\6\5\7\22\0\31\6\3\7"+
    "\4\0\13\6\5\0\30\6\1\0\6\6\1\0\2\7\6\0\10\7"+
    "\52\6\72\7\66\6\3\7\1\6\22\7\1\6\7\7\12\6\2\7"+
    "\2\0\12\7\1\0\20\6\3\7\1\0\10\6\2\0\2\6\2\0"+
    "\26\6\1\0\7\6\1\0\1\6\3\0\4\6\2\0\1\7\1\6"+
    "\7\7\2\0\2\7\2\0\3\7\1\6\10\0\1\7\4\0\2\6"+
    "\1\0\3\6\2\7\2\0\12\7\4\6\7\0\2\6\1\0\1\7"+
    "\2\0\3\7\1\0\6\6\4\0\2\6\2\0\26\6\1\0\7\6"+
    "\1\0\2\6\1\0\2\6\1\0\2\6\2\0\1\7\1\0\5\7"+
    "\4\0\2\7\2\0\3\7\3\0\1\7\7\0\4\6\1\0\1\6"+
    "\7\0\14\7\3\6\1\7\13\0\3\7\1\0\11\6\1\0\3\6"+
    "\1\0\26\6\1\0\7\6\1\0\2\6\1\0\5\6\2\0\1\7"+
    "\1\6\10\7\1\0\3\7\1\0\3\7\2\0\1\6\17\0\2\6"+
    "\2\7\2\0\12\7\1\0\1\6\7\0\1\6\6\7\1\0\3\7"+
    "\1\0\10\6\2\0\2\6\2\0\26\6\1\0\7\6\1\0\2\6"+
    "\1\0\5\6\2\0\1\7\1\6\7\7\2\0\2\7\2\0\3\7"+
    "\7\0\3\7\4\0\2\6\1\0\3\6\2\7\2\0\12\7\1\0"+
    "\1\6\20\0\1\7\1\6\1\0\6\6\3\0\3\6\1\0\4\6"+
    "\3\0\2\6\1\0\1\6\1\0\2\6\3\0\2\6\3\0\3\6"+
    "\3\0\14\6\4\0\5\7\3\0\3\7\1\0\4\7\2\0\1\6"+
    "\6\0\1\7\16\0\12\7\11\0\1\6\6\0\5\7\10\6\1\0"+
    "\3\6\1\0\27\6\1\0\20\6\2\0\1\7\1\6\7\7\1\0"+
    "\3\7\1\0\4\7\7\0\2\7\1\0\3\6\2\0\1\6\2\0"+
    "\2\6\2\7\2\0\12\7\20\0\1\6\3\7\1\0\10\6\1\0"+
    "\3\6\1\0\27\6\1\0\12\6\1\0\5\6\2\0\1\7\1\6"+
    "\7\7\1\0\3\7\1\0\4\7\7\0\2\7\6\0\2\6\1\0"+
    "\2\6\2\7\2\0\12\7\1\0\2\6\15\0\4\7\11\6\1\0"+
    "\3\6\1\0\51\6\2\7\1\6\7\7\1\0\3\7\1\0\4\7"+
    "\1\6\5\0\3\6\1\7\7\0\3\6\2\7\2\0\12\7\12\0"+
    "\6\6\1\0\3\7\1\0\22\6\3\0\30\6\1\0\11\6\1\0"+
    "\1\6\2\0\7\6\3\0\1\7\4\0\6\7\1\0\1\7\1\0"+
    "\10\7\6\0\12\7\2\0\2\7\15\0\60\6\1\7\2\6\7\7"+
    "\4\0\10\6\10\7\1\0\12\7\47\0\2\6\1\0\1\6\1\0"+
    "\5\6\1\0\30\6\1\0\1\6\1\0\12\6\1\7\2\6\11\7"+
    "\1\6\2\0\5\6\1\0\1\6\1\0\6\7\2\0\12\7\2\0"+
    "\4\6\40\0\1\6\27\0\2\7\6\0\12\7\13\0\1\7\1\0"+
    "\1\7\1\0\1\7\4\0\2\7\10\6\1\0\44\6\4\0\24\7"+
    "\1\0\2\7\5\6\13\7\1\0\44\7\11\0\1\7\71\0\53\6"+
    "\24\7\1\6\12\7\6\0\6\6\4\7\4\6\3\7\1\6\3\7"+
    "\2\6\7\7\3\6\4\7\15\6\14\7\1\6\17\7\2\0\46\6"+
    "\1\0\1\6\5\0\1\6\2\0\53\6\1\0\u014d\6\1\0\4\6"+
    "\2\0\7\6\1\0\1\6\1\0\4\6\2\0\51\6\1\0\4\6"+
    "\2\0\41\6\1\0\4\6\2\0\7\6\1\0\1\6\1\0\4\6"+
    "\2\0\17\6\1\0\71\6\1\0\4\6\2\0\103\6\2\0\3\7"+
    "\40\0\20\6\20\0\126\6\2\0\6\6\3\0\u026c\6\2\0\21\6"+
    "\1\0\32\6\5\0\113\6\3\0\13\6\7\0\22\6\4\7\11\0"+
    "\23\6\3\7\13\0\22\6\2\7\14\0\15\6\1\0\3\6\1\0"+
    "\2\7\14\0\64\6\40\7\3\0\1\6\3\0\2\6\1\7\2\0"+
    "\12\7\41\0\17\7\6\0\131\6\7\0\5\6\2\7\42\6\1\7"+
    "\1\6\5\0\106\6\12\0\37\6\1\0\14\7\4\0\14\7\12\0"+
    "\12\7\36\6\2\0\5\6\13\0\54\6\4\0\32\6\6\0\12\7"+
    "\46\0\27\6\5\7\4\0\65\6\12\7\1\0\35\7\2\0\13\7"+
    "\6\0\12\7\15\0\1\6\10\0\16\7\1\0\20\7\61\0\5\7"+
    "\57\6\21\7\10\6\3\0\12\7\21\0\11\7\14\0\3\7\36\6"+
    "\15\7\2\6\12\7\54\6\16\7\14\0\44\6\24\7\10\0\12\7"+
    "\3\0\3\6\12\7\44\6\2\0\11\6\7\0\53\6\2\0\3\6"+
    "\20\0\3\7\1\0\25\7\4\6\1\7\6\6\1\7\2\6\3\7"+
    "\1\6\5\0\300\6\100\7\u0116\6\2\0\6\6\2\0\46\6\2\0"+
    "\6\6\2\0\10\6\1\0\1\6\1\0\1\6\1\0\1\6\1\0"+
    "\37\6\2\0\65\6\1\0\7\6\1\0\1\6\3\0\3\6\1\0"+
    "\7\6\3\0\4\6\2\0\6\6\4\0\15\6\5\0\3\6\1\0"+
    "\7\6\16\0\5\7\30\0\1\54\1\54\5\7\20\0\2\6\23\0"+
    "\1\6\13\0\5\7\1\0\12\7\1\0\1\6\15\0\1\6\20\0"+
    "\15\6\3\0\41\6\17\0\15\7\4\0\1\7\3\0\14\7\21\0"+
    "\1\6\4\0\1\6\2\0\12\6\1\0\1\6\3\0\5\6\6\0"+
    "\1\6\1\0\1\6\1\0\1\6\1\0\4\6\1\0\13\6\2\0"+
    "\4\6\5\0\5\6\4\0\1\6\21\0\51\6\u0a77\0\345\6\6\0"+
    "\4\6\3\7\2\6\14\0\46\6\1\0\1\6\5\0\1\6\2\0"+
    "\70\6\7\0\1\6\17\0\1\7\27\6\11\0\7\6\1\0\7\6"+
    "\1\0\7\6\1\0\7\6\1\0\7\6\1\0\7\6\1\0\7\6"+
    "\1\0\7\6\1\0\40\7\57\0\1\6\u01d5\0\3\6\31\0\11\6"+
    "\6\7\1\0\5\6\2\0\5\6\4\0\126\6\2\0\2\7\2\0"+
    "\3\6\1\0\132\6\1\0\4\6\5\0\53\6\1\0\136\6\21\0"+
    "\40\6\60\0\20\6\u0200\0\u19c0\6\100\0\u568d\6\103\0\56\6\2\0"+
    "\u010d\6\3\0\20\6\12\7\2\6\24\0\57\6\1\7\4\0\12\7"+
    "\1\0\37\6\2\7\120\6\2\7\45\0\11\6\2\0\147\6\2\0"+
    "\100\6\5\0\2\6\1\0\1\6\1\0\5\6\30\0\20\6\1\7"+
    "\3\6\1\7\4\6\1\7\27\6\5\7\4\0\1\7\13\0\1\6"+
    "\7\0\64\6\14\0\2\7\62\6\22\7\12\0\12\7\6\0\22\7"+
    "\6\6\3\0\1\6\1\0\2\6\13\7\34\6\10\7\2\0\27\6"+
    "\15\7\14\0\35\6\3\0\4\7\57\6\16\7\16\0\1\6\12\7"+
    "\6\0\5\6\1\7\12\6\12\7\5\6\1\0\51\6\16\7\11\0"+
    "\3\6\1\7\10\6\2\7\2\0\12\7\6\0\27\6\3\0\1\6"+
    "\3\7\62\6\1\7\1\6\3\7\2\6\2\7\5\6\2\7\1\6"+
    "\1\7\1\6\30\0\3\6\2\0\13\6\5\7\2\0\3\6\2\7"+
    "\12\0\6\6\2\0\6\6\2\0\6\6\11\0\7\6\1\0\7\6"+
    "\1\0\53\6\1\0\16\6\6\0\163\6\10\7\1\0\2\7\2\0"+
    "\12\7\6\0\u2ba4\6\14\0\27\6\4\0\61\6\u2104\0\u016e\6\2\0"+
    "\152\6\46\0\7\6\14\0\5\6\5\0\1\6\1\7\12\6\1\0"+
    "\15\6\1\0\5\6\1\0\1\6\1\0\2\6\1\0\2\6\1\0"+
    "\154\6\41\0\u016b\6\22\0\100\6\2\0\66\6\50\0\15\6\3\0"+
    "\20\7\20\0\20\7\3\0\2\6\30\0\3\6\31\0\1\6\6\0"+
    "\5\6\1\0\207\6\2\0\1\7\4\0\1\6\13\0\12\7\7\0"+
    "\32\6\4\0\1\6\1\0\32\6\13\0\131\6\3\0\6\6\2\0"+
    "\6\6\2\0\6\6\2\0\3\6\3\0\2\6\3\0\2\6\22\0"+
    "\3\7\4\0\14\6\1\0\32\6\1\0\23\6\1\0\2\6\1\0"+
    "\17\6\2\0\16\6\42\0\173\6\105\0\65\6\210\0\1\7\202\0"+
    "\35\6\3\0\61\6\17\0\1\7\37\0\40\6\15\0\36\6\5\0"+
    "\46\6\5\7\5\0\36\6\2\0\44\6\4\0\10\6\1\0\5\6"+
    "\52\0\236\6\2\0\12\7\6\0\44\6\4\0\44\6\4\0\50\6"+
    "\10\0\64\6\14\0\13\6\1\0\17\6\1\0\7\6\1\0\2\6"+
    "\1\0\13\6\1\0\17\6\1\0\7\6\1\0\2\6\103\0\u0137\6"+
    "\11\0\26\6\12\0\10\6\30\0\6\6\1\0\52\6\1\0\11\6"+
    "\105\0\6\6\2\0\1\6\1\0\54\6\1\0\2\6\3\0\1\6"+
    "\2\0\27\6\12\0\27\6\11\0\37\6\101\0\23\6\1\0\2\6"+
    "\12\0\26\6\12\0\32\6\106\0\70\6\6\0\2\6\100\0\1\6"+
    "\3\7\1\0\2\7\5\0\4\7\4\6\1\0\3\6\1\0\35\6"+
    "\2\0\3\7\4\0\1\7\40\0\35\6\3\0\35\6\43\0\10\6"+
    "\1\0\34\6\2\7\31\0\66\6\12\0\26\6\12\0\23\6\15\0"+
    "\22\6\156\0\111\6\67\0\63\6\15\0\63\6\15\0\44\6\4\7"+
    "\10\0\12\7\u0146\0\52\6\1\0\2\7\3\0\2\6\116\0\35\6"+
    "\12\0\1\6\10\0\26\6\13\7\37\0\22\6\4\7\52\0\25\6"+
    "\33\0\27\6\11\0\3\7\65\6\17\7\37\0\13\7\2\6\2\7"+
    "\1\6\11\0\4\7\55\6\13\7\2\0\1\7\4\0\1\7\12\0"+
    "\1\7\2\0\31\6\7\0\12\7\6\0\3\7\44\6\16\7\1\0"+
    "\12\7\4\0\1\6\2\7\1\6\10\0\43\6\1\7\2\0\1\6"+
    "\11\0\3\7\60\6\16\7\4\6\4\0\4\7\1\0\14\7\1\6"+
    "\1\0\1\6\43\0\22\6\1\0\31\6\14\7\6\0\1\7\101\0"+
    "\7\6\1\0\1\6\1\0\4\6\1\0\17\6\1\0\12\6\7\0"+
    "\57\6\14\7\5\0\12\7\6\0\4\7\1\0\10\6\2\0\2\6"+
    "\2\0\26\6\1\0\7\6\1\0\2\6\1\0\5\6\1\0\2\7"+
    "\1\6\7\7\2\0\2\7\2\0\3\7\2\0\1\6\6\0\1\7"+
    "\5\0\5\6\2\7\2\0\7\7\3\0\5\7\213\0\65\6\22\7"+
    "\4\6\5\0\12\7\4\0\1\7\3\6\36\0\60\6\24\7\2\6"+
    "\1\0\1\6\10\0\12\7\246\0\57\6\7\7\2\0\11\7\27\0"+
    "\4\6\2\7\42\0\60\6\21\7\3\0\1\6\13\0\12\7\46\0"+
    "\53\6\15\7\1\6\7\0\12\7\66\0\33\6\2\0\17\7\4\0"+
    "\12\7\6\0\7\6\271\0\54\6\17\7\145\0\100\6\12\7\25\0"+
    "\10\6\2\0\1\6\2\0\10\6\1\0\2\6\1\0\30\6\6\7"+
    "\1\0\2\7\2\0\4\7\1\6\1\7\1\6\2\7\14\0\12\7"+
    "\106\0\10\6\2\0\47\6\7\7\2\0\7\7\1\6\1\0\1\6"+
    "\1\7\33\0\1\6\12\7\50\6\7\7\1\6\4\7\10\0\1\7"+
    "\10\0\1\6\13\7\56\6\20\7\3\0\1\6\22\0\111\6\u0107\0"+
    "\11\6\1\0\45\6\10\7\1\0\10\7\1\6\17\0\12\7\30\0"+
    "\36\6\2\0\26\7\1\0\16\7\111\0\7\6\1\0\2\6\1\0"+
    "\46\6\6\7\3\0\1\7\1\0\2\7\1\0\7\7\1\6\1\7"+
    "\10\0\12\7\6\0\6\6\1\0\2\6\1\0\40\6\5\7\1\0"+
    "\2\7\1\0\5\7\1\6\7\0\12\7\u0136\0\23\6\4\7\271\0"+
    "\1\6\54\0\4\6\37\0\u039a\6\146\0\157\6\21\0\304\6\u0a4c\0"+
    "\141\6\17\0\u042f\6\1\0\11\7\u0fc7\0\u0247\6\u21b9\0\u0239\6\7\0"+
    "\37\6\1\0\12\7\6\0\117\6\1\0\12\7\6\0\36\6\2\0"+
    "\5\7\13\0\60\6\7\7\11\0\4\6\14\0\12\7\11\0\25\6"+
    "\5\0\23\6\u02b0\0\100\6\200\0\113\6\4\0\1\7\1\6\67\7"+
    "\7\0\4\7\15\6\100\0\2\6\1\0\1\6\1\7\13\0\2\7"+
    "\16\0\u17f8\6\10\0\u04d6\6\52\0\11\6\u22e7\0\4\6\1\0\7\6"+
    "\1\0\2\6\1\0\u0123\6\55\0\3\6\21\0\4\6\10\0\u018c\6"+
    "\u0904\0\153\6\5\0\15\6\3\0\11\6\7\0\12\6\3\0\2\7"+
    "\1\0\4\7\u125c\0\56\7\2\0\27\7\u021e\0\5\7\3\0\26\7"+
    "\2\0\7\7\36\0\4\7\224\0\3\7\u01bb\0\125\6\1\0\107\6"+
    "\1\0\2\6\2\0\1\6\2\0\2\6\2\0\4\6\1\0\14\6"+
    "\1\0\1\6\1\0\7\6\1\0\101\6\1\0\4\6\2\0\10\6"+
    "\1\0\7\6\1\0\34\6\1\0\4\6\1\0\5\6\1\0\1\6"+
    "\3\0\7\6\1\0\u0154\6\2\0\31\6\1\0\31\6\1\0\37\6"+
    "\1\0\31\6\1\0\37\6\1\0\31\6\1\0\37\6\1\0\31\6"+
    "\1\0\37\6\1\0\31\6\1\0\10\6\2\0\62\7\u0200\0\67\7"+
    "\4\0\62\7\10\0\1\7\16\0\1\7\26\0\5\7\1\0\17\7"+
    "\u0450\0\37\6\341\0\7\7\1\0\21\7\2\0\7\7\1\0\2\7"+
    "\1\0\5\7\325\0\55\6\3\0\7\7\7\6\2\0\12\7\4\0"+
    "\1\6\u0141\0\36\6\1\7\21\0\54\6\16\7\5\0\1\6\u04e0\0"+
    "\7\6\1\0\4\6\1\0\2\6\1\0\17\6\1\0\305\6\13\0"+
    "\7\7\51\0\104\6\7\7\1\6\4\0\12\7\u0356\0\1\6\u014f\0"+
    "\4\6\1\0\33\6\1\0\2\6\1\0\1\6\2\0\1\6\1\0"+
    "\12\6\1\0\4\6\1\0\1\6\1\0\1\6\6\0\1\6\4\0"+
    "\1\6\1\0\1\6\1\0\1\6\1\0\3\6\1\0\2\6\1\0"+
    "\1\6\2\0\1\6\1\0\1\6\1\0\1\6\1\0\1\6\1\0"+
    "\1\6\1\0\2\6\1\0\1\6\2\0\4\6\1\0\7\6\1\0"+
    "\4\6\1\0\4\6\1\0\1\6\1\0\12\6\1\0\21\6\5\0"+
    "\3\6\1\0\5\6\1\0\21\6\u0d34\0\12\7\u0406\0\ua6e0\6\40\0"+
    "\u1039\6\7\0\336\6\2\0\u1682\6\16\0\u1d31\6\u0c1f\0\u021e\6\u05e2\0"+
    "\u134b\6\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uecc0\0"+
    "\1\7\36\0\140\7\200\0\360\7\uffff\0\uffff\0\ufe12\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\2\2\1\3\1\1\1\4\1\1\1\5"+
    "\1\1\1\5\1\1\4\4\1\6\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\2\16\1\1\1\17\1\3"+
    "\1\0\1\20\1\21\1\22\1\0\2\21\1\22\2\0"+
    "\4\4\2\23\1\24\1\25\1\23\1\26\1\27\1\30"+
    "\1\31\1\32\1\33\1\23\2\3\2\0\2\34\1\21"+
    "\1\22\1\0\5\4\4\0\1\34\1\21\1\35\3\4"+
    "\1\36\1\37\1\0\1\40\1\34\1\21\2\4\1\41"+
    "\1\34\1\21\2\4\1\34\1\21\1\4\1\42\1\34"+
    "\1\21\1\4\1\34\1\21\1\43\1\34\7\21";

  private static int [] zzUnpackAction() {
    int [] result = new int[112];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\215\0\274\0\215\0\353\0\u011a"+
    "\0\u0149\0\u0178\0\u01a7\0\u01d6\0\u0205\0\u0234\0\u0263\0\u0292"+
    "\0\u02c1\0\u02f0\0\215\0\215\0\215\0\215\0\215\0\215"+
    "\0\215\0\u031f\0\u034e\0\215\0\u037d\0\215\0\u03ac\0\u03db"+
    "\0\u040a\0\u0439\0\u0468\0\u0497\0\u04c6\0\u04f5\0\u0524\0\u0553"+
    "\0\u0234\0\u0582\0\u05b1\0\u05e0\0\u060f\0\215\0\u063e\0\215"+
    "\0\215\0\u066d\0\215\0\215\0\215\0\215\0\215\0\215"+
    "\0\u069c\0\u06cb\0\215\0\u06fa\0\u0729\0\u0497\0\u0758\0\u0787"+
    "\0\u07b6\0\u07b6\0\u07e5\0\u0814\0\u0843\0\u0872\0\u08a1\0\u08d0"+
    "\0\u08ff\0\u092e\0\u095d\0\u098c\0\u09bb\0\u0149\0\u09ea\0\u0a19"+
    "\0\u0a48\0\u0149\0\215\0\u069c\0\215\0\u0a77\0\u0aa6\0\u0ad5"+
    "\0\u0b04\0\u0149\0\u0b33\0\u0b62\0\u0b91\0\u0bc0\0\u0bef\0\u0c1e"+
    "\0\u0c4d\0\u0149\0\u0c7c\0\u0cab\0\u0cda\0\u0d09\0\u0d38\0\u0149"+
    "\0\215\0\u0d67\0\u0d96\0\u0dc5\0\u0df4\0\u0e23\0\u0e52\0\u0468";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[112];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\1\5\1\6\1\7\1\10\1\4\1\11\1\4"+
    "\1\12\1\13\1\14\1\15\2\11\2\15\1\16\1\11"+
    "\2\4\1\17\1\20\3\11\1\21\3\11\1\22\4\11"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\11"+
    "\1\4\1\11\1\4\1\7\1\4\1\32\1\33\1\34"+
    "\20\32\1\35\24\32\1\36\6\32\57\4\61\0\1\6"+
    "\57\0\1\7\51\0\1\7\5\0\1\37\1\40\57\0"+
    "\2\11\1\0\1\11\1\0\5\11\1\0\1\11\2\0"+
    "\16\11\7\0\1\11\1\0\1\11\2\0\1\11\6\0"+
    "\1\41\5\0\2\41\3\0\1\41\2\0\16\41\7\0"+
    "\1\41\1\0\1\41\14\0\1\42\1\0\1\43\1\44"+
    "\1\0\1\45\1\46\1\47\1\50\6\0\1\50\22\0"+
    "\1\44\14\0\1\43\1\0\1\15\2\0\2\15\1\51"+
    "\47\0\1\15\1\0\1\15\2\0\2\15\1\47\1\50"+
    "\6\0\1\50\37\0\1\47\1\0\1\47\2\0\2\47"+
    "\45\0\2\11\1\0\1\11\1\0\5\11\1\0\1\11"+
    "\2\0\1\11\1\52\14\11\7\0\1\11\1\0\1\11"+
    "\2\0\1\11\6\0\2\11\1\0\1\11\1\0\5\11"+
    "\1\0\1\11\2\0\1\53\15\11\7\0\1\11\1\0"+
    "\1\11\2\0\1\11\6\0\2\11\1\0\1\11\1\0"+
    "\5\11\1\0\1\11\2\0\7\11\1\54\6\11\7\0"+
    "\1\11\1\0\1\11\2\0\1\11\6\0\2\11\1\0"+
    "\1\11\1\0\5\11\1\0\1\11\2\0\12\11\1\55"+
    "\3\11\7\0\1\11\1\0\1\11\2\0\1\11\1\32"+
    "\2\0\20\32\1\0\24\32\1\0\6\32\2\0\1\34"+
    "\54\0\1\56\2\0\6\56\1\57\4\56\2\57\3\56"+
    "\1\60\1\61\1\62\3\56\1\63\3\56\1\64\1\65"+
    "\11\56\1\66\1\67\1\70\1\71\3\0\1\37\1\72"+
    "\1\73\54\37\5\74\1\75\51\74\6\0\2\41\1\0"+
    "\1\41\1\0\5\41\1\0\1\41\2\0\16\41\7\0"+
    "\1\41\1\0\1\41\2\0\1\41\11\0\1\42\1\0"+
    "\1\43\2\0\1\45\1\46\1\47\1\50\6\0\1\50"+
    "\37\0\1\43\1\0\1\43\2\0\2\43\1\47\1\50"+
    "\6\0\1\50\37\0\1\76\1\0\1\77\1\0\3\77"+
    "\1\0\1\77\5\0\3\77\1\0\1\77\15\0\1\77"+
    "\16\0\1\46\1\0\1\43\2\0\2\46\1\47\1\50"+
    "\6\0\1\50\37\0\1\100\1\0\1\43\2\0\2\100"+
    "\1\47\1\50\6\0\1\50\37\0\1\47\1\0\1\47"+
    "\2\0\2\47\1\0\1\50\6\0\1\50\37\0\1\101"+
    "\1\102\1\101\2\0\2\101\2\0\1\102\42\0\2\11"+
    "\1\0\1\11\1\0\5\11\1\0\1\11\2\0\2\11"+
    "\1\103\13\11\7\0\1\11\1\0\1\11\2\0\1\11"+
    "\6\0\2\11\1\0\1\11\1\0\5\11\1\0\1\11"+
    "\2\0\3\11\1\104\7\11\1\105\2\11\7\0\1\11"+
    "\1\0\1\11\2\0\1\11\6\0\2\11\1\0\1\11"+
    "\1\0\5\11\1\0\1\11\2\0\2\11\1\106\13\11"+
    "\7\0\1\11\1\0\1\11\2\0\1\11\6\0\2\11"+
    "\1\0\1\11\1\0\5\11\1\0\1\11\2\0\1\11"+
    "\1\107\14\11\7\0\1\11\1\0\1\11\2\0\1\11"+
    "\11\0\1\110\4\0\2\110\50\0\1\111\1\0\1\111"+
    "\1\0\3\111\1\0\1\111\5\0\3\111\1\0\1\111"+
    "\15\0\1\111\16\0\1\112\1\0\1\112\1\0\3\112"+
    "\1\0\1\112\5\0\3\112\1\0\1\112\15\0\1\112"+
    "\7\0\1\73\54\0\5\74\1\113\51\74\4\0\1\73"+
    "\1\75\62\0\1\114\1\0\1\114\1\0\3\114\1\0"+
    "\1\114\5\0\3\114\1\0\1\114\15\0\1\114\16\0"+
    "\1\115\1\0\1\43\2\0\2\115\1\47\1\50\6\0"+
    "\1\50\37\0\1\101\1\0\1\101\2\0\2\101\45\0"+
    "\2\11\1\0\1\11\1\0\5\11\1\0\1\11\2\0"+
    "\2\11\1\116\13\11\7\0\1\11\1\0\1\11\2\0"+
    "\1\11\6\0\2\11\1\0\1\11\1\0\5\11\1\0"+
    "\1\11\2\0\4\11\1\117\11\11\7\0\1\11\1\0"+
    "\1\11\2\0\1\11\6\0\2\11\1\0\1\11\1\0"+
    "\5\11\1\0\1\11\2\0\1\120\15\11\7\0\1\11"+
    "\1\0\1\11\2\0\1\11\6\0\2\11\1\0\1\11"+
    "\1\0\5\11\1\0\1\11\2\0\10\11\1\121\5\11"+
    "\7\0\1\11\1\0\1\11\2\0\1\11\6\0\2\11"+
    "\1\0\1\11\1\0\5\11\1\0\1\11\2\0\4\11"+
    "\1\122\11\11\7\0\1\11\1\0\1\11\2\0\1\11"+
    "\11\0\1\123\4\0\2\123\50\0\1\124\1\0\1\124"+
    "\1\0\3\124\1\0\1\124\5\0\3\124\1\0\1\124"+
    "\15\0\1\124\16\0\1\125\1\0\1\125\1\0\3\125"+
    "\1\0\1\125\5\0\3\125\1\0\1\125\15\0\1\125"+
    "\5\0\4\74\1\73\1\113\51\74\11\0\1\126\1\0"+
    "\1\126\1\0\3\126\1\0\1\126\5\0\3\126\1\0"+
    "\1\126\15\0\1\126\16\0\1\127\1\0\1\43\2\0"+
    "\2\127\1\47\1\50\6\0\1\50\34\0\2\11\1\0"+
    "\1\11\1\0\5\11\1\0\1\11\2\0\5\11\1\130"+
    "\10\11\7\0\1\11\1\0\1\11\2\0\1\11\6\0"+
    "\2\11\1\0\1\11\1\0\5\11\1\0\1\11\2\0"+
    "\14\11\1\131\1\11\7\0\1\11\1\0\1\11\2\0"+
    "\1\11\6\0\2\11\1\0\1\11\1\0\5\11\1\0"+
    "\1\11\2\0\4\11\1\132\11\11\7\0\1\11\1\0"+
    "\1\11\2\0\1\11\11\0\1\133\1\0\1\133\1\0"+
    "\3\133\1\0\1\133\5\0\3\133\1\0\1\133\15\0"+
    "\1\133\16\0\1\134\1\0\1\43\2\0\2\134\1\47"+
    "\1\50\6\0\1\50\34\0\2\11\1\0\1\11\1\0"+
    "\5\11\1\0\1\11\2\0\6\11\1\135\7\11\7\0"+
    "\1\11\1\0\1\11\2\0\1\11\6\0\2\11\1\0"+
    "\1\11\1\0\5\11\1\0\1\11\2\0\15\11\1\136"+
    "\7\0\1\11\1\0\1\11\2\0\1\11\11\0\1\137"+
    "\1\0\1\137\1\0\3\137\1\0\1\137\5\0\3\137"+
    "\1\0\1\137\15\0\1\137\16\0\1\140\1\0\1\43"+
    "\2\0\2\140\1\47\1\50\6\0\1\50\34\0\2\11"+
    "\1\0\1\11\1\0\5\11\1\0\1\11\2\0\1\141"+
    "\15\11\7\0\1\11\1\0\1\11\2\0\1\11\6\0"+
    "\2\11\1\0\1\11\1\0\5\11\1\0\1\11\2\0"+
    "\1\142\15\11\7\0\1\11\1\0\1\11\2\0\1\11"+
    "\11\0\1\143\1\0\1\143\1\0\3\143\1\0\1\143"+
    "\5\0\3\143\1\0\1\143\15\0\1\143\16\0\1\144"+
    "\1\0\1\43\2\0\2\144\1\47\1\50\6\0\1\50"+
    "\34\0\2\11\1\0\1\11\1\0\5\11\1\0\1\11"+
    "\2\0\4\11\1\145\11\11\7\0\1\11\1\0\1\11"+
    "\2\0\1\11\11\0\1\146\1\0\1\146\1\0\3\146"+
    "\1\0\1\146\5\0\3\146\1\0\1\146\15\0\1\146"+
    "\16\0\1\147\1\0\1\43\2\0\2\147\1\47\1\50"+
    "\6\0\1\50\34\0\2\11\1\0\1\11\1\0\5\11"+
    "\1\0\1\11\2\0\3\11\1\150\12\11\7\0\1\11"+
    "\1\0\1\11\2\0\1\11\11\0\1\151\1\0\1\151"+
    "\1\0\3\151\1\0\1\151\5\0\3\151\1\0\1\151"+
    "\15\0\1\151\16\0\1\152\1\0\1\43\2\0\2\152"+
    "\1\47\1\50\6\0\1\50\37\0\1\153\1\0\1\43"+
    "\2\0\2\153\1\47\1\50\6\0\1\50\37\0\1\154"+
    "\1\0\1\43\2\0\2\154\1\47\1\50\6\0\1\50"+
    "\37\0\1\155\1\0\1\43\2\0\2\155\1\47\1\50"+
    "\6\0\1\50\37\0\1\156\1\0\1\43\2\0\2\156"+
    "\1\47\1\50\6\0\1\50\37\0\1\157\1\0\1\43"+
    "\2\0\2\157\1\47\1\50\6\0\1\50\37\0\1\160"+
    "\1\0\1\43\2\0\2\160\1\47\1\50\6\0\1\50"+
    "\26\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3713];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\1\1\1\11\14\1\7\11\2\1\1\11"+
    "\1\1\1\11\1\1\1\0\3\1\1\0\3\1\2\0"+
    "\4\1\1\11\1\1\2\11\1\1\6\11\2\1\1\11"+
    "\2\0\4\1\1\0\5\1\4\0\7\1\1\11\1\0"+
    "\1\11\23\1\1\11\7\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[112];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */

    StringBuilder string = new StringBuilder();

    private static String xmlTagName = "";

    public int yychar() {
        return yychar;
    }

    private final Stack<ParsedSymbol> pushedBack = new Stack<>();

    public int yyline() {
        return yyline + 1;
    }

    public void pushback(ParsedSymbol symb) {
        pushedBack.push(symb);
        last = null;
    }

    ParsedSymbol last;
    public ParsedSymbol lex() throws java.io.IOException, AmfParseException {
        ParsedSymbol ret = null;
        if (!pushedBack.isEmpty()){
            ret = last = pushedBack.pop();
        } else {
            ret = last = yylex();
        }
        return ret;
    }



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public AmfLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 3800) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;           
    int totalRead = 0;
    while (totalRead < requested) {
      int numRead = zzReader.read(zzBuffer, zzEndRead + totalRead, requested - totalRead);
      if (numRead == -1) {
        break;
      }
      totalRead += numRead;
    }

    if (totalRead > 0) {
      zzEndRead += totalRead;
      if (totalRead == requested) { /* possibly more input available */
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      return false;
    }

    // totalRead = 0: End of stream
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public ParsedSymbol yylex() throws java.io.IOException, AmfParseException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { 
          }
        case 36: break;
        case 2: 
          { yyline++;
          }
        case 37: break;
        case 3: 
          { /*ignore*/
          }
        case 38: break;
        case 4: 
          { return new ParsedSymbol(SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext());
          }
        case 39: break;
        case 5: 
          { return new ParsedSymbol(SymbolGroup.INTEGER, SymbolType.INTEGER, Long.parseLong((yytext())));
          }
        case 40: break;
        case 6: 
          { return new ParsedSymbol(SymbolGroup.OPERATOR, SymbolType.CURLY_OPEN, yytext());
          }
        case 41: break;
        case 7: 
          { return new ParsedSymbol(SymbolGroup.OPERATOR, SymbolType.CURLY_CLOSE, yytext());
          }
        case 42: break;
        case 8: 
          { return new ParsedSymbol(SymbolGroup.OPERATOR, SymbolType.BRACKET_OPEN, yytext());
          }
        case 43: break;
        case 9: 
          { return new ParsedSymbol(SymbolGroup.OPERATOR, SymbolType.BRACKET_CLOSE, yytext());
          }
        case 44: break;
        case 10: 
          { return new ParsedSymbol(SymbolGroup.OPERATOR, SymbolType.COMMA, yytext());
          }
        case 45: break;
        case 11: 
          { return new ParsedSymbol(SymbolGroup.OPERATOR, SymbolType.COLON, yytext());
          }
        case 46: break;
        case 12: 
          { string.setLength(0);
                                    yybegin(STRING);
          }
        case 47: break;
        case 13: 
          { string.append(yytext());
          }
        case 48: break;
        case 14: 
          { yybegin(YYINITIAL);  yyline++;
          }
        case 49: break;
        case 15: 
          { yybegin(YYINITIAL);
                                     // length also includes the trailing quote
                                     return new ParsedSymbol(SymbolGroup.STRING, SymbolType.STRING, string.toString());
          }
        case 50: break;
        case 16: 
          { return new ParsedSymbol(SymbolGroup.IDENTIFIER, SymbolType.REFERENCE, yytext().substring(1));
          }
        case 51: break;
        case 17: 
          { return new ParsedSymbol(SymbolGroup.INTEGER, SymbolType.INTEGER, Long.parseLong(yytext(), 8));
          }
        case 52: break;
        case 18: 
          { return new ParsedSymbol(SymbolGroup.DOUBLE, SymbolType.DOUBLE, Double.parseDouble((yytext())));
          }
        case 53: break;
        case 19: 
          { string.append('\\'); /*illegal escape sequence*/
          }
        case 54: break;
        case 20: 
          { string.append('\\');
          }
        case 55: break;
        case 21: 
          { string.append('\n');
          }
        case 56: break;
        case 22: 
          { string.append('\f');
          }
        case 57: break;
        case 23: 
          { string.append('\t');
          }
        case 58: break;
        case 24: 
          { string.append('\r');
          }
        case 59: break;
        case 25: 
          { string.append('\"');
          }
        case 60: break;
        case 26: 
          { string.append('\b');
          }
        case 61: break;
        case 27: 
          { string.append('\'');
          }
        case 62: break;
        case 28: 
          { return new ParsedSymbol(SymbolGroup.INTEGER, SymbolType.INTEGER, Long.parseLong(yytext().substring(2), 16));
          }
        case 63: break;
        case 29: 
          { return new ParsedSymbol(SymbolGroup.GLOBALCONST, SymbolType.NULL, yytext());
          }
        case 64: break;
        case 30: 
          { return new ParsedSymbol(SymbolGroup.KEYWORD, SymbolType.TRUE, yytext());
          }
        case 65: break;
        case 31: 
          { char val = (char) Integer.parseInt(yytext().substring(1), 8);
                        				   string.append(val);
          }
        case 66: break;
        case 32: 
          { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   string.append(val);
          }
        case 67: break;
        case 33: 
          { return new ParsedSymbol(SymbolGroup.KEYWORD, SymbolType.FALSE, yytext());
          }
        case 68: break;
        case 34: 
          { return new ParsedSymbol(SymbolGroup.UNKNOWN, SymbolType.UNKNOWN, yytext());
          }
        case 69: break;
        case 35: 
          { return new ParsedSymbol(SymbolGroup.GLOBALCONST, SymbolType.UNDEFINED, yytext());
          }
        case 70: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              {
                return new ParsedSymbol(SymbolGroup.EOF, SymbolType.EOF, null);
              }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
