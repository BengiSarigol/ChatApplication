package com.example.bengi.chatting;

public class MesajModel {

   public MesajModel(){

   };

    private String from,text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {

        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }



    public MesajModel(String from, String text) {
        this.from = from;
        this.text = text;
    }

    @Override
    public String toString() {
        return "MesajModel{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
