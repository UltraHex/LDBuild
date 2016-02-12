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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import ldbuild.FileManager;
import ldbuild.gui.MainWindow;

/**
 *
 * @author Matthew
 */
public class LDFile {

    private File file;
    private String title;

    private boolean modified = false;

    public ArrayList<ILineType> contents = new ArrayList<>();

    public LDFile(File file) {
        this.file = file;
        new FileParser().parseFile();
    }

    public boolean isModified() {
        return modified;
    }

    public String getName() {
        if (title != null) {
            return title;
        } else {
            return file.getName();
        }
    }

    public File getPath() {
        return file;
    }

    private class FileParser {

        void parseFile() {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    parseLine(scanner.nextLine().trim());
                }

                if (contents.size() > 0 && contents.get(0) instanceof LineComment && !((LineComment) contents.get(0)).getComment().isEmpty()) {
                        title = ((LineComment) contents.get(0)).getComment();
                } else {
                    title = getName();
                }

            } catch (FileNotFoundException ex) {
                String errMessage = "File not found while parsing " + file.getAbsolutePath();
                MainWindow.getInstance().showErrorDialog(errMessage);
                Logger.getLogger(LDFile.class.getName()).log(Level.SEVERE, errMessage, ex);
            }
        }

        void parseLine(String line) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            if (!tokenizer.hasMoreTokens()) {
                contents.add(new LineComment(""));
                return;
            }
            String token = tokenizer.nextToken();
            switch (token) {
                case "0": { //comment/meta command
                    contents.add(new LineComment(line.substring(line.indexOf('0') + 1).trim())); //corrects pre-existing whitespace errors! :D
                    //TODO: Implement meta command detection and parsing
                    break;
                }
                case "1": { //sub-file
                    if (tokenizer.countTokens() < 13) {
                        //TODO: Log parse error
                        contents.add(new LineComment(line.trim())); //convert the malformed line into a comment so it isn't deleted on save.
                        break;
                    }

                    int colourID = Integer.parseInt(tokenizer.nextToken());
                    Colour colour = new Colour("Default", colourID, 0x808080, 0xFFFFFF); //TODO: Implement colour manager. This shouldn't be hard-coded here!

                    Double x = Double.parseDouble(tokenizer.nextToken());
                    Double y = Double.parseDouble(tokenizer.nextToken());
                    Double z = Double.parseDouble(tokenizer.nextToken());

                    double[][] elements = new double[3][3];
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            elements[i][j] = Double.parseDouble(tokenizer.nextToken()); //if matrix gets transposed swap i and j.
                        }
                    }

                    Matrix matrix = new Matrix(elements);

                    //this will break if file isn't in LDraw Dir and needs to 
                    //fixed to compmly with file format standards.
                    File subFile = new File(FileManager.getLDrawDir(), tokenizer.nextToken());
                    contents.add(new LineSubFile(colour, x, y, z, matrix, subFile));
                    break;
                }
                case "2": { //line
                    if (tokenizer.countTokens() < 7) {
                        //TODO: Log parse error
                        contents.add(new LineComment(line.trim())); //convert the malformed line into a comment so it isn't deleted on save.
                        break;
                    }

                    int colourID = new Integer(tokenizer.nextToken());
                    Colour colour = new Colour("Default", colourID, 0x808080, 0xFFFFFF); //TODO: Implement colour manager. This shouldn't be hard-coded here!

                    double[] coords = new double[6];
                    for (int i = 0; i < 6; i++) {
                        coords[i] = Double.parseDouble(tokenizer.nextToken());
                    }

                    Point point1 = new Point(coords[0], coords[1], coords[2]);
                    Point point2 = new Point(coords[3], coords[4], coords[5]);

                    contents.add(new LineLine(colour, point1, point2));
                    break;
                }
                case "3": {
                    if (tokenizer.countTokens() < 10) {
                        //TODO: Log parse error
                        contents.add(new LineComment(line.trim())); //convert the malformed line into a comment so it isn't deleted on save.
                        break;
                    }

                    int colourID = new Integer(tokenizer.nextToken());
                    Colour colour = new Colour("Default", colourID, 0x808080, 0xFFFFFF); //TODO: Implement colour manager. This shouldn't be hard-coded here!

                    double[] coords = new double[9];
                    for (int i = 0; i < 9; i++) {
                        coords[i] = Double.parseDouble(tokenizer.nextToken());
                    }

                    Point point1 = new Point(coords[0], coords[1], coords[2]);
                    Point point2 = new Point(coords[3], coords[4], coords[5]);
                    Point point3 = new Point(coords[6], coords[7], coords[8]);

                    contents.add(new LineTriangle(colour, point1, point2, point3));
                    break;
                }
                case "4": {
                    if (tokenizer.countTokens() < 13) {
                        //TODO: Log parse error
                        contents.add(new LineComment(line.trim())); //convert the malformed line into a comment so it isn't deleted on save.
                        break;
                    }

                    int colourID = new Integer(tokenizer.nextToken());
                    Colour colour = new Colour("Default", colourID, 0x808080, 0xFFFFFF); //TODO: Implement colour manager. This shouldn't be hard-coded here!

                    double[] coords = new double[12];
                    for (int i = 0; i < 12; i++) {
                        coords[i] = Double.parseDouble(tokenizer.nextToken());
                    }

                    Point point1 = new Point(coords[0], coords[1], coords[2]);
                    Point point2 = new Point(coords[3], coords[4], coords[5]);
                    Point point3 = new Point(coords[6], coords[7], coords[8]);
                    Point point4 = new Point(coords[9], coords[10], coords[11]);

                    contents.add(new LineQuad(colour, point1, point2, point3, point4));
                    break;
                }
                case "5": {
                    if (tokenizer.countTokens() < 13) {
                        //TODO: Log parse error
                        contents.add(new LineComment(line.trim())); //convert the malformed line into a comment so it isn't deleted on save.
                        break;
                    }

                    int colourID = new Integer(tokenizer.nextToken());
                    Colour colour = new Colour("Default", colourID, 0x808080, 0xFFFFFF); //TODO: Implement colour manager. This shouldn't be hard-coded here!

                    double[] coords = new double[12];
                    for (int i = 0; i < 12; i++) {
                        coords[i] = Double.parseDouble(tokenizer.nextToken());
                    }

                    Point point1 = new Point(coords[0], coords[1], coords[2]);
                    Point point2 = new Point(coords[3], coords[4], coords[5]);
                    Point point3 = new Point(coords[6], coords[7], coords[8]);
                    Point point4 = new Point(coords[9], coords[10], coords[11]);

                    contents.add(new LineOptionalLine(colour, point1, point2, point3, point4));
                    break;
                }
                default: {
                    //TODO: log unrecognized line type.
                    contents.add(new LineComment(line.trim())); //convert the malformed line into a comment so it isn't deleted on save.
                    break;
                }
            }
        }
        //File Parsing: Done! (mostly)
    }

    @Override
    public String toString() {
        return getName();
    }
}
