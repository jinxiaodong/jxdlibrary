package com.xiaodong.library.net.Interceptor;


import com.xiaodong.library.helper.ThreadPoolManager;
import com.xiaodong.library.utils.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * Created by Wisn on 2018/4/2 下午6:43.
 */

public class HttpLoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private volatile Level printLevel = Level.NONE;
    private java.util.logging.Level colorLevel;
    private Logger logger;

    public enum Level {
        NONE,       //不打印log
        BASIC,      //只打印 请求首行 和 响应首行
        HEADERS,    //打印请求和响应的所有 Header
        BODY        //所有数据全部打印
    }

    public HttpLoggingInterceptor(String tag) {
        logger = Logger.getLogger(tag);
    }

    public void setPrintLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        printLevel = level;
    }

    public void setColorLevel(java.util.logging.Level level) {
        colorLevel = level;
    }

    private void log(String message) {
        logger.log(colorLevel, message);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (printLevel == Level.NONE) {
            return chain.proceed(request);
        }
        //请求日志拦截
        logForRequest(request, chain.connection());
        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        //响应日志拦截
        return logForResponse(response, tookMs);
    }

    private void logForRequest(Request request, Connection connection) throws IOException {
        ArrayList<String> logArrays = new ArrayList<>();
        boolean logBody = (printLevel == Level.BODY);
        boolean logHeaders = (printLevel == Level.BODY || printLevel == Level.HEADERS);
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        try {
            String requestStartMessage = "-->Request " + request.method() + ' ' + request.url() + ' ' + protocol;
            logArrays.add(requestStartMessage);

            if (logHeaders) {
                if (hasRequestBody) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    if (requestBody.contentType() != null) {
                        logArrays.add("\tContent-Type: " + requestBody.contentType());
                    }
                    if (requestBody.contentLength() != -1) {
                        logArrays.add("\tContent-Length: " + requestBody.contentLength());
                    }
                }
                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        logArrays.add("\t" + name + ": " + headers.value(i));
                    }
                }

                if (logBody && hasRequestBody) {
                    logArrays.add("\t" + "Content-type: " + requestBody.contentType());
                    if (isPlaintext(requestBody.contentType())) {
                        try {
                            Request copy = request.newBuilder().build();
                            RequestBody body = copy.body();
                            if (body == null) return;
                            Buffer buffer = new Buffer();
                            body.writeTo(buffer);
                            MediaType mediaType = body.contentType();
                            Charset charset = getCharset(mediaType);
                            logToLongString(buffer.readString(charset), logArrays);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        logArrays.add("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logArrays.add("--> Request END " + request.method());
            logArrays.add(" ");
            printLog(logArrays);
        }
    }

    private Response logForResponse(Response response, long tookMs) {
        ArrayList<String> logArrays = new ArrayList<>();
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        boolean logBody = (printLevel == Level.BODY);
        boolean logHeaders = (printLevel == Level.BODY || printLevel == Level.HEADERS);
        try {
            logArrays.add("<--Response " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
            if (logHeaders) {
                Headers headers = clone.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    logArrays.add("\t" + headers.name(i) + ": " + headers.value(i));
                }
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (responseBody == null) return response;
                    if (isPlaintext(responseBody.contentType())) {
                        byte[] bytes = IOUtils.toByteArray(responseBody.byteStream());
                        String body = null;
                        if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                            body = getGzipString(bytes);
                        } else {
                            MediaType contentType = responseBody.contentType();
                            body = new String(bytes, getCharset(contentType));
                        }
                        logToLongString(body, logArrays);
                        responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                    } else {
                        logArrays.add("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
            return response.newBuilder().body(responseBody).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logArrays.add("<-- Response  END HTTP");
            logArrays.add(" ");
            printLog(logArrays);
        }
        return response;
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return true;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }

    /**
     * 超过限制不能输出完整，需要分行输出
     *
     * @param log
     * @param logArray
     */
    public void logToLongString(String log, ArrayList<String> logArray) {
        if (log == null) return;
        if (log.length() > 3900) {
            int i = 0;
            for (; i < log.length() / 3900; i++) {
                if (i == 0) {
                    logArray.add("\tbody:" + log.substring(i * 3900, (i + 1) * 3900));
                } else {
                    logArray.add(log.substring(i * 3900, (i + 1) * 3900));
                }
            }
            logArray.add(log.substring(i * 3900, log.length()));
        } else {
            logArray.add("\tbody:" + log);
        }
    }

    private String getGzipString(byte[] bytes) {
        try {
            GZIPInputStream gzip = new GZIPInputStream(
                    new ByteArrayInputStream(bytes));
            InputStreamReader isr = new InputStreamReader(gzip);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
                sb.append("\r\n");
            }
            isr.close();
            gzip.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void printLog(ArrayList<String> logs) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    for (String log : logs) {
                        log(log);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, true);
    }
}