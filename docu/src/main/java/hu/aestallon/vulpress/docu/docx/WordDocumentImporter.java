package hu.aestallon.vulpress.docu.docx;

import hu.aestallon.vulpress.docu.DocumentFormat;
import hu.aestallon.vulpress.docu.importer.DocumentImportOptions;
import hu.aestallon.vulpress.docu.importer.DocumentImportResult;
import hu.aestallon.vulpress.docu.importer.DocumentImporter;
import hu.aestallon.vulpress.docu.model.Document;
import hu.aestallon.vulpress.docu.model.Text;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class WordDocumentImporter implements DocumentImporter {

  @Override
  public DocumentImportResult doImport(InputStream inputStream, DocumentImportOptions options) {
    try (final var docx = new XWPFDocument(inputStream)) {
      // first naive implementation
      return docx.getParagraphs().stream()
          .map(XWPFParagraph::getText)
          .map(Text::new)
          .collect(collectingAndThen(toList(), texts -> {
            final var document = new Document(null, DocumentFormat.DOCX);
            document.append(texts);
            return DocumentImportResult.ok(document);
          }));
    } catch (IOException e) {
      return DocumentImportResult.err(e);
    }
  }

  @Override
  public DocumentImportResult doImport(Path path, DocumentImportOptions options) {
    try (InputStream inputStream = Files.newInputStream(path)) {
      return doImport(inputStream, options);
    } catch (IOException e) {
      return DocumentImportResult.err(e);
    }
  }

}
