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

/**
 *
 * @author Matthew
 */
public class LineLine implements ILineType {

    private Colour colour;
    private Point point1;
    private Point point2;

    public LineLine(Colour colour, Point point1, Point point2) {
        this.colour = colour;
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public int getLineType() {
        return 2;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s %s",  getLineType(), colour, point1, point2);
    }

}
