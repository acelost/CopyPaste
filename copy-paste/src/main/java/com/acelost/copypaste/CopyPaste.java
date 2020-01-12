package com.acelost.copypaste;

import android.content.Context;

import com.acelost.copypaste.request.PasteRequest;
import com.acelost.copypaste.request.PasteRequestExecutor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.acelost.copypaste.util.Preconditions.checkNotNull;

public final class CopyPaste {

    @NonNull
    private static final Object instanceLock = new Object();

    @Nullable
    private static CopyPaste instance;

    @NonNull
    private final Object executorLock = new Object();

    @Nullable
    private PasteRequestExecutor executor;

    @NonNull
    public static CopyPaste getInstance() {
        synchronized (instanceLock) {
            if (instance == null) {
                instance = new CopyPaste();
            }
        }
        return checkNotNull(instance);
    }

    public void paste(@NonNull final Context context, @NonNull final PasteRequest request) {
        checkNotNull(context);
        checkNotNull(request);
        getExecutor(context).execute(request);
    }

    @NonNull
    private PasteRequestExecutor getExecutor(@NonNull final Context context) {
        synchronized (executorLock) {
            if (executor == null) {
                executor = new PasteRequestExecutor(context);
            }
        }
        return checkNotNull(executor);
    }

}
