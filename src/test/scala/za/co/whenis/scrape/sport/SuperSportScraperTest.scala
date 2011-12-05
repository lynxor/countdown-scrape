package za.co.whenis.scrape.sport

import org.specs2.mutable._
import java.io.InputStream

import org.joda.time.LocalDate


/**
 * User: dawidmalan
 * Date: 2011/12/05
 * Time: 1:39 PM
 */

class SuperSportScraperTest extends Specification {
  val xmlStream: InputStream = getClass.getResourceAsStream("supersport_soccer.html")
  val results = new SuperSportScraper().getFrom(xmlStream)

  "The scraper" should {
    "scrape some dates for me from the super sport date page" in {
      results.length mustEqual 31
    }
  }
}