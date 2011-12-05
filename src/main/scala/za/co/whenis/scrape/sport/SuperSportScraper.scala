package za.co.whenis.scrape.sport

import java.io.InputStream
import org.xml.sax.InputSource
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import za.co.whenis.scrape.{ParsedEvent, Scraper}
import xml._


/**
 * User: dawidmalan
 * Date: 2011/12/05
 * Time: 10:45 AM
 */

class SuperSportScraper extends Scraper {
  val urls = List("http://www.supersport.com/football/international/fixtures?print=true")
  val dateFormat = DateTimeFormat.forPattern("EE dd MMM yyyy")
  val hourMinuteFormat = DateTimeFormat.forPattern("kk:mm")

  def getOnline = {
    urls.map( (s:String) => {
    val source = new InputSource(s)
        parseDates(adapter.loadXML(source, parser))
    }).flatten
  }

  def getFrom(is: InputStream) = {
    val source = new InputSource(is)
    parseDates(adapter.loadXML(source, parser))
  }

  def parseDates(node: Node): Seq[ParsedEvent] = {
    val fixturesTables = node \\ "div" \ "table"

    val items = fixturesTables.grouped(2).map {
      case n: NodeSeq if (n.length == 2) => {
        val parsed = parseDate(n(0)) map ((date: DateTime) => parseEvent((n(1)), date))
        parsed.seq.flatten
      }
      case x: NodeSeq => Nil
    }
    items.flatten.toSeq
  }

  def parseDate(dateTable: Node): Option[DateTime] = {
    (dateTable \\ "td").headOption.map((n: Node) => dateFormat.parseDateTime(n.text))
  }

  def parseEvent(eventTable: Node, date: DateTime): Seq[ParsedEvent] = {
    val events = eventTable \ "tr" map {
      case (n: Node) => {
        val tds = n \ "td"
        val eventName = tds(0).text + " vs " + tds(2).text + " at " + tds(3).text
        val hm = hourMinuteFormat.parseDateTime(tds(4).text)

        Some(ParsedEvent(eventName,
          date.withHourOfDay(hm.getHourOfDay).withMinuteOfHour(hm.getMinuteOfHour),
          List("sport", "soccer", "football")))
      }
      case _ => None
    }
    events.flatten
  }


}