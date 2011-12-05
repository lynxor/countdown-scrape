package za.co.whenis.scrape.game

import org.specs2.mutable._
import java.io.InputStream
import org.joda.time.{LocalDate, DateTime}

/**
 * User: dawidmalan
 * Date: 2011/11/29
 * Time: 1:29 PM
 */

class BtGamesScrapeTest extends Specification {
  val xmlStream:InputStream = getClass.getResourceAsStream("bt_release_dates.html")
  val results = BtGamesScrape.getFrom(xmlStream)

  "The scraper" should {
    "scrape some dates for me from the bt games release date page" in {
      results.length mustEqual 20
    }
    "Assassin's Creed Revelations' event date has to be parsed correctly" in  {
      results.find( _.name == "Assassin's Creed Revelations" ).get.eventDate mustEqual new LocalDate(2011,12,02).toDateTimeAtStartOfDay
    }
  }
}