/*
 * Copyright (C) 2014 Matthew W. Noel
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
public class LineMeta implements ILineType {

    private String command;
    
    /**
     * 
     * @param command command string not including leading 0 and whitespace
     */
    public LineMeta(String command) {
        this.command = command;
    }
    
    @Override
    public int getLineType() {
        return 0;
    }

    @Override
    public String toString() {
        return "0 " + command;
    }
    
}
