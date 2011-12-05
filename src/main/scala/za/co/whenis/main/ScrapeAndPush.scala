package za.co.whenis.main

import za.co.whenis.agent.PushAgent
import org.joda.time.DateTime

import za.co.whenis.scrape.Scraper
import za.co.whenis.scrape.movie.TeaserTrailerScrape
import za.co.whenis.scrape.game.BtGamesScrape
import za.co.whenis.scrape.sport.{SuperSportScraper, AltSuperSportScraper}

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 3:39 PM
 */

object ScrapeAndPush extends App {

  val scrapers: List[Scraper] = List(
    BtGamesScrape,
    new TeaserTrailerScrape(new DateTime),
    new AltSuperSportScraper,
    new SuperSportScraper)

  val pushAgent = try {
    new PushAgent(args(0), args(1).toInt)
  } catch {
    case e: Exception => {
      println("You did not specify a host and port - trying localhost:55555")
      new PushAgent("localhost", 55555)
    }
  }

  scrapers.flatMap(_.getOnline) foreach (pushAgent.pushEvents)
}