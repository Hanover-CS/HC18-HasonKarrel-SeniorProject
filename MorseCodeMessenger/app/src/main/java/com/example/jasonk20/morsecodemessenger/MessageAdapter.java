package com.example.jasonk20.morsecodemessenger;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jasonk20 on 1/6/18.
 */

public class MessageAdapter extends ArrayAdapter<ChatBubble> {

    private Activity activity;
    private List<ChatBubble> messages;

    /**
     * This is the constructor for the MessageAdapter Class
     * @param context The current activity used for the adapter
     * @param resource The layout that will be used to create the chatbubbles
     * @param  objects The list of ChatBubble objects with the corresponding messsages from the DB
     */
    public MessageAdapter(Activity context, int resource, List<ChatBubble> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatBubble ChatBubble = getItem(position);

        layoutResource = R.layout.left_chat_bubble;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        //set message content
        holder.msg.setText(ChatBubble.getContent());

        return convertView;
    }

    private class ViewHolder {
        private TextView msg;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
        }
    }
}
