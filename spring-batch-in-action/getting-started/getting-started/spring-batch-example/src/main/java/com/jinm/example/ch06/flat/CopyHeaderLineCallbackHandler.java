package com.jinm.example.ch06.flat;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.LineCallbackHandler;

/**
 * 
 *
 * 2013-4-3下午08:01:45
 */
public class CopyHeaderLineCallbackHandler implements LineCallbackHandler,
		FlatFileHeaderCallback {
	private String header = "";

	@Override
    public void handleLine(String line) {
		this.header = line;
	}

	@Override
    public void writeHeader(Writer writer) throws IOException {
		writer.write(header);
	}
}
