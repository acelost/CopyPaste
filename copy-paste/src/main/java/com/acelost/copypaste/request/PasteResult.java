package com.acelost.copypaste.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.acelost.copypaste.util.Preconditions.checkNotEmpty;
import static com.acelost.copypaste.util.Preconditions.checkNotNull;

public final class PasteResult {

    private final boolean success;

    @Nullable
    private final String accessLink;

    @Nullable
    private final Throwable throwable;

    private PasteResult(final boolean success,
            @Nullable final String accessLink,
            @Nullable final Throwable throwable) {
        this.success = success;
        this.accessLink = accessLink;
        this.throwable = throwable;
    }

    public boolean isSuccess() {
        return success;
    }

    @NonNull
    public String getAccessLink() {
        if (!success) {
            throw new IllegalStateException("Paste is failed!");
        }
        return checkNotNull(accessLink);
    }

    @NonNull
    public Throwable getThrowable() {
        if (success) {
            throw new IllegalStateException("There should not be throwable!");
        }
        return checkNotNull(throwable);
    }

    @NonNull
    static PasteResult success(@NonNull final String accessLink) {
        checkNotEmpty(accessLink);
        return new PasteResult(true, accessLink, null);
    }

    @NonNull
    static PasteResult failure(@NonNull final Throwable throwable) {
        return new PasteResult(false, null, throwable);
    }

}
