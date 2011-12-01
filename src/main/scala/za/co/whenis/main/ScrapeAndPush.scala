package za.co.whenis.main

import co.za.whenis.game.BtGamesScrape
import za.co.whenis.agent.PushAgent
import za.co.whenis.ParsedEvent
import org.joda.time.DateTime

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 3:39 PM
 */

object ScrapeAndPush extends App{
  BtGamesScrape.getOnline.foreach(PushAgent.pushEvents)
}