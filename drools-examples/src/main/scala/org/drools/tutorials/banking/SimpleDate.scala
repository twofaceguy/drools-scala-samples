package org.drools.tutorials.banking

import java.text.{ParseException, SimpleDateFormat}
import java.util.Date

/**
 * Created by Simon on 2014/8/28
 */

object SimpleDate {
  private final val format: SimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy")

  def apply(datestr: String) = {
    val d = new Date
    try {
      d.setTime(format.parse(datestr).getTime)
      d
    }
    catch {
      case e: ParseException =>
        throw new IllegalArgumentException("Could not parse date (" + datestr + ").", e)
    }
  }
}
