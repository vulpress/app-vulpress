package hu.aestallon.vulpress.docu.model;

public class Text extends Node<String> {

  public Text(String content) {
    super(content);
  }

  @Override
  public String content() {
    return content;
  }

  @Override
  public void content(String s) {
    this.content = s;
  }

}
