package hu.aestallon.vulpress.docu.model;


/*
 * Node
 *  - Document : container for other nodes
 *  - Text     : continuous text
 *  - Heading  : heading with a weight
 *  - List     : container of texts with numbering metadata
 *  - Image    : image
 */
public abstract class Node<T> {

  protected T content;
  protected Node() {}

  protected Node(T content) {
    this.content(content);
  }

  public abstract T content();

  public abstract void content(T t);

}
