/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.lib;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class HttpHelper {

	/**
	 * Sends a GET request.
	 *
	 * @param url	the request url
	 * @return		the response
	 * @throws Exception	any exceptions
	 */
	public static String requestGet(String url) throws Exception {
		return requestGet(url, null, null);
	}

	/**
	 * Sends a GET request.
	 *
	 * @param url	the request url
	 * @param connection	an instance of URLConnection
	 * @return	the response
	 * @throws Exception	any exceptions
	 */
	public static String requestGet(String url, URLConnection connection) throws Exception {
		return requestGet(url, connection, "utf-8");
	}

	/**
	 * Sends a GET request.
	 *
	 * @param url	the request url
	 * @param connection	an instance of URLConnection
	 * @param charset	the request charset
	 * @return	the response
	 * @throws Exception	any exceptions
	 */
	public static String requestGet(String url, URLConnection connection, String charset) throws Exception {

		if (connection == null) {
			connection = openConnection(url);
		}
		
		if (charset == null || charset == "") {
			charset = "utf-8";
		}
		
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", charset);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}

		return resultBuffer.toString();
	}

	/**
	 * Sends a Post request.
	 *
	 * @param url	the request url
	 * @param parameterMap	the post data typed {@literal (Map<?, ?>)}
	 * @return	the response
	 * @throws Exception	any exceptions
	 */
	public static String requestPost(String url, Map<?, ?> parameterMap) throws Exception {
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator<?> iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}

				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}

		URLConnection connection = openConnection(url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			outputStreamWriter.write(parameterBuffer.toString());
			outputStreamWriter.flush();

			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}

	/**
	 * Opens a connection.
	 *
	 * @param urlString	the url
	 * @return	an instance of URLConnection
	 * @throws IOException	IOException
	 */
	public static URLConnection openConnection(String urlString) throws IOException {
		return openConnection(urlString, null, null);
	}

	/**
	 * Opens a connection.
	 *
	 * @param urlString	the url
	 * @param proxyHost	the proxy host
	 * @param proxyPort	the proxy port
	 * @return	an instance of URLConnection
	 * @throws IOException	IOException
	 */
	public static URLConnection openConnection(String urlString, String proxyHost, Integer proxyPort) throws IOException {
		URL url = new URL(urlString);
		URLConnection connection;
		if (proxyHost != null && proxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			connection = url.openConnection(proxy);
		} else {
			connection = url.openConnection();
		}
		return connection;
	}

	/**
	 * Gets root url for website.
	 *
	 * @param request	an instance of HttpServletRequest
	 * @return	the root url
	 */
	public static String getRootUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
