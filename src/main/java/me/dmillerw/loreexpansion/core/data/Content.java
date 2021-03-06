package me.dmillerw.loreexpansion.core.data;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class Content {

    private final String title;
    private final String body;
    private final String audio;
    private final boolean autoplay;

    public Content(String title, String body, String audio, boolean autoplay) {
        this.title = title;
        this.body = body;
        this.audio = audio;
        this.autoplay = autoplay;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAudio() {
        return audio;
    }

    public boolean shouldAutoplay() {
        return autoplay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("body", StringEscapeUtils.escapeJava(body))
                .append("audio", audio)
                .append("autoplay", autoplay)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Content)) return false;

        Content content = (Content) o;

        if (autoplay != content.autoplay) return false;
        if (getTitle() != null ? !getTitle().equals(content.getTitle()) : content.getTitle() != null) return false;
        if (getBody() != null ? !getBody().equals(content.getBody()) : content.getBody() != null) return false;
        return getAudio() != null ? getAudio().equals(content.getAudio()) : content.getAudio() == null;

    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        result = 31 * result + (getAudio() != null ? getAudio().hashCode() : 0);
        result = 31 * result + (autoplay ? 1 : 0);
        return result;
    }
}
