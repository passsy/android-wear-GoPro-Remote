package com.pascalwelsch.goprowearremote.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author Pascal Welsch
 * @since 25.06.14.
 */
public class GoProRequest extends Request {

    private static final String TAG = GoProRequest.class.getSimpleName();

    public GoProRequest(final String password, final String type, final String code,
            final boolean isBacpacRequest,
            final Response.ErrorListener error) {
        super(Method.GET, buildUrl(password, type, code, isBacpacRequest), error);
        Log.v(TAG, "url: " + getUrl());
        setShouldCache(false);
    }

    @Override
    protected void deliverResponse(final Object data) {
        // not handled
    }

    @Override
    protected Response parseNetworkResponse(final NetworkResponse networkResponse) {
        return Response.success("success", null);
    }

    @Override
    public int compareTo(final Object another) {
        return 0;
    }

    private static String buildUrl(final String password, final String method,
            final String param, final boolean bacpacRequest) {

        String path = "camera";
        if (bacpacRequest) {
            path = "bacpac";
        }

        String url = "http://10.5.5.9/" + path + "/" + method + "?t=" + password;

        if (!TextUtils.isEmpty(param)) {
            url += "&p=%" + param;
        }
        return url;
    }
}
