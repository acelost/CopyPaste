package com.acelost.copypaste.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface PasteResultListener {

    void onSuccess(@NonNull String accessLink);

    void onFailure(@Nullable Throwable error);

}
