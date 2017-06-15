package coza.trojanc.receipt.template.process.loader.impl;

import coza.trojanc.receipt.TestUtils;
import coza.trojanc.receipt.template.process.ProcessedTemplate;
import coza.trojanc.receipt.template.process.loader.ProcessedTemplateLoader;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Unit test for {@link XmlProcessedTemplateLoader}
 */
public class XmlProcessedTemplateLoaderTest {
@Test
	public void testGenerateJson() throws IOException {
		ProcessedTemplateLoader loader = new XmlProcessedTemplateLoader();
		ProcessedTemplate processedTemplate = TestUtils.getProcessedTemplate();

		loader.write(processedTemplate, System.out);
	}

	@Test
	public void testLoadBackJsonTemplate() throws IOException {
		ProcessedTemplateLoader loader = new XmlProcessedTemplateLoader();
		ProcessedTemplate processedTemplate = TestUtils.getProcessedTemplate();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		loader.write(processedTemplate, byteArrayOutputStream);
		loader.load(byteArrayOutputStream.toString());
	}
}