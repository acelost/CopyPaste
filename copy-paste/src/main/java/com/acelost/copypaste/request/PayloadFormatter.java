package com.acelost.copypaste.request;

import androidx.annotation.NonNull;

public interface PayloadFormatter {

    @NonNull
    byte[] formatPayload(@NonNull String content);

}
