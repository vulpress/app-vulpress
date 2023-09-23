package hu.aestallon.vulpress.docu.model;

import hu.aestallon.vulpress.docu.DocumentFormat;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public final class Document extends Node<List<Node>> {

  private String filename;
  private DocumentFormat originalFormat;

  public Document(String filename, DocumentFormat originalFormat) {
    this.filename = filename;
    this.originalFormat = originalFormat;
  }

  @Override
  public List<Node> content() {
    return new ArrayList<>(content);
  }

  @Override
  public void content(List<Node> nodes) {
    this.content = new ArrayList<>(nodes);
  }

  public <T extends Node> void append(List<T> nodes) {
    if (content == null) {
      content = new ArrayList<>();
    }
    content.addAll(nodes);
  }

  public String filename() {
    return filename;
  }

  public DocumentFormat originalFormat() {
    return originalFormat;
  }
}
