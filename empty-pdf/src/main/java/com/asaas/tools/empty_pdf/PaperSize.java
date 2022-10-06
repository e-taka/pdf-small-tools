package com.asaas.tools.empty_pdf;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public enum PaperSize {
	A4(PDRectangle.A4),
	OCR(200.0, 283.0);

	private PDRectangle _rectangle;
	private PaperSize(PDRectangle rectangle) {
		_rectangle = rectangle;
	}
	private PaperSize(double width, double height) {
		this(create(width, height));
	}

	private static PDRectangle create(double width, double height) {
		BigDecimal pointsPerMM = BigDecimal.ONE.divide(new BigDecimal("25.4"), 10, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(72));
		BigDecimal w = BigDecimal.valueOf(width).multiply(pointsPerMM);
		BigDecimal h = BigDecimal.valueOf(height).multiply(pointsPerMM);
		return new PDRectangle(w.floatValue(), h.floatValue());
	}

	public PDPage createPage(boolean landscape) {
		if (landscape) {
			return new PDPage(new PDRectangle(_rectangle.getHeight(), _rectangle.getWidth()));
		} else {
			return new PDPage(_rectangle);
		}
	}
}
