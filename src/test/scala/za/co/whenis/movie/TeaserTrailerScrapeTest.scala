package za.co.whenis.movie

import org.specs2.mutable._
import java.io.InputStream
import org.joda.time.{LocalDate, DateTime}

/**
 * User: dawidmalan
 * Date: 2011/12/02
 * Time: 2:22 PM
 */

class TeaserTrailerScrapeTest extends Specification {

  val xmlStream: InputStream = getClass.getResourceAsStream("teaser-trailer.html")
  val results = new TeaserTrailerScrape(new LocalDate(2011,12,2).toDateTimeAtStartOfDay).getFrom(xmlStream)

  "The scraper" should {
    "scrape some dates for me from the teaser trailer release date page" in {
        results.length mustEqual 12
    }
  }
}