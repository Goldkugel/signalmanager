package com.goldkugel.signalmanager;

import java.util.HashMap;

import com.goldkugel.signal.Signal;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Peter Pallaoro
 *
 */
@Log4j2
public class SignalManager {

  /*
   * 
   */
  protected static final HashMap<String, Signal> signal =
      new HashMap<String, Signal>();

  /**
   * 
   * @param signalName
   * @return
   */
  public synchronized static Signal getSignal(String signalName) {
    return SignalManager.signal.get(signalName);
  }

  /**
   * 
   * @param signalName
   * @param s
   * @return
   */
  public synchronized static Integer addSignal(String signalName, Signal s) {
    Integer ret = SignalManager.signal.size();

    if (SignalManager.signal.get(signalName) == null) {
      SignalManager.signal.put(signalName, s);
      ret++;
    } else {
      log.warn("Signal \"" + signalName + "\" already added. "
          + "Can not add a second signal with the same name.");
    }

    return ret;
  }

  /**
   * 
   * @param signalName
   * @return
   */
  public synchronized static Integer removeSignal(String signalName) {
    Integer ret = SignalManager.signal.size();

    if (SignalManager.signal.remove(signalName) != null) {
      ret--;
    }

    return ret;
  }

  /**
   * 
   * @return
   */
  public synchronized static String[] getSignalNames() {
    return (String[]) SignalManager.signal.keySet().toArray();
  }

}
