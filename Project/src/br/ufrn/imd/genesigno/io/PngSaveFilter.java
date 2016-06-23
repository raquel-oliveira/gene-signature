package br.ufrn.imd.genesigno.io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * file filter for specifically extensions.
 *
 */
public class PngSaveFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.isFile()&&(f.getName().endsWith(".png") || f.getName().endsWith(".PNG"));
	}

	@Override
	public String getDescription() {
		return "Imagem PNG";
	}

}
