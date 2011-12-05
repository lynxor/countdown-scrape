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

class AltSuperSportScraper extends Scraper {
  val urls = List("http://www.supersport.com/cricket/sa-team/fixtures?print=true",
    "http://www.supersport.com/rugby/springboks/fixtures?print=true",
    "http://www.supersport.com/rugby/super-rugby/fixtures?print=true",
    "http://www.supersport.com/rugby/vodacom-cup/fixtures?print=true",
    "http://www.supersport.com/rugby/six-nations/fixtures?print=true")

  val yearMonth = DateTimeFormat.forPattern("MMMM yyyy")
  val hourMinuteFormat = DateTimeFormat.forPattern("kk:mm")

  def getOnline = {
    urls.map((url: String) => {
      val source = new InputSource(url)
      parseDates(adapter.loadXML(source, parser))
    }).flatten
  }

  def getFrom(is: InputStream) = {
    val source = new InputSource(is)
    parseDates(adapter.loadXML(source, parser))
  }

  def parseDates(node: Node): Seq[ParsedEvent] = {
    val fixturesTables = node \\ "div" \ "table"

    val items = fixturesTables.map {
      case n: Node => {
        val trs = n \ "tr"
        val parsed = parseDate(trs.headOption) map ((date: DateTime) => parseEvents(trs.drop(1), date))
        parsed.seq.flatten
      }
      case _ => Nil
    }
    items.flatten.toSeq
  }

  def parseDate(dateTr: Option[Node]): Option[DateTime] = {
    dateTr match {
      case Some(n: Node) => {
        val dateTd = n \ "td"
        dateTd.map((dateString: Node) => yearMonth.parseDateTime(dateString.text)).headOption
      }
      case _ => None
    }
  }

  def parseEvents(eventTrs: NodeSeq, date: DateTime): Seq[ParsedEvent] = {
    eventTrs map {
      case (n: Node) if ((n \ "td").length == 7) => {
        val tds = n \ "td"
        val eventName = tds(1).text + " vs " + tds(3).text + " at " + tds(5).text
        Some(ParsedEvent(eventName,
          date.withDayOfMonth(tds(0).text.toInt),
          List("sport", "cricket")))
      }
      case (n: Node) if ((n \ "td").length == 6) => {
        val tds = n \ "td"
        val eventName = tds(1).text + " vs " + tds(3).text + " at " + tds(4).text

        val hm = try {
          hourMinuteFormat.parseDateTime(tds(5).text)
        } catch {
          case e: Exception => new DateTime
        }

        Some(ParsedEvent(eventName,
          date.withDayOfMonth(tds(0).text.toInt)
            .withHourOfDay(hm.getHourOfDay)
            .withMinuteOfHour(hm.getMinuteOfHour),
          List("sport", "rugby")))
      }
      case _ => None
    } flatten

  }


}