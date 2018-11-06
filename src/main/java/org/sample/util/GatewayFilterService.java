package org.sample.util;

import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class GatewayFilterService {
  public byte[] retrieveBytes(List<? extends DataBuffer> dataBuffers) {
    byte[] retrieveBytes = null;
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      dataBuffers.forEach(i -> processBuffer(i, outputStream));
      retrieveBytes = outputStream.toByteArray();
    } catch (IOException e) {
      log.error("IOException closing ByteArrayOutputStream", e);
    } catch (TaskException e) {
      log.error("TaskException processing DataBuffer", e);
    }
    return retrieveBytes;
  }

  private void processBuffer(DataBuffer dataBuffer, ByteArrayOutputStream outputStream) {
    int count;
    while ((count = dataBuffer.readableByteCount()) > 0) {
      byte[] array = new byte[count];
      dataBuffer.read(array);
      try {
        outputStream.write(array);
      } catch (IOException e) {
        throw new TaskException(e);
      }
    }
    ReferenceCountUtil.release(dataBuffer);
  }

  /** Wrapper exception to propogate checked exceptions out of stream processing */
  static class TaskException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TaskException(IOException e) {
      super(e);
    }
  }
}
