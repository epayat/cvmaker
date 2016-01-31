package com.progsan.atlantis.wicket.view;

import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by Erdal on 31.01.2016.
 */
public class HomePage extends BaseWebPage {
    @Override
    protected void onInitialize(){
        super.onInitialize();
        add(new Label("helloMessage", "Hello WicketWorld!"));
    }
}
