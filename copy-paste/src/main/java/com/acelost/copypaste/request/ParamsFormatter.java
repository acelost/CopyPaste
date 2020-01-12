package com.acelost.copypaste.request;

import androidx.annotation.NonNull;

public interface ParamsFormatter {

    @NonNull
    String formatParams(@NonNull String content);

}
