package hu.aestallon.vulpress.docu.docx;

import hu.aestallon.vulpress.docu.DocumentComposer;
import hu.aestallon.vulpress.docu.DocumentComposerImpl;
import hu.aestallon.vulpress.docu.DocumentFormat;
import hu.aestallon.vulpress.docu.importer.DocumentImportResult;
import hu.aestallon.vulpress.docu.importer.DocumentImporter;
import hu.aestallon.vulpress.docu.model.Document;
import hu.aestallon.vulpress.docu.model.Node;
import hu.aestallon.vulpress.docu.model.Text;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WordDocumentImporterTest {

  @Test
  @DisplayName("Word document with simple paragraphs are imported correctly")
  void importingWordDocumentWithSimpleParagraphs_isParsedIntoAListOfTextElements() throws
      IOException {

    final DocumentComposer composer = new DocumentComposerImpl()
        .importing(importer -> importer
            .format(DocumentFormat.DOCX)
            .with(new WordDocumentImporter()));
    final DocumentImporter wordImporter = composer.provideImporter(DocumentFormat.DOCX);
    assertThat(wordImporter).isNotNull();

    try (InputStream in = WordDocumentImporterTest.class
        .getResourceAsStream("/docx/test-resource-01.docx")) {
      assertThat(in).isNotNull();

      DocumentImportResult result = wordImporter.doImport(in);
      assertThat(result).isNotNull();

      // we are going to pattern match after upgrading to Java 21: Until then.
      assertThat(result).returns(true, DocumentImportResult::isOk);
      if (result instanceof DocumentImportResult.Ok ok) {
        Document document = ok.document();
        assertThat(document).isNotNull();

        List<Node> content = document.content();
        assertThat(content).isNotNull().hasSize(6)
            .allSatisfy(node -> assertThat(node).isInstanceOf(Text.class));

        List<String> texts = content.stream()
            .map(Text.class::cast)
            .map(Text::content)
            .toList();
        assertThat(texts).containsExactly("A", "B", "c", "d", "element", "FAFOU");

      } else {
        throw new AssertionError();
      }
    }
  }

}
