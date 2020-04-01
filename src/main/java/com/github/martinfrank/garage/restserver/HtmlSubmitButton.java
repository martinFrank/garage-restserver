package com.github.martinfrank.garage.restserver;

import java.util.Objects;

public class HtmlSubmitButton {

    private final String name;
    private final String value;
    private final String text;
    private final SubmitAction action;

    public HtmlSubmitButton(String name, String value, String text, SubmitAction action) {
        this.name = name;
        this.value = value;
        this.text = text;
        this.action = action;
    }

    public String createSubmitButtonHtmlString() {
        return "<button type=\"submit\" name=\"" + name + "\" value=\"" + value + "\" style=\"height:100px;width:300px\">" + text + "</button>";
    }

    public void apply(String name, String value) {
        if (Objects.equals(this.name, name) && Objects.equals(this.value, value)) {
            System.out.println("apply " + name);
            action.apply();
        }
    }


}
