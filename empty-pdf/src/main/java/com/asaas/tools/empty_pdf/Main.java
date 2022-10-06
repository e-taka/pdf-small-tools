package com.asaas.tools.empty_pdf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "empty-pdf",
	description = "空白1ページのPDFを生成します。",
	mixinStandardHelpOptions = true)
public class Main implements Callable<Integer> {
	@Parameters(index = "0", description = "生成するページサイズ: A4")
	private PaperSize size = PaperSize.A4;
	@Option(names = {"-o", "--output"},
			description = "出力先ファイルパス(デフォルト: '${DEFAULT-VALUE}')",
			defaultValue = "empty.pdf")
	private File path;
	@Option(names = "--landscape", description = "横向きのサイズ")
	private boolean _landscape = false;

	public static void main(String...args) {
		int exitCode = new CommandLine(new Main()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public Integer call() throws IOException {
		try (PDDocument doc = new PDDocument()) {
			var page = size.createPage(_landscape);
			// なにか追加しておかいないと、report-serviceでエラーになる。
			try (var stream = new PDPageContentStream(doc, page)) {
				stream.addComment("empty");
			}
			doc.addPage(page);

			try (OutputStream os = new BufferedOutputStream(new FileOutputStream(path))) {
				doc.save(os);
			}
			System.out.println("PDFを生成しました: " + path);
		}
		return 0;
	}
}
