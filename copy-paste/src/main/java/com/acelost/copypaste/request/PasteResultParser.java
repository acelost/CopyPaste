package com.acelost.copypaste.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface PasteResultParser {

    @NonNull
    PasteResult parseResult(@NonNull PasteRequest request, @Nullable String response);

}
