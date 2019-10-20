package com.myf.qq.service;

import java.io.InputStream;

public interface WavePlayerService
{
	void play(InputStream source);
	void stop();
	byte[] getSamples();
}
