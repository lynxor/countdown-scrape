package za.co.whenis.scrape.movie

import org.joda.time.format.DateTimeFormat
import org.xml.sax.InputSource
import java.io.InputStream
import xml.Node
import scalaz._
import Scalaz._
import org.joda.time.DateTime
import za.co.whenis.scrape.{ParsedEvent, Scraper}

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 2:47 PM
 */

class TeaserTrailerScrape(minDate: DateTime) extends Scraper {

  val dateFormat = DateTimeFormat.forPattern("dd MMM yyyy")
  val dateRegex = """\w{3}\s\d{2}\,\s\w{3}\s\d{4}""".r

  def getOnline = {
    val base = "http://teaser-trailer.com/"
    val currentYear = new DateTime().year.get

    (0 to 2).flatMap {
      (i: Int) => {
        val year = currentYear + i
        val source = new InputSource(base + "movies-" + (currentYear + i) + ".html")
        parseDates(adapter.loadXML(source, parser))
      }
    }
  }

  def getFrom(is: InputStream) = {
    val source = new InputSource(is)
    parseDates(adapter.loadXML(source, parser))
  }

  def parseDates(node: Node) = {
    val usableTables = (node \\ "table").filter((n: Node) => (n \ "tr").length === 1)
    usableTables.map(parseTable).flatten
  }

  def parseTable(tableNode: Node): Option[ParsedEvent] = {
    val divs = (tableNode \\ "div")
    if (divs.length === 2) parseDiv(divs(1)) else None
  }

  def parseDiv(infoDiv: Node) = {
    ((infoDiv \\ "a").headOption, dateRegex.findAllIn(infoDiv.toString).toList) match {
      case (Some(anchor: Node), List(dateString: String)) => {
        val cleanedString = dateString.split("""[\,\s]""").drop(1).filter(_ != "").mkString(" ")
        val date = dateFormat.parseDateTime(cleanedString)
        if (date.compareTo(minDate) >= 0)
          Some(ParsedEvent(anchor.text, date, List("movies")))
        else
          None
      }
      case _ => None
    }
  }
}