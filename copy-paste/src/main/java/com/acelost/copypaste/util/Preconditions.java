package com.acelost.copypaste.util;

import androidx.annotation.NonNull;

public final class Preconditions {

    @NonNull
    public static <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    @NonNull
    public static String checkNotEmpty(final String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return value;
    }

}
