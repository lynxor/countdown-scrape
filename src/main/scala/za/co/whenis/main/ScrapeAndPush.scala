package za.co.whenis.main

import co.za.whenis.game.BtGamesScrape
import za.co.whenis.agent.PushAgent

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 3:39 PM
 */

object ScrapeAndPush extends App {

  val pushAgent = try {
    new PushAgent(args(0), args(1).toInt)
  } catch {
    case e: Exception => {
      println("You did not specify a host and port - trying localhost:55555")
      new PushAgent("localhost", 55555)
    }
  }

  BtGamesScrape.getOnline.foreach(pushAgent.pushEvents)
}