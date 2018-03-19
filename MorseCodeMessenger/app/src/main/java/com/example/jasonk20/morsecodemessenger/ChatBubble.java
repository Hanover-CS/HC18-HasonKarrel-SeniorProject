package com.example.jasonk20.morsecodemessenger;

/**
 * Created by jasonk20 on 1/6/18.
 */

public class ChatBubble {


    private String content;
    /**
     * This is the constuctor for creating a chatbubble for the MainActivity.
     * @param content This is the message that is added to the chatbubble
     */

    public ChatBubble(String content) {
        this.content = content;
    }

    /**
     * This is a getter for the content of the chatbubble.
     * @return String This returns the message from the chatbubble
     */
    public String getContent() {
        return content;
    }

}
