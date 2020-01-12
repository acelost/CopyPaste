package com.acelost.copypaste.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.acelost.copypaste.util.Preconditions.checkNotEmpty;
import static com.acelost.copypaste.util.Preconditions.checkNotNull;

public final class PasteRequests {

    @NonNull
    public static PasteRequest cl1pNetRequest(@NonNull final String content,
            @Nullable final PasteResultListener listener) {
        return cl1pNetRequest(content, generatePasteId(), listener);
    }

    @NonNull
    public static PasteRequest cl1pNetRequest(@NonNull final String content,
            @NonNull final String pasteId,
            @Nullable final PasteResultListener listener) {
        checkNotNull(content);
        checkNotEmpty(pasteId);
        return new Cl1pNetRequestBuilder(content, pasteId, listener).build();
    }

    @NonNull
    public static PasteRequest friendPasteComRequest(@NonNull final String content,
            @Nullable final PasteResultListener listener) {
        checkNotNull(content);
        return new FriendPasteRequestBuilder(content, listener).build();
    }

    @NonNull
    private static String generatePasteId() {
        return Long.toString(System.currentTimeMillis(), 36);
    }

}
