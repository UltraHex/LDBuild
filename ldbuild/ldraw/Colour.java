/*
 * Copyright (C) 2015 Matthew
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ldbuild.ldraw;

import java.io.File;

/**
 *
 * @author Matthew
 */
public class Colour {

    private final String name;
    private final int code;
    private final int value;
    private final int edge;

    private int alpha = 255; //0(invisible) - 255(opaque)
    private int luminance = 0;

    Material material = Material.DEFAULT;
    
    private int glitterValue;
    private float glitterFraction;
    private float glitterVFraction;
    private int glitterSize;

    private int speckleValue;
    private float speckleFraction;
    private int speckleMinSize;
    private int speckleMaxSize;

    public static void loadColours(File ldconfig) {
        //TODO: implement
    }
    
    //TODO: implement color manager and make constructors private
    public Colour(String name, int code, int value, int edge) {
        this.name = name;
        this.code = code;
        this.value = value;
        this.edge = edge;
    }

    public Colour(String name, int code, int value, int edge, int alpha, int luminance) {
        this(name, code, value, edge);
        this.alpha = alpha;
        this.luminance = luminance;
    }

    public Colour(String name, int code, int value, int edge, int alpha, int luminance, Material material) {
        this(name, code, value, edge, alpha, luminance);
        this.material = material;
    }
    
    public Colour(String name, int code, int value, int edge, int alpha, int glitterValue, float glitterFraction, float glitterVFraction, int glitterSize) {
        this(name, code, value, edge);
        this.alpha = alpha;
        this.material = Material.GLITTER;
        this.glitterValue = glitterValue;
        this.glitterFraction = glitterFraction;
        this.glitterVFraction = glitterVFraction;
        this.glitterSize = glitterSize;
    }
    
    public Colour(String name, int code, int value, int edge, int speckleValue, float speckleFraction, int speckleMinSize, int speckleMaxSize) {
        this(name, code, value, edge);
        this.material = Material.SPECKLE;
        this.speckleValue = speckleValue;
        this.speckleFraction = speckleFraction;
        this.speckleMinSize = speckleMinSize;
        this.speckleMaxSize = speckleMaxSize;
    }
    
    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public int getValue() {
        return value;
    }

    public int getEdge() {
        return edge;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getLuminance() {
        return luminance;
    }

    public Material getMaterial() {
        return material;
    }

    public int getGlitterValue() {
        return glitterValue;
    }

    public float getGlitterFraction() {
        return glitterFraction;
    }

    public float getGlitterVFraction() {
        return glitterVFraction;
    }

    public int getGlitterSize() {
        return glitterSize;
    }

    public int getSpeckleValue() {
        return speckleValue;
    }

    public float getSpeckleFraction() {
        return speckleFraction;
    }

    public int getSpeckleMinSize() {
        return speckleMinSize;
    }

    public int getSpeckleMaxSize() {
        return speckleMaxSize;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
    
}
