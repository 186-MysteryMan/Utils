package com.puer.brand.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author shenggongjie
 * @date 2021/1/19 10:14
 */
@Slf4j
public class CloseStreamUtil {
    public static void close(Closeable bgIs, Closeable shareIs, Closeable headIs, HttpURLConnection httpUrl,String message){
        try {
            if (bgIs != null) {
                bgIs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(message, e);
        } finally {
            try {
                if (shareIs != null) {
                    shareIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error(message, e);
            } finally {
                try {
                    if (headIs != null) {
                        headIs.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(message, e);
                }finally {
                    if (httpUrl!=null) {
                        httpUrl.disconnect();
                    }
                }
            }
        }
    }
    public static void close(Closeable bgIs, Closeable shareIs, Closeable headIs,String message){
        close(bgIs,shareIs,headIs,null,message);
    }
    public static void close(Closeable bgIs, Closeable shareIs,String message){
        close(bgIs,shareIs,null,null,message);
    }
    public static void close(Closeable bgIs,String message){
        close(bgIs,null,null,null,message);
    }
}
