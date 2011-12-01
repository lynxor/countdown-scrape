package za.co.whenis.game

import co.za.whenis.game.BtGamesScrape
import org.specs2.mutable._
import java.io.InputStream

/**
 * User: dawidmalan
 * Date: 2011/11/29
 * Time: 1:29 PM
 */

class GamesWebScrapeTest extends Specification {
  "The scraper" should {
    "scrape some dates for me from the bt games release date page" in {
      val xmlStream:InputStream = getClass.getResourceAsStream("bt_release_dates.html")
      val results = BtGamesScrape.getDateItems(xmlStream)
      println(results)
      results.length mustEqual 20
    }
  }
}