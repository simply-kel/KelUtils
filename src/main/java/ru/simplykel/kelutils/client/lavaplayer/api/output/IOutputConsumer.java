package ru.simplykel.kelutils.client.lavaplayer.api.output;

@FunctionalInterface
public interface IOutputConsumer {
	
	void accept(byte[] buffer, int chunkSize);
	
}
