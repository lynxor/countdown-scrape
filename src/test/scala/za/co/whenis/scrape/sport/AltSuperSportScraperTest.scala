package za.co.whenis.scrape.sport

import org.specs2.mutable.Specification
import java.io.InputStream

/**
 * User: dawidmalan
 * Date: 2011/12/05
 * Time: 3:51 PM
 */

class AltSuperSportScraperTest extends Specification {
  val cricketStream: InputStream = getClass.getResourceAsStream("supersport_cricket.html")
  val rugbyStream: InputStream = getClass.getResourceAsStream("supersport_rugby.html")
  val springbokStream =  getClass.getResourceAsStream("supersport_springboks.html")

  "The scraper" should {
    "scrape some dates for me from the cricket date page" in {
      val results = new AltSuperSportScraper().getFrom(cricketStream)
      results.length mustEqual 28
    }
    "scrape some dates for me from the super rugby date page" in {
      val results = new AltSuperSportScraper().getFrom(rugbyStream)
      results.length mustEqual 120
    }
    "scrape some dates for me from the springboks date page" in {
      val results = new AltSuperSportScraper().getFrom(springbokStream)
      results.length mustEqual 12
    }
  }
}