package com.lixin.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public final class RequestUtils {
    private RequestUtils() {
    }

    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            return index != -1 ? ip.substring(0, index) : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            if (StrUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            } else {
                ip = request.getHeader("Proxy-Client-IP");
                if (StrUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                    return ip;
                } else {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                    return StrUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip) ? ip : request.getRemoteAddr();
                }
            }
        }
    }

    public static String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        } else {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Cookie cookie = var3[var5];
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }

            return null;
        }
    }

    public static Map<String, String> getStorage(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        String storage = request.getHeader("Set-Storage");
        if (!StringUtils.isEmpty(storage)) {
            String[] ss = storage.split(";\\s*");

            for(int i = 0; i < ss.length; ++i) {
                String kv = ss[i];
                int index = kv.indexOf("=");
                if (index != -1) {
                    String name = kv.substring(0, index);
                    String value = kv.substring(index + 1, kv.length());
                    map.put(name, value);
                }
            }
        }

        return map;
    }

    public static String getDomain(HttpServletRequest request) {
        String serverName = request.getServerName();
        int port = request.getServerPort();
        if (isHttps(request)) {
            serverName = "https://" + serverName;
            if (port != 443) {
                serverName = serverName + ":" + port;
            }
        } else {
            serverName = "http://" + serverName;
            if (port != 80) {
                serverName = serverName + ":" + port;
            }
        }

        return serverName;
    }

    public static boolean isHttps(HttpServletRequest request) {
        return "true".equals(request.getHeader("isHttps"));
    }

    public static String getContextPath(HttpServletRequest request) {
        String contextPath = (String)request.getAttribute("javax.servlet.include.context_path");
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }

        contextPath = normalize(decodeRequestString(request, contextPath), true);
        if ("/".equals(contextPath)) {
            contextPath = "";
        }

        return contextPath;
    }

    public static String getRelativePath(HttpServletRequest request) {
        String var10000;
        String result;
        if (null != request.getAttribute("javax.servlet.include.request_uri")) {
            result = (String)request.getAttribute("javax.servlet.include.path_info");
            if (null != result) {
                result = (String)request.getAttribute("javax.servlet.include.servlet_path");
            } else {
                var10000 = (String)request.getAttribute("javax.servlet.include.servlet_path");
                result = var10000 + result;
            }

            if (StringUtils.isEmpty(result)) {
                result = "/";
            }

            return result;
        } else {
            result = request.getPathInfo();
            if (result == null) {
                result = request.getServletPath();
            } else {
                var10000 = request.getServletPath();
                result = var10000 + result;
            }

            if (result == null || result.equals("")) {
                result = "/";
            }

            return result;
        }
    }

    public static String getRequestUri(HttpServletRequest request) {
        String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
        if (uri == null) {
            uri = request.getRequestURI();
        }

        return normalize(decodeAndCleanUriString(request, uri), true);
    }

    public static String getPathWithinApplication(HttpServletRequest request) {
        String contextPath = getContextPath(request);
        String requestUri = getRequestUri(request);
        if (startsWithIgnoreCase(requestUri, contextPath)) {
            String path = requestUri.substring(contextPath.length());
            return hasText(path) ? path : "/";
        } else {
            return requestUri;
        }
    }

    public static String decodeRequestString(HttpServletRequest request, String source) {
        String enc = determineEncoding(request);

        try {
            return URLDecoder.decode(source, enc);
        } catch (UnsupportedEncodingException var4) {
            return URLDecoder.decode(source);
        }
    }



    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasLength(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str != null && prefix != null) {
            if (str.startsWith(prefix)) {
                return true;
            } else if (str.length() < prefix.length()) {
                return false;
            } else {
                String lcStr = str.substring(0, prefix.length()).toLowerCase();
                String lcPrefix = prefix.toLowerCase();
                return lcStr.equals(lcPrefix);
            }
        } else {
            return false;
        }
    }

    private static String determineEncoding(HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = "ISO-8859-1";
        }

        return enc;
    }

    private static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
        uri = decodeRequestString(request, uri);
        int semicolonIndex = uri.indexOf(59);
        return semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri;
    }

    private static String normalize(String path, boolean replaceBackSlash) {
        if (path == null) {
            return null;
        } else {
            String normalized = path;
            if (replaceBackSlash && path.indexOf(92) >= 0) {
                normalized = path.replace('\\', '/');
            }

            if (normalized.equals("/.")) {
                return "/";
            } else {
                if (!normalized.startsWith("/")) {
                    normalized = "/" + normalized;
                }

                while(true) {
                    int index = normalized.indexOf("//");
                    String var10000;
                    if (index < 0) {
                        while(true) {
                            index = normalized.indexOf("/./");
                            if (index < 0) {
                                while(true) {
                                    index = normalized.indexOf("/../");
                                    if (index < 0) {
                                        return normalized;
                                    }

                                    if (index == 0) {
                                        return null;
                                    }

                                    int index2 = normalized.lastIndexOf(47, index - 1);
                                    var10000 = normalized.substring(0, index2);
                                    normalized = var10000 + normalized.substring(index + 3);
                                }
                            }

                            var10000 = normalized.substring(0, index);
                            normalized = var10000 + normalized.substring(index + 2);
                        }
                    }

                    var10000 = normalized.substring(0, index);
                    normalized = var10000 + normalized.substring(index + 1);
                }
            }
        }
    }
}
