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
import java.text.DecimalFormat;

// 1 <colour> x y z a b c d e f g h i <file>

/**
 *
 * @author Matthew
 */
public class LineSubFile extends LDFile implements ILineType {

    private Colour colour;
    private double x;
    private double y;
    private double z;
    private Matrix matrix;
  //private File file; inherited form superclass

    public LineSubFile(Colour colour, double x, double y, double z, Matrix matrix, File file) {
        super(file);
        this.colour = colour;
        this.x = x;
        this.y = y;
        this.z = z;
        this.matrix = matrix;
    }
    
    @Override
    public int getLineType() {
        return 1;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.######");
        return String.format("%s %s %s %s %s %s %s", getLineType(), colour, df.format(x), df.format(y), df.format(z), matrix, getName());
    }
    
}
