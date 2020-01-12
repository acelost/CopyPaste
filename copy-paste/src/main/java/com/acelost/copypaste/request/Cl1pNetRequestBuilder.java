package com.acelost.copypaste.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.acelost.copypaste.util.Preconditions.checkNotEmpty;
import static com.acelost.copypaste.util.Preconditions.checkNotNull;

final class Cl1pNetRequestBuilder {

    private static final String BASE_URL = "https://api.cl1p.net/";

    @NonNull
    private final String content;

    @NonNull
    private final String pasteId;

    @Nullable
    private final PasteResultListener listener;

    Cl1pNetRequestBuilder(@NonNull final String content,
            @NonNull final String pasteId,
            @Nullable final PasteResultListener listener) {
        this.content = checkNotNull(content);
        this.pasteId = checkNotEmpty(pasteId);
        this.listener = listener;
    }

    @NonNull
    PasteRequest build() {
        final String url = BASE_URL + pasteId;
        return PasteRequest.newBuilder(url, content)
                .addHeader("Content-Type", "text/html")
                .setResultListener(listener)
                .build();
    }

}
