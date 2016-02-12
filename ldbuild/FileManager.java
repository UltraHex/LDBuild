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
package ldbuild;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ldbuild.gui.MainWindow;
import ldbuild.ldraw.ILineType;
import ldbuild.ldraw.LDFile;

/**
 *
 * @author Matthew
 */
public class FileManager {

    private static LDFile ldfile;
    private static boolean isTempFile = false;
    private static File LDrawDir = new File("C:\\Program Files(x86)\\LDraw\\"); //TODO: This must be configurable and must NOT be hardcoded! (Hint: Gui)

    public static boolean newFile() {
        if (ldfile != null && ldfile.isModified()) {
            boolean canceled = MainWindow.getInstance().showSaveDiscardCancelDialog();
            if (canceled) {
                return false;
            }
        }

        MainWindow.getInstance().setTitle("LDBuild");

        if (isTempFile) {
            ldfile.getPath().delete();
        }

        try {
            ldfile = new LDFile(File.createTempFile("Untitled", ".ldr"));
            isTempFile = true;
            MainWindow.getInstance().setTitle("LDBuild");
            return true;
        } catch (IOException ex) {
            String errMessage = "IO Exception while trying to create a temporary file";
            MainWindow.getInstance().showErrorDialog(errMessage);
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, errMessage, ex);
            return false;
        }
    }

    public static boolean openFile() {
        if (ldfile != null && ldfile.isModified()) {
            boolean canceled = MainWindow.getInstance().showSaveDiscardCancelDialog();
            if (canceled) {
                return false;
            }
        }
        File f = MainWindow.getInstance().showOpenDialog();
        if (f != null) {
            ldfile = new LDFile(f);
            MainWindow.getInstance().setTitle("LDBuild - " + ldfile.getName());
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return whether the save was successful.
     */
    public static boolean saveFile() {
        if (ldfile == null || !ldfile.isModified()) {
            return false;
        }
        if (isTempFile) {
            File f = MainWindow.getInstance().showSaveDialog();
            return saveFileAs(f);
        } else {
            return saveFileAs(ldfile.getPath());
        }
    }

    /**
     *
     * @param path the save location
     * @return whether the save was successful.
     */
    public static boolean saveFileAs(File path) {
        if (path == null || ldfile == null || path.isDirectory()) {
            return false;
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
            for (ILineType item : ldfile.contents) {
                out.write(item.toString());
                out.newLine();
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean reload() {
        if (ldfile == null) {
            return false;
        }
        if (ldfile.isModified()) {
            if (!MainWindow.getInstance().showConfirmationDialog()) {
                return false;
            }
        }
        ldfile = new LDFile(ldfile.getPath());
        return true;
    }

    public static File getLDrawDir() {
        return LDrawDir;
    }

}
