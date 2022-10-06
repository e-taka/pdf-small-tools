package com.asaas.tools.pdf.splitter;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "pdf-splitter", description = "PDFファイルをページ毎に分割します。")
public class Main implements Callable<Integer> {
	@Parameters(index = "0", description = "ページ分割するPDFファイルのパス")
	private File file;
	@Option(names = {"-o", "--output"}, description = "出力先ディレクトリのパス")
	private File out;

	public static void main(String...args) {
		int exitcode = new CommandLine(new Main()).execute(args);
		System.exit(exitcode);
	}

	@Override
	public Integer call() throws IOException {
		int totalPages = totalPages();
		File dir = Optional.ofNullable(out).orElse(file.getParentFile());
		for (int pageNo = 0; pageNo < totalPages; pageNo++) {
			String filename = String.format("%s-%d.%s",
					FilenameUtils.getBaseName(file.getName()),
					pageNo + 1,
					FilenameUtils.getExtension(file.getName()));

			try (PDDocument doc = PDDocument.load(file)) {
				doc.setAllSecurityToBeRemoved(true);

				for (int i = 0; i < pageNo; i++) {
					doc.removePage(0);
				}
				for (int i = pageNo + 1; i < totalPages; i++) {
					doc.removePage(1);
				}

				System.out.printf("(%d/%d) %s%n", pageNo + 1, totalPages, filename);
				doc.save(new File(dir, filename));
			}
		}

		return 0;
	}

	private int totalPages() throws IOException {
		try (PDDocument doc = PDDocument.load(file)) {
			return doc.getNumberOfPages();
		}
	}
}
