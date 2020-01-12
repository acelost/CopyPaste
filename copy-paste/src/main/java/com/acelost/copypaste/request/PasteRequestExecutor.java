package com.acelost.copypaste.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.acelost.copypaste.util.Preconditions.checkNotNull;

public final class PasteRequestExecutor {

    @NonNull
    private final Context context;

    @NonNull
    private final Object initLock = new Object();

    private volatile boolean initialized = false;

    @Nullable
    private volatile RequestQueue queue;

    public PasteRequestExecutor(@NonNull final Context context) {
        this.context = checkNotNull(context);
    }

    private void prepare() {
        synchronized (initLock) {
            if (!initialized) {
                queue = Volley.newRequestQueue(context);
                initialized = true;
            }
        }
    }

    public void execute(@NonNull final PasteRequest request) {
        checkNotNull(request);
        prepare();
        final Request volleyRequest = createVolleyRequest(request);
        getQueue().add(volleyRequest);
    }

    @NonNull
    private RequestQueue getQueue() {
        if (queue == null) {
            throw new IllegalStateException("It seems you forgot to call init method!");
        }
        return checkNotNull(queue);
    }

    @NonNull
    private Request createVolleyRequest(@NonNull final PasteRequest request) {
        final String url = buildRequestUrl(request);
        final byte[] body = buildRequestBody(request);
        final Map<String, String> headers = request.getHeaders();
        final PasteResultListener listener = request.getListener();
        return new GenericRequest(
                url, body, headers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        handleRequestResponse(request, response, listener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        handleRequestError(error, listener);
                    }
                }
        );
    }

    private void handleRequestResponse(@NonNull final PasteRequest request,
            @Nullable final String response,
            @Nullable final PasteResultListener listener) {
        final PasteResult result = parsePasteResult(request, response);
        if (result.isSuccess()) {
            final String accessLink = result.getAccessLink();
            Log.i("CopyPasteLog", "Content successfully pasted! Access link: " + accessLink);
            if (listener != null) {
                listener.onSuccess(accessLink);
            }
        } else {
            final Throwable throwable = result.getThrowable();
            handleRequestError(throwable, listener);
        }
    }

    private void handleRequestError(@Nullable final Throwable error,
            @Nullable final PasteResultListener listener) {
        Log.e("CopyPasteLog", "Failed to paste content! Error: " + error);
        if (listener != null) {
            listener.onFailure(error);
        }
    }

    @NonNull
    private String buildRequestUrl(@NonNull final PasteRequest request) {
        final String baseUrl = request.getUrl();
        final ParamsFormatter formatter = request.getParamsFormatter();
        if (formatter != null) {
            final String params = formatter.formatParams(request.getContent());
            if (!params.isEmpty()) {
                if (baseUrl.endsWith("/")) {
                    return baseUrl.concat(params);
                } else {
                    return baseUrl.concat("/").concat(params);
                }
            }
        }
        return baseUrl;
    }

    @Nullable
    private byte[] buildRequestBody(@NonNull final PasteRequest request) {
        final String content = request.getContent();
        final PayloadFormatter formatter = request.getPayloadFormatter();
        if (formatter != null) {
            return formatter.formatPayload(content);
        }
        return content.getBytes();
    }

    @NonNull
    private PasteResult parsePasteResult(@NonNull final PasteRequest request,
            @Nullable final String response) {
        final PasteResultParser parser = request.getPasteResultParser();
        if (parser != null) {
            return parser.parseResult(request, response);
        }
        return PasteResult.success(request.getUrl());
    }

    private static class GenericRequest extends StringRequest {

        @NonNull
        private final Map<String, String> headers;

        @Nullable
        private final byte[] body;

        GenericRequest(@NonNull final String url,
                @Nullable final byte[] body,
                @NonNull final Map<String, String> headers,
                @NonNull final Response.Listener<String> listener,
                @NonNull final Response.ErrorListener errorListener) {
            super(Request.Method.POST, url, listener, errorListener);
            setRetryPolicy(new DefaultRetryPolicy());
            this.headers = new HashMap<>(checkNotNull(headers));
            this.body = body;
        }

        @Override
        @NonNull
        public Map<String, String> getHeaders() {
            return headers;
        }

        @Override
        public byte[] getBody() throws AuthFailureError {
            return body;
        }
    }

}
