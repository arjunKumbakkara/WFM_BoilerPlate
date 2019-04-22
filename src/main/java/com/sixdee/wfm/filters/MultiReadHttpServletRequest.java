package com.sixdee.wfm.filters;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
	  private ByteArrayOutputStream cachedBytes;
	  public MultiReadHttpServletRequest(HttpServletRequest request) {
	    super(request);
	  }
	  @Override
	  public ServletInputStream getInputStream() throws IOException {
	    if (cachedBytes == null)
	      cacheInputStream();
	      return new CachedServletInputStream();
	  }
	  @Override
	  public BufferedReader getReader() throws IOException{
	    return new BufferedReader(new InputStreamReader(getInputStream()));
	  }
	  private void cacheInputStream() throws IOException {
	    /* The inputstream is cached for multi reads.
	     */
	    cachedBytes = new ByteArrayOutputStream();
	    IOUtils.copy(super.getInputStream(), cachedBytes);
	  }
	  /* An inputstream which reads the request body that we cached.*/
	  public class CachedServletInputStream extends ServletInputStream {
	    private ByteArrayInputStream input;

	    public CachedServletInputStream() {
	      /* create a new input stream from the cached request body */
	      input = new ByteArrayInputStream(cachedBytes.toByteArray());
	    }

	    @Override
	    public int read() throws IOException {
	      return input.read();
	    }

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener arg0) {
		}
	  }
	}