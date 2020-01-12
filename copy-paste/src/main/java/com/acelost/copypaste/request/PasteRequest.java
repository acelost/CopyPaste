package com.acelost.copypaste.request;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.acelost.copypaste.util.Preconditions.checkNotEmpty;
import static com.acelost.copypaste.util.Preconditions.checkNotNull;

public final class PasteRequest {

    @NonNull
    private final String url;

    @NonNull
    private final String content;

    @NonNull
    private final Map<String, String> headers;

    @Nullable
    private final ParamsFormatter paramsFormatter;

    @Nullable
    private final PayloadFormatter payloadFormatter;

    @Nullable
    private final PasteResultParser pasteResultParser;

    @Nullable
    private final PasteResultListener listener;

    private PasteRequest(@NonNull final String url,
            @NonNull final String content,
            @NonNull final Map<String, String> headers,
            @Nullable final ParamsFormatter paramsFormatter,
            @Nullable final PayloadFormatter payloadFormatter,
            @Nullable final PasteResultParser pasteResultParser,
            @Nullable final PasteResultListener listener) {
        this.url = checkNotEmpty(url);
        this.content = checkNotEmpty(content);
        this.headers = new HashMap<>(headers);
        this.paramsFormatter = paramsFormatter;
        this.payloadFormatter = payloadFormatter;
        this.pasteResultParser = pasteResultParser;
        this.listener = listener;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    @NonNull
    public Map<String, String> getHeaders() {
        return new HashMap<>(headers);
    }

    @Nullable
    public ParamsFormatter getParamsFormatter() {
        return paramsFormatter;
    }

    @Nullable
    public PayloadFormatter getPayloadFormatter() {
        return payloadFormatter;
    }

    @Nullable
    public PasteResultParser getPasteResultParser() {
        return pasteResultParser;
    }

    @Nullable
    public PasteResultListener getListener() {
        return listener;
    }

    @CheckResult
    @NonNull
    public static Builder newBuilder(@NonNull final String url, @NonNull final String content) {
        return new PasteRequest.Builder(url, content);
    }

    @Override
    public String toString() {
        return "PasteRequest{" +
                "url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", headers=" + headers +
                '}';
    }

    public static final class Builder {

        @NonNull
        private final Map<String, String> headers = new HashMap<>();

        @NonNull
        private final String url;

        @NonNull
        private final String content;

        @Nullable
        private ParamsFormatter paramsFormatter;

        @Nullable
        private PayloadFormatter payloadFormatter;

        @Nullable
        private PasteResultParser pasteResultParser;

        @Nullable
        private PasteResultListener listener;

        private Builder(@NonNull final String url, @NonNull final String content) {
            this.url = checkNotEmpty(url);
            this.content = checkNotNull(content);
        }

        @CheckResult
        @NonNull
        public Builder addHeader(@NonNull final String name, @NonNull final String value) {
            headers.put(checkNotEmpty(name), checkNotNull(value));
            return this;
        }

        @CheckResult
        @NonNull
        public Builder setParamsFormatter(@Nullable final ParamsFormatter formatter) {
            this.paramsFormatter = formatter;
            return this;
        }

        @CheckResult
        @NonNull
        public Builder setPayloadFormatter(@Nullable final PayloadFormatter formatter) {
            this.payloadFormatter = formatter;
            return this;
        }

        @CheckResult
        @NonNull
        public Builder setPasteResultParser(@Nullable final PasteResultParser parser) {
            this.pasteResultParser = parser;
            return this;
        }

        @CheckResult
        @NonNull
        public Builder setResultListener(@Nullable final PasteResultListener listener) {
            this.listener = listener;
            return this;
        }

        @NonNull
        public PasteRequest build() {
            return new PasteRequest(
                    url,
                    content,
                    headers,
                    paramsFormatter,
                    payloadFormatter,
                    pasteResultParser,
                    listener
            );
        }

    }

}
