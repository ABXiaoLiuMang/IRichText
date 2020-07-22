package com.zzhoujay.xhtml.spann;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.zzhoujay.xhtml.HtmlTag;
import com.zzhoujay.xhtml.HtmlView;
import com.zzhoujay.xhtml.callback.SpanClickListener;


public class Link extends ClickableSpan {
    private final String url;
    private final SpanClickListener listener;

    public Link(String url, SpanClickListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    public void onClick(View widget) {
        if (listener != null && url != null && !url.isEmpty()) {
            listener.onSpanClick(HtmlTag.A, url);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(HtmlView.URL_COLOR);
        ds.setUnderlineText(false);
    }

}
