package com.acelost.copypaste.request;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.acelost.copypaste.util.Preconditions.checkNotNull;

final class FriendPasteRequestBuilder {

    private static final String POST_URL = "https://www.friendpaste.com/";

    @NonNull
    private final String content;

    @Nullable
    private final PasteResultListener listener;

    FriendPasteRequestBuilder(@NonNull final String content,
            @Nullable final PasteResultListener listener) {
        this.content = checkNotNull(content);
        this.listener = listener;
    }

    @NonNull
    PasteRequest build() {
        return PasteRequest.newBuilder(POST_URL, content)
                .addHeader("Content-Type", "application/json")
                .setPayloadFormatter(new JsonPayloadFormatter())
                .setPasteResultParser(new JsonPasteResultParser())
                .setResultListener(listener)
                .build();
    }

    private static final class JsonPayloadFormatter implements PayloadFormatter {

        @NonNull
        @Override
        public byte[] formatPayload(@NonNull final String content) {
            final JSONObject payload = new JSONObject();
            try {
                payload.put("snippet", content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return payload.toString().getBytes();
        }
    }

    private static final class JsonPasteResultParser implements PasteResultParser {

        @NonNull
        @Override
        public PasteResult parseResult(@NonNull final PasteRequest request,
                @Nullable final String response) {
            if (response != null) {
                try {
                    final JSONObject json = new JSONObject(response);
                    if (json.getBoolean("ok")) {
                        return PasteResult.success(json.getString("url").concat("/raw"));
                    }
                    return PasteResult.failure(new RuntimeException(json.getString("reason")));
                } catch (JSONException e) {
                    return PasteResult.failure(e);
                }
            }
            return PasteResult.failure(new NullPointerException());
        }
    }

}
