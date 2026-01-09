package com.hello.background.utils;

/**
 * word文档样式
 */
public class FontStyle {
    public Integer fontSize = 12;
    public String fontFamily = "微软雅黑";
    public Boolean isBold = false;

    public FontStyle(Integer _fontSize, String _fontFamily, Boolean _isBold) {
        if (_fontSize != null) {
            fontSize = _fontSize;
        }
        if (_fontFamily != null) {
            fontFamily = _fontFamily;
        }
        if (_isBold != null) {
            isBold = _isBold;
        }
    }
}
