package za.co.whenis

import org.joda.time.DateTime

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 2:39 PM
 */

case class ParsedEvent(name:String, eventDate:DateTime, tags:List[String]){
  def toParams = {
    Map(
      "label" -> name,
      "eventDate" -> eventDate.getMillis.toString,
      "tags" -> tags.mkString (",")
    )
  }
}

