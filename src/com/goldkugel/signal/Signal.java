package com.goldkugel.signal;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.goldkugel.signalreceiver.SignalHandler;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Peter Pallaoro
 *
 */
@Log4j2
public class Signal implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7877447538280354453L;

  /**
   * 
   */
  @Getter
  protected final String name;

  /**
   * 
   */
  @Getter
  protected final Long id;

  /**
   * 
   */
  private static final Long[] nextId = new Long[] {1L};

  /**
   * 
   */
  protected final HashMap<Integer, SignalHandler> handlers =
      new HashMap<Integer, SignalHandler>();

  /**
   * 
   */
  public Signal(@NonNull String name) {
    this.name = name;
    synchronized (Signal.nextId) {
      this.id = Signal.nextId[0];
      Signal.nextId[0]++;
    }
  }

  /**
   * 
   * @param obj
   * @return
   */
  public synchronized Integer emit(Object[] obj) {
    Integer ret = 0;
    Long time = new GregorianCalendar().getTimeInMillis();

    Integer[] indexes = (Integer[]) this.handlers.keySet().toArray();
    Arrays.sort(indexes);

    for (int i = 0; i < indexes.length; i++) {
      this.handlers.get(indexes[i]).handle(this, indexes[i], time, obj);
      ret++;
    }

    return ret;
  }

  /**
   * 
   * @param handlerIndex
   * @return
   */
  public SignalHandler getHandler(@NonNull Integer handlerIndex) {
    return this.handlers.get(handlerIndex);
  }

  /**
   * 
   * @param hander
   * @param handlerIndex
   * @return
   */
  public Integer addHandler(@NonNull SignalHandler hander,
      @NonNull Integer handlerIndex) {
    Integer ret = this.handlers.size();

    if (handlerIndex < 0) {
      int index = Collections.max(this.handlers.keySet());
      this.handlers.put(index, hander);
      ret++;
    } else {
      if (this.handlers.get(handlerIndex) == null) {
        log.warn("Index " + handlerIndex
            + " already in use. Please choose another one. "
            + "Handler has not been added.");
      } else {
        this.handlers.put(handlerIndex, hander);
        ret++;
      }
    }

    return ret;
  }

  /**
   * 
   * @param handler
   * @return
   */
  public Integer removeHandler(@NonNull Integer handlerIndex) {
    Integer ret = this.handlers.size();

    if (this.handlers.remove(handlerIndex) != null) {
      ret--;
    }

    return ret;
  }
}
