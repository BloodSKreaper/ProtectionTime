package me.chrisochs.protectiontime.configuration;

public class TextEntry {

  private String text;
  private boolean useTitles;

  public TextEntry(String text, boolean useTitles) {
    this.text = text;
    this.useTitles = useTitles ? true : false;
  }

  public String getText() {
    return text;
  }

  public boolean useTitles() {
    return useTitles;
  }

}
