package com.goldkugel.signalreceiver;

import com.goldkugel.signal.Signal;

import lombok.NonNull;

/**
 * 
 * @author Peter Pallaoro
 *
 */
public interface SignalHandler {

  /**
   * 
   * @param s
   * @param handlerIndex
   * @param emitTime
   * @param objs
   */
  public void handle(@NonNull Signal s, @NonNull Integer handlerIndex,
      @NonNull Long emitTime, Object[] objs);

}
